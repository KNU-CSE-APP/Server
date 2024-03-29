package com.knu.cse;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final Environment env;

    @GetMapping("/health")
    public String index(){
        return "현재 Profile " + Arrays.stream(env.getActiveProfiles()).findFirst().orElse("") + " - CI/CD 구축 COMPLETE";
    }

    @GetMapping("/health/profile")
    public String getProfile(){
        return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("");
    }
}
