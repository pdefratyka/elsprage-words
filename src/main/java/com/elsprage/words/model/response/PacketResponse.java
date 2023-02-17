package com.elsprage.words.model.response;

import com.elsprage.words.model.dto.PacketDTO;
import lombok.Value;

@Value
public class PacketResponse {
    PacketDTO packet;
}
