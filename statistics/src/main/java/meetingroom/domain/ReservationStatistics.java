package meetingroom.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import meetingroom.StatisticsApplication;
import meetingroom.domain.MeetingRoomCancelAnalyzed;
import meetingroom.domain.MeetingRoomReservationAnalyzed;

@Entity
@Table(name = "ReservationStatistics_table")
@Data
//<<< DDD / Aggregate Root
public class ReservationStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long statisticsId;

    private Integer reservedCount;

    private Long roomId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @PostPersist
    public void onPostPersist() {
        MeetingRoomReservationAnalyzed meetingRoomReservationAnalyzed = new MeetingRoomReservationAnalyzed(
            this
        );
        meetingRoomReservationAnalyzed.publishAfterCommit();

        MeetingRoomCancelAnalyzed meetingRoomCancelAnalyzed = new MeetingRoomCancelAnalyzed(
            this
        );
        meetingRoomCancelAnalyzed.publishAfterCommit();
    }

    public static ReservationStatisticsRepository repository() {
        ReservationStatisticsRepository reservationStatisticsRepository = StatisticsApplication.applicationContext.getBean(
            ReservationStatisticsRepository.class
        );
        return reservationStatisticsRepository;
    }

    //<<< Clean Arch / Port Method
    public static void analyzeReservationMeeting(
        ReservationCreated reservationCreated
    ) {
        
        ReservationStatistics reservationStatistics = new ReservationStatistics();
        reservationStatistics.setReservedCount(1);
        reservationStatistics.setReservationStatus(ReservationStatus.RESERVED);
        repository().save(reservationStatistics);

        MeetingRoomReservationAnalyzed meetingRoomReservationAnalyzed = new MeetingRoomReservationAnalyzed(reservationStatistics);
        meetingRoomReservationAnalyzed.publishAfterCommit();
        

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void analyzeCancelReservation(
        ReservationCancelled reservationCancelled
    ) {
        

        repository().findById(reservationCancelled.getReservationId()).ifPresent(reservationStatistics->{
            
            reservationStatistics.setReservedCount(reservationStatistics.getReservedCount() - 1);
            reservationStatistics.setReservationStatus(ReservationStatus.CANCELED);
            repository().save(reservationStatistics);

            MeetingRoomCancelAnalyzed meetingRoomCancelAnalyzed = new MeetingRoomCancelAnalyzed(reservationStatistics);
            meetingRoomCancelAnalyzed.publishAfterCommit();
        });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
