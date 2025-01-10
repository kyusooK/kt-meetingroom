package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FacilityModified extends AbstractEvent {

    private Long facilityRequestId;
    private ResourceType resourceType;

    public FacilityModified(FacilityRequest aggregate) {
        super(aggregate);
    }

    public FacilityModified() {
        super();
    }
}
//>>> DDD / Domain Event
