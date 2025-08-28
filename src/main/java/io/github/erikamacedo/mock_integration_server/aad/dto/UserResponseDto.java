/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.erikamacedo.mock_integration_server.aad.dto;

import io.github.erikamacedo.mock_integration_server.aad.model.User;

/**
 *
 * @author erika
 */
public record UserResponseDto(Long id,String email) {
   public UserResponseDto(User savedUser) {
        this(savedUser.getId(),savedUser.getEmail());
    }   
}
