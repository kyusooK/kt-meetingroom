package meetingroom.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import meetingroom.StatisticsApplication;
import meetingroom.domain.UsingFacilityAnalyzed;
import meetingroom.domain.UsingFacilityRegistered;

@Entity
@Table(name = "FacilityStatistics_table")
@Data
//<<< DDD / Aggregate Root
public class FacilityStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long facilityId;

    private String facilityName;

    private Integer facilityCount;

    public static FacilityStatisticsRepository repository() {
        FacilityStatisticsRepository facilityStatisticsRepository = StatisticsApplication.applicationContext.getBean(
            FacilityStatisticsRepository.class
        );
        return facilityStatisticsRepository;
    }

    //<<< Clean Arch / Port Method
    public static void analyzeUsingFacility(
        FacilityDecreased facilityDecreased
    ) {
        repository().findByFacilityName(facilityDecreased.getResourceType().toString()).ifPresent(facilityStatistics->{
            facilityStatistics.setFacilityCount(facilityStatistics.getFacilityCount() + 1);
            repository().save(facilityStatistics);

            UsingFacilityAnalyzed usingFacilityAnalyzed = new UsingFacilityAnalyzed(facilityStatistics);
            usingFacilityAnalyzed.publishAfterCommit();

        });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void registerUsingFacility(FacilityCreated facilityCreated) {
        FacilityStatistics newStatistics = new FacilityStatistics();
            newStatistics.setFacilityName(facilityCreated.getResourceType().toString());
            newStatistics.setFacilityCount(1);
            repository().save(newStatistics);

            UsingFacilityRegistered usingFacilityRegistered = new UsingFacilityRegistered(newStatistics);
            usingFacilityRegistered.publishAfterCommit();

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
