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
// @RequestMapping(value="/reservations")
@Transactional
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping(
        value = "/reservations/{id}/cancelreservation",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Reservation cancelReservation(
        @PathVariable(value = "id") Long id,
        @RequestBody CancelReservationCommand cancelReservationCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /reservation/cancelReservation  called #####"
        );
        Optional<Reservation> optionalReservation = reservationRepository.findById(
            id
        );

        optionalReservation.orElseThrow(() -> new Exception("No Entity Found"));
        Reservation reservation = optionalReservation.get();
        reservation.cancelReservation(cancelReservationCommand);

        reservationRepository.save(reservation);
        return reservation;
    }

    @RequestMapping(
        value = "/reservations/{id}/completemeeting",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Reservation completemeeting(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /reservation/completemeeting  called #####");
        Optional<Reservation> optionalReservation = reservationRepository.findById(
            id
        );

        optionalReservation.orElseThrow(() -> new Exception("No Entity Found"));
        Reservation reservation = optionalReservation.get();
        reservation.completemeeting();

        reservationRepository.save(reservation);
        return reservation;
    }
}
//>>> Clean Arch / Inbound Adaptor
