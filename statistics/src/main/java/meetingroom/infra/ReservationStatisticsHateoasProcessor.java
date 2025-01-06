package meetingroom.infra;

import meetingroom.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ReservationStatisticsHateoasProcessor
    implements
        RepresentationModelProcessor<EntityModel<ReservationStatistics>> {

    @Override
    public EntityModel<ReservationStatistics> process(
        EntityModel<ReservationStatistics> model
    ) {
        return model;
    }
}
