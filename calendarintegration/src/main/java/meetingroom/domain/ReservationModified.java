package meetingroom.domain;

import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

@Data
@ToString
public class ReservationModified extends AbstractEvent {

    private Long reservationId;
    private Date startDate;
    private Date endDate;
    private String meetingName;
    private String location;
    private Object facilityRequestId;
}
