package tacos.tacos06.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import tacos.tacos06.Ingredient;
import tacos.tacos06.Ingredient.Type;
import tacos.tacos06.TacoOrder;
import tacos.tacos06.Taco;
import tacos.tacos06.User;
import tacos.tacos06.data.IngredientRepository;
import tacos.tacos06.data.TacoRepository;
import tacos.tacos06.data.UserRepository;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

  private final IngredientRepository ingredientRepo;

  private TacoRepository tacoRepo;

  private UserRepository userRepo;

  @Autowired
  public DesignTacoController(
        IngredientRepository ingredientRepo,
        TacoRepository tacoRepo,
        UserRepository userRepo) {
    this.ingredientRepo = ingredientRepo;
    this.tacoRepo = tacoRepo;
    this.userRepo = userRepo;
  }

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepo.findAll().forEach(i -> ingredients.add(i));

    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }
  }

  @ModelAttribute(name = "order")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @ModelAttribute(name = "user")
  public User user(Principal principal) {
	    String username = principal.getName();
	    User user = userRepo.findByUsername(username);
	    return user;
  }


  @GetMapping
  public String showDesignForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(
      @Valid Taco taco, Errors errors,
      @ModelAttribute TacoOrder order) {

    if (errors.hasErrors()) {
      return "design";
    }

    Taco saved = tacoRepo.save(taco);
    order.addTaco(saved);

    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

}
