package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RoomModified extends AbstractEvent {

    private Long id;
    private String roomName;
    private String location;

    public RoomModified(MeetingRoom aggregate) {
        super(aggregate);
    }

    public RoomModified() {
        super();
    }
}
//>>> DDD / Domain Event
