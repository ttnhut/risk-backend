package com.utc2.riskmanagement.repositories;

import com.utc2.riskmanagement.entities.MasterData;
import com.utc2.riskmanagement.entities.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RiskRepository extends JpaRepository<Risk, String> {
    Optional<Risk> findByReportedClassAndDevice(MasterData reportedClass, MasterData device);
    List<Risk> findByReportedClassId(String reportedClassId);
}
