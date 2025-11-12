package tacos.tacos05.data;
import org.springframework.data.repository.CrudRepository;
import tacos.tacos05.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
