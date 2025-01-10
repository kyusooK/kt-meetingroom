package meetingroom.infra;

import meetingroom.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class FacilityRequestHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<FacilityRequest>> {

    @Override
    public EntityModel<FacilityRequest> process(
        EntityModel<FacilityRequest> model
    ) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/checkfacility")
                .withRel("checkfacility")
        );

        return model;
    }
}
