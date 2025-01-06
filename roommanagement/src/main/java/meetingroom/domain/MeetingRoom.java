package meetingroom.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import meetingroom.RoommanagementApplication;
import meetingroom.domain.RoomCreated;
import meetingroom.domain.RoomModified;
import meetingroom.domain.RoomStatusUpdated;

@Entity
@Table(name = "MeetingRoom_table")
@Data
//<<< DDD / Aggregate Root
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String roomName;

    private String location;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private String rank;

    private String department;

    @PostPersist
    public void onPostPersist() {
        RoomCreated roomCreated = new RoomCreated(this);
        roomCreated.publishAfterCommit();

        RoomStatusUpdated roomStatusUpdated = new RoomStatusUpdated(this);
        roomStatusUpdated.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        RoomModified roomModified = new RoomModified(this);
        roomModified.publishAfterCommit();
    }

    public static MeetingRoomRepository repository() {
        MeetingRoomRepository meetingRoomRepository = RoommanagementApplication.applicationContext.getBean(
            MeetingRoomRepository.class
        );
        return meetingRoomRepository;
    }

    //<<< Clean Arch / Port Method
    public static void updateRoomStatus(ReservationCreated reservationCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        MeetingRoom meetingRoom = new MeetingRoom();
        repository().save(meetingRoom);

        RoomStatusUpdated roomStatusUpdated = new RoomStatusUpdated(meetingRoom);
        roomStatusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if reservationCreated.facilityRequestIduserIdmeetingRoomId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> reservationMap = mapper.convertValue(reservationCreated.getFacilityRequestId(), Map.class);
        // Map<String, Object> reservationMap = mapper.convertValue(reservationCreated.getUserId(), Map.class);
        // Map<Long, Object> reservationMap = mapper.convertValue(reservationCreated.getMeetingRoomId(), Map.class);

        repository().findById(reservationCreated.get???()).ifPresent(meetingRoom->{
            
            meetingRoom // do something
            repository().save(meetingRoom);

            RoomStatusUpdated roomStatusUpdated = new RoomStatusUpdated(meetingRoom);
            roomStatusUpdated.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateRoomStatus(
        ReservationCancelled reservationCancelled
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        MeetingRoom meetingRoom = new MeetingRoom();
        repository().save(meetingRoom);

        RoomStatusUpdated roomStatusUpdated = new RoomStatusUpdated(meetingRoom);
        roomStatusUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if reservationCancelled.facilityRequestIduserIdmeetingRoomId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> reservationMap = mapper.convertValue(reservationCancelled.getFacilityRequestId(), Map.class);
        // Map<String, Object> reservationMap = mapper.convertValue(reservationCancelled.getUserId(), Map.class);
        // Map<Long, Object> reservationMap = mapper.convertValue(reservationCancelled.getMeetingRoomId(), Map.class);

        repository().findById(reservationCancelled.get???()).ifPresent(meetingRoom->{
            
            meetingRoom // do something
            repository().save(meetingRoom);

            RoomStatusUpdated roomStatusUpdated = new RoomStatusUpdated(meetingRoom);
            roomStatusUpdated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
