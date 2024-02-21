package com.example.wordgame;

import jakarta.persistence.*;

@Entity
@Table(name = "wordpool")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String word;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty rating;

    @Column(nullable = false)
    private String hint; // Added field for the hint

    // Default constructor
    public Word() {
    }

    // Parameterized constructor
    public Word(String word, Difficulty rating, String hint) {
        this.word = word;
        this.rating = rating;
        this.hint = hint;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Difficulty getRating() {
        return rating;
    }

    public void setRating(Difficulty rating) {
        this.rating = rating;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    // Enum for difficulty levels
    public enum Difficulty {
        Easy, Medium, Difficult
    }
}
