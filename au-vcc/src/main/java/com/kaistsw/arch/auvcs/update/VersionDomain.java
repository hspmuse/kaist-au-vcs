package com.kaistsw.arch.auvcs.update;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "software_version")
@ToString
@Getter
public class VersionDomain {
    @Id
    private int version;
    private String filename;
    private LocalDateTime createdAt;
}
