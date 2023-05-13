package com.elsprage.words.persistance.repository;

import com.elsprage.words.persistance.entity.Packet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface PacketRepository extends JpaRepository<Packet, Long> {
    Set<Packet> findByUserId(Long userId);

    @Query("SELECT w.id FROM Packet p JOIN p.words w WHERE w.id IN :wordsIds")
    List<Long> findUsedWords(@Param("wordsIds") List<Long> wordsIds);
}
