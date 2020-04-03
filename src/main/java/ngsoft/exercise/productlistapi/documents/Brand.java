package ngsoft.exercise.productlistapi.documents;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@QueryEntity
@Document(collection = "brands")
public class Brand {

    @Id
    private String id;
    @NotNull
    private String title;
    private String headquarters;

    public Brand(String title, String headquarters) {
        this.title = title;
        this.headquarters = headquarters;
    }

    public String getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", headquarters='" + headquarters + '\'' +
                '}';
    }
}
