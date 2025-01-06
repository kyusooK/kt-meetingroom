package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ReservationRejected extends AbstractEvent {

    private Long reservationId;
    private Date startDate;
    private Date endDate;
    private String meetingName;
    private String location;
    private ReservationStatus reservationStatus;
    private FacilityRequestId facilityRequestId;
    private String roomName;
    private UserId userId;
    private MeetingRoomId meetingRoomId;

    public ReservationRejected(Reservation aggregate) {
        super(aggregate);
    }

    public ReservationRejected() {
        super();
    }
}
//>>> DDD / Domain Event
