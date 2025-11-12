package tacos.tacos04.data;

import org.springframework.data.repository.CrudRepository;

import tacos.tacos04.Ingredient;

public interface IngredientRepository 
         extends CrudRepository<Ingredient, String> {
  
}
