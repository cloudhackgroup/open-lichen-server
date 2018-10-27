package org.cloudhack.lichenapp.repository;

import org.cloudhack.lichenapp.model.LichenReportSingle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichenReportSingleRepositories extends MongoRepository<LichenReportSingle, String> {
    public List<LichenReportSingle> findTop1000ByOrderByDatetimeDesc();
}
