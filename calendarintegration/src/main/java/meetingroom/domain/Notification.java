package meetingroom.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import meetingroom.CalendarintegrationApplication;
import meetingroom.domain.CalendarDeleted;
import meetingroom.domain.CalendarRegistered;
import meetingroom.domain.NotificationSent;

@Entity
@Table(name = "Notification_table")
@Data
//<<< DDD / Aggregate Root
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    private String userId;

    private Date startDate;

    private Date endDate;

    private String roomName;

    private String location;

    private String message;

    private String meetingName;

    @PostPersist
    public void onPostPersist() {
        CalendarRegistered calendarRegistered = new CalendarRegistered(this);
        calendarRegistered.publishAfterCommit();

        NotificationSent notificationSent = new NotificationSent(this);
        notificationSent.publishAfterCommit();

        CalendarDeleted calendarDeleted = new CalendarDeleted(this);
        calendarDeleted.publishAfterCommit();
    }

    public static NotificationRepository repository() {
        NotificationRepository notificationRepository = CalendarintegrationApplication.applicationContext.getBean(
            NotificationRepository.class
        );
        return notificationRepository;
    }

    //<<< Clean Arch / Port Method
    public static void registerCalendar(ReservationCreated reservationCreated) {
        
        ObjectMapper mapper = new ObjectMapper();
        Map<Long, Object> reservationMap = mapper.convertValue(reservationCreated.getUserId(), Map.class);

        Notification notification = new Notification();
        notification.setUserId((String)reservationMap.get("userId"));
        notification.setLocation(reservationCreated.getLocation());
        notification.setMeetingName(reservationCreated.getMeetingName());
        notification.setStartDate(reservationCreated.getStartDate());
        notification.setEndDate(reservationCreated.getEndDate());
        notification.setRoomName(reservationCreated.getRoomName());
        repository().save(notification);

        CalendarRegistered calendarRegistered = new CalendarRegistered(notification);
        calendarRegistered.publishAfterCommit();

    }

    public static void sendToUser(ReservationCreated reservationCreated) {
        ObjectMapper mapper = new ObjectMapper();
        Map<Long, Object> reservationMap = mapper.convertValue(reservationCreated.getUserId(), Map.class);

        Notification notification = new Notification();
        notification.setUserId((String)reservationMap.get("userId"));
        notification.setMessage(reservationCreated.getMeetingName() + "회의가 예약되었습니다" + "장소:" + reservationCreated.getLocation() + "/" + reservationCreated.getRoomName() + " 예약 시간:" + reservationCreated.getStartDate() + "~" + reservationCreated.getEndDate()) ;
        repository().save(notification);

        CalendarRegistered calendarRegistered = new CalendarRegistered(notification);
        calendarRegistered.publishAfterCommit();

    }

    //<<< Clean Arch / Port Method
    public static void sendToUser(ReservationModified reservationModified) {
        
        repository().findById(reservationModified.getReservationId()).ifPresent(notification->{
            
            notification.setMessage(reservationModified.getMeetingName() + "회의가 예약되었습니다" + "장소:" + reservationModified.getLocation() + "/" + reservationModified.getRoomName() + " 예약 시간:" + reservationModified.getStartDate() + "~" + reservationModified.getEndDate()) ;
            repository().save(notification);
    
            CalendarDeleted calendarDeleted = new CalendarDeleted(notification);
            calendarDeleted.publishAfterCommit();
    
        });

    }

    public static void sendToUser(ReservationCancelled reservationCancelled) {
        repository().findById(reservationCancelled.getReservationId()).ifPresent(notification->{
            
            notification.setMessage("회의가 취소되었습니다") ;
            repository().save(notification);

            CalendarDeleted calendarDeleted = new CalendarDeleted(notification);
            calendarDeleted.publishAfterCommit();

        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void deleteCalendar(
        ReservationCancelled reservationCancelled
    ) {
        repository().findById(reservationCancelled.getReservationId()).ifPresent(notification->{
            
            notification.setMessage("회의가 취소되었습니다");
            repository().save(notification);

            CalendarDeleted calendarDeleted = new CalendarDeleted(notification);
            calendarDeleted.publishAfterCommit();

        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
