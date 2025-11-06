package tacos.tacos03.data;

import java.util.Optional;

import tacos.tacos03.Ingredient;

public interface IngredientRepository {

  Iterable<Ingredient> findAll();
  
  Optional<Ingredient> findById(String id);
  
  Ingredient save(Ingredient ingredient);
  
}
