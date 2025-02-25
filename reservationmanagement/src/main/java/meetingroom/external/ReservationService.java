package meetingroom.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "reservation", url = "${api.url.reservation}")
public interface ReservationService {
    @RequestMapping(method = RequestMethod.POST, path = "/reservations")
    public void createReservation(@RequestBody Reservation reservation);
}
