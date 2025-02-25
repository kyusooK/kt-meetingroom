package meetingroom.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import meetingroom.ReservationmanagementApplication;
import meetingroom.domain.ReservationCreated;
import meetingroom.domain.ReservationModified;
import meetingroom.domain.ReservationRejected;

@Entity
@Table(name = "Reservation_table")
@Data
//<<< DDD / Aggregate Root
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        meetingroom.external.Reservation reservation = new meetingroom.external.Reservation();
        // mappings goes here
        ReservationmanagementApplication.applicationContext
            .getBean(meetingroom.external.ReservationService.class)
            .createReservation(reservation);

        ReservationCreated reservationCreated = new ReservationCreated(this);
        reservationCreated.publishAfterCommit();

        ReservationRejected reservationRejected = new ReservationRejected(this);
        reservationRejected.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        ReservationModified reservationModified = new ReservationModified(this);
        reservationModified.publishAfterCommit();
    }

    public static ReservationRepository repository() {
        ReservationRepository reservationRepository = ReservationmanagementApplication.applicationContext.getBean(
            ReservationRepository.class
        );
        return reservationRepository;
    }

    //<<< Clean Arch / Port Method
    public void cancelReservation(
        CancelReservationCommand cancelReservationCommand
    ) {
        //implement business logic here:

        ReservationCancelled reservationCancelled = new ReservationCancelled(
            this
        );
        reservationCancelled.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void completemeeting() {
        //implement business logic here:

        MeetingCompleted meetingCompleted = new MeetingCompleted(this);
        meetingCompleted.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
