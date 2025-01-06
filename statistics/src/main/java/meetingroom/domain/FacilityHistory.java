package meetingroom.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "FacilityHistory_table")
@Data
public class FacilityHistory {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String facilityName;
    private String facilityCount;
}
