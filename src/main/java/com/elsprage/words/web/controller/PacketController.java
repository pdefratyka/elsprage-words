package com.elsprage.words.web.controller;

import com.elsprage.words.model.dto.PacketDTO;
import com.elsprage.words.model.request.PacketRequest;
import com.elsprage.words.model.response.PacketResponse;
import com.elsprage.words.model.response.UsersPacketsResponse;
import com.elsprage.words.service.PacketService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/packets")
@AllArgsConstructor
@Log4j2
public class PacketController {

    private final PacketService packetService;

    @PostMapping
    public ResponseEntity<PacketResponse> savePacket(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                     @RequestBody PacketRequest packetRequest) {
        final PacketDTO packetDTO = packetService.savePacket(packetRequest, token);
        final PacketResponse packetResponse = new PacketResponse(packetDTO);
        return ResponseEntity.ok(packetResponse);
    }

    @GetMapping
    public ResponseEntity<UsersPacketsResponse> usersPackets(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        final Set<PacketDTO> packetsDTOs = packetService.getUsersPackets(token);
        final UsersPacketsResponse packets = new UsersPacketsResponse(packetsDTOs);
        return ResponseEntity.ok(packets);
    }

    @GetMapping("/{packetId}")
    public ResponseEntity<PacketDTO> getPacketById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long packetId) {
        final PacketDTO packet = packetService.getPacketById(packetId, token);
        return ResponseEntity.ok(packet);
    }

    @DeleteMapping("/{packetId}")
    public ResponseEntity<Void> removePacket(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long packetId) {
        packetService.deletePacket(packetId, token);
        return ResponseEntity.ok().build();
    }
}
