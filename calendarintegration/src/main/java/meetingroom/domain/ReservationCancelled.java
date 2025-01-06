package meetingroom.domain;

import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

@Data
@ToString
public class ReservationCancelled extends AbstractEvent {

    private Long reservationId;
    private Object reservationStatus;
    private Object meetingRoomId;
}
