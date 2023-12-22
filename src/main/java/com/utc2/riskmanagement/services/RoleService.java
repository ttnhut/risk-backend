package com.utc2.riskmanagement.services;

import com.utc2.riskmanagement.payloads.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO getSingleRole(String id);
    List<RoleDTO> getAllRoles();
    RoleDTO create(RoleDTO roleDTO);
    RoleDTO update(String id, RoleDTO roleDTO);
    void delete(String id);
}
