package tacos.tacos05.data;

import org.springframework.data.repository.CrudRepository;

import tacos.tacos05.Ingredient;

public interface IngredientRepository 
         extends CrudRepository<Ingredient, String> {

}
