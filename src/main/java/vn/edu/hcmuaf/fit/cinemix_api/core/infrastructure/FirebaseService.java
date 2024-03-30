package vn.edu.hcmuaf.fit.cinemix_api.core.infrastructure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {

//    @PostConstruct
//    public void init() throws IOException {
//        FileInputStream serviceAccount =
//                new FileInputStream("./serviceAccountKey.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                                                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                                                 .setStorageBucket("travie-8acf1.appspot.com")
//                                                 .build();
//
//        FirebaseApp.initializeApp(options);
//    }
}
