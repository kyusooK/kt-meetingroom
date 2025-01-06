package meetingroom.domain;

import meetingroom.external.MeetingRoom;
import meetingroom.ReservationmanagementApplication;
import javax.persistence.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

        ObjectMapper mapper = new ObjectMapper();
        Map<Long, Object> meetingRoomMap = mapper.convertValue(getMeetingRoomId(), Map.class);
        Map<Long, Object> userMap = mapper.convertValue(getUserId(), Map.class);

        MeetingRoom findRoom = ReservationmanagementApplication.applicationContext
        .getBean(meetingroom.external.MeetingRoomService.class)
        .getMeetingRoom((Long)meetingRoomMap.get("id"));

        RestTemplate restTemplate = new RestTemplate();
        String userServiceUrl = "http://localhost:8084/users/" + (Long)userMap.get("id");
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
        ReservationCreated reservationCreated = new ReservationCreated(this);
        reservationCreated.publishAfterCommit();
    
    }

    @PostUpdate
    public void onPostUpdate(){
        ReservationModified reservationModified = new ReservationModified(this);
        reservationModified.publishAfterCommit();
    }

    public static ReservationRepository repository(){
        ReservationRepository reservationRepository = ReservationmanagementApplication.applicationContext.getBean(ReservationRepository.class);
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
