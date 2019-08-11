package com.jhcorea.template.repository.writable;

import com.jhcorea.template.repository.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
}
