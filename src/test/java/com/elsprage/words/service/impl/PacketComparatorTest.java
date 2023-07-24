package com.elsprage.words.service.impl;

import com.elsprage.words.model.dto.PacketDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacketComparatorTest {

    @Test
    public void shouldComparePacketsWhenBothValuesAreNull() {
        PacketDTO p1 = PacketDTO.builder().build();
        PacketDTO p2 = PacketDTO.builder().build();

        int result = PacketComparator.comparePackets(p1, p2);
        assertEquals(0, result);
    }

    @Test
    public void shouldComparePacketsWhenNoDigits() {
        PacketDTO p1 = PacketDTO.builder().name("abc").build();
        PacketDTO p2 = PacketDTO.builder().name("bcd").build();

        int result = PacketComparator.comparePackets(p1, p2);
        assertEquals(-1, result);
    }

    @Test
    public void shouldComparePacketsWithDigits() {
        PacketDTO p1 = PacketDTO.builder().name("abc_12").build();
        PacketDTO p2 = PacketDTO.builder().name("abc_1").build();

        int result = PacketComparator.comparePackets(p1, p2);
        assertEquals(1, result);
    }
}
