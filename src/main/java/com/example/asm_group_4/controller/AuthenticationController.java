package com.example.asm_group_4.controller;

import com.example.asm_group_4.model.Account;
import com.example.asm_group_4.repository.AccountRepository;
import com.example.asm_group_4.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AccountRepository accountRepository;
    AuthenticationService service;
    @GetMapping("/login")
    public String loginPage() {
        return "/pages/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Account account = service.validAccount(username, password);

        if (account != null) {
            session.setAttribute("account", account);
            return "redirect:/asset/field";
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "/pages/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }

}
