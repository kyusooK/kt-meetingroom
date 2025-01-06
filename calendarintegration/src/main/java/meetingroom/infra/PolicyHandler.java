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
    NotificationRepository notificationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReservationCreated'"
    )
    public void wheneverReservationCreated_RegisterCalendar(
        @Payload ReservationCreated reservationCreated
    ) {
        ReservationCreated event = reservationCreated;
        System.out.println(
            "\n\n##### listener RegisterCalendar : " +
            reservationCreated +
            "\n\n"
        );

        // Sample Logic //
        Notification.registerCalendar(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReservationModified'"
    )
    public void wheneverReservationModified_SendToUser(
        @Payload ReservationModified reservationModified
    ) {
        ReservationModified event = reservationModified;
        System.out.println(
            "\n\n##### listener SendToUser : " + reservationModified + "\n\n"
        );

        // Sample Logic //
        Notification.sendToUser(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReservationCancelled'"
    )
    public void wheneverReservationCancelled_SendToUser(
        @Payload ReservationCancelled reservationCancelled
    ) {
        ReservationCancelled event = reservationCancelled;
        System.out.println(
            "\n\n##### listener SendToUser : " + reservationCancelled + "\n\n"
        );

        // Sample Logic //
        Notification.sendToUser(event);
    }

    // @StreamListener(
    //     value = KafkaProcessor.INPUT,
    //     condition = "headers['type']=='ReservationCreated'"
    // )
    // public void wheneverReservationCreated_SendToUser(
    //     @Payload ReservationCreated reservationCreated
    // ) {
    //     ReservationCreated event = reservationCreated;
    //     System.out.println(
    //         "\n\n##### listener SendToUser : " + reservationCreated + "\n\n"
    //     );

    //     // Sample Logic //
    //     Notification.sendToUser(event);
    // }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReservationCancelled'"
    )
    public void wheneverReservationCancelled_DeleteCalendar(
        @Payload ReservationCancelled reservationCancelled
    ) {
        ReservationCancelled event = reservationCancelled;
        System.out.println(
            "\n\n##### listener DeleteCalendar : " +
            reservationCancelled +
            "\n\n"
        );

        // Sample Logic //
        Notification.deleteCalendar(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
