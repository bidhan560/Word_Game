package com.example.wordgame;

import com.example.wordgame.Word;
import com.example.wordgame.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordService {
    @Autowired
    private WordRepository wordRepository;

    public Optional<Word> getRandomWord() {
        return wordRepository.findRandomWord();
    }
}
