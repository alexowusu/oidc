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

    public static Claims getClaim(RSAPublicKey pubKey, String jwts ){
        return Jwts.parser()
                .setSigningKey(pubKey)
                .parseClaimsJws(jwts).getBody();
    }

    public static Optional<RSAPublicKey> getParsedPublicKey(){
        String PUBLIC_KEY =System.getenv("PUBLIC_KEY") ;

        try {
            byte[] decode = com.google.api.client.util.Base64.decodeBase64(PUBLIC_KEY);
//            byte[] decode2 = Base64.getDecoder().decode(PUBLIC_KEY);
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(decode);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);
            return Optional.of(pubKey);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println("Exception block | Invalid public key | Parsing error ");
            return Optional.empty();
        }
    }
}
