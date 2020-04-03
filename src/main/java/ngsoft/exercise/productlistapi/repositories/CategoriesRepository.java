package ngsoft.exercise.productlistapi.repositories;

import ngsoft.exercise.productlistapi.documents.Brand;
import ngsoft.exercise.productlistapi.documents.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends MongoRepository<Category, String>, QuerydslPredicateExecutor<Category> {

}
