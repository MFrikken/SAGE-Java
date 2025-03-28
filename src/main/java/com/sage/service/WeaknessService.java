package com.sage.service;

import java.util.List;
import java.util.logging.Logger;

import com.sage.model.weakness.WeaknessDto;
import com.sage.model.weakness.WeaknessModel;
import com.sage.repository.WeaknessRepository;

public class WeaknessService {
    private static final Logger LOGGER = Logger.getLogger(WeaknessService.class.getName());

    private final WeaknessRepository weaknessRepository;

    public WeaknessService() {
        weaknessRepository = new WeaknessRepository();
    }

    public WeaknessDto getById(String id) {
        Integer idInt = Integer.parseInt(id);
        WeaknessModel model = weaknessRepository.getByID(idInt);
        if (model != null) {
            WeaknessDto dto = model.toWeaknessDto();
            return dto;
        }

        LOGGER.info("[WeaknessService] No entity found by (id)=" + id);
        return null;
    }
    
    public boolean save(WeaknessDto weaknessDto) {
        WeaknessModel model = weaknessDto.toModel(0, 2);
        return weaknessRepository.save(model);
    }
}
