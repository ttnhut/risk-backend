package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.payloads.APIResponse;
import com.utc2.riskmanagement.payloads.RoleDTO;
import com.utc2.riskmanagement.services.RoleService;
import com.utc2.riskmanagement.utils.APIConStant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(APIConStant.Role.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public RoleDTO getSingleRole(@PathVariable(value = "id") String id) {
        return this.roleService.getSingleRole(id);
    }

    @GetMapping(APIConStant.Role.ENDPOINT)
    public List<RoleDTO> getAllRoles() {
        return this.roleService.getAllRoles();
    }

    @PostMapping(APIConStant.Role.ENDPOINT)
    public RoleDTO create(@RequestBody RoleDTO roleDTO) {
        return this.roleService.create(roleDTO);
    }

    @PutMapping(APIConStant.Role.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public RoleDTO update(@PathVariable(value = "id") String id, @RequestBody RoleDTO roleDTO) {
        return this.roleService.update(id, roleDTO);
    }

    @DeleteMapping(APIConStant.Role.GET_SINGLE_OR_DELETE_OR_UPDATE_ENDPOINT)
    public APIResponse delete(@PathVariable(value = "id") String id) {
        this.roleService.delete(id);
        return new APIResponse(APIConStant.Role.getDeleteSuccessStatus(id), Boolean.TRUE);
    }
}
