package meetingroom.domain;

import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

@Data
@ToString
public class MeetingCompleted extends AbstractEvent {

    private Long reservationId;
    private Object facilityRequestId;
    private Object reservationStatus;
}
