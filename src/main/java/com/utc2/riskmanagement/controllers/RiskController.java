package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.payloads.APIResponse;
import com.utc2.riskmanagement.payloads.RiskDTO;
import com.utc2.riskmanagement.payloads.TrackingDTO;
import com.utc2.riskmanagement.services.RiskService;
import com.utc2.riskmanagement.utils.APIConStant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RiskController {
    @Autowired
    private RiskService riskService;

    @GetMapping(APIConStant.Risk.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public RiskDTO getSingleRisk(@PathVariable(value = "id") String id) {
        return this.riskService.getSingleRisk(id);
    }

    @GetMapping(APIConStant.Risk.TRACKING_ENDPOINT)
    public TrackingDTO getTrackingInformation() {
        return this.riskService.trackTask();
    }

    @GetMapping(APIConStant.Risk.ENDPOINT)
    public List<RiskDTO> getAllRisks(@RequestParam(value = "status", required = false) String status) {
        if (StringUtils.isEmpty(status)) {
            return this.riskService.getAllRisks();
        }
        return this.riskService.getAllRisks().stream().filter(r -> r.getProgress().getValue().equals(status)).collect(Collectors.toList());
    }

    @PostMapping(APIConStant.Risk.ENDPOINT)
    public RiskDTO create(@ModelAttribute RiskDTO riskDTO) {
        return this.riskService.create(riskDTO);
    }

    @PutMapping(APIConStant.Risk.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public RiskDTO update(@PathVariable(value = "id") String id, @RequestBody RiskDTO riskDTO) throws Exception {
        return this.riskService.update(id, riskDTO);
    }

    @DeleteMapping(APIConStant.Risk.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public APIResponse delete(@PathVariable(value = "id") String id) {
        this.riskService.delete(id);
        return new APIResponse(APIConStant.Risk.getDeleteSuccessStatus(id), Boolean.TRUE);
    }
}
