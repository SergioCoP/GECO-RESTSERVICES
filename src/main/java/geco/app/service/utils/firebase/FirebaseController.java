package geco.app.service.utils.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/api/image-upload")
@CrossOrigin("*")
public class FirebaseController {
    private final String FIREBASE_FILE = "geco-firebase.json";
    private HashMap<String, Object> response;

    @PostMapping("")
    public ResponseEntity<?> uploadImageIncidence(@RequestParam("image")MultipartFile image) {
        response = new HashMap<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FIREBASE_FILE);
            Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(inputStream)).build().getService();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            String format = sdf.format(date);

            String fileName = "incidences/" + format;

            BlobId blobId = BlobId.of("gs://geco-99573.appspot.com", fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(image.getContentType()).build();
            Blob blob = storage.create(blobInfo, image.getBytes());

            response.put("status", HttpStatus.OK);
            response.put("message", "Imagen almacenada con exito");
            response.put("imageUrl", blob.getMediaLink());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", "No se creó pudo mandar la imágen");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> uploadHotelLogo(@RequestParam("image") MultipartFile image) {
        response = new HashMap<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FIREBASE_FILE);
            Storage storage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(inputStream)).build().getService();

            String fileName = "logos/" + image.getOriginalFilename();

            BlobId blobId = BlobId.of("geco-99573.appspot.com", fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(image.getContentType()).build();
            Blob blob = storage.create(blobInfo, image.getBytes());

            response.put("status", HttpStatus.OK);
            response.put("message", "Imagen almacenada con exito");
            response.put("imageUrl", blob.getMediaLink());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", "No se creó pudo mandar la imágen");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
