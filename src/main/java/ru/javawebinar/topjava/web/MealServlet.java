package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealsStorage;
import ru.javawebinar.topjava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Storage<Meal, Integer> storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = new MealsStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("redirect to meals list");
        request.setAttribute("resumes", storage.findAll());
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
