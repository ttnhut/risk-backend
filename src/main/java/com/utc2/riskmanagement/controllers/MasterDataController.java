package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.payloads.APIResponse;
import com.utc2.riskmanagement.payloads.MasterDataDTO;
import com.utc2.riskmanagement.services.MasterDataService;
import com.utc2.riskmanagement.services.RiskService;
import com.utc2.riskmanagement.utils.APIConStant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class MasterDataController {

    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    private RiskService riskService;

    @GetMapping(APIConStant.MasterData.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public MasterDataDTO getSingleMasterData(@PathVariable(value = "id") String id) {
        return this.masterDataService.getSingleMasterData(id);
    }

    @GetMapping(APIConStant.MasterData.ENDPOINT)
    public List<MasterDataDTO> getAllMasterDatas(@RequestParam(value = "type", required = false) String type) {
        if (Objects.isNull(type)) {
            return this.masterDataService.getAllMasterDatas().stream().sorted((m1,m2)-> m1.getType().compareTo(m2.getType())).collect(Collectors.toList());
        }
        return this.masterDataService.getByType(type).stream().sorted((m1,m2)-> m1.getType().compareTo(m2.getType())).collect(Collectors.toList());
    }

    @PostMapping(APIConStant.MasterData.ENDPOINT)
    public MasterDataDTO create(@RequestBody MasterDataDTO masterDataDTO) {
        return this.masterDataService.create(masterDataDTO);
    }

    @PutMapping(APIConStant.MasterData.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public MasterDataDTO update(@RequestBody MasterDataDTO masterDataDTO, @PathVariable(value = "id") String id) {
        return this.masterDataService.update(id, masterDataDTO);
    }

    @DeleteMapping(APIConStant.MasterData.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public APIResponse delete(@PathVariable(value = "id") String id) {
       this.masterDataService.delete(id);
       return new APIResponse(APIConStant.MasterData.getDeleteSuccessStatus(id), Boolean.TRUE);
    }

    @GetMapping(APIConStant.MasterData.RECOMMEND_CLASS_ENDPOINT)
    public Map<String,String> recommendClass() {
        Map<String, String> result = new HashMap<>();
        result.put("recommendClass", this.riskService.recommendClass());
        return result;
    }
}
