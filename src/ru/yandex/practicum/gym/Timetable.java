package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (! timetable.containsKey(trainingSession.getDayOfWeek())) {
            Map<TimeOfDay, TrainingSession> dayTraining = new TreeMap<>();
            dayTraining.put(trainingSession.getTimeOfDay(), trainingSession);
            timetable.put(trainingSession.getDayOfWeek(), dayTraining);
        } else {
            Map<TimeOfDay, TrainingSession> dayTraining = timetable.get(trainingSession.getDayOfWeek());
            dayTraining.put(trainingSession.getTimeOfDay(), trainingSession);
        }
    }

    public Map<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        Map<TimeOfDay, TrainingSession> dayTraining = timetable.get(dayOfWeek);
        if (dayTraining == null) {
            return new HashMap<>();
        }
        return dayTraining;
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, TrainingSession> dayTraining = timetable.get(dayOfWeek);
        if (dayTraining == null) {
            return null;
        }
        return dayTraining.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachTreningCount = new HashMap<>();
        for (DayOfWeek day : timetable.keySet()) {
            for (TimeOfDay time : timetable.get(day).keySet()) {
                TrainingSession training = timetable.get(day).get(time);
                coachTreningCount.put(training.getCoach(), coachTreningCount.getOrDefault(training.getCoach(), 0) + 1);
            }
        }
        List<CounterOfTrainings> counterList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachTreningCount.entrySet()) {
            counterList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(counterList);
        return counterList;
    }
}
