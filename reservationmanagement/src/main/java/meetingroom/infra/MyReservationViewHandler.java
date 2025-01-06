package meetingroom.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import meetingroom.config.kafka.KafkaProcessor;
import meetingroom.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MyReservationViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MyReservationRepository myReservationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationCreated_then_CREATE_1(
        @Payload ReservationCreated reservationCreated
    ) {
        try {
            if (!reservationCreated.validate()) return;

            // view 객체 생성
            MyReservation myReservation = new MyReservation();
            // view 객체에 이벤트의 Value 를 set 함
            myReservation.setRoomName(reservationCreated.getRoomName());
            myReservation.setLocation(reservationCreated.getLocation());
            myReservation.setStartDate(reservationCreated.getStartDate());
            myReservation.setEndDate(reservationCreated.getEndDate());
            myReservation.setReservationStatus(
                String.valueOf(reservationCreated.getReservationStatus())
            );
            // view 레파지 토리에 save
            myReservationRepository.save(myReservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
