package com.volcanoes.volcanoes.Controllers;

import com.volcanoes.volcanoes.Models.Volcano;
import com.volcanoes.volcanoes.Services.VolcanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class VolcanoesController {


    private VolcanoService volcanoService;

    @Autowired
    public VolcanoesController(VolcanoService service) {
        this.volcanoService = service;
        try {dsd
            volcanoService.loadVolcanos();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/volcanoes/{year}")
    public List<Volcano> getVolcanoesByYear(@PathVariable Integer year) {

        return volcanoService.displayVolcanoesErupted1970(year);

    }
}
