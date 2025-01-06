package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class UsingFacilityAnalyzed extends AbstractEvent {

    private Long facilityId;
    private String facilityName;
    private Integer facilityCount;

    public UsingFacilityAnalyzed(FacilityStatistics aggregate) {
        super(aggregate);
    }

    public UsingFacilityAnalyzed() {
        super();
    }
}
//>>> DDD / Domain Event
