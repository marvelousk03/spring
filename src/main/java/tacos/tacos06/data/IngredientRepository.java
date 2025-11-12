package tacos.tacos06.data;

import org.springframework.data.repository.CrudRepository;

import tacos.tacos06.Ingredient;

public interface IngredientRepository 
         extends CrudRepository<Ingredient, String> {

}
