package io.turntabl.ping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import io.turntabl.tokenVerifier.GoogleIdVerifier;
//import io.turntabl.tokenVerifier.GoogleIdVerifier;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

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
    @ApiOperation("verify token")
    @PostMapping(value = "v1/api/tokenVerify")
    public String verify(@RequestHeader("authorization") String tokenId) {
        tokenId = tokenId.replace("Bearer ", "");
        System.out.println("***** " + tokenId);
        String payload = tokenId.split("\\.")[1];

        if(!(tokenId.isEmpty())) {
            System.out.println("....not empty....");

            try {
                String decode = new String(Base64.decodeBase64(payload), "UTF-8");
                System.out.println("###########");
                System.out.println(decode);
                System.out.println("###########");

                return decode;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "Invalid token";

    }
}
