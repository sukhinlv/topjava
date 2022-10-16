package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (isEmpty(id)) {
            Meal newMeal = new Meal(null, dateTime, description, calories, authUserId());
            log.info("Create {}", newMeal);
            mealRestController.create(newMeal);
        } else {
            Meal updatedMeal = mealRestController.get(Integer.parseInt(id));
            Meal newMeal = new Meal(updatedMeal.getId(), dateTime, description, calories, updatedMeal.getUserId());
            log.info("Update {}", newMeal);
            mealRestController.update(newMeal, newMeal.getId());
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, authUserId()) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("filter");
                saveFilterDataAndRedirect(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                restoreFilterDataAndRedirect(request, response);
                break;
        }
    }

    private void restoreFilterDataAndRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate fromDate = (LocalDate) request.getSession().getAttribute("fromDate");
        LocalTime fromTime = (LocalTime) request.getSession().getAttribute("fromTime");
        LocalDate toDate = (LocalDate) request.getSession().getAttribute("toDate");
        LocalTime toTime = (LocalTime) request.getSession().getAttribute("toTime");
        request.setAttribute("meals", mealRestController.getAllFiltered(fromDate, fromTime, toDate, toTime));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private void saveFilterDataAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("fromDate", getDateFromRequest(request, "fromDate"));
        request.getSession().setAttribute("fromTime", getTimeFromRequest(request, "fromTime"));
        request.getSession().setAttribute("toDate", getDateFromRequest(request, "toDate"));
        request.getSession().setAttribute("toTime", getTimeFromRequest(request, "toTime"));
        response.sendRedirect("meals");
    }


    private LocalDate getDateFromRequest(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        return isEmpty(paramValue) ? LocalDate.parse(paramValue) : null;
    }

    private LocalTime getTimeFromRequest(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        return isEmpty(paramValue) ? LocalTime.parse(paramValue) : null;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
