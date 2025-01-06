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

    @PostPersist
    public void onPostPersist() {
        UsingFacilityAnalyzed usingFacilityAnalyzed = new UsingFacilityAnalyzed(
            this
        );
        usingFacilityAnalyzed.publishAfterCommit();

        UsingFacilityRegistered usingFacilityRegistered = new UsingFacilityRegistered(
            this
        );
        usingFacilityRegistered.publishAfterCommit();
    }

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
        //implement business logic here:

        /** Example 1:  new item 
        FacilityStatistics facilityStatistics = new FacilityStatistics();
        repository().save(facilityStatistics);

        UsingFacilityAnalyzed usingFacilityAnalyzed = new UsingFacilityAnalyzed(facilityStatistics);
        usingFacilityAnalyzed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(facilityDecreased.get???()).ifPresent(facilityStatistics->{
            
            facilityStatistics // do something
            repository().save(facilityStatistics);

            UsingFacilityAnalyzed usingFacilityAnalyzed = new UsingFacilityAnalyzed(facilityStatistics);
            usingFacilityAnalyzed.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void registerUsingFacility(FacilityCreated facilityCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        FacilityStatistics facilityStatistics = new FacilityStatistics();
        repository().save(facilityStatistics);

        UsingFacilityRegistered usingFacilityRegistered = new UsingFacilityRegistered(facilityStatistics);
        usingFacilityRegistered.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(facilityCreated.get???()).ifPresent(facilityStatistics->{
            
            facilityStatistics // do something
            repository().save(facilityStatistics);

            UsingFacilityRegistered usingFacilityRegistered = new UsingFacilityRegistered(facilityStatistics);
            usingFacilityRegistered.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
