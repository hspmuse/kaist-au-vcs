package com.kaistsw.arch.auvcs.update;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionDomainRepository extends JpaRepository<VersionDomain, Integer> {

    public List<VersionDomain> findByVersionGreaterThan(int version);
}
