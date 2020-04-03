package ngsoft.exercise.productlistapi.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;

public class ImageService {

    public static byte[] fetchByProductId(String id) throws IOException {
        try {
            ClassPathResource image = new ClassPathResource("images/Zdrada.jpg");
            return StreamUtils.copyToByteArray(image.getInputStream());
        } catch (IOException ex) {
            throw new IOException("No image for this product");
        }
    }
}
