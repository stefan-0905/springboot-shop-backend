package ngsoft.exercise.productlistapi.seeders;

import ngsoft.exercise.productlistapi.documents.User;
import ngsoft.exercise.productlistapi.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserSeeder implements CommandLineRunner {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public UserSeeder(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User stex = new User("stex", passwordEncoder.encode("password"));
        User mixa = new User("mixa", passwordEncoder.encode("password"));

        //delete all users
        this.usersRepository.deleteAll();

        //add users to db
        List<User> users = Arrays.asList(stex,mixa);
        this.usersRepository.saveAll(users);
    }
}
