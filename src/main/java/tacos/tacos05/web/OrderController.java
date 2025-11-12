package tacos.tacos05.web;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.tacos05.TacoOrder;
import tacos.tacos05.User;
import tacos.tacos05.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute TacoOrder order,
                            Model model) {
        // Pre-fill delivery info if empty
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }

        model.addAttribute("order", order);
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("order") TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            log.warn("⚠️ Order form has validation errors: {}", errors);
            return "orderForm";
        }

        // ✅ Link the order to the current user
        order.setUser(user);

        // ✅ Persist order (includes tacos linked via cascade)
        TacoOrder savedOrder = orderRepo.save(order);
        log.info("✅ Saved order ID={} for user={}", savedOrder.getId(), user.getUsername());

        // ✅ Clear the session to reset order for next time
        sessionStatus.setComplete();

        // Redirect home or confirmation
        return "redirect:/";
    }
}
