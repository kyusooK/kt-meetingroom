package meetingroom.infra;

import meetingroom.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class FacilityStatisticsHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<FacilityStatistics>> {

    @Override
    public EntityModel<FacilityStatistics> process(
        EntityModel<FacilityStatistics> model
    ) {
        return model;
    }
}
