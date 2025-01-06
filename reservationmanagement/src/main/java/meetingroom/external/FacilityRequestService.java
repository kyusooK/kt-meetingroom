package meetingroom.external;

import java.util.Date;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resourcemanagement", url = "${api.url.resourcemanagement}")
public interface FacilityRequestService {
    @GetMapping(path = "/facilityRequests/{facilityRequestId}")
    public FacilityRequest getFacility(
        @PathVariable("facilityRequestId") Long facilityRequestId
    );
}
