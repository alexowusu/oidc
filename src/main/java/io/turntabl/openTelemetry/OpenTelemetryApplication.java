package io.turntabl.openTelemetry;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.turntabl.openTelemetry.config.OTConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class OpenTelemetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenTelemetryApplication.class, args);
	}

    @Bean
    public OTeleServiceImpl getoTeleService(){
        return new OTeleServiceImpl();
    }


}
