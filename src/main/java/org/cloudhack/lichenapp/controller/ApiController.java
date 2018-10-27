package org.cloudhack.lichenapp.controller;

import lombok.RequiredArgsConstructor;
import org.cloudhack.lichenapp.model.AtmosphereIndexes;
import org.cloudhack.lichenapp.model.LichenReport;
import org.cloudhack.lichenapp.model.LichenReportSingle;
import org.cloudhack.lichenapp.repository.AtmosphereIndexRepositories;
import org.cloudhack.lichenapp.repository.LichenReportRepositories;
import org.cloudhack.lichenapp.repository.LichenReportSingleRepositories;
import org.cloudhack.lichenapp.service.LichenAnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApiController {
    public final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private LichenReportRepositories lichenReportRepositories;
    @Autowired
    private LichenReportSingleRepositories lichenReportSingleRepositories;
    @Autowired
    private AtmosphereIndexRepositories atmosphereIndexRepositories;
    @Autowired
    private LichenAnalyticsService lichenAnalyticsService;

    /**
     * Api REST V2, por compatibilidad de la anterior por ahora subsisten ambas
     * la idea es que una vez aprobado este modelo por el nuestros consejales teoricos
     * la api V1 sea eliminad junto con su escaso DataSet
     */

    @RequestMapping(value="/reportv2", method=RequestMethod.POST)
    public ResponseEntity reportSingleResponse(@RequestBody LichenReportSingle report){
        logger.info(report.toString());
        lichenReportSingleRepositories.save(report);
        return ResponseEntity.ok()
                .build();
    }

    @RequestMapping(value="/reportv2", method=RequestMethod.GET)
    public ResponseEntity<List<LichenReportSingle>> getLastReportsSingle(@RequestParam("last") Integer lasts){
        logger.info("Retrieving lasts {} reports from Database",lasts);
        return ResponseEntity.ok()
                .body(lichenReportSingleRepositories.findTop1000ByOrderByDatetimeDesc()
                        .stream()
                        .limit(lasts)
                        .collect(Collectors.toList()));
    }

    /**
     * Por otro lado aun hace falta trabajo de compatibilizacion entre el nuevo modelo
     * propuesto en la V2, y los calculos de indices actuales
     * @param report
     * @return
     */

    @RequestMapping(value="/report", method=RequestMethod.POST)
    public ResponseEntity<AtmosphereIndexes> reportResponse(@RequestBody LichenReport report){
        logger.info(report.toString());
        lichenReportRepositories.save(report);
        AtmosphereIndexes atmosphereIndexes = AtmosphereIndexes.builder()
                .iapf(lichenAnalyticsService.getIAPF(report))
                .iapq(lichenAnalyticsService.getIAPQ(report))
                .build();
        atmosphereIndexRepositories.save(atmosphereIndexes);
        return ResponseEntity.ok()
                .body(atmosphereIndexes);
    }

    @RequestMapping(value="/report", method=RequestMethod.GET)
    public ResponseEntity<List<LichenReport>> getLastReports(@RequestParam("last") Integer lasts){
        logger.info("Retrieving lasts {} reports from Database",lasts);
        return ResponseEntity.ok()
                .body(lichenReportRepositories.findTop1000ByOrderByDatetimeDesc()
                        .stream()
                        .limit(lasts)
                        .collect(Collectors.toList()));
    }


    @RequestMapping(value="/atmos", method=RequestMethod.GET)
    public ResponseEntity<AtmosphereIndexes> getLastIndex(){
        logger.info("Retrieving index actual atmosphere reports from Database");
        return ResponseEntity.ok()
                .body(atmosphereIndexRepositories.findById(1)
                        .orElseGet(() -> AtmosphereIndexes.builder()
                                        .iapf(0)
                                        .iapq(0).build()));
    }

}