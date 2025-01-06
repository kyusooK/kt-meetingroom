package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RoomCreated extends AbstractEvent {

    private Long id;
    private String roomName;
    private String location;
    private ReservationStatus reservationStatus;

    public RoomCreated(MeetingRoom aggregate) {
        super(aggregate);
    }

    public RoomCreated() {
        super();
    }
}
//>>> DDD / Domain Event
