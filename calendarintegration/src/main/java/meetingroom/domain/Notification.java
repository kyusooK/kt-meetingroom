package meetingroom.domain;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import meetingroom.CalendarintegrationApplication;
import meetingroom.domain.CalendarRegistered;

@Entity
@Table(name = "Notification_table")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;
    private Date startDate;
    private Date endDate;
    private String roomName;
    private String location;
    private String message;
    private String meetingName;

    public static NotificationRepository repository() {
        return CalendarintegrationApplication.applicationContext.getBean(NotificationRepository.class);
    }

    public static void registerCalendar(ReservationCreated reservationCreated) {
        ObjectMapper mapper = new ObjectMapper();
        Map<Long, Object> reservationMap = mapper.convertValue(reservationCreated.getUserId(), Map.class);

        Notification notification = new Notification();
        notification.setUserId(reservationMap.get("userId").toString());
        notification.setLocation(reservationCreated.getLocation());
        notification.setMeetingName(reservationCreated.getMeetingName());
        notification.setStartDate(reservationCreated.getStartDate());
        notification.setEndDate(reservationCreated.getEndDate());
        notification.setRoomName(reservationCreated.getRoomName());
        repository().save(notification);

        GoogleCalendarService.addEventToCalendar(notification);

        CalendarRegistered calendarRegistered = new CalendarRegistered(notification);
        calendarRegistered.publishAfterCommit();
     
    }

}