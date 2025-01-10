package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FacilityStatusUpdated extends AbstractEvent {

    private Long facilityRequestId;
    private Boolean isUsable;

    public FacilityStatusUpdated(FacilityRequest aggregate) {
        super(aggregate);
    }

    public FacilityStatusUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
