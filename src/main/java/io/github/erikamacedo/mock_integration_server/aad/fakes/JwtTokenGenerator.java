/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.erikamacedo.mock_integration_server.aad.fakes;

/**
 *
 * @author erika
 */
import io.github.erikamacedo.mock_integration_server.aad.model.User;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JwtTokenGenerator {

    public static final Map<String, User> authCodes = new ConcurrentHashMap<>();
    public static String tenantId = null;

    public static String getTenantId() {
        return tenantId;
    }
     public static String setTenantId(String tenantId) {
        return JwtTokenGenerator.tenantId = tenantId;
    }

    /**
     * Gera um id_token simulado (JWT), usado principalmente para identificar o usuário autenticado.
     * Dados como "name", "preferred_username", "email" e "oid" normalmente vêm da API de autenticação.
     * O "aud" (audience) vem do front, pois representa o client_id da aplicação.
     */
    
   public static String generateFakeIdToken(String clientId, String code) {
    String header = base64UrlEncode("{\"alg\":\"RS256\",\"typ\":\"JWT\"}");

    // Obtém o usuário do código
    User user = authCodes.get(code);

    String payload = base64UrlEncode("{\n" +
            "  \"aud\": \"" + clientId + "\",\n" +                // <- dado do front (client_id)
            "  \"iss\": \"http://localhost:8080/tenant-id/v2.0\",\n" + // <- URL do provedor real (backend)
            "  \"iat\": " + getFakeTimestamp() + ",\n" +
            "  \"nbf\": " + getFakeTimestamp() + ",\n" +
            "  \"exp\": " + getFakeExpiration() + ",\n" +
            "  \"name\": \"" + formatNameFromEmail(user.getEmail()) + "\",\n" +              // <- dado simulado do backend
            "  \"preferred_username\": \"" + user.getEmail() + "\",\n" +
            "  \"oid\": \"fake-user-object-id\",\n" +
            "  \"tid\": \"fake-tenant-id\",\n" +
            "  \"email\": \"" + user.getEmail() + "\"\n" +
            "}");

    String signature = "fake-idtoken-signature";

    return header + "." + payload + "." + signature;
}


    /**
     * Gera um access_token simulado (JWT), usado para autorização (acesso a recursos protegidos).
     * Ele contém escopos, aplicação que gerou, e informações do tenant/usuário.
     */
    public static String generateFakeAccessToken(String clientId) {
        String header = base64UrlEncode("{\"alg\":\"RS256\",\"typ\":\"JWT\"}");

        String payload = base64UrlEncode("{\n" +
                "  \"aud\": \"" + clientId + "\",\n" +               // <- dado do front (client_id)
                "  \"iss\": \"https://http://localhost:8080/fake-tenant-id/\",\n" +  // <- URL do token service (backend)
                "  \"iat\": " + getFakeTimestamp() + ",\n" +
                "  \"nbf\": " + getFakeTimestamp() + ",\n" +
                "  \"exp\": " + getFakeExpiration() + ",\n" +
                "  \"scp\": \"openid profile email\",\n" +           // <- escopos requisitados (vem do front)
                "  \"appid\": \"" + clientId + "\",\n" +             // <- client_id enviado no POST token (do front)
                "  \"sub\": \"fake-subject-id\",\n" +
                "  \"oid\": \"fake-user-object-id\",\n" +
                "  \"tid\": \"fake-tenant-id\"\n" +
                "}");

        String signature = "fake-accesstoken-signature";

        return header + "." + payload + "." + signature;
    }

    private static String base64UrlEncode(String json) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    private static long getFakeTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    private static long getFakeExpiration() {
        return (System.currentTimeMillis() / 1000) + 3600; // 1 hora de validade
    }
    
    public static String formatNameFromEmail(String email) {
    if (email == null || !email.contains("@")) {
        return "";
    }
    String localPart = email.substring(0, email.indexOf('@'));
    String[] parts = localPart.split("\\.");
    
    StringBuilder formattedName = new StringBuilder();
    for (String part : parts) {
        if (part.length() > 0) {
            formattedName.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                formattedName.append(part.substring(1).toLowerCase());
            }
            formattedName.append(" ");
        }
    }
    return formattedName.toString().trim();
}

}
