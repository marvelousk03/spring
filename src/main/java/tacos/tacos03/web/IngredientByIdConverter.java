package tacos.tacos03.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.tacos03.data.IngredientRepository;
import tacos.tacos03.Ingredient;


@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

  private IngredientRepository ingredientRepo;

  @Autowired
  public IngredientByIdConverter(IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }

  @Override
  public Ingredient convert(String id) {
    return ingredientRepo.findById(id).orElse(null);
  }

}
