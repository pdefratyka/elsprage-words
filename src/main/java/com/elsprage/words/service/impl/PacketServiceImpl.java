package com.elsprage.words.service.impl;

import com.elsprage.words.common.mapper.PacketMapper;
import com.elsprage.words.exception.PacketException;
import com.elsprage.words.model.dto.LanguageDTO;
import com.elsprage.words.model.dto.PacketDTO;
import com.elsprage.words.model.dto.PacketWithNumberOfWordsDTO;
import com.elsprage.words.model.request.PacketRequest;
import com.elsprage.words.persistance.entity.Packet;
import com.elsprage.words.persistance.entity.Word;
import com.elsprage.words.persistance.repository.PacketRepository;
import com.elsprage.words.persistance.repository.WordRepository;
import com.elsprage.words.service.JwtService;
import com.elsprage.words.service.PacketService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
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
        final List<PacketWithNumberOfWordsDTO> packets = findPacketDTOsWithWordIdsByUserId(userId);
        final Set<PacketDTO> packetDTOS = packetMapper.mapToPacketDTOsWithoutWordsMapping(packets);
        sortPackets(packetDTOS);
        log.info("Users packets: {}", packetDTOS);
        return packetDTOS;
    }

    public List<PacketWithNumberOfWordsDTO> findPacketDTOsWithWordIdsByUserId(Long userId) {
        List<Object[]> results = packetRepository.findByUserIdPacketWithWords(userId);
        List<PacketWithNumberOfWordsDTO> dtos = new ArrayList<>();

        for (Object[] result : results) {
            Integer id = (Integer) result[0];
            String name = (String) result[1];
            // The concatenated word IDs
            Integer valueLanguageId = (Integer) result[2];
            Integer translationLanguageId = (Integer) result[3];
            String valueLanguageSymbol = (String) result[4];
            String valueLanguageName = (String) result[5];
            String translationLanguageSymbol = (String) result[6];
            String translationLanguageName = (String) result[7];
            String wordIds = (String) result[8];
            List<Long> wordIdList = Arrays.stream(wordIds.split(";"))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            PacketWithNumberOfWordsDTO dto =
                    PacketWithNumberOfWordsDTO.builder()
                            .id(id.longValue())
                            .name(name)
                            .valueLanguageId(valueLanguageId.longValue())
                            .translationLanguageId(translationLanguageId.longValue())
                            .valueLanguage(LanguageDTO.builder()
                                    .id(valueLanguageId.longValue())
                                    .symbol(valueLanguageSymbol)
                                    .name(valueLanguageName)
                                    .build())
                            .translationLanguage(LanguageDTO.builder()
                                    .id(translationLanguageId.longValue())
                                    .symbol(translationLanguageSymbol)
                                    .name(translationLanguageName)
                                    .build())
                            .wordsIds(wordIdList)
                            .build();
            dtos.add(dto);
        }
        return dtos;
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

    private void validateIfUserHasAccessToPacket(final Packet packet, final String token) {
        final Long userId = jwtService.extractUserId(token);
        if (!packet.getUserId().equals(userId)) {
            throw new PacketException.NoAccessToPacket(packet.getId(), userId);
        }
    }

    private void sortPackets(Set<PacketDTO> packetDTOS) {
        Set<PacketDTO> sortedPackets = packetDTOS.stream()
                .sorted(PacketComparator::comparePackets)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        packetDTOS.clear();
        packetDTOS.addAll(sortedPackets);
    }
}
