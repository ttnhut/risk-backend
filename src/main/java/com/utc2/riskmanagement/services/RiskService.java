package com.utc2.riskmanagement.services;

import com.utc2.riskmanagement.payloads.RiskDTO;
import com.utc2.riskmanagement.payloads.TrackingDTO;

import java.util.List;

public interface RiskService {
    RiskDTO getSingleRisk(String id);
    List<RiskDTO> getAllRisks();
    RiskDTO create(RiskDTO riskDTO);
    RiskDTO update(String id, RiskDTO riskDTO) throws Exception;
    void delete(String id);
    TrackingDTO trackTask();
}
