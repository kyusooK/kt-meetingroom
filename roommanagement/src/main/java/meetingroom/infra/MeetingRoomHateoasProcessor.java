package meetingroom.infra;

import meetingroom.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class MeetingRoomHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<MeetingRoom>> {

    @Override
    public EntityModel<MeetingRoom> process(EntityModel<MeetingRoom> model) {
        return model;
    }
}
