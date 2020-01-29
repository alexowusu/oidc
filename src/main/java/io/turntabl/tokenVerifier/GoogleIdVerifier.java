package io.turntabl.tokenVerifier;

import com.google.api.client.auth.openidconnect.IdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

@Component
public class GoogleIdVerifier extends IdTokenVerifier {
    public UUID getUserUUIDByIdToken(String idTokenString) throws GeneralSecurityException, IOException, IllegalArgumentException {
        System.out.println("inside class ...");
        ApacheHttpTransport.Builder builder = new ApacheHttpTransport.Builder();
        GoogleIdTokenVerifier localVerifier = new GoogleIdTokenVerifier.Builder(builder.build(), new JacksonFactory())
                .setAudience(Collections.singletonList("859455735473-bgmqqco3q588kgaog0g2k0fmnur5qvf9.apps.googleusercontent.com"))
                .build();
        GoogleIdToken idToken = localVerifier.verify(idTokenString);
        System.out.println("before if ...");
//        if (idToken != null) {
            System.out.println("Inside conditional token ...... ");
            GoogleIdToken.Payload payload = idToken.getPayload();
        System.out.println(payload.getEmail());
        System.out.println(payload.getHostedDomain());

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

//        }
        return null;
    }
}
