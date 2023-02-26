package com.elsprage.words.common.mapper;

import com.elsprage.words.model.dto.PacketDTO;
import com.elsprage.words.model.request.PacketRequest;
import com.elsprage.words.persistance.entity.Packet;
import com.elsprage.words.persistance.entity.Word;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public final class PacketMapper {

    private final WordMapper wordMapper;
    private final LanguageMapper languageMapper;

    public Packet mapToPacket(final PacketRequest packetRequest, final Long userId, final Set<Word> words) {
        if (packetRequest == null) {
            return null;
        }
        return Packet.builder()
                .userId(userId)
                .name(packetRequest.getName())
                .valueLanguageId(packetRequest.getValueLanguageId())
                .translationLanguageId(packetRequest.getTranslationLanguageId())
                .description(packetRequest.getDescription())
                .words(words)
                .build();
    }

    public PacketDTO mapToPacketDTO(final Packet packet) {
        if (packet == null) {
            return null;
        }
        return PacketDTO.builder()
                .description(packet.getDescription())
                .name(packet.getName())
                .valueLanguageId(packet.getValueLanguageId())
                .translationLanguageId(packet.getTranslationLanguageId())
                .id(packet.getId())
                .words(wordMapper.mapToWordsDTO(packet.getWords().stream().toList()))
                .build();
    }

    public Set<PacketDTO> mapToPacketDTOs(final Set<Packet> packets) {
        return packets.stream()
                .map(this::mapToPacketDTO)
                .collect(Collectors.toSet());
    }

    public PacketDTO mapToPacketDtoWithoutWordMapping(final Packet packet) {
        if (packet == null) {
            return null;
        }
        return PacketDTO.builder()
                .description(packet.getDescription())
                .name(packet.getName())
                .valueLanguageId(packet.getValueLanguageId())
                .translationLanguageId(packet.getTranslationLanguageId())
                .valueLanguage(languageMapper.mapToLanguageDTO(packet.getValueLanguage()))
                .translationLanguage(languageMapper.mapToLanguageDTO(packet.getTranslationLanguage()))
                .id(packet.getId())
                .wordsIds(packet.getWords().stream().map(Word::getId).toList())
                .build();
    }

    public Set<PacketDTO> mapToPacketDTOsWithoutWordsMapping(final Set<Packet> packets) {
        return packets.stream()
                .map(this::mapToPacketDtoWithoutWordMapping)
                .collect(Collectors.toSet());
    }
}
