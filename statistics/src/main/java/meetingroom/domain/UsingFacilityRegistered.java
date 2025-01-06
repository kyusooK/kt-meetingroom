package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class UsingFacilityRegistered extends AbstractEvent {

    private Long facilityId;
    private String facilityName;
    private Integer facilityCount;

    public UsingFacilityRegistered(FacilityStatistics aggregate) {
        super(aggregate);
    }

    public UsingFacilityRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
