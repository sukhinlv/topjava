package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Query("select m from Meal m where m.id = :id and m.user.id = :userId")
    Meal get(@Param("id") Integer id,@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("delete from Meal m where m.id=:id and m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("select m from Meal m where m.user.id = :userId order by m.dateTime DESC")
    List<Meal> getAllSorted(@Param("userId") Integer userId);

    @Query("""
            select m from Meal m
            where m.dateTime >= :startTime and m.dateTime < :endTime and m.user.id = :userId
            order by m.dateTime DESC""")
    List<Meal> getBetweenHalfOpen(@Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime,
                                  @Param("userId") Integer userId);
}
