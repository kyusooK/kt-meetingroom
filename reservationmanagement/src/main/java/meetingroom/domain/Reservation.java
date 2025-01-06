package meetingroom.domain;

import meetingroom.domain.ReservationCreated;
import meetingroom.domain.ReservationRejected;
import meetingroom.domain.ReservationModified;
import meetingroom.ReservationmanagementApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;


@Entity
@Table(name="Reservation_table")
@Data

//<<< DDD / Aggregate Root
public class Reservation  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    private Long reservationId;
    
    
    
    
    private Date startDate;
    
    
    
    
    private Date endDate;
    
    
    
    
    private String meetingName;
    
    
    
    
    private String location;
    
    
    
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    
    
    
    @Embedded
    private FacilityRequestId facilityRequestId;
    
    
    
    
    private String roomName;
    
    
    
    @Embedded
    private UserId userId;
    
    
    
    @Embedded
    private MeetingRoomId meetingRoomId;

    @PostPersist
    public void onPostPersist(){


        ReservationCreated reservationCreated = new ReservationCreated(this);
        reservationCreated.publishAfterCommit();



        ReservationRejected reservationRejected = new ReservationRejected(this);
        reservationRejected.publishAfterCommit();

    
    }
    @PrePersist
    public void onPrePersist(){
    FacilityRequest facilityRequest = ReservationApplication.applicationContext
        .getBean(meetingroom.external.FacilityRequestService.class)
        .getFacility(get??);
    
    }
    @PreUpdate
    public void onPreUpdate(){


        ReservationModified reservationModified = new ReservationModified(this);
        reservationModified.publishAfterCommit();

    
    }

    public static ReservationRepository repository(){
        ReservationRepository reservationRepository = ReservationmanagementApplication.applicationContext.getBean(ReservationRepository.class);
        return reservationRepository;
    }

    public void CreateReservation(){
        //
    }


//<<< Clean Arch / Port Method
    public void cancelReservation(CancelReservationCommand cancelReservationCommand){
        
        //implement business logic here:
        


        ReservationCancelled reservationCancelled = new ReservationCancelled(this);
        reservationCancelled.publishAfterCommit();
    }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    public void completemeeting(){
        
        //implement business logic here:
        


        MeetingCompleted meetingCompleted = new MeetingCompleted(this);
        meetingCompleted.publishAfterCommit();
    }
//>>> Clean Arch / Port Method



}
//>>> DDD / Aggregate Root
