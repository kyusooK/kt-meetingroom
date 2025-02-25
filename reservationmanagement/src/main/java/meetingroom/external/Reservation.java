package meetingroom.external;

import java.util.Date;
import lombok.Data;

@Data
public class Reservation {

    private String taskId;
    private String userId;
    private String title;
    private String description;
    private Date dueDate;
    private Boolean now;
}
