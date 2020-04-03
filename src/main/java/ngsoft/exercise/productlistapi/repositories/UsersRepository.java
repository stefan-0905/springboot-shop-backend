package ngsoft.exercise.productlistapi.repositories;

import ngsoft.exercise.productlistapi.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User> {
    User findByUsername(String username);
}
