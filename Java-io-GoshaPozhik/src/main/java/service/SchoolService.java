package service;

import model.*;
import util.FileUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SchoolService {
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Subject> subjects;
    private List<Group> groups;
    private List<Grade> grades;
    private List<Schedule> schedules;

    private final ExcelReaderService excelReaderService;
    private final JsonService jsonService;

    public SchoolService() {
        this.excelReaderService = new ExcelReaderService();
        this.jsonService = new JsonService();
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.schedules = new ArrayList<>();
    }

    public void loadDataFromExcel() {
        FileUtils.createDirectoryIfNotExists();

        this.students = excelReaderService.readStudents();
        this.teachers = excelReaderService.readTeachers();
        this.subjects = excelReaderService.readSubjects();
        this.groups = excelReaderService.readGroups();
        this.grades = excelReaderService.readGrades();
        this.schedules = excelReaderService.readSchedule();
    }

    public void saveDataToJson() {
        jsonService.saveStudents(students, "students.json");
        jsonService.saveTeachers(teachers, "teachers.json");
        jsonService.saveSubjects(subjects, "subjects.json");
        jsonService.saveGroups(groups, "groups.json");
        jsonService.saveGrades(grades, "grades.json");
        jsonService.saveSchedule(schedules, "schedule.json");
    }

    public void loadDataFromJson() {
        List<Student> loadedStudents = jsonService.loadStudents("students.json");
        if (loadedStudents != null) this.students = loadedStudents;

        List<Teacher> loadedTeachers = jsonService.loadTeachers("teachers.json");
        if (loadedTeachers != null) this.teachers = loadedTeachers;

        List<Subject> loadedSubjects = jsonService.loadSubjects("subjects.json");
        if (loadedSubjects != null) this.subjects = loadedSubjects;

        List<Group> loadedGroups = jsonService.loadGroups("groups.json");
        if (loadedGroups != null) this.groups = loadedGroups;

        List<Grade> loadedGrades = jsonService.loadGrades("grades.json");
        if (loadedGrades != null) this.grades = loadedGrades;

        List<Schedule> loadedSchedules = jsonService.loadSchedule("schedule.json");
        if (loadedSchedules != null) this.schedules = loadedSchedules;
    }

    public boolean addGrade(int studentId, int subjectId, int grade) {
        if (grade < 1 || grade > 5) {
            System.out.println("Оценка должна быть от 1 до 5");
            return false;
        }

        Optional<Student> studentOpt = students.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst();

        Optional<Subject> subjectOpt = subjects.stream()
                .filter(s -> s.getId() == subjectId)
                .findFirst();

        if (studentOpt.isEmpty()) {
            System.out.println("Студент с ID " + studentId + " не найден");
            return false;
        }

        if (subjectOpt.isEmpty()) {
            System.out.println("Предмет с ID " + subjectId + " не найден");
            return false;
        }

        int newGradeId = grades.stream()
                .mapToInt(Grade::getId)
                .max()
                .orElse(0) + 1;

        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        Grade newGrade = new Grade(newGradeId, studentId, subjectId, grade, currentDate);
        grades.add(newGrade);

        System.out.println("Оценка " + grade + " поставлена студенту " +
                studentOpt.get().getName() + " по предмету " +
                subjectOpt.get().getName());
        return true;
    }

    public List<Schedule> getScheduleForGroup(int groupId) {
        return schedules.stream()
                .filter(s -> s.getGroupId() == groupId)
                .collect(Collectors.toList());
    }

    public boolean addStudent(String name, int groupId) {
        Optional<Group> groupOpt = groups.stream()
                .filter(g -> g.getId() == groupId)
                .findFirst();

        if (groupOpt.isEmpty()) {
            System.out.println("Группа с ID " + groupId + " не найдена");
            return false;
        }

        int newStudentId = students.stream()
                .mapToInt(Student::getId)
                .max()
                .orElse(0) + 1;

        Student newStudent = new Student(newStudentId, name, groupId);
        students.add(newStudent);

        System.out.println("Студент " + name + " добавлен в группу " +
                groupOpt.get().getName());
        return true;
    }

    public boolean removeStudent(int studentId) {
        Optional<Student> studentOpt = students.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst();

        if (studentOpt.isEmpty()) {
            System.out.println("Студент с ID " + studentId + " не найден");
            return false;
        }

        students.removeIf(s -> s.getId() == studentId);
        grades.removeIf(g -> g.getStudentId() == studentId);

        System.out.println("Студент " + studentOpt.get().getName() + " удален");
        return true;
    }

    public boolean changeStudentGroup(int studentId, int newGroupId) {
        Optional<Student> studentOpt = students.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst();

        Optional<Group> groupOpt = groups.stream()
                .filter(g -> g.getId() == newGroupId)
                .findFirst();

        if (studentOpt.isEmpty()) {
            System.out.println("Студент с ID " + studentId + " не найден");
            return false;
        }

        if (groupOpt.isEmpty()) {
            System.out.println("Группа с ID " + newGroupId + " не найдена");
            return false;
        }

        studentOpt.get().setGroupId(newGroupId);

        System.out.println("Студент " + studentOpt.get().getName() +
                " переведен в группу " + groupOpt.get().getName());
        return true;
    }

    public List<Grade> getStudentGrades(int studentId) {
        return grades.stream()
                .filter(g -> g.getStudentId() == studentId)
                .collect(Collectors.toList());
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teachers);
    }

    public List<Grade> getGradesBySubject(int subjectId) {
        return grades.stream()
                .filter(g -> g.getSubjectId() == subjectId)
                .collect(Collectors.toList());
    }

    public double getAverageGradeBySubject(int subjectId) {
        List<Grade> subjectGrades = getGradesBySubject(subjectId);

        if (subjectGrades.isEmpty()) {
            return 0.0;
        }

        return subjectGrades.stream()
                .mapToInt(Grade::getGrade)
                .average()
                .orElse(0.0);
    }

    public double getAverageGradeBySubjects(List<Integer> subjectIds) {
        List<Grade> allGrades = grades.stream()
                .filter(g -> subjectIds.contains(g.getSubjectId()))
                .collect(Collectors.toList());

        if (allGrades.isEmpty()) {
            return 0.0;
        }

        return allGrades.stream()
                .mapToInt(Grade::getGrade)
                .average()
                .orElse(0.0);
    }

    public Optional<Student> findStudentById(int studentId) {
        return students.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst();
    }

    public Optional<Subject> findSubjectById(int subjectId) {
        return subjects.stream()
                .filter(s -> s.getId() == subjectId)
                .findFirst();
    }

    public Optional<Group> findGroupById(int groupId) {
        return groups.stream()
                .filter(g -> g.getId() == groupId)
                .findFirst();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(subjects);
    }

    public List<Group> getAllGroups() {
        return new ArrayList<>(groups);
    }
}
