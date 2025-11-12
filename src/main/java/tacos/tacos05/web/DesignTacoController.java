package tacos.tacos05.web;

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

import lombok.extern.slf4j.Slf4j;
import tacos.tacos05.Ingredient;
import tacos.tacos05.Ingredient.Type;
import tacos.tacos05.TacoOrder;
import tacos.tacos05.Taco;
import tacos.tacos05.User;
import tacos.tacos05.data.IngredientRepository;
import tacos.tacos05.data.TacoRepository;
import tacos.tacos05.data.UserRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepo;
    private final UserRepository userRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo,
                                TacoRepository tacoRepo,
                                UserRepository userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
        this.userRepo = userRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        for (Type type : Ingredient.Type.values()) {
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
        if (principal == null) {
            return null;
        }
        return userRepo.findByUsername(principal.getName());
    }

    @GetMapping
    public String showDesignForm(Model model) {
        if (!model.containsAttribute("taco")) {
            model.addAttribute("taco", new Taco());
        }
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid @ModelAttribute("taco") Taco taco,
            Errors errors,
            @ModelAttribute TacoOrder order,
            Model model) {

        if (errors.hasErrors()) {
            log.warn("Taco design has validation errors: {}", errors);
            return "design";
        }

        // ✅ Step 1: link taco to its order before saving
        taco.setTacoOrder(order);

        // ✅ Step 2: save taco
        Taco saved = tacoRepo.save(taco);

        // ✅ Step 3: add saved taco to the order
        order.addTaco(saved);

        log.info("✅ Saved taco '{}' linked to order ID: {}", saved.getName(), order.getId());

        // ✅ Step 4: redirect to order page
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
