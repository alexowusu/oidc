package io.turntabl.ping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import io.turntabl.tokenVerifier.GoogleIdVerifier;
//import io.turntabl.tokenVerifier.GoogleIdVerifier;
import io.turntabl.model.JwtPayload;
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
    public boolean verify(@RequestBody String tokenId) {
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        tokenId = tokenId.replace("Bearer ", "");
//        System.out.println("***** " + tokenId);
        String payload = tokenId.split("\\.")[1];

        String decode;

        try {
            decode = new String(Base64.decodeBase64(payload), "UTF-8");
            System.out.println("###########");
            System.out.println(decode);
            System.out.println("###########");
            JwtPayload jsonNode = OBJECT_MAPPER.readValue(decode, JwtPayload.class);
            System.out.println(jsonNode.getEmail());
            System.out.println(jsonNode.getHd());

            if (jsonNode.getHd().equals("turntabl.io")) {
                System.out.println("works !");
                return true;
            }

            } catch (JsonProcessingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        return false;
    }
}
