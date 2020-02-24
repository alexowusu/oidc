package io.turntabl.openTelemetry;

import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
public class OTController {

    @Autowired
    JaegerTracer jaegerTracer;

    @CrossOrigin
    @ApiOperation("Get Erbynn")
    @GetMapping(value = "v1/api/getErbynn")
    public String getErbynn( @RequestParam("abc") String abc){

        return "Hello from Erbynn";
    }

    @CrossOrigin
    @ApiOperation("Get Alex")
    @GetMapping(value = "v1/api/getAlex")
    public String getAlex(){
//
        return "Hello from Alex";
    }
}


