package com.example.wordgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private static final int MAX_ATTEMPTS = 5;

    @Autowired
    private WordService wordService;

    private Word currentWord;
    private int attempts;

    @GetMapping("/")
    public String startGame(Model model) {
        currentWord = wordService.getRandomWord().orElseThrow();
        attempts = MAX_ATTEMPTS; // Reset the attempts
        model.addAttribute("rating", currentWord.getRating() + " Word");
        model.addAttribute("hint", currentWord.getHint()); // Add the hint to the model
        model.addAttribute("attempts", attempts); // Add the number of attempts left to the model
        return "game"; // Ensure you have a game.html in your templates directory
    }

    @PostMapping("/guess")
    public String guess(@RequestParam("guess") String guess, Model model) {
        if (guess.length() != 5) {
            model.addAttribute("error", "Your guess must be exactly 5 letters long.");
            model.addAttribute("rating", currentWord.getRating() + " Word");
            model.addAttribute("hint", currentWord.getHint());
            return "game";
        }

        boolean win = guess.equalsIgnoreCase(currentWord.getWord());
        attempts--;

        model.addAttribute("rating", currentWord.getRating() + " Word");
        model.addAttribute("hint", currentWord.getHint());
        model.addAttribute("attempts", attempts); // Add the number of attempts left to the model

        if (win) {
            model.addAttribute("message", "Congratulations, you've won!");
        } else if (attempts <= 0) {
            model.addAttribute("message", "Sorry, you've lost. The correct word was " + currentWord.getWord() + ".");
        } else {
            model.addAttribute("message", "Try again! You have " + attempts + " attempts left.");
        }

        return "game";
    }
}
