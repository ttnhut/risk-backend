package com.utc2.riskmanagement.services.impl;

import com.utc2.riskmanagement.entities.Role;
import com.utc2.riskmanagement.exception.ResourceNotFoundException;
import com.utc2.riskmanagement.payloads.RoleDTO;
import com.utc2.riskmanagement.repositories.RoleRepository;
import com.utc2.riskmanagement.services.RoleService;
import com.utc2.riskmanagement.utils.ExceptionConstant;
import com.utc2.riskmanagement.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Override
    public RoleDTO getSingleRole(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.Role.RESOURCE, ExceptionConstant.Role.ID_FIELD, id));
        return this.modelMapperUtil.getModelMapper().map(role, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = this.roleRepository.findAll();
        return roles.stream().map(role -> this.modelMapperUtil.getModelMapper().map(role, RoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO create(RoleDTO roleDTO) {
        Role role = this.modelMapperUtil.getModelMapper().map(roleDTO, Role.class);
        Role savedRole = this.roleRepository.save(role);
        return this.modelMapperUtil.getModelMapper().map(savedRole, RoleDTO.class);
    }

    @Override
    public RoleDTO update(String id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.Role.RESOURCE, ExceptionConstant.Role.ID_FIELD, id));
        role.setName(roleDTO.getName());
        Role savedRole = this.roleRepository.save(role);
        return this.modelMapperUtil.getModelMapper().map(savedRole, RoleDTO.class);
    }

    @Override
    public void delete(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.Role.RESOURCE, ExceptionConstant.Role.ID_FIELD, id));
        this.roleRepository.delete(role);
    }
}
