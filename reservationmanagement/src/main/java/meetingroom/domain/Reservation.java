package meetingroom.domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import meetingroom.ReservationmanagementApplication;
import meetingroom.external.MeetingRoom;

@Entity
@Table(name = "Reservation_table")
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

        ObjectMapper mapper = new ObjectMapper();
        Map<Long, Object> meetingRoomMap = mapper.convertValue(getMeetingRoomId(), Map.class);
        Map<Long, Object> userMap = mapper.convertValue(getUserId(), Map.class);

        MeetingRoom findRoom = ReservationmanagementApplication.applicationContext
        .getBean(meetingroom.external.MeetingRoomService.class)
        .getMeetingRoom((Long)meetingRoomMap.get("id"));

        RestTemplate restTemplate = new RestTemplate();
        String userServiceUrl = "http://localhost:8084/users/" + (Long)userMap.get("userId");
        ResponseEntity<Map> userInfo = restTemplate.getForEntity(userServiceUrl, Map.class);

        if (findRoom == null) {
            throw new IllegalArgumentException("요청한 회의실을 찾을 수 없습니다.");
        }
    
        if (!userInfo.getBody().get("rank").equals(findRoom.getRank()) && 
            !userInfo.getBody().get("department").equals(findRoom.getDepartment())) {
            throw new IllegalStateException("해당 회의실에 대한 예약 권한이 없습니다.");
        }
    

        this.setReservationStatus(ReservationStatus.RESERVED);
        this.setLocation(findRoom.getLocation());
        this.setRoomName(findRoom.getRoomName());

        repository().save(this);

        ReservationCreated reservationCreated = new ReservationCreated(this);
        reservationCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){
        ReservationModified reservationModified = new ReservationModified(this);
        reservationModified.publishAfterCommit();
    }

    public static ReservationRepository repository() {
        ReservationRepository reservationRepository = ReservationmanagementApplication.applicationContext.getBean(
            ReservationRepository.class
        );
        return reservationRepository;
    }

    public void cancelReservation(CancelReservationCommand cancelReservationCommand){
        
        repository().findById(reservationId).ifPresent(meetingRoom->{
            
            meetingRoom.setReservationStatus(ReservationStatus.AVAILABLED);
            repository().save(meetingRoom);

            ReservationCancelled reservationCancelled = new ReservationCancelled(this);
            reservationCancelled.publishAfterCommit();
        });
    }
    
    public void completemeeting(){
        repository().findById(reservationId).ifPresent(meetingRoom->{
            
            meetingRoom.setReservationStatus(ReservationStatus.AVAILABLED);
            repository().save(meetingRoom);

            MeetingCompleted meetingCompleted = new MeetingCompleted(this);
            meetingCompleted.publishAfterCommit();
        });

    }



}
//>>> DDD / Aggregate Root
