package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ReservationModified extends AbstractEvent {

    private Long reservationId;
    private Date startDate;
    private Date endDate;
    private String meetingName;
    private String location;
    private FacilityRequestId facilityRequestId;
    private String roomName;

    public ReservationModified(Reservation aggregate) {
        super(aggregate);
    }

    public ReservationModified() {
        super();
    }
}
//>>> DDD / Domain Event
