package com.utc2.riskmanagement.repositories;

import com.utc2.riskmanagement.entities.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterDataRepository extends JpaRepository<MasterData, String> {
    List<MasterData> findByType(String type);
    MasterData findByValue(String value);
}
