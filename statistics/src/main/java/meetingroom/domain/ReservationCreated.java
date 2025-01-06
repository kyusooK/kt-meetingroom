package meetingroom.domain;

import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

@Data
@ToString
public class ReservationCreated extends AbstractEvent {

    private Long reservationId;
    private Date startDate;
    private Date endDate;
    private String meetingName;
    private String location;
    private Object reservationStatus;
    private Object facilityRequestId;
    private String roomName;
    private Object userId;
    private Object meetingRoomId;
}
