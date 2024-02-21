package com.example.wordgame;



import com.example.wordgame.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void updateScore(Long userId, int newScore) {
        com.example.wordgame.User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            //user.setScore(newScore);
            userRepository.save(user);
        } else {

        }
    }
}