package com.elsprage.words.model.response;

import com.elsprage.words.model.dto.PacketDTO;
import lombok.Value;

import java.util.Set;

@Value
public class UsersPacketsResponse {
    Set<PacketDTO> packets;
}
