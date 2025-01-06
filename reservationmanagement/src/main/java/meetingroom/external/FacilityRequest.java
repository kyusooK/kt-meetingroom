package meetingroom.external;

import java.util.Date;
import lombok.Data;

@Data
public class FacilityRequest {

    private Long facilityRequestId;
    private Object resourceType;
    private Integer quantity;
}
