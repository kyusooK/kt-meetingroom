package meetingroom.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import meetingroom.ResourcemanagementApplication;
import meetingroom.domain.FacilityCreated;
import meetingroom.domain.FacilityDecreased;
import meetingroom.domain.FacilityDeleted;
import meetingroom.domain.FacilityModified;

@Entity
@Table(name = "FacilityRequest_table")
@Data
//<<< DDD / Aggregate Root
public class FacilityRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long facilityRequestId;

    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    private Integer quantity;

    @PostPersist
    public void onPostPersist() {
        FacilityCreated facilityCreated = new FacilityCreated(this);
        facilityCreated.publishAfterCommit();
    }


    @PostUpdate
    public void onPostUpdate() {
        FacilityModified facilityModified = new FacilityModified(this);
        facilityModified.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        FacilityDeleted facilityDeleted = new FacilityDeleted(this);
        facilityDeleted.publishAfterCommit();
    }

    public static FacilityRequestRepository repository() {
        FacilityRequestRepository facilityRequestRepository = ResourcemanagementApplication.applicationContext.getBean(
            FacilityRequestRepository.class
        );
        return facilityRequestRepository;
    }

    //<<< Clean Arch / Port Method
    public static void decreaseFacility(ReservationCreated reservationCreated) {
        
        ObjectMapper mapper = new ObjectMapper();
        Map<Long, Object> reservationMap = mapper.convertValue(reservationCreated.getFacilityRequestId(), Map.class);
        System.out.println(reservationMap);

        repository().findById(((Integer) reservationMap.get("id")).longValue()).ifPresent(facilityRequest->{
            
            facilityRequest.setQuantity(facilityRequest.getQuantity() - 1); // do something
            repository().save(facilityRequest);

            FacilityDecreased facilityDecreased = new FacilityDecreased(facilityRequest);
            facilityDecreased.publishAfterCommit();

         });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
