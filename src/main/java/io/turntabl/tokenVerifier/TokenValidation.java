package io.turntabl.tokenVerifier;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

public class TokenValidation {

    public static boolean isTokenValidated(String jwts, RSAPublicKey  pubKey) {
        boolean iss = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwts).getBody().get("iss").equals("https://accounts.google.com");
        boolean aud = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwts).getBody().get("aud").equals("859455735473-bgmqqco3q588kgaog0g2k0fmnur5qvf9.apps.googleusercontent.com");
        boolean hd = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwts).getBody().get("hd").equals("turntabl.io");

        return iss && aud && hd;
    }

    public static Claims getClaim(RSAPublicKey pubKey, String jwts ){
        return Jwts.parser()
                .setSigningKey(pubKey)
                .parseClaimsJws(jwts).getBody();
    }

    public static Optional<RSAPublicKey> getPublicKey(){
        String publicKeyPEM ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqOcCdoOH4mcM7oyR6Sur" +
                "Q50dQLRkOjM4e4TbCZAHpBX5rdY4wK+jKjvrwJoFQ220G47BypMnnXBFjd7WbO5i" +
                "QQzP6oIzwQF+7JI2hzqUUSNc44rYldunmjshl+xXplPuvRqcAQn9s3XM8NkbXcuM" +
                "Q3azZO0ZL7XIhm3Zah5Hhc0MVbQxMiUSK2A7phV5JXS4fUJhfKb02RIj/ZtbgpJU" +
                "o2A7IUzvgQHFiKf+AhVYiwKY2nuIdUbOyTY0JYw6KW4U8rv8Aga2H4WhGy7jlcZs" +
                "wwmnJMzergQP3Wuq41Ap/13Kc5XXt18sWMD6ixYCOU6lPCco0hr7iO+V1t59oDfg" +
                "JwIDAQAB" ;
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyPEM));

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
            return Optional.of(pubKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println("Exception block ");
            return Optional.empty();
        }

    }
}
