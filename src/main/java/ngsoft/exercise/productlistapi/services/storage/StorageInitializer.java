package ngsoft.exercise.productlistapi.services.storage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class StorageInitializer implements CommandLineRunner {

    private final Path rootLocation;

    public StorageInitializer(StorageProperties properties) {
        rootLocation = Paths.get("src/main/resources/uploads");
    }

    @Override
    public void run(String... args) throws Exception {
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
        try {
            Files.createDirectory(this.rootLocation);
        } catch(IOException ex) {
            System.out.println("Could not create directory!");
        }
    }
}
