package meetingroom.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CheckFacilityCommand {

    private Long facilityRequestId;
    private Boolean isUsable;
}
