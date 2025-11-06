package tacos.tacos02.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.validation.Errors;

import tacos.tacos02.TacoOrder;

@Controller
public class OrderController {

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @GetMapping("/orders/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping("/orders")
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        System.out.println("Order submitted: " + tacoOrder);
        return "redirect:/";
    }
}
