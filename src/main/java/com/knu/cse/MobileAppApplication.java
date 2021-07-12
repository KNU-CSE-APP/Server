package com.knu.cse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching // 캐시 관련 어노테이션이 붙은 public 메서드의 모든 스프링 빈 스캔
// 부합하는 대상 발견 시 해당 메서드를 intercept하여 캐싱 기능이 추가된 프록시를 생성함
@EnableJpaAuditing // Auditing
public class MobileAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppApplication.class, args);
	}

}
