package com.example.wordgame;

import com.example.wordgame.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query(value = "SELECT * FROM wordpool ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Word> findRandomWord();
}
