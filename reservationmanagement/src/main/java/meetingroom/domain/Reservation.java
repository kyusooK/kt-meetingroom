package meetingroom.domain;

import meetingroom.domain.ReservationCreated;
import meetingroom.domain.ReservationRejected;
import meetingroom.external.MeetingRoom;
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

        List<MeetingRoom> meetingRooms = ReservationmanagementApplication.applicationContext
        .getBean(meetingroom.external.MeetingRoomService.class)
        .getMeetingRoom();

        MeetingRoom findRoom = meetingRooms.stream()
        .filter(room -> room.getRoomName().equals(this.getRoomName()))
        .findFirst()
        .orElse(null);

        ObjectMapper mapper = new ObjectMapper();
        Map<Long, Object> reservationMap = mapper.convertValue(getUserId(), Map.class);

        if (findRoom != null) {
            if (findRoom.getReservationStatus() == "AVAILABLED" && (reservationMap.get("rank").equals(findRoom.getRank()) || reservationMap.get("department").equals(findRoom.getDepartment()))) {
                this.setReservationStatus(ReservationStatus.RESERVED);
                ReservationCreated reservationCreated = new ReservationCreated(this);
                reservationCreated.publishAfterCommit();
            } else {
                ReservationRejected reservationRejected = new ReservationRejected(this);
                reservationRejected.publishAfterCommit();
            }
        } else {
            // 요청한 회의실을 찾을 수 없는 경우
            ReservationRejected reservationRejected = new ReservationRejected(this);
            reservationRejected.publishAfterCommit();
        }
    
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
