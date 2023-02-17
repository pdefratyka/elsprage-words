package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.PacketMapper;
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
import java.util.Set;

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
}
