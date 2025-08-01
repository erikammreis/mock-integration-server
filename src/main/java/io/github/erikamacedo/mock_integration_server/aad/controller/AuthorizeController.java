/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.erikamacedo.mock_integration_server.aad.controller;

import io.github.erikamacedo.mock_integration_server.aad.fakes.JwtTokenGenerator;
import io.github.erikamacedo.mock_integration_server.aad.fakes.OAuthClientFake;
import io.github.erikamacedo.mock_integration_server.aad.model.User;
import io.github.erikamacedo.mock_integration_server.aad.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author erika
 */
@Controller
public class AuthorizeController {
    
    @Autowired
    private UserRepository userRepository;
    
     @GetMapping("/{TENANT_ID}/oauth2/v2.0/authorize")
    public String loginMicrosoftForm(
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            @RequestParam(required = false) String state,
            @RequestParam String response_type,
            @RequestParam String response_mode,
            @RequestParam String scope,
            Model model) {

        model.addAttribute("client_id", client_id);
        model.addAttribute("redirect_uri", redirect_uri);
        model.addAttribute("response_type", response_type);
        model.addAttribute("response_mode", response_mode);
        model.addAttribute("state", state);
        model.addAttribute("scope", scope);

        return "login-microsoft";
    }

    

    @PostMapping("/login-microsoft")
    public String loginMicrosoftSubmit(@RequestParam String email,
            @RequestParam String password,
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            @RequestParam(required = false) String state,
            Model model) {    
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            String code = "fakecode123";
            String redirectUrl = redirect_uri + "?code=" + code;
            if (state != null) {
                redirectUrl += "&state=" + state;
            }
            JwtTokenGenerator.authCodes.put(code, userOpt.get());
            return "redirect:" + redirectUrl;
        } else {
            model.addAttribute("error", "Usuário ou senha inválidos");
            return "login-microsoft";
        }
    }


}
