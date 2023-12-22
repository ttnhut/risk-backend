package com.utc2.riskmanagement.services;

import com.utc2.riskmanagement.payloads.MasterDataDTO;

import java.util.List;

public interface MasterDataService {
    MasterDataDTO getSingleMasterData(String id);
    List<MasterDataDTO> getAllMasterDatas();
    MasterDataDTO create(MasterDataDTO masterDataDTO);
    MasterDataDTO update(String id, MasterDataDTO masterDataDTO);
    void delete(String id);
    List<MasterDataDTO> getByType(String type);
}
