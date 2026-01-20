package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.lang.reflect.AnnotatedArrayType;
import java.util.*;


public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);


        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), "Количество тренировок не совпадает");
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size(), "Количество тренировок не совпадает");
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));

        List<TimeOfDay> times = new ArrayList<>(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).keySet());

        assertEquals((new TimeOfDay(13, 0)), times.get(0),"Первое занятие должно быть в 13:00");
        assertEquals((new TimeOfDay(20, 0)), times.get(1),"Второе занятие должно быть в 20:00");


        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        assertNotNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetCountByCoaches_NoTrainings() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        assertTrue(countByCoaches.isEmpty()); // Проверяем, что при отсутствии тренировок возвращается пустой список
    }

    @Test
    void testGetCountByCoaches_Sorting() {
        Timetable timetable = new Timetable();
        Coach coachA = new Coach("CoachA", "CoachAA", "CoachAAA");
        Coach coachB = new Coach("CoachB", "CoachBB", "CoachBBB");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);


        // Добавляем тренировки так, чтобы тренеры были отсортированы по убыванию количества тренировок
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachA,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachB,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachA,
                DayOfWeek.MONDAY, new TimeOfDay(17, 0)));


        List<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        assertEquals(2, countByCoaches.size()); // Проверяем, что количество тренеров верно
        assertEquals(coachA, countByCoaches.get(0).getCoach()); // Тренер A должен быть первым (больше тренировок)
        assertEquals(coachB, countByCoaches.get(1).getCoach()); // Тренер B должен быть вторым
    }

    @Test
    void testGetCountByCoaches_MultipleCoaches() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coachA = new Coach("CoachA", "CoachAA", "CoachAAA");
        Coach coachB = new Coach("CoachB", "CoachBB", "CoachBBB");
        Coach coachC = new Coach("CoachC", "CoachCC", "CoachCCC");

        // Добавляем тренировки для разных тренеров
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachA,
                DayOfWeek.MONDAY, new TimeOfDay(17, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachB,
                DayOfWeek.MONDAY, new TimeOfDay(18, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coachC,
                DayOfWeek.MONDAY, new TimeOfDay(19, 0)));

        List<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        assertEquals(3, countByCoaches.size()); // Проверяем, что количество тренеров верно
    }


}
