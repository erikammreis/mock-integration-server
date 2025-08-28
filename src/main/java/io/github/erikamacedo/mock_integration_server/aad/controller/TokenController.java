/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.erikamacedo.mock_integration_server.aad.controller;

/**
 *
 * @author erika
 *
 * Este endpoint simula o fluxo de autenticação OAuth2 e não representa nenhum
 * serviço real de terceiros. Criado apenas para testes e aprendizado.
 *
 */
import io.github.erikamacedo.mock_integration_server.aad.fakes.JwtTokenGenerator;
import io.github.erikamacedo.mock_integration_server.aad.fakes.OAuthClientFake;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TokenController {

    @PostMapping("/{TENANT_ID}/oauth2/v2.0/token")
    public ResponseEntity<Map<String, Object>> token(
            @PathVariable("TENANT_ID") String tenant,
            @RequestParam("client_id") String clientId,
            @RequestParam("scope") String scope,
            @RequestParam("code") String code,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_secret") String clientSecret
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("token_type", "Bearer");
        response.put("scope", scope);
        response.put("expires_in", 3600);
        response.put("access_token", JwtTokenGenerator.generateFakeAccessToken(clientId));
        response.put("id_token", JwtTokenGenerator.generateFakeIdToken(clientId,code));
        response.put("refresh_token", "M.R3_BAY_FAKE_REFRESH_TOKEN");

        return ResponseEntity.ok(response);
    }

}
