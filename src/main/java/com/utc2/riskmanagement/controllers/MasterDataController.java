package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.payloads.APIResponse;
import com.utc2.riskmanagement.payloads.MasterDataDTO;
import com.utc2.riskmanagement.services.MasterDataService;
import com.utc2.riskmanagement.utils.APIConStant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class MasterDataController {

    @Autowired
    private MasterDataService masterDataService;

    @GetMapping(APIConStant.MasterData.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public MasterDataDTO getSingleMasterData(@PathVariable(value = "id") String id) {
        return this.masterDataService.getSingleMasterData(id);
    }

    @GetMapping(APIConStant.MasterData.ENDPOINT)
    public List<MasterDataDTO> getAllMasterDatas(@RequestParam(value = "type", required = false) String type) {
        if (Objects.isNull(type)) {
            return this.masterDataService.getAllMasterDatas();
        }
        return this.masterDataService.getByType(type);
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
}
