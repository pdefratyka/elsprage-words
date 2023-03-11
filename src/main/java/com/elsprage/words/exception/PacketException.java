package com.elsprage.words.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PacketException extends RuntimeException {

    private final HttpStatus httpStatus;

    public PacketException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static class PacketNotFound extends PacketException {
        public PacketNotFound(Long packetId) {
            super("Packet with id: " + packetId + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public static class NoAccessToPacket extends PacketException {
        public NoAccessToPacket(Long packetId, Long userId) {
            super("User with id: " + userId + " does not have permission to packet with id: " + packetId, HttpStatus.FORBIDDEN);
        }
    }
}
