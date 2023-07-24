package com.elsprage.words.service.impl;

import com.elsprage.words.model.dto.PacketDTO;

public final class PacketComparator {
    public static int comparePackets(PacketDTO packet1, PacketDTO packet2) {
        final String p1 = packet1.getName();
        final String p2 = packet2.getName();
        if (p1 == null && p2 == null) {
            return 0;
        } else if (p1 == null) {
            return -1;
        } else if (p2 == null) {
            return 1;
        }
        int p1DigitIndex = getIndexOfDigit(p1);
        int p2DigitIndex = getIndexOfDigit(p2);
        if (p1DigitIndex >= 0 && p2DigitIndex >= 0) {
            String p1Substring = p1.substring(0, p1DigitIndex);
            String p2Substring = p2.substring(0, p2DigitIndex);
            if (p1Substring.equals(p2Substring)) {
                int p1Digits = Integer.parseInt(p1.substring(p1DigitIndex + 1));
                int p2Digits = Integer.parseInt(p2.substring(p2DigitIndex + 1));
                return Integer.compare(p1Digits, p2Digits);
            }
        }
        return p1.compareTo(p2);
    }

    private static int getIndexOfDigit(String text) {
        if (text == null) {
            return -1;
        }
        for (int i = text.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(text.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
}
