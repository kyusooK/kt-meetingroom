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
    private Integer reservedCount;
    private ReservationStatus reservationStatus;
    private String roomName;

    public MeetingRoomReservationAnalyzed(ReservationStatistics aggregate) {
        super(aggregate);
    }

    public MeetingRoomReservationAnalyzed() {
        super();
    }
}
//>>> DDD / Domain Event
