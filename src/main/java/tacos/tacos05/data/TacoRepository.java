package tacos.tacos05.data;

import org.springframework.data.repository.CrudRepository;

import tacos.tacos05.Taco;

public interface TacoRepository 
         extends CrudRepository<Taco, Long> {

}
