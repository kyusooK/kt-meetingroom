package meetingroom.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MyReservation_table")
@Data
public class MyReservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String roomName;
    private String location;
    private String reservationStatus;
    private Date startDate;
    private Date endDate;
}
