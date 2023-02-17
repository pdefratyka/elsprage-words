package com.elsprage.words.persistance.repository;

import com.elsprage.words.persistance.entity.Packet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PacketRepository extends JpaRepository<Packet, Long> {
    Set<Packet> findByUserId(Long userId);
}
