package ngsoft.exercise.productlistapi;

import ngsoft.exercise.productlistapi.services.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ProductListApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductListApiApplication.class, args);
    }
}
