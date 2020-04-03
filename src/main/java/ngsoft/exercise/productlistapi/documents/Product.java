package ngsoft.exercise.productlistapi.documents;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@QueryEntity
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    @Min(value = 3, message = "Title needs to be at least 3 characters long.")
    private String title;
    @Positive(message = "Price needs to be greater then 0.")
    private Double price;
    private List<String> sizes;
    private String image;
    @DBRef
    private Category category;
    @DBRef
    private Brand brand;

    public Product() {
    }

    public Product(String title, Double price, List<String> sizes, String image, Category category, Brand brand) {
        this.title = title;
        this.price = price;
        this.sizes = sizes;
        this.image = image;
        this.category = category;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Brand getBrand() {
        return brand;
    }


    public List<String> getSizes() {
        return sizes;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", sizes=" + sizes +
                ", image='" + image + '\'' +
                ", category=" + category +
                ", brand=" + brand +
                '}';
    }
}
