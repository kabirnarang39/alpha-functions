package com.alpha.functions.repositories;

import com.alpha.functions.entities.AlphaFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlphaFunctionRepository extends JpaRepository<AlphaFunction, Long> {

    AlphaFunction findByIdAndIsLatestVersion(Long id, Boolean isLatestVersion);

    List<AlphaFunction> findAllByIsLatestVersion(Boolean isLatestVersion);

    AlphaFunction findByIdAndVersion(Long id, Long version);

}
