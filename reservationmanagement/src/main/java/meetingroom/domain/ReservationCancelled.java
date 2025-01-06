package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ReservationCancelled extends AbstractEvent {

    private Long reservationId;
    private ReservationStatus reservationStatus;
    private MeetingRoomId meetingRoomId;

    public ReservationCancelled(Reservation aggregate) {
        super(aggregate);
    }

    public ReservationCancelled() {
        super();
    }
}
//>>> DDD / Domain Event
