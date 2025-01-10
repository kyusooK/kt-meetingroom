package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FacilityChecked extends AbstractEvent {

    private Long facilityRequestId;
    private ResourceType resourceType;
    private Boolean isUsable;

    public FacilityChecked(FacilityRequest aggregate) {
        super(aggregate);
    }

    public FacilityChecked() {
        super();
    }
}
//>>> DDD / Domain Event
