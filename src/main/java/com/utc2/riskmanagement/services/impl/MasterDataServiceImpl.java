package com.utc2.riskmanagement.services.impl;

import com.utc2.riskmanagement.entities.MasterData;
import com.utc2.riskmanagement.exception.ResourceNotFoundException;
import com.utc2.riskmanagement.payloads.MasterDataDTO;
import com.utc2.riskmanagement.repositories.MasterDataRepository;
import com.utc2.riskmanagement.services.MasterDataService;
import com.utc2.riskmanagement.utils.ExceptionConstant;
import com.utc2.riskmanagement.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterDataServiceImpl implements MasterDataService {

    @Autowired
    private MasterDataRepository masterDataRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Override
    public MasterDataDTO getSingleMasterData(String id) {
        MasterData masterData = this.masterDataRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(ExceptionConstant.MasterData.RESOURCE,
                                                 ExceptionConstant.MasterData.ID_FIELD, id));
        return this.modelMapperUtil.getModelMapper().map(masterData, MasterDataDTO.class);
    }

    @Override
    public List<MasterDataDTO> getAllMasterDatas() {
        List<MasterData> masterDataList = this.masterDataRepository.findAll();
        return masterDataList.stream().map(m -> this.modelMapperUtil
                                                .getModelMapper()
                                                .map(m, MasterDataDTO.class))
                                                .collect(Collectors.toList());
    }

    @Override
    public MasterDataDTO create(MasterDataDTO masterDataDTO) {
        MasterData masterData = this.modelMapperUtil.getModelMapper().map(masterDataDTO, MasterData.class);
        MasterData savedMasterData = this.masterDataRepository.save(masterData);
        return this.modelMapperUtil.getModelMapper().map(savedMasterData, MasterDataDTO.class);
    }

    @Override
    public MasterDataDTO update(String id, MasterDataDTO masterDataDTO) {
        MasterData masterData = this.masterDataRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(ExceptionConstant.MasterData.RESOURCE,
                                                 ExceptionConstant.MasterData.ID_FIELD, id));
        masterData.setType(masterDataDTO.getType());
        masterData.setValue(masterDataDTO.getValue());
        MasterData savedMasterData = this.masterDataRepository.save(masterData);
        return this.modelMapperUtil.getModelMapper().map(savedMasterData, MasterDataDTO.class);
    }

    @Override
    public void delete(String id) {
        MasterData masterData = this.masterDataRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(ExceptionConstant.MasterData.RESOURCE,
                                                 ExceptionConstant.MasterData.ID_FIELD, id));
        this.masterDataRepository.delete(masterData);
    }

    @Override
    public List<MasterDataDTO> getByType(String type) {
        List<MasterData> masterDataList = this.masterDataRepository.findByType(type);
        return masterDataList.stream().map(m -> this.modelMapperUtil
                        .getModelMapper()
                        .map(m, MasterDataDTO.class))
                .collect(Collectors.toList());
    }
}
