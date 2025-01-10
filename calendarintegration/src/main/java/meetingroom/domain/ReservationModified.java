package meetingroom.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
import meetingroom.infra.AbstractEvent;

@Data
@ToString
public class ReservationModified extends AbstractEvent {

    private Long reservationId;
    private Date startDate;
    private Date endDate;
    private String meetingName;
    private String location;
    private String roomName;
    private Object facilityRequestId;
}
