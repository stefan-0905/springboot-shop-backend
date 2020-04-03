package ngsoft.exercise.productlistapi.services.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IStore {
    void store(MultipartFile file);
}
