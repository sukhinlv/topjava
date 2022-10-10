package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.exception.NotFoundStorageException;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    @SuppressWarnings("FieldCanBeLocal")
    // TODO: remove hardcoded caloriesPerDay
    private static final int CALORIES_PER_DAY = 2000;
    private final Logger log = getLogger(MealServlet.class);
    private Storage<Meal, Integer> storage;

    @Override
    public void init() {
        storage = new MemoryMealStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        log.debug("parse request parameters");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String paramId = request.getParameter("id");
        Meal meal;
        if (paramId == null || paramId.isEmpty()) {
            meal = new Meal(dateTime, description, calories);
        } else {
            meal = new Meal(Integer.parseInt(paramId), dateTime, description, calories);
        }

        storage.save(meal);
        log.debug("redirect to meals list");
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO: remove hardcoded time
        LocalTime startTime = LocalTime.MIN;
        LocalTime endTime = LocalTime.MAX;

        log.debug("parse request parameters");
        String action = request.getParameter("action");
        Meal meal;
        switch (action == null ? "noAction" : action) {
            case "new":
                meal = MealsUtil.emptyMeal();
                break;
            case "view":
            case "edit":
                int id = getId(request);
                meal = storage.findById(id).orElseThrow(() -> new NotFoundStorageException(String.format("Meal with id = %d not found", id)));
                break;
            case "delete":
                storage.deleteById(getId(request));
            default:
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(storage.findAll(), startTime, endTime, CALORIES_PER_DAY));
                log.debug("redirect to meals list");
                request.getRequestDispatcher("mealsList.jsp").forward(request, response);
                return;
        }

        request.setAttribute("meal", meal);
        log.debug("redirect to meals edit/view with action={}", action);
        request.getRequestDispatcher(("view".equals(action) ? "mealView.jsp" : "mealEdit.jsp")).forward(request, response);
    }

    private static int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
