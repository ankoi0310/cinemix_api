package vn.edu.hcmuaf.fit.cinemix_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync
@SpringBootApplication
public class CinemixApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemixApiApplication.class, args);
    }

}
