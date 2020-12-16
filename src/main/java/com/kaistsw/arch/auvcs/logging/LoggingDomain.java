package com.kaistsw.arch.auvcs.logging;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "loggin_store")
public class LoggingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="instanceId")
    private String instanceId;
    @Column(name="logStr")
    private String logStr;
    @Column(name="createdAt")
    private LocalDateTime createdAt;

    public static LoggingDomain of(LoggingParam param) {
        return new LoggingDomain(null, param.getInstanceId(), param.getLogStr(), LocalDateTime.now());
    }
}
