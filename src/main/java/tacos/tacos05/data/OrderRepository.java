package tacos.tacos05.data;

import org.springframework.data.repository.CrudRepository;

import tacos.tacos05.TacoOrder;

public interface OrderRepository 
         extends CrudRepository<TacoOrder, Long> {

}
