/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.erikamacedo.mock_integration_server.aad.service;

/**
 *
 * @author erika
 */

import io.github.erikamacedo.mock_integration_server.aad.fakes.JwtTokenGenerator;
import io.github.erikamacedo.mock_integration_server.aad.model.User;
import io.github.erikamacedo.mock_integration_server.aad.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<String> loginMicrosoft(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            String code = UUID.randomUUID().toString();;
            JwtTokenGenerator.authCodes.put(code, userOpt.get());
            return Optional.of(code);
        }

        return Optional.empty();
    }
}
