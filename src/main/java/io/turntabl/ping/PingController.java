package io.turntabl.ping;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.turntabl.tokenVerifier.TokenValidation;
import org.springframework.web.bind.annotation.*;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;


@Api
@RestController
public class PingController {

    @CrossOrigin
    @ApiOperation("test pong")
    @GetMapping(value = "v1/api/ping")
    public String ping(){
        return "Hello pong from Ping endpoint";
    }

    // functional
    @CrossOrigin
    @ApiOperation("validate token")
    @GetMapping(value = "/validateToken")
    public boolean isValid(){
        String jwt = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImIyZWQwZGIxZjY2MWQ4OTg5OTY5YmFiNzhkMmZhZTc1NjRmZGMzYTkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI5MjQzNjgwNzQxMTUxMjY4MTciLCJoZCI6InR1cm50YWJsLmlvIiwiZW1haWwiOiJqb2huLmVyYnlubkB0dXJudGFibC5pbyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiRnh6UWNNSXdQMHVKMDN0aWVfU2pHQSIsIm5hbWUiOiJKb2huIEVyYnlubiIsImdpdmVuX25hbWUiOiJKb2huIiwiZmFtaWx5X25hbWUiOiJFcmJ5bm4iLCJsb2NhbGUiOiJlbiIsImlhdCI6MTU4MDc1MzExMiwiZXhwIjoxNTgwNzU2NzEyfQ.YbK1fDL9a64JA_2kvabKw_YgkedqibJuRHAEsfz-qJuZSYymSi5nOAM19M5nH2P-UjGxEc3s_QgV7p7oDTHgaIhliqntAXJ1qCnIfMEEbNwsZjUVeGgjVmyV8m_Aq2ELKBxQhwOZdzdJwGacMnAPbxvEYIuJ5Tc1_3yVX4prpPJs9ELjJJY8OP7lgFTXiZQYLwzOWOWcHa9y3hTe54XF8NbT7RBFI4atd9HtJEyQt0b-pcf1GQnY-nIioY-6Rdlhf6q9rth5zgRFSwgFDxkEzPGArB1zs6CiOTkQVoy_JyxJYwgC2Jt8t1yKyQ6yRDutVHjejtaOILx7nRhwxcdc8w";

        if (TokenValidation.getPublicKey().isPresent()){
            Claims claim = TokenValidation.getClaim(TokenValidation.getPublicKey().get(), jwt);
            System.out.println(claim);

            boolean tokenValidated = TokenValidation.isTokenValidated(jwt, TokenValidation.getPublicKey().get());
            System.out.println(">>>>> Validation successful :)");
            return tokenValidated;
        }
        else {
            System.out.println("Public Key not present");
            System.out.println(">>>>> Validation unsuccessful :)");
            return false;
        }

    }

    @CrossOrigin
    @ApiOperation("verify token")
    @PostMapping(value = "v1/api/tokenVerify")
    public boolean isVerified(@RequestHeader (name="Authorization") String tokenId) {
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        System.out.println(">>>  " + tokenId);
        tokenId = tokenId.replace("Bearer ", "");

//        --------------------------
//        Jws<Claims> claimsJws;
//        claimsJws = Jwts.parser().setSigningKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqOcCdoOH4mcM7oyR6Sur\n" +
//                "Q50dQLRkOjM4e4TbCZAHpBX5rdY4wK+jKjvrwJoFQ220G47BypMnnXBFjd7WbO5i\n" +
//                "QQzP6oIzwQF+7JI2hzqUUSNc44rYldunmjshl+xXplPuvRqcAQn9s3XM8NkbXcuM\n" +
//                "Q3azZO0ZL7XIhm3Zah5Hhc0MVbQxMiUSK2A7phV5JXS4fUJhfKb02RIj/ZtbgpJU\n" +
//                "o2A7IUzvgQHFiKf+AhVYiwKY2nuIdUbOyTY0JYw6KW4U8rv8Aga2H4WhGy7jlcZs\n" +
//                "wwmnJMzergQP3Wuq41Ap/13Kc5XXt18sWMD6ixYCOU6lPCco0hr7iO+V1t59oDfg\n" +
//                "JwIDAQAB").parseClaimsJws(tokenId);

//        ---------------------------
        System.out.println("***** " + tokenId);
        String payload = tokenId.split("\\.")[1];

        String decode;

//        try {
//            decode = new String(Base64.decodeBase64(payload), "UTF-8");
//            System.out.println("###########");
//            System.out.println(decode);
//
//            System.out.println("###########");
//            JwtPayload jsonNode = OBJECT_MAPPER.readValue(decode, JwtPayload.class);
//            System.out.println(jsonNode.getEmail());
//            System.out.println(jsonNode.getHd());
//
//            if (jsonNode.getHd().equals("turntabl.io")) {
//                System.out.println("works !");
//                return true;
//            }
//
//            } catch (JsonProcessingException | UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

        return false;
    }


    ////// old \\\\\\\\
    @CrossOrigin
    @ApiOperation("verify token test")
    @GetMapping(value = "/verifytest")
    public boolean isVerified() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String jwt = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImIyZWQwZGIxZjY2MWQ4OTg5OTY5YmFiNzhkMmZhZTc1NjRmZGMzYTkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI5MjQzNjgwNzQxMTUxMjY4MTciLCJoZCI6InR1cm50YWJsLmlvIiwiZW1haWwiOiJqb2huLmVyYnlubkB0dXJudGFibC5pbyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiSTBzekZQSklkdXY1bXFzVzMzTkZYUSIsIm5hbWUiOiJKb2huIEVyYnlubiIsImdpdmVuX25hbWUiOiJKb2huIiwiZmFtaWx5X25hbWUiOiJFcmJ5bm4iLCJsb2NhbGUiOiJlbiIsImlhdCI6MTU4MDUwMzYwOCwiZXhwIjoxNTgwNTA3MjA4fQ.E4OSz8ud2HgjTwTNwK1ylRZI15VT5xjT5e4lyK488EeINYfZSXxqRaYVPCFgP-_iES_4LPe9j4qarcRod_OlSVUpI_cK46pP6xfnzi9obChe4hk17t4rX9fuc5SbTmprjb39xUQn-p_5DhwwE7Memqfc3ZheO-nRW-1rKram6wU2CfLNjzrl3W6iIDHGiSMzS84mu0e0IGWHx7BasLIvgr6Fe7DllH3PDnV2I3OrndF3zCUMqEwrn8pvEb4jHzP4GV6zZ0hrJbHBv8H2gTXroSgu5t-UT5zpxjG949wkvcjuGV3UfhJ9eFauQX2h3W1VXko4sCeoZIZmpJnQgV9IHQ";
//        String jwt2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImIyZWQwZGIxZjY2MWQ4OTg5OTY5YmFiNzhkMmZhZTc1NjRmZGMzYTkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI5MjQzNjgwNzQxMTUxMjY4MTciLCJoZCI6InR1cm50YWJsLmlvIiwiZW1haWwiOiJqb2huLmVyYnlubkB0dXJudGFibC5pbyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiZlVmTU96b1Fqc2c5SlFoRFpzWnFCUSIsIm5hbWUiOiJKb2huIEVyYnlubiIsImdpdmVuX25hbWUiOiJKb2huIiwiZmFtaWx5X25hbWUiOiJFcmJ5bm4iLCJsb2NhbGUiOiJlbiIsImlhdCI6MTU4MDc0Mzc4OCwiZXhwIjoxNTgwNzQ3Mzg4fQ.fcZCTeWnjF2a76zgx5Iio33nRh-OogUYGtzLcT8TbCsimw4yebFkAX-s5SaKkjfvJvwuOvjES5b07bywnUAHP9L9Gq5DKpcBW4eHvUhqjxQwrEaFsIrKOciKeBB42StmlIIoj2lAJMv4K024nr71gucj6NnxwkornZms6_lNEzpKz29BNn0fdaEcfpwsyoQhZJivNhynlbOrem6oM0y9btXCOT9LGjW0y2JYTQ52PBi4WZuueg-FM5TNpj11C3T9u9rVm2vHWc2uT_o8htYNVW5KwQatV2ww8Mx0qdmVjSpX0VB51x5XluLyEbDjd0BdPBfKCtmzSIZAcwd85LhOrA";
        String jwt2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImIyZWQwZGIxZjY2MWQ4OTg5OTY5YmFiNzhkMmZhZTc1NjRmZGMzYTkiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4NTk0NTU3MzU0NzMtYmdtcXFjbzNxNTg4a2dhb2cwZzJrMGZtbnVyNXF2ZjkuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI5MjQzNjgwNzQxMTUxMjY4MTciLCJoZCI6InR1cm50YWJsLmlvIiwiZW1haWwiOiJqb2huLmVyYnlubkB0dXJudGFibC5pbyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiOHpUZTN1N2EwTlh4VUk5SHR3SEZwZyIsIm5hbWUiOiJKb2huIEVyYnlubiIsImdpdmVuX25hbWUiOiJKb2huIiwiZmFtaWx5X25hbWUiOiJFcmJ5bm4iLCJsb2NhbGUiOiJlbiIsImlhdCI6MTU4MDc0NzcyNywiZXhwIjoxNTgwNzUxMzI3fQ.QYqpnXDG3iq_31CGsisNyR_atmzt2Go0yKA47RJQPj3Ez1i8WKQ05RVbuoW-IYzVJKwQ0iPHJRVPpYRpSIbZa7gliv8zi4Qkiw7a5bZtSDSmRnxjBnjTJKVyzbjsleNx2SDDE5mJS4nXmLovzBn1YFtaRvBhKBZMQAnq-fb88YMsmF36fbtrlAlNakxQSr8nicHmlY6fmb-UZc1raPCQdfWX_92Y_H6FWLqx89EpYEbGaS9BIx6wITl0NyPINPcVd5civ0z40656YjMcpzHCJpi6G34W0VX9NeHhurVi6zMPcxN8bJva_ESG1nge2454gxK9EZCAp2YKHrDipXZVag";
        String publicKeyPEM ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqOcCdoOH4mcM7oyR6Sur" +
                "Q50dQLRkOjM4e4TbCZAHpBX5rdY4wK+jKjvrwJoFQ220G47BypMnnXBFjd7WbO5i" +
                "QQzP6oIzwQF+7JI2hzqUUSNc44rYldunmjshl+xXplPuvRqcAQn9s3XM8NkbXcuM" +
                "Q3azZO0ZL7XIhm3Zah5Hhc0MVbQxMiUSK2A7phV5JXS4fUJhfKb02RIj/ZtbgpJU" +
                "o2A7IUzvgQHFiKf+AhVYiwKY2nuIdUbOyTY0JYw6KW4U8rv8Aga2H4WhGy7jlcZs" +
                "wwmnJMzergQP3Wuq41Ap/13Kc5XXt18sWMD6ixYCOU6lPCco0hr7iO+V1t59oDfg" +
                "JwIDAQAB" ;


        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyPEM));
//        System.out.println(keySpecX509);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
//        System.out.println(pubKey);

        Claims claims = Jwts.parser()
                .setSigningKey(pubKey)
                .parseClaimsJws(jwt2).getBody();

        System.out.println(claims);


        boolean iss = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt2).getBody().get("iss").equals("https://accounts.google.com");
        boolean aud = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt2).getBody().get("aud").equals("859455735473-bgmqqco3q588kgaog0g2k0fmnur5qvf9.apps.googleusercontent.com");
        boolean hd = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt2).getBody().get("hd").equals("turntabl.io");
        System.out.println(hd);

//        if (TokenValidation.getPublicKey().isPresent()){
//            TokenValidation.getClaim(TokenValidation.getPublicKey().get(), "");
//        }
//        else {
//            System.out.println("Public Key not present");
//        }

//        assert joe;
        System.out.println(">>>>>>> ");
        System.out.println(iss);
        System.out.println(">>>>>>> ");
        System.out.println(aud);
        System.out.println(">>>>>>> ");
//        System.out.println(sub);



        return true;
    }

}
