package meetingroom.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import meetingroom.StatisticsApplication;

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
    public static void analyzeUsingFacility(MeetingCompleted meetingCompleted) {

        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> reservationMap = mapper.convertValue(meetingCompleted.getFacilityRequestId(), Map.class);
        // Map<String, Object> reservationMap = mapper.convertValue(meetingCompleted.getUserId(), Map.class);
        // Map<Long, Object> reservationMap = mapper.convertValue(meetingCompleted.getMeetingRoomId(), Map.class);

        // repository().findById(meetingCompleted.get???()).ifPresent(facilityStatistics->{
            
        //     facilityStatistics // do something
        //     repository().save(facilityStatistics);

        //     UsingFacilityAnalyzed usingFacilityAnalyzed = new UsingFacilityAnalyzed(facilityStatistics);
        //     usingFacilityAnalyzed.publishAfterCommit();

        // });

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
