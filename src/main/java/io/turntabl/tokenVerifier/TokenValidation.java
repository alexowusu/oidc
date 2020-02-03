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
        String jwt2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImIyZWQwZGIxZjY2MWQ4OTg5OTY5YmFiNzhkMmZhZTc1NjRmZGMzYTkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI5MjQzNjgwNzQxMTUxMjY4MTciLCJoZCI6InR1cm50YWJsLmlvIiwiZW1haWwiOiJqb2huLmVyYnlubkB0dXJudGFibC5pbyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiOHpUZTN1N2EwTlh4VUk5SHR3SEZwZyIsIm5hbWUiOiJKb2huIEVyYnlubiIsImdpdmVuX25hbWUiOiJKb2huIiwiZmFtaWx5X25hbWUiOiJFcmJ5bm4iLCJsb2NhbGUiOiJlbiIsImlhdCI6MTU4MDc0NzcyNywiZXhwIjoxNTgwNzUxMzI3fQ.QYqpnXDG3iq_31CGsisNyR_atmzt2Go0yKA47RJQPj3Ez1i8WKQ05RVbuoW-IYzVJKwQ0iPHJRVPpYRpSIbZa7gliv8zi4Qkiw7a5bZtSDSmRnxjBnjTJKVyzbjsleNx2SDDE5mJS4nXmLovzBn1YFtaRvBhKBZMQAnq-fb88YMsmF36fbtrlAlNakxQSr8nicHmlY6fmb-UZc1raPCQdfWX_92Y_H6FWLqx89EpYEbGaS9BIx6wITl0NyPINPcVd5civ0z40656YjMcpzHCJpi6G34W0VX9NeHhurVi6zMPcxN8bJva_ESG1nge2454gxK9EZCAp2YKHrDipXZVag";

        boolean iss = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt2).getBody().get("iss").equals("https://accounts.google.com");
        boolean aud = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt2).getBody().get("aud").equals("859455735473-bgmqqco3q588kgaog0g2k0fmnur5qvf9.apps.googleusercontent.com");
        boolean hd = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt2).getBody().get("hd").equals("turntabl.io");

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
            return Optional.empty();
        }

    }
}
