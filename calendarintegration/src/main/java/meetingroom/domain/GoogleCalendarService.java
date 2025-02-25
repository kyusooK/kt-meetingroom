package meetingroom.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "meetingroom"; 
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json"; 

    public static Calendar getCalendarService() throws IOException, GeneralSecurityException {
        // 인증을 위한 Credential 객체 생성
        Credential credential = authorize();
        
        // 구글 캘린더 서비스 객체 생성
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = GoogleCalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new IOException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
    
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    
        // 인증 흐름 설정
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, Collections.singleton("https://www.googleapis.com/auth/calendar"))
                .setAccessType("offline")
                .build();
    
                String redirectUri = "urn:ietf:wg:oauth:2.0:oob"; // 로컬에서 테스트할 때 사용
                String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
                System.out.println("Please open the following URL in your browser:");
                System.out.println(authorizationUrl);
            
                // 사용자로부터 인증 코드를 입력받음
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter the authorization code: ");
                String code = br.readLine();

                GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
                Credential credential = flow.createAndStoreCredential(tokenResponse, "user");
    
        // 인증된 Credential 객체 반환
        return credential;
    }

    public static void addEventToCalendar(Notification notification) {
        // 구글 캘린더 API와 연동하여 이벤트 등록 로직
        try {
            Calendar service = getCalendarService(); // 인증된 Calendar 서비스 객체를 가져오는 메소드
            
            // 이벤트 생성
            Event event = new Event()
                .setSummary(notification.getMeetingName())
                .setLocation(notification.getLocation())
                .setStart(new EventDateTime().setDateTime(new DateTime(notification.getStartDate())))
                .setEnd(new EventDateTime().setDateTime(new DateTime(notification.getEndDate())));
            
            // 이벤트 추가
            String calendarId = "primary"; // 기본 캘린더에 추가
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}