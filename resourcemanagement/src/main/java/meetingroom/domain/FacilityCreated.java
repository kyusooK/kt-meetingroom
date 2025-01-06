package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FacilityCreated extends AbstractEvent {

    private Long facilityRequestId;
    private ResourceType resourceType;
    private Integer quantity;

    public FacilityCreated(FacilityRequest aggregate) {
        super(aggregate);
    }

    public FacilityCreated() {
        super();
    }
}
//>>> DDD / Domain Event
