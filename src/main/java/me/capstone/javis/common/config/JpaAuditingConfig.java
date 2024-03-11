package me.capstone.javis.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//JPA에서 엔티티의 생성일 및 수정일을 자동으로 관리하기 위한 설정을 담당
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
