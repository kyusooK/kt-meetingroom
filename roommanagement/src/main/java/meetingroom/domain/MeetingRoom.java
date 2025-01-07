package meetingroom.domain;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import meetingroom.RoommanagementApplication;

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

        repository().findById(reservationCreated.getReservationId()).ifPresent(meetingRoom->{
            
            meetingRoom.setReservationStatus(ReservationStatus.RESERVED);
            repository().save(meetingRoom);

            RoomStatusUpdated roomStatusUpdated = new RoomStatusUpdated(meetingRoom);
            roomStatusUpdated.publishAfterCommit();

        });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateRoomStatus(
        ReservationCancelled reservationCancelled
    ) {

        repository().findById(reservationCancelled.getReservationId()).ifPresent(meetingRoom->{
            
            meetingRoom.setReservationStatus(ReservationStatus.AVAILABLED);
            repository().save(meetingRoom);

            RoomStatusUpdated roomStatusUpdated = new RoomStatusUpdated(meetingRoom);
            roomStatusUpdated.publishAfterCommit();

        });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
