package tacos.tacos06.data;

import org.springframework.data.repository.CrudRepository;

import tacos.tacos06.Taco;

public interface TacoRepository 
         extends CrudRepository<Taco, Long> {

}
