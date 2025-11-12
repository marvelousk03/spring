package tacos.tacos06.data;
import org.springframework.data.repository.CrudRepository;
import tacos.tacos06.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
