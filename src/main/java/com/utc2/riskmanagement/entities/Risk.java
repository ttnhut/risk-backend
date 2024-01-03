package com.utc2.riskmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Table(name = "risk")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Risk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    private String image;

    @ManyToOne
    @JoinColumn(name = "level")
    private MasterData level;

    @ManyToOne
    @JoinColumn(name = "progress")
    private MasterData progress;

    @ManyToOne
    @JoinColumn(name = "reported_class")
    private MasterData reportedClass;

    @ManyToOne
    @JoinColumn(name = "type_risk")
    private MasterData typeRisk;

    @ManyToOne
    @JoinColumn(name = "device")
    private MasterData device;

    @ManyToOne
    @JoinColumn(name = "assignee")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "reporter")
    private User reporter;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "completed_date")
    private Date completedDate;

    @Transient
    private MultipartFile file;
}
