package com.alpha.functions.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "version"}), @UniqueConstraint(columnNames = {"alphaName"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlphaFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alpha_function_sequence")
    @SequenceGenerator(name = "alpha_function_sequence", sequenceName = "alpha_function_sequence", allocationSize = 1)
    private Long id;

    private String alphaName;

    private Long version;

    private String runtime;

    private String language;

    private String handler;

    private String description;

    private Boolean isLatestVersion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id")
    @JoinColumn(name = "version", referencedColumnName = "version")
    @JsonManagedReference
    private List<FileUtility> fileUtilities;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime createdDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime updatedDateTime;

    private String repositoryUserName;

    private boolean parallelExecutionEnabled;

    private int parallelReplicas;

    private long timeout;

    private String minimumCpu;

    private String maximumCpu;

    private String minimumMemory;

    private String maximumMemory;

    private String envVariables;

    private boolean isExecuted;

    private int maxRetries;

    @Transient
    private Map<String,Object> data;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime lastExecutionStartDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime lastExecutionEndDateTime;

    @PrePersist
    public void prePersist() {
        if (this.fileUtilities != null && !this.fileUtilities.isEmpty()) {
            this.fileUtilities = fileUtilities.stream().map(fileUtility -> {
                fileUtility.setId(this.id);
                fileUtility.setVersion(this.version);
                return fileUtility;
            }).collect(Collectors.toList());
        }
    }

}
