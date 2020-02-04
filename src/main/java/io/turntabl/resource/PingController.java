package io.turntabl.resource;

import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.turntabl.tokenVerifier.TokenValidation;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
public class PingController {

    @CrossOrigin
    @ApiOperation("test pong")
    @GetMapping(value = "v1/api/ping")
    public String ping(){
        return "Hello pong from Ping endpoint";
    }

    @CrossOrigin
    @ApiOperation("validate token")
    @PostMapping(value = "v1/api/validateToken")
    public boolean isTokenValid(@RequestHeader (name="Authorization") String tokenId){
        if (!(tokenId.startsWith("Bearer "))) {
            return false;
        }
        String jwt = tokenId.replace("Bearer ", "");

        if (TokenValidation.getParsedPublicKey().isPresent()){
            Claims claim = TokenValidation.getClaim(TokenValidation.getParsedPublicKey().get(), jwt);
            System.out.println(claim.getExpiration());

            boolean tokenExpired = TokenValidation.isTokenExpired(claim);
            System.out.println(">>>>>>> ");
            System.out.println(tokenExpired);

            if (claim.isEmpty() && tokenExpired) {return false;}

            boolean tokenValidated = TokenValidation.isTokenValidated(jwt, TokenValidation.getParsedPublicKey().get());
            System.out.println(">>>>> Validation successful :)");
            return tokenValidated;
        }
        else {
            System.out.println("Public Key not present");
            System.out.println(">>>>> Validation unsuccessful :)");
            return false;
        }
    }
}
