package org.cloudhack.lichenapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class LichenReportSingle {
    @Id
    private String reportId;
    private double lat;
    private double lng;
    private int datetime;
    private Map<String,SpecimenData> samples;
}
