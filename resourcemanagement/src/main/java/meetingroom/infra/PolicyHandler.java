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
    FacilityRequestRepository facilityRequestRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MeetingCompleted'"
    )
    public void wheneverMeetingCompleted_UpdateFacilityStatus(
        @Payload MeetingCompleted meetingCompleted
    ) {
        MeetingCompleted event = meetingCompleted;
        System.out.println(
            "\n\n##### listener UpdateFacilityStatus : " +
            meetingCompleted +
            "\n\n"
        );

        // Sample Logic //
        FacilityRequest.updateFacilityStatus(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
