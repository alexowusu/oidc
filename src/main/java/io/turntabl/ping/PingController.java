package io.turntabl.ping;

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
            System.out.println(claim);

            if (claim.isEmpty() && TokenValidation.isTokenExpired(claim)) {
                return false;
            }

            System.out.println(">>>>> Validation might be successful :)");
            return TokenValidation.isTokenValidated(jwt, TokenValidation.getParsedPublicKey().get());
        }
        else {
            System.out.println(">>>>> Public Key not present | Validation unsuccessful :(");
            return false;
        }
    }
}