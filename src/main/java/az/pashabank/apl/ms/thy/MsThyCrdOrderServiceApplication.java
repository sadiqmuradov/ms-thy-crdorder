package az.pashabank.apl.ms.thy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Locale;

@SpringBootApplication
@EnableFeignClients(basePackages = {"az.pashabank.apl.ms.thy.proxy"})
@EnableScheduling
public class MsThyCrdOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsThyCrdOrderServiceApplication.class, args);
        Locale.setDefault(Locale.US);
    }

}
