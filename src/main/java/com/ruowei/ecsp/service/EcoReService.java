package com.ruowei.ecsp.service;

import com.ruowei.ecsp.repository.EcoResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EcoReService {

    private final EcoResourceRepository ecoResourceRepository;

    public EcoReService(EcoResourceRepository ecoResourceRepository) {
        this.ecoResourceRepository = ecoResourceRepository;
    }
}
