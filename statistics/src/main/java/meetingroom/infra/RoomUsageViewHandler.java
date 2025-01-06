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
public class RoomUsageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private RoomUsageRepository roomUsageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMeetingRoomReservationAnalyzed_then_CREATE_1(
        @Payload MeetingRoomReservationAnalyzed meetingRoomReservationAnalyzed
    ) {
        try {
            if (!meetingRoomReservationAnalyzed.validate()) return;

            // view 객체 생성
            RoomUsage roomUsage = new RoomUsage();
            // view 객체에 이벤트의 Value 를 set 함
            roomUsage.setRoomId(meetingRoomReservationAnalyzed.getRoomId());
            roomUsage.setReservedCount(
                meetingRoomReservationAnalyzed.getReservedCount()
            );
            // view 레파지 토리에 save
            roomUsageRepository.save(roomUsage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenMeetingRoomCancelAnalyzed_then_UPDATE_1(
        @Payload MeetingRoomCancelAnalyzed meetingRoomCancelAnalyzed
    ) {
        try {
            if (!meetingRoomCancelAnalyzed.validate()) return;
            // view 객체 조회
            Optional<RoomUsage> roomUsageOptional = roomUsageRepository.findById(
                meetingRoomCancelAnalyzed.getStatisticsId()
            );

            if (roomUsageOptional.isPresent()) {
                RoomUsage roomUsage = roomUsageOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                roomUsage.setReservedCount(
                    meetingRoomCancelAnalyzed.getReservedCount()
                );
                // view 레파지 토리에 save
                roomUsageRepository.save(roomUsage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
