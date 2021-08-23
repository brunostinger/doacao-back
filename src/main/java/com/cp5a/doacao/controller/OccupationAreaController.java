package com.cp5a.doacao.controller;

import com.cp5a.doacao.model.OccupationArea;
import com.cp5a.doacao.service.OccupationAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/occupationarea")
@AllArgsConstructor
@Api(tags = {"Endpoint '/occupationarea'"})
public class OccupationAreaController {
    OccupationAreaService occupationAreaService;

    @ApiOperation(value = "Retorna uma lista contendo as áreas de atuação das instituições")
    @GetMapping("/list")
    @ResponseStatus(code = HttpStatus.OK)
    public List<OccupationArea> listAreas(){
        return occupationAreaService.listAreas();
    }

}
