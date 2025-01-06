package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CalendarDeleted extends AbstractEvent {

    private Long notificationId;

    public CalendarDeleted(Notification aggregate) {
        super(aggregate);
    }

    public CalendarDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
