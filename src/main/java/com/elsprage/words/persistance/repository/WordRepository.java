package com.elsprage.words.persistance.repository;

import com.elsprage.words.persistance.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByUserId(Long userId);
}
