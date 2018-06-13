package ir.geeglo.business.data.business;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Repository
public class GoogleBusiness {
    static Logger logger = Logger.getLogger(GoogleBusiness.class);
    private NetHttpTransport netHttpTransport;
    private JacksonFactory defaultInstance;
    private File secretFile;
    private FileReader fileReader;
    private GoogleClientSecrets clientSecrets;

    @PostConstruct
    public void postConstruct() {
        try {
            secretFile = new File(GoogleBusiness.class
                    .getResource("/client_secret.json").getFile());
            fileReader = new FileReader(secretFile);
            netHttpTransport = new NetHttpTransport();
            defaultInstance = JacksonFactory.getDefaultInstance();
            clientSecrets =
                    GoogleClientSecrets.load(
                            defaultInstance, fileReader);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public GoogleIdToken verify(String code) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                netHttpTransport, defaultInstance)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientSecrets.getDetails().getClientId()))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken = null;

        try {
            idToken = verifier.verify(code);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return idToken;
    }
}
