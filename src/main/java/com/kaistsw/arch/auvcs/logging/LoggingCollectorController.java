package com.kaistsw.arch.auvcs.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoggingCollectorController {

    @Autowired
    private LoggingDomainRepository loggingDomainRepository;

    @PostMapping(value = "logging")
    public void collector(@RequestBody LoggingParam param) {
        log.debug("logging param = {}", param);
        loggingDomainRepository.save(LoggingDomain.of(param));
    }

    @GetMapping(value = "hello")
    public String hello() {
        log.debug("hello!!!");
        return "hello";
    }
}
