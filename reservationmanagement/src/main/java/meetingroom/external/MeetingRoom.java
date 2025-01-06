package meetingroom.external;

import java.util.Date;
import lombok.Data;

@Data
public class MeetingRoom {

    private Long id;
    private String roomName;
    private String location;
    private Object reservationStatus;
    private String rank;
    private String department;
}
