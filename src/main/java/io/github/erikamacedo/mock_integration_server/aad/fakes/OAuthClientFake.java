/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.erikamacedo.mock_integration_server.aad.fakes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author erika
 */
public class OAuthClientFake {

    public static final String CLIENT_ID = "mock-client-id";
    public static final String CLIENT_SECRET = "fake-secret";
    public static final String REDIRECT_URI = "http://localhost:8080/index";
    public static final String TENANT = "mock-tenant-id";
    public static final String STATE = "abc123";
    public static final String RESPONSE_TYPE = "code";
    public static final String RESPONSE_MODE = "query";
    public static final String SCOPE = "openid profile email";
    public static final String GRANT_TYPE = "authorization_code";

   

}
