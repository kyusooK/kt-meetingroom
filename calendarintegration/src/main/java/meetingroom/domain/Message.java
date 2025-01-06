package meetingroom.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

//<<< DDD / Value Object
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String location;

    private Date usingStartDate;

    private Date usingEndDate;

    private String meetingName;

    private ReservationStatus reservationStatus;
}
//>>> DDD / Value Object
