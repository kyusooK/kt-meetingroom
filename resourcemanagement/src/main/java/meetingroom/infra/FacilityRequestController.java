package meetingroom.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import meetingroom.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/facilityRequests")
@Transactional
public class FacilityRequestController {

    @Autowired
    FacilityRequestRepository facilityRequestRepository;

    @RequestMapping(
        value = "/facilityRequests/{id}/checkfacility",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public FacilityRequest checkFacility(
        @PathVariable(value = "id") Long id,
        @RequestBody CheckFacilityCommand checkFacilityCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /facilityRequest/checkFacility  called #####"
        );
        Optional<FacilityRequest> optionalFacilityRequest = facilityRequestRepository.findById(
            id
        );

        optionalFacilityRequest.orElseThrow(() ->
            new Exception("No Entity Found")
        );
        FacilityRequest facilityRequest = optionalFacilityRequest.get();
        facilityRequest.checkFacility(checkFacilityCommand);

        facilityRequestRepository.save(facilityRequest);
        return facilityRequest;
    }
}
//>>> Clean Arch / Inbound Adaptor
