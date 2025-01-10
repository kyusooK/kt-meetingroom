package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MeetingCompleted extends AbstractEvent {

    private Long reservationId;
    private ReservationStatus reservationStatus;
    private FacilityRequestId facilityRequestId;

    public MeetingCompleted(Reservation aggregate) {
        super(aggregate);
    }

    public MeetingCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
