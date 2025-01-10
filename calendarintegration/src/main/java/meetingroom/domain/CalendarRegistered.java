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
    private String userId;
    private Date startDate;
    private Date endDate;
    private String roomName;
    private String location;
    private String message;

    public CalendarRegistered(Notification aggregate) {
        super(aggregate);
    }

    public CalendarRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
