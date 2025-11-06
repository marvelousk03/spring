package tacos.tacos03.web;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.tacos03.Taco;
import tacos.tacos03.TacoOrder;
import tacos.tacos03.Ingredient.Type;
import tacos.tacos03.Ingredient;
import tacos.tacos03.data.IngredientRepository;

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

  private final IngredientRepository ingredientRepo;

  @Autowired
  public DesignTacoController(
        IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    Iterable<Ingredient> ingredients = ingredientRepo.findAll();
    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @GetMapping
  public String showDesignForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(
      @Valid Taco taco, Errors errors,
      @ModelAttribute TacoOrder tacoOrder) {

    if (errors.hasErrors()) {
      return "design";
    }

    tacoOrder.addTaco(taco);

    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByType(
      Iterable<Ingredient> ingredients, Type type) {
    return StreamSupport.stream(ingredients.spliterator(), false)
              .filter(i -> i.getType().equals(type))
              .collect(Collectors.toList());
  }

}
