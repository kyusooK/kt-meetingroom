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
// @RequestMapping(value="/facilityStatistics")
@Transactional
public class FacilityStatisticsController {

    @Autowired
    FacilityStatisticsRepository facilityStatisticsRepository;
}
//>>> Clean Arch / Inbound Adaptor
