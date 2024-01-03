package com.utc2.riskmanagement.services.impl;

import com.utc2.riskmanagement.entities.MasterData;
import com.utc2.riskmanagement.entities.Risk;
import com.utc2.riskmanagement.entities.User;
import com.utc2.riskmanagement.exception.ResourceNotFoundException;
import com.utc2.riskmanagement.payloads.RiskDTO;
import com.utc2.riskmanagement.payloads.TrackingDTO;
import com.utc2.riskmanagement.repositories.MasterDataRepository;
import com.utc2.riskmanagement.repositories.RiskRepository;
import com.utc2.riskmanagement.repositories.UserRepository;
import com.utc2.riskmanagement.services.EmailService;
import com.utc2.riskmanagement.services.FileService;
import com.utc2.riskmanagement.services.RiskService;
import com.utc2.riskmanagement.utils.AssignRiskConstant;
import com.utc2.riskmanagement.utils.EmailConstant;
import com.utc2.riskmanagement.utils.ExceptionConstant;
import com.utc2.riskmanagement.utils.ModelMapperUtil;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    private RiskRepository riskRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MasterDataRepository masterDataRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public RiskDTO getSingleRisk(String id) {
        Risk risk = this.riskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.Risk.RESOURCE, ExceptionConstant.Risk.ID_FIELD, id));
        return this.modelMapperUtil.getModelMapper().map(risk, RiskDTO.class);
    }

    @Override
    public List<RiskDTO> getAllRisks() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Risk> risks = new ArrayList<>();
        User user = userRepository.findById(currentUserEmail).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.User.RESOURCE, ExceptionConstant.User.ID_FIELD, currentUserEmail));
        if (user.getRole().getName().equals("ROLE_USER")) {
            risks = user.getReportedRisks();
        }
        else {
            risks = user.getAssignedRisks();
        }
        return risks.stream().map(r -> this.modelMapperUtil.getModelMapper().map(r, RiskDTO.class)).sorted((r2,r1) -> r1.getCreatedDate().compareTo(r2.getCreatedDate())).collect(Collectors.toList());
    }

    @Override
    public RiskDTO create(RiskDTO riskDTO) throws Exception {
        Risk risk = this.modelMapperUtil.getModelMapper().map(riskDTO, Risk.class);
        Optional<Risk> existedRisk = this.riskRepository.findByReportedClassAndDevice(this.modelMapperUtil.getModelMapper().map(riskDTO.getReportedClass(), MasterData.class), this.modelMapperUtil.getModelMapper().map(riskDTO.getDevice(), MasterData.class));
        if (existedRisk.isPresent()) {
            throw new Exception("Risk is existed !!!");
        }
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        risk.setCreatedDate(new Date());
        risk.setProgress(this.masterDataRepository.findByValue("NEW"));
        risk.setReporter(userRepository.findById(currentUserEmail).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.User.RESOURCE, ExceptionConstant.User.ID_FIELD, currentUserEmail)));
        risk.setImage(fileService.uploadImage(riskDTO.getFile()));
        Risk savedRisk = this.riskRepository.save(risk);
        this.emailService.sendProgressRiskEmail(savedRisk.getReporter().getEmail(), "Cập nhật trạng thái của vấn đề: " + savedRisk.getName(), savedRisk.getProgress().getValue());

        // send mail
        MasterData masterData = this.masterDataRepository.findById(risk.getTypeRisk().getId()).get();
        masterData.getUsersOfType().forEach(u -> {
            try {
                this.emailService.sendActivationEmail(u.getEmail(), AssignRiskConstant.ASSIGN_RISK_URL + savedRisk.getId(), EmailConstant.ASSIGN_RISK_SUBJECT, AssignRiskConstant.ASSIGN_RISK_DESCRIPTION, u.getName());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

        return this.modelMapperUtil.getModelMapper().map(savedRisk, RiskDTO.class);
    }

    @Override
    public RiskDTO update(String id, RiskDTO riskDTO) throws Exception {
        Risk risk = this.riskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.Risk.RESOURCE, ExceptionConstant.Risk.ID_FIELD, id));
        if (Objects.nonNull(riskDTO.getFile())) {
            risk.setImage(fileService.uploadImage(riskDTO.getFile()));
        }

        if (Objects.isNull(risk.getAssignee()) && Objects.nonNull(riskDTO.getAssignee())) {
            risk.setAssignee(this.modelMapperUtil.getModelMapper().map(riskDTO.getAssignee(), User.class));
        }

        if (Objects.isNull(risk.getCompletedDate()) && Objects.nonNull(riskDTO.getCompletedDate())) {
            risk.setCompletedDate(riskDTO.getCompletedDate());
        }
        if (!StringUtils.isEmpty(riskDTO.getName())) {
            risk.setName(riskDTO.getName());
        }
        if (!StringUtils.isEmpty(riskDTO.getDescription())) {
            risk.setName(riskDTO.getDescription());
        }

        if (!StringUtils.isEmpty(riskDTO.getLevel().getId())) {
            risk.setLevel(this.modelMapperUtil.getModelMapper().map(riskDTO.getLevel(), MasterData.class));
        }

        if (!StringUtils.isEmpty(riskDTO.getProgress().getId())) {
            MasterData progress = this.masterDataRepository.findById(riskDTO.getProgress().getId()).get();
            if (risk.getProgress().getValue().equals("COMPLETED")) {
                throw new Exception("Cập nhật trạng thái không hợp lệ !");
            }
            else if (progress.getValue().equals("COMPLETED") && risk.getProgress().getValue().equals("IN-PROGRESS")) {
                risk.setProgress(progress);
                risk.setCompletedDate(new Date());
                this.emailService.sendProgressRiskEmail(risk.getReporter().getEmail(), "Cập nhật trạng thái của vấn đề: " + risk.getName(), risk.getProgress().getValue());
            }
            else if (progress.getValue().equals("IN-PROGRESS") && risk.getProgress().getValue().equals("NEW")) {
                risk.setProgress(progress);
                this.emailService.sendProgressRiskEmail(risk.getReporter().getEmail(), "Cập nhật trạng thái của vấn đề: " + risk.getName(), risk.getProgress().getValue());
            }
            else {
                throw new Exception("Cập nhật trạng thái không hợp lệ !");
            }
            risk.setProgress(this.modelMapperUtil.getModelMapper().map(riskDTO.getProgress(), MasterData.class));
        }

        if (!StringUtils.isEmpty(riskDTO.getReportedClass().getId())) {
            risk.setReportedClass(this.modelMapperUtil.getModelMapper().map(riskDTO.getReportedClass(), MasterData.class));
        }

        Risk savedRisk = this.riskRepository.save(risk);
        return this.modelMapperUtil.getModelMapper().map(savedRisk, RiskDTO.class);
    }

    @Override
    public void delete(String id) {
        Risk risk = this.riskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.Risk.RESOURCE, ExceptionConstant.Risk.ID_FIELD, id));
        this.riskRepository.delete(risk);
    }

    @Override
    public TrackingDTO trackTask(String classID) {
        List<RiskDTO> risk = new ArrayList<>();
        if (StringUtils.isBlank(classID)) {
            risk = this.getAllRisks();
        }
        else {
            risk = this.riskRepository.findByReportedClassId(classID).stream().map(r -> this.modelMapperUtil.getModelMapper().map(r, RiskDTO.class)).collect(Collectors.toList());
        }
        return TrackingDTO.builder().newTask(risk.stream().filter(r -> r.getProgress().getValue().equals("NEW")).count())
                .inProgressTask(risk.stream().filter(r -> r.getProgress().getValue().equals("IN-PROGRESS")).count())
                .completedTask(risk.stream().filter(r -> r.getProgress().getValue().equals("COMPLETED")).count())
                .build();
    }

    @Override
    public List<RiskDTO> getAllRisksOfClass(String classID) {
        return this.riskRepository.findByReportedClassId(classID).stream().map(r -> this.modelMapperUtil.getModelMapper().map(r, RiskDTO.class)).collect(Collectors.toList());
    }
}
