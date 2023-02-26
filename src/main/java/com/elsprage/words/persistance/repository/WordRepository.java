package com.elsprage.words.persistance.repository;

import com.elsprage.words.persistance.entity.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query("SELECT w FROM Word w WHERE w.userId = :userId and (LOWER(w.value) like %:query% or LOWER(w.translation) like %:query%)")
    List<Word> findByUserId(@Param("userId") Long userId, @Param("query") String query, Pageable pageable );

    @Query("SELECT count(w) FROM Word w WHERE w.userId = :userId and (LOWER(w.value) like %:query% or LOWER(w.translation) like %:query%)")
    BigDecimal findSizeOfListOfWords(@Param("userId") Long userId, @Param("query") String query);
}
