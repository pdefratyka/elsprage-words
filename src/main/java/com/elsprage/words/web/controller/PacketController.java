package com.elsprage.words.web.controller;

import com.elsprage.words.model.dto.PacketDTO;
import com.elsprage.words.model.request.PacketRequest;
import com.elsprage.words.model.response.PacketResponse;
import com.elsprage.words.model.response.UsersPacketsResponse;
import com.elsprage.words.service.PacketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/packets")
@AllArgsConstructor
@Slf4j
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
}
