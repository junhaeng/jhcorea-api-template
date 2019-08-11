package com.jhcorea.template.repository.readonly;

import com.jhcorea.template.repository.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadOnlyTemplateRepository extends JpaRepository<TemplateEntity, Long> {
}
