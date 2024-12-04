package dev.back_end.utility;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class ConvertImage {
    public static String convertMultipartFileToBase64WithMimeType(MultipartFile file) throws IOException {
        // Detect the content type (MIME type)
        String mimeType = file.getContentType();
        // Encode the bytes to Base64
        String base64 = Base64.getEncoder().encodeToString(file.getBytes());
        // Return Base64 string with MIME type
        return "data:" + mimeType + ";base64," + base64;
    }
}
