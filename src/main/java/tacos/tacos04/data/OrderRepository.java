package tacos.tacos04.data;

import org.springframework.data.repository.CrudRepository;

import tacos.tacos04.TacoOrder;

public interface OrderRepository 
         extends CrudRepository<TacoOrder, String> {

}
