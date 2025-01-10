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
import meetingroom.domain.FacilityDeleted;
import meetingroom.domain.FacilityModified;
import meetingroom.domain.FacilityStatusUpdated;

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

    private Boolean isUsable;

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
    public void checkFacility(CheckFacilityCommand checkFacilityCommand) {
        //implement business logic here:

        FacilityChecked facilityChecked = new FacilityChecked(this);
        facilityChecked.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void updateFacilityStatus(MeetingCompleted meetingCompleted) {

        
        // if meetingCompleted.facilityRequestIduserIdmeetingRoomId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> reservationMap = mapper.convertValue(meetingCompleted.getFacilityRequestId(), Map.class);
        // Map<String, Object> reservationMap = mapper.convertValue(meetingCompleted.getUserId(), Map.class);
        // Map<Long, Object> reservationMap = mapper.convertValue(meetingCompleted.getMeetingRoomId(), Map.class);

        // repository().findById(meetingCompleted.get???()).ifPresent(facilityRequest->{
            
        //     facilityRequest.setQuantity(facilityRequest.getQuantity() - 1); // do something
        //     repository().save(facilityRequest);

        //     FacilityStatusUpdated facilityStatusUpdated = new FacilityStatusUpdated(facilityRequest);
        //     facilityStatusUpdated.publishAfterCommit();

        //  });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
