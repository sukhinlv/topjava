package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealMemoryStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private final Logger log = getLogger(MealServlet.class);
    @SuppressWarnings("FieldCanBeLocal")
    private final int CALORIES_PER_DAY = 2000;
    private Storage<Meal, Integer> storage;

    @Override
    public void init() {
        storage = new MealMemoryStorage();
        // TODO remove hardcoded storage initialization
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
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
        if (action == null) {
            redirectToMealsList(request, response, startTime, endTime);
            return;
        }
        int id;
        switch (action) {
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                storage.deleteById(id);
                log.debug("redirect to meals list with action=delete");
                response.sendRedirect("meals");
                return;
            case "view":
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                meal = storage.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Meal with id = %d not found", id)));
                break;
            case "new":
                meal = MealsUtil.EMPTY;
                break;
            default:
                redirectToMealsList(request, response, startTime, endTime);
                return;
        }

        request.setAttribute("meal", meal);
        log.debug("redirect to meals edit/view with action={}", action);
        request.getRequestDispatcher(("view".equals(action) ? "mealView.jsp" : "mealEdit.jsp")).forward(request, response);
    }

    private void redirectToMealsList(HttpServletRequest request, HttpServletResponse response, LocalTime startTime, LocalTime endTime) throws ServletException, IOException {
        request.setAttribute("mealsTo", MealsUtil.filteredByStreams(storage.findAll(), startTime, endTime, CALORIES_PER_DAY));
        log.debug("redirect to meals list with action=null");
        request.getRequestDispatcher("mealsList.jsp").forward(request, response);
    }
}
