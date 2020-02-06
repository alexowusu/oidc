package io.turntabl.tokenVerifier;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

public class TokenValidation {

    public static boolean isTokenValidated(String jwt, RSAPublicKey  pubKey) {

        String ISSUER = System.getenv("ISSUER");
        String CLIENT_ID = System.getenv("CLIENT_ID");
        String HOST_DOMAIN = System.getenv("HOST_DOMAIN");

        boolean iss = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt).getBody().get("iss").equals(ISSUER);
        boolean aud = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt).getBody().get("aud").equals(CLIENT_ID);
        boolean hd = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt).getBody().get("hd").equals(HOST_DOMAIN);

        return iss && aud && hd;
    }

//    public static Claims getClaim(RSAPublicKey pubKey, String jwts ){
//        return Jwts.parser()
//                .setSigningKey(pubKey)
//                .parseClaimsJws(jwts).getBody();
//    }

    public static Optional<RSAPublicKey> getParsedPublicKey(){
        String PUB_KEY = System.getenv("PUBLIC_KEY") ;

        // removes white spaces(char 20)
        String PUBLIC_KEY = "";
        if (!PUB_KEY.isEmpty()) {
            PUBLIC_KEY = PUB_KEY.replace(" ", "");
            System.out.println( "PUB_KEY: " + PUBLIC_KEY);
        }

        try {
//            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(com.google.api.client.util.Base64.decodeBase64(PUBLIC_KEY));  // alt B
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY));
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpecX509);
            return Optional.of(pubKey);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println("Exception block | Invalid public key | Parsing error ");
            return Optional.empty();
        }
    }
}
