package com.kaistsw.arch.auvcs.update;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RestController
public class UpdateController {

    @Autowired
    private VersionDomainRepository versionDomainRepository;

    private static Map<Integer,String> versionMap;

    static {
        versionMap = new HashMap<>();
        versionMap.put(1,"version_1.zip");
        versionMap.put(2,"version_2.zip");
    }

    private static final String SERVER_LOCATION = "/Users/hspmuse/Documents/kaist/sw-arch/sharedfolder";
    private static final String PREFIX = "version_";
    private static final String EXTENSION = ".zip";

    @GetMapping("update/check/{version}")
    public Integer check(@PathVariable Integer version) {

        log.info("version={}", version);
        List<VersionDomain> versionDomainList = versionDomainRepository.findByVersionGreaterThan(version);
        log.info("versionDomainList = {}", versionDomainList);
        if(versionDomainList == null || versionDomainList.size() == 0) return version;
        else {
            return versionDomainList.stream()
                        .sorted(Comparator.comparing(e -> e.getVersion()))
                        .findFirst().get().getVersion();
        }
    }

    @GetMapping("update/download/{version}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer version) throws IOException {

        log.debug("version={}", version);
        Optional<VersionDomain> versionDomain = versionDomainRepository.findById(version);
        log.info("versionDomain={}", versionDomain.get());

        File file = new File(SERVER_LOCATION + File.separator + versionDomain.get().getFilename());
        log.debug("file name={}", file.getName());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
