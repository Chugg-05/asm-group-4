package com.example.asm_group_4.controller;

import com.example.asm_group_4.model.Account;
import com.example.asm_group_4.repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AccountRepository accountRepository;

    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        model.addAttribute("account", new Account());
        return "/pages/login";
    }

    @PostMapping("/auth/login")
    public String login(@Valid @ModelAttribute("account") Account account,
                        BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "/pages/login";
        }
        Account existing = accountRepository.findByUsernameAndPassword(
                account.getUsername(), account.getPassword());
        if (existing == null) {
            model.addAttribute("loginError", "Sai username hoặc password");
            return "/pages/login";
        }

        session.setAttribute("loggedInUser", existing); // Lưu session
        return "redirect:/auth/home";
    }

    @GetMapping("/auth/sign-in")
    public String signInPage(Model model) {
        model.addAttribute("account", new Account());
        return "/pages/signIn";
    }

    @PostMapping("/auth/create-account")
    public String createAccount(@Valid @ModelAttribute("account") Account account,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/pages/signIn";
        }
        accountRepository.save(account);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/home")
    public String showHomePage() {
        return "/pages/home"; // Không cần .html
    }
}
