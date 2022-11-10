package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getAllMeals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String createMeal(Model model, HttpServletRequest request) {
        createOrUpdateMeal(model, request, true);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal(Model model, HttpServletRequest request) {
        createOrUpdateMeal(model, request, false);
        return "mealForm";
    }

    @GetMapping("/delete")
    public String deleteMeal(Model model, HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:meals";
    }

    private void createOrUpdateMeal(Model model, HttpServletRequest request, boolean isNew) {
        final Meal meal = isNew ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                super.get(getId(request));
        model.addAttribute("meal", meal);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
