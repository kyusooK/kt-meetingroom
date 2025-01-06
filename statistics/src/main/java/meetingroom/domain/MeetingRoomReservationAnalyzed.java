package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MeetingRoomReservationAnalyzed extends AbstractEvent {

    private Long statisticsId;
    private Long roomId;
    private Integer reservedCount;
    private ReservationStatus reservationStatus;

    public MeetingRoomReservationAnalyzed(ReservationStatistics aggregate) {
        super(aggregate);
    }

    public MeetingRoomReservationAnalyzed() {
        super();
    }
}
//>>> DDD / Domain Event
