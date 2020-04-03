package ngsoft.exercise.productlistapi.repositories;

import ngsoft.exercise.productlistapi.documents.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends MongoRepository<Brand, String>, QuerydslPredicateExecutor<Brand> {
}
