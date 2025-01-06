package meetingroom.external;

import java.util.Date;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "roommanagement", url = "${api.url.roommanagement}")
public interface MeetingRoomService {
    @GetMapping(path = "/meetingRooms/search/getMeetingRoom")
    public List<MeetingRoom> getMeetingRoom();
}
