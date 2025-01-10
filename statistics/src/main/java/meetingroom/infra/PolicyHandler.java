package meetingroom.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import meetingroom.config.kafka.KafkaProcessor;
import meetingroom.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    ReservationStatisticsRepository reservationStatisticsRepository;

    @Autowired
    FacilityStatisticsRepository facilityStatisticsRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReservationCreated'"
    )
    public void wheneverReservationCreated_AnalyzeReservationMeeting(
        @Payload ReservationCreated reservationCreated
    ) {
        ReservationCreated event = reservationCreated;
        System.out.println(
            "\n\n##### listener AnalyzeReservationMeeting : " +
            reservationCreated +
            "\n\n"
        );

        // Sample Logic //
        ReservationStatistics.analyzeReservationMeeting(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MeetingCompleted'"
    )
    public void wheneverMeetingCompleted_AnalyzeUsingFacility(
        @Payload MeetingCompleted meetingCompleted
    ) {
        MeetingCompleted event = meetingCompleted;
        System.out.println(
            "\n\n##### listener AnalyzeUsingFacility : " +
            meetingCompleted +
            "\n\n"
        );

        // Sample Logic //
        FacilityStatistics.analyzeUsingFacility(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FacilityCreated'"
    )
    public void wheneverFacilityCreated_RegisterUsingFacility(
        @Payload FacilityCreated facilityCreated
    ) {
        FacilityCreated event = facilityCreated;
        System.out.println(
            "\n\n##### listener RegisterUsingFacility : " +
            facilityCreated +
            "\n\n"
        );

        // Sample Logic //
        FacilityStatistics.registerUsingFacility(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReservationCancelled'"
    )
    public void wheneverReservationCancelled_AnalyzeCancelReservation(
        @Payload ReservationCancelled reservationCancelled
    ) {
        ReservationCancelled event = reservationCancelled;
        System.out.println(
            "\n\n##### listener AnalyzeCancelReservation : " +
            reservationCancelled +
            "\n\n"
        );

        // Sample Logic //
        ReservationStatistics.analyzeCancelReservation(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
