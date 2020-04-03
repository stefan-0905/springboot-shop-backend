package ngsoft.exercise.productlistapi.repositories;

import ngsoft.exercise.productlistapi.documents.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends MongoRepository<Product, String>, QuerydslPredicateExecutor<Product> {

}
