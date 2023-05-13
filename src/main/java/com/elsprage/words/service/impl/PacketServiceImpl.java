package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.PacketMapper;
import com.elsprage.words.exception.PacketException;
import com.elsprage.words.model.dto.PacketDTO;
import com.elsprage.words.model.request.PacketRequest;
import com.elsprage.words.persistance.entity.Packet;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.PacketRepository;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.JwtService;
import com.elsprage.words.service.PacketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PacketServiceImpl implements PacketService {

    private final PacketRepository packetRepository;
    private final WordRepository wordRepository;
    private final JwtService jwtService;
    private final PacketMapper packetMapper;

    @Override
    public PacketDTO savePacket(final PacketRequest packetRequest, final String token) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Save packet: {} with userId: {}", packetRequest, userId);
        final Set<Word> words = new HashSet<>(wordRepository.findAllById(packetRequest.getWordsIds()));
        final Packet packet = packetMapper.mapToPacket(packetRequest, userId, words);
        final Packet savedPacket = packetRepository.save(packet);
        log.info("Saved packet: {}", savedPacket);
        return packetMapper.mapToPacketDTO(savedPacket);
    }

    @Override
    public Set<PacketDTO> getUsersPackets(String token) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Get packets for user with id: {}", userId);
        final Set<Packet> packets = packetRepository.findByUserId(userId);
        final Set<PacketDTO> packetDTOS = packetMapper.mapToPacketDTOsWithoutWordsMapping(packets);
        log.info("Users packets: {}", packetDTOS);
        return packetDTOS;
    }

    @Override
    public PacketDTO getPacketById(final Long packetId, final String token) {
        final Packet packet = packetRepository.findById(packetId)
                .orElseThrow(() -> new PacketException.PacketNotFound(packetId));
        validateIfUserHasAccessToPacket(packet, token);
        return packetMapper.mapToPacketDTO(packet);
    }

    @Override
    public void deletePacket(final Long packetId, final String token) {
        log.info("Delete packet with id: {}", packetId);
        final Packet packet = packetRepository.findById(packetId)
                .orElseThrow(() -> new PacketException.PacketNotFound(packetId));
        validateIfUserHasAccessToPacket(packet, token);
        packetRepository.delete(packet);
        log.info("Packet with id: {} has been deleted", packetId);
    }

    @Override
    public Map<Long, Boolean> isWordInUse(final List<Long> wordsIds) {
        log.debug("Check if words with ids: {} are in use", wordsIds);
        final List<Long> usedWords = packetRepository.findUsedWords(wordsIds);
        log.debug("Words in use: {}", usedWords);
        return wordsIds.stream().collect(Collectors.toMap(id -> id, id -> usedWords.contains(id)));
    }

    private void validateIfUserHasAccessToPacket(final Packet packet, final String token) {
        final Long userId = jwtService.extractUserId(token);
        if (!packet.getUserId().equals(userId)) {
            throw new PacketException.NoAccessToPacket(packet.getId(), userId);
        }
    }
}
