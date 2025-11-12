package tacos.tacos06.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.tacos06.TacoOrder;
import tacos.tacos06.User;

public interface OrderRepository
         extends CrudRepository<TacoOrder, Long> {

  List<TacoOrder> findByUserOrderByPlacedAtDesc(
          User user, Pageable pageable);

  /*
  List<Order> findByUserOrderByPlacedAtDesc(User user);
   */

}
