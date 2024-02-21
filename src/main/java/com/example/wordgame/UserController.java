package com.example.wordgame;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/index")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/settings")
    public String setting(HttpSession session, Model model) {
        return "settings";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        // Validate input
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("emailError", "Email address is required");
            return "redirect:/login";
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("passwordError", "Password is required");
            return "redirect:/login";
        }

        // Check if user exists in the database
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("user", existingUser);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Invalid email or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Validate input
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("emailError", "Email address is required");
            return "redirect:/signup";
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("passwordError", "Password is required");
            return "redirect:/signup";
        }

        // Check if the email is already registered
        if (userRepository.findByEmail(user.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("emailError", "Email is already registered");
            return "redirect:/signup";
        }

        // Save the user to the database
        userRepository.save(user);

        // Redirect to login page after successful signup
        return "redirect:/login";
    }
}
