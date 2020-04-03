package ngsoft.exercise.productlistapi.documents;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@QueryEntity
@Document(collection = "categories")
public class Category {

    @Id
    private String id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
