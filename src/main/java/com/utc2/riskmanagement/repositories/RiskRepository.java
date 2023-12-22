package com.utc2.riskmanagement.repositories;

import com.utc2.riskmanagement.entities.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskRepository extends JpaRepository<Risk, String> {
}
