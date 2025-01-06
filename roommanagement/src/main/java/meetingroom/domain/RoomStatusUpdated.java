package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RoomStatusUpdated extends AbstractEvent {

    private Long id;
    private ReservationStatus reservationStatus;

    public RoomStatusUpdated(MeetingRoom aggregate) {
        super(aggregate);
    }

    public RoomStatusUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
