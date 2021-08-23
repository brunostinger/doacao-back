package com.cp5a.doacao.service;

import com.cp5a.doacao.model.OccupationArea;
import com.cp5a.doacao.repository.OccupationAreaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OccupationAreaService {
    OccupationAreaRepository occupationAreaRepository;

    public List<OccupationArea> listAreas(){
        return occupationAreaRepository.findAll().stream().collect(Collectors.toList());
    }

}
