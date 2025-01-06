package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FacilityDeleted extends AbstractEvent {

    private Long facilityRequestId;

    public FacilityDeleted(FacilityRequest aggregate) {
        super(aggregate);
    }

    public FacilityDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
