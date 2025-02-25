package meetingroom.external;

import java.util.Date;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accesscontrol", url = "${api.url.accesscontrol}")
public interface UserService {
    @GetMapping(path = "/users/{userId}")
    public User getUser(@PathVariable("userId") String userId);
}
