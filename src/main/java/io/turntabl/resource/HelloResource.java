package io.turntabl.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class HelloResource {

    @CrossOrigin
    @GetMapping(value = "/hello")
    public String hello(){
        return "Hello from planet earth :)";
    }
}
