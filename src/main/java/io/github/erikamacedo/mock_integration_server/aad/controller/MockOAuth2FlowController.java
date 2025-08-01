/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.erikamacedo.mock_integration_server.aad.controller;

/**
 *
 * @author erika
 */
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.erikamacedo.mock_integration_server.aad.fakes.JwtTokenGenerator;
import io.github.erikamacedo.mock_integration_server.aad.fakes.OAuthClientFake;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Controller
public class MockOAuth2FlowController {


    @GetMapping("/index")
    public String index(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state,
            Model model) {

        if (code != null && !code.isEmpty() && state != null && !state.isEmpty()) {
            System.out.println("Fluxo AAD: recebemos code=" + code + " e state=" + state);

            String tokenUrl = "http://localhost:8080/" + OAuthClientFake.TENANT + "/oauth2/v2.0/token";
            System.out.println("url " + tokenUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", OAuthClientFake.CLIENT_ID);
            params.add("client_secret", OAuthClientFake.CLIENT_SECRET);
            params.add("code", code);
            params.add("redirect_uri", OAuthClientFake.REDIRECT_URI);
            params.add("grant_type", OAuthClientFake.GRANT_TYPE);
            params.add("scope", OAuthClientFake.SCOPE);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
                Map<String, Object> tokenData = response.getBody();

                model.addAttribute("loginStatus", "Autenticado!");

                // Adiciona o access token bruto
                model.addAttribute("accessToken", tokenData.get("access_token"));

                // Decodifica o id_token e passa como JSON para a view
                String idToken = (String) tokenData.get("id_token");
                Map<String, Object> idTokenPayload = decodeJwtPayload(idToken);
                model.addAttribute("idTokenPayload", idTokenPayload);

                // Opcional: adicionar o refresh_token
                model.addAttribute("refreshToken", tokenData.get("refresh_token"));

                return "dashboard";

            } catch (Exception e) {
                model.addAttribute("loginStatus", "Erro ao obter token: " + e.getMessage());
                return "index";
            }
        }

        model.addAttribute("tenantId", OAuthClientFake.TENANT);
        return "index";
    }

// Método utilitário para decodificar o payload do JWT (parte do meio)
    private Map<String, Object> decodeJwtPayload(String jwt) throws IOException {
        if (jwt == null) {
            return Map.of();
        }

        String[] parts = jwt.split("\\.");
        if (parts.length < 2) {
            return Map.of();
        }
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
         return mapper.readValue(payloadJson, new TypeReference<Map<String, Object>>() {
        });
    }


    @GetMapping("static/{TENANT_ID}/oauth2/v2.0/authorize")
    public String loginMicrosoftForm(@PathVariable("TENANT_ID") String tenantId, Model model) {
        model.addAttribute("client_id", OAuthClientFake.CLIENT_ID);
        model.addAttribute("redirect_uri", OAuthClientFake.REDIRECT_URI);
        model.addAttribute("response_type", OAuthClientFake.RESPONSE_TYPE);
        model.addAttribute("response_mode", OAuthClientFake.RESPONSE_MODE);
        model.addAttribute("state", OAuthClientFake.STATE);
        model.addAttribute("scope", OAuthClientFake.SCOPE);
        JwtTokenGenerator.tenantId = tenantId;
        return "login-microsoft";
    }


}
