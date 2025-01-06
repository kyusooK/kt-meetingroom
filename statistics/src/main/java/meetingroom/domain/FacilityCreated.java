package meetingroom.domain;

import java.util.*;
import lombok.*;
import meetingroom.domain.*;
import meetingroom.infra.AbstractEvent;

@Data
@ToString
public class FacilityCreated extends AbstractEvent {

    private Long facilityRequestId;
    private Object resourceType;
    private Integer quantity;
}
