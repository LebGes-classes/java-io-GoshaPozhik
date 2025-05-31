package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class JsonService {
    private final Gson gson;

    public JsonService() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveStudents(List<Student> students, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(students, writer);
        } catch (IOException e) {
            System.err.println("Error saving students to JSON: " + e.getMessage());
        }
    }

    public List<Student> loadStudents(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type studentListType = new TypeToken<List<Student>>(){}.getType();
            return gson.fromJson(reader, studentListType);
        } catch (IOException e) {
            System.err.println("Error loading students from JSON: " + e.getMessage());
            return null;
        }
    }

    public void saveTeachers(List<Teacher> teachers, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(teachers, writer);
        } catch (IOException e) {
            System.err.println("Error saving teachers to JSON: " + e.getMessage());
        }
    }

    public List<Teacher> loadTeachers(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type teacherListType = new TypeToken<List<Teacher>>(){}.getType();
            return gson.fromJson(reader, teacherListType);
        } catch (IOException e) {
            System.err.println("Error loading teachers from JSON: " + e.getMessage());
            return null;
        }
    }

    public void saveSubjects(List<Subject> subjects, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(subjects, writer);
        } catch (IOException e) {
            System.err.println("Error saving subjects to JSON: " + e.getMessage());
        }
    }

    public List<Subject> loadSubjects(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type subjectListType = new TypeToken<List<Subject>>(){}.getType();
            return gson.fromJson(reader, subjectListType);
        } catch (IOException e) {
            System.err.println("Error loading subjects from JSON: " + e.getMessage());
            return null;
        }
    }

    public void saveGroups(List<Group> groups, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(groups, writer);
        } catch (IOException e) {
            System.err.println("Error saving groups to JSON: " + e.getMessage());
        }
    }

    public List<Group> loadGroups(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type groupListType = new TypeToken<List<Group>>(){}.getType();
            return gson.fromJson(reader, groupListType);
        } catch (IOException e) {
            System.err.println("Error loading groups from JSON: " + e.getMessage());
            return null;
        }
    }

    public void saveGrades(List<Grade> grades, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(grades, writer);
        } catch (IOException e) {
            System.err.println("Error saving grades to JSON: " + e.getMessage());
        }
    }

    public List<Grade> loadGrades(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type gradeListType = new TypeToken<List<Grade>>(){}.getType();
            return gson.fromJson(reader, gradeListType);
        } catch (IOException e) {
            System.err.println("Error loading grades from JSON: " + e.getMessage());
            return null;
        }
    }

    public void saveSchedule(List<Schedule> schedules, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(schedules, writer);
        } catch (IOException e) {
            System.err.println("Error saving schedule to JSON: " + e.getMessage());
        }
    }

    public List<Schedule> loadSchedule(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Type scheduleListType = new TypeToken<List<Schedule>>(){}.getType();
            return gson.fromJson(reader, scheduleListType);
        } catch (IOException e) {
            System.err.println("Error loading schedule from JSON: " + e.getMessage());
            return null;
        }
    }
}
