package com.elsprage.words.persistance.repository;

import com.elsprage.words.persistance.entity.Packet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PacketRepository extends JpaRepository<Packet, Long> {

    Set<Packet> findByUserId(Long userId);

    @Query(value = "SELECT p.id, p.name, p.value_language_id, p.translation_language_id, " +
            "MIN(vl.symbol), MIN(vl.name), MIN(tl.symbol), MIN(tl.name), STRING_AGG(CAST(w.id AS TEXT), ';' ORDER BY w.id) " +
            "FROM elsprage_words.packets p " +
            "LEFT JOIN elsprage_words.packets_words pw ON p.id = pw.packet_id " +
            "LEFT JOIN elsprage_words.words w ON pw.word_id = w.id " +
            "LEFT JOIN elsprage_words.languages vl ON p.value_language_id = vl.id " +
            "LEFT JOIN elsprage_words.languages tl ON p.translation_language_id = tl.id " +
            "WHERE p.user_id = :userId " +
            "GROUP BY p.id", nativeQuery = true)
    List<Object[]> findByUserIdPacketWithWords(@Param("userId") Long userId);
}
