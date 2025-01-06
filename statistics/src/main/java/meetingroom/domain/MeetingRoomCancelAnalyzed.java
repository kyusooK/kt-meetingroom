package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MeetingRoomCancelAnalyzed extends AbstractEvent {

    private Long statisticsId;
    private Integer reservedCount;
    private Long roomId;
    private ReservationStatus reservationStatus;

    public MeetingRoomCancelAnalyzed(ReservationStatistics aggregate) {
        super(aggregate);
    }

    public MeetingRoomCancelAnalyzed() {
        super();
    }
}
//>>> DDD / Domain Event
