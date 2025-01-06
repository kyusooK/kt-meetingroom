package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CalendarRegistered extends AbstractEvent {

    private Long notificationId;
    private Message message;
    private UserId userId;

    public CalendarRegistered(Notification aggregate) {
        super(aggregate);
    }

    public CalendarRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
