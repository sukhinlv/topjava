package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
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
import static ru.javawebinar.topjava.util.TimeUtil.DATE_TIME_FORMATTER;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private Storage<Meal, Integer> storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = MealStorage.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        int id = getIntFromString(request.getParameter("id"));
        LocalDateTime dateTime = getDateTimeFromString(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = getIntFromString(request.getParameter("calories"));

        Meal meal;
        if (id == -1) {
            meal = new Meal(dateTime, description, calories);
        } else {
            meal = storage.findById(id).get();
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
        }

        storage.save(meal);
        response.sendRedirect("resume");
    }

    private static LocalDateTime getDateTimeFromString(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO: remove hardcoded time
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 59);

        Integer id = getIntFromString(request.getParameter("id"));
        String action = request.getParameter("action");
        Meal meal;
        if (action == null) {
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(storage.findAll(), startTime, endTime, CALORIES_PER_DAY));
            log.debug("redirect to meals list with action=null");
            request.getRequestDispatcher("mealsList.jsp").forward(request, response);
            return;
        } else if (action.equals("delete")) {
            storage.deleteById(id);
            log.debug("redirect to meals list with action=delete");
            response.sendRedirect("meals");
            return;
        } else if (action.equals("view") || action.equals("edit")) {
            meal = storage.findById(id).orElse(Meal.EMPTY);
        } else if (action.equals("new")) {
            meal = Meal.EMPTY;
        } else {
            throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("meal", meal);
        log.debug("redirect to meals edit/view with action={}", action);
        request.getRequestDispatcher(("view".equals(action) ? "mealView.jsp" : "mealEdit.jsp")).forward(request, response);
    }

    private static int getIntFromString(String idParam) {
        return (idParam == null) ? -1 : Integer.parseInt(idParam);
    }
}
