package tacos.tacos03.data;

import java.util.Optional;

import tacos.tacos03.TacoOrder;

public interface OrderRepository {

  TacoOrder save(TacoOrder order);

  Optional<TacoOrder> findById(Long id);

}
