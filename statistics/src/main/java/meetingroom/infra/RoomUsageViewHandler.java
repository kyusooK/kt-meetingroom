package meetingroom.infra;

import meetingroom.domain.*;
import meetingroom.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RoomUsageViewHandler {

//<<< DDD / CQRS
    @Autowired
    private RoomUsageRepository roomUsageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMeetingRoomReservationAnalyzed_then_CREATE_1 (@Payload MeetingRoomReservationAnalyzed meetingRoomReservationAnalyzed) {
        try {

            if (!meetingRoomReservationAnalyzed.validate()) return;

            // view 객체 생성
            RoomUsage roomUsage = new RoomUsage();
            // view 객체에 이벤트의 Value 를 set 함
            roomUsage.setReservedCount(meetingRoomReservationAnalyzed.getReservedCount());
            roomUsage.setRoomName(meetingRoomReservationAnalyzed.getRoomName());
            // view 레파지 토리에 save
            roomUsageRepository.save(roomUsage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

//>>> DDD / CQRS
}

