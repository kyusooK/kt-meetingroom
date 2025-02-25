package meetingroom.domain;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "meetingroom";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/service-account-key.json";

    public static Calendar getCalendarService() throws IOException, GeneralSecurityException {
        // GoogleCredentials를 생성하고 HttpCredentialsAdapter를 통해 변환
        GoogleCredentials googleCredentials = ServiceAccountCredentials.fromStream(
            GoogleCalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH))
            .createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar"));
        
        HttpCredentialsAdapter credential = new HttpCredentialsAdapter(googleCredentials);
        
        // 구글 캘린더 서비스 객체 생성
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void addEventToCalendar(Notification notification) {
        // 구글 캘린더 API와 연동하여 이벤트 등록 로직
        try {
            Calendar service = getCalendarService(); // 인증된 Calendar 서비스 객체를 가져오는 메소드

            // 시작 시간 설정 (예: 오전 9시)
            DateTime startDateTime = new DateTime(notification.getStartDate().getTime());
            EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Seoul");
    
            // 종료 시간 설정
            DateTime endDateTime = new DateTime(notification.getEndDate().getTime());
            EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Seoul");
    
            
            // 이벤트 생성
            Event event = new Event()
                .setSummary(notification.getMeetingName())
                .setLocation(notification.getLocation())
                .setStart(start)
                .setEnd(end);
            
            // 이벤트 추가
            String calendarId = "rbtn110@gmail.com"; // 기본 캘린더에 추가
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}