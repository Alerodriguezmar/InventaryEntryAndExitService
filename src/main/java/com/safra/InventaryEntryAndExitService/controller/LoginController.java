package com.safra.InventaryEntryAndExitService.controller;


import com.safra.InventaryEntryAndExitService.config.CertificateSSLConfig;
import com.safra.InventaryEntryAndExitService.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("login")
    public String login(HttpServletRequest request) {
        System.out.println("Login");
        HttpSession session = request.getSession(true);
       new CertificateSSLConfig().trustSelfSignedSSL();
        HttpHeaders login = loginService.login();
        session.setAttribute("cookies",login);
        return "Login: "+login;
    }
}
