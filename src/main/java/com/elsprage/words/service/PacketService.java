package com.elsprage.words.service;

import com.elsprage.words.model.dto.PacketDTO;
import com.elsprage.words.model.request.PacketRequest;

import java.util.Set;

public interface PacketService {
    PacketDTO savePacket(PacketRequest packetRequest, String token);

    Set<PacketDTO> getUsersPackets(String token);

    PacketDTO getPacketById(Long packetId, String token);

    void deletePacket(Long packetId, String token);
}
