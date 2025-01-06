package meetingroom.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Table;

import lombok.Data;
import meetingroom.StatisticsApplication;

@Entity
@Table(name = "ReservationStatistics_table")
@Data
//<<< DDD / Aggregate Root
public class ReservationStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long statisticsId;

    private Integer reservedCount;

    private String roomName;


    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @PostPersist
    public void onPostPersist() {
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
        reservationStatistics.setRoomName(reservationCreated.getRoomName());
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
