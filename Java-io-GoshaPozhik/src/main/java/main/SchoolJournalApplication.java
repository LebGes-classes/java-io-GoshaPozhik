package main;

import model.*;
import service.SchoolService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SchoolJournalApplication {
    private final SchoolService schoolService;
    private final Scanner scanner;

    public SchoolJournalApplication() {
        this.schoolService = new SchoolService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        SchoolJournalApplication app = new SchoolJournalApplication();
        app.run();
    }

    public void run() {
        System.out.println("=== Система управления классным журналом ===");
        System.out.println("Загрузка данных из Excel файлов...");

        schoolService.loadDataFromExcel();

        System.out.println("Сохранение данных в JSON формат...");
        schoolService.saveDataToJson();

        System.out.println("Добро пожаловать, директор!");

        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addGrade();
                    break;
                case 2:
                    showSchedule();
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    changeStudentGroup();
                    break;
                case 6:
                    showStudentGrades();
                    break;
                case 7:
                    showAllTeachers();
                    break;
                case 8:
                    showGradesBySubject();
                    break;
                case 9:
                    showAverageGrade();
                    break;
                case 10:
                    saveData();
                    break;
                case 11:
                    loadData();
                    break;
                case 0:
                    running = false;
                    saveData();
                    System.out.println("До свидания!");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }

        scanner.close();
    }

    private void showMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Поставить оценку");
        System.out.println("2. Получить расписание");
        System.out.println("3. Добавить студента");
        System.out.println("4. Удалить студента");
        System.out.println("5. Изменить группу студента");
        System.out.println("6. Посмотреть оценки студента");
        System.out.println("7. Получить список преподавателей");
        System.out.println("8. Получить оценки по предмету");
        System.out.println("9. Получить средний балл");
        System.out.println("10. Сохранить данные");
        System.out.println("11. Загрузить данные из JSON");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void addGrade() {
        System.out.println("\n=== ПОСТАНОВКА ОЦЕНКИ ===");

        showAllStudents();
        System.out.print("Введите ID студента: ");
        int studentId = getIntInput();

        showAllSubjects();
        System.out.print("Введите ID предмета: ");
        int subjectId = getIntInput();

        System.out.print("Введите оценку (1-5): ");
        int grade = getIntInput();

        schoolService.addGrade(studentId, subjectId, grade);
    }

    private void showSchedule() {
        System.out.println("\n=== РАСПИСАНИЕ ===");

        showAllGroups();
        System.out.print("Введите ID группы: ");
        int groupId = getIntInput();

        List<Schedule> groupSchedule = schoolService.getScheduleForGroup(groupId);

        if (groupSchedule.isEmpty()) {
            System.out.println("Расписание для данной группы не найдено");
            return;
        }

        Optional<Group> groupOpt = schoolService.findGroupById(groupId);
        if (groupOpt.isPresent()) {
            System.out.println("Расписание для группы: " + groupOpt.get().getName());
        }

        System.out.printf("%-15s %-20s %-20s %-20s%n",
                "День недели", "Время", "Предмет", "Преподаватель");
        System.out.println("=".repeat(80));

        for (Schedule schedule : groupSchedule) {
            Optional<Subject> subjectOpt = schoolService.findSubjectById(schedule.getSubjectId());
            String subjectName = subjectOpt.map(Subject::getName).orElse("Неизвестный предмет");

            String teacherName = schoolService.getAllTeachers().stream()
                    .filter(t -> t.getId() == schedule.getTeacherId())
                    .map(Teacher::getName)
                    .findFirst()
                    .orElse("Неизвестный преподаватель");

            System.out.printf("%-15s %-20s %-20s %-20s%n",
                    schedule.getDayOfWeek(),
                    schedule.getTimeSlot(),
                    subjectName,
                    teacherName);
        }
    }

    private void addStudent() {
        System.out.println("\n=== ДОБАВЛЕНИЕ СТУДЕНТА ===");

        System.out.print("Введите имя студента: ");
        String name = scanner.nextLine();

        showAllGroups();
        System.out.print("Введите ID группы: ");
        int groupId = getIntInput();

        schoolService.addStudent(name, groupId);
    }

    private void removeStudent() {
        System.out.println("\n=== УДАЛЕНИЕ СТУДЕНТА ===");

        showAllStudents();
        System.out.print("Введите ID студента для удаления: ");
        int studentId = getIntInput();

        schoolService.removeStudent(studentId);
    }

    private void changeStudentGroup() {
        System.out.println("\n=== ИЗМЕНЕНИЕ ГРУППЫ СТУДЕНТА ===");

        showAllStudents();
        System.out.print("Введите ID студента: ");
        int studentId = getIntInput();

        showAllGroups();
        System.out.print("Введите ID новой группы: ");
        int newGroupId = getIntInput();

        schoolService.changeStudentGroup(studentId, newGroupId);
    }

    private void showStudentGrades() {
        System.out.println("\n=== ОЦЕНКИ СТУДЕНТА ===");

        showAllStudents();
        System.out.print("Введите ID студента: ");
        int studentId = getIntInput();

        Optional<Student> studentOpt = schoolService.findStudentById(studentId);
        if (studentOpt.isEmpty()) {
            System.out.println("Студент не найден");
            return;
        }

        List<Grade> studentGrades = schoolService.getStudentGrades(studentId);

        if (studentGrades.isEmpty()) {
            System.out.println("У студента " + studentOpt.get().getName() + " нет оценок");
            return;
        }

        System.out.println("Оценки студента: " + studentOpt.get().getName());
        System.out.printf("%-20s %-10s %-15s%n", "Предмет", "Оценка", "Дата");
        System.out.println("=".repeat(50));

        for (Grade grade : studentGrades) {
            Optional<Subject> subjectOpt = schoolService.findSubjectById(grade.getSubjectId());
            String subjectName = subjectOpt.map(Subject::getName).orElse("Неизвестный предмет");

            System.out.printf("%-20s %-10d %-15s%n",
                    subjectName,
                    grade.getGrade(),
                    grade.getDate());
        }
    }

    private void showAllTeachers() {
        System.out.println("\n=== СПИСОК ПРЕПОДАВАТЕЛЕЙ ===");

        List<Teacher> teachers = schoolService.getAllTeachers();

        if (teachers.isEmpty()) {
            System.out.println("Преподаватели не найдены");
            return;
        }

        System.out.printf("%-5s %-25s %-25s%n", "ID", "Имя", "Предмет");
        System.out.println("=".repeat(60));

        for (Teacher teacher : teachers) {
            Optional<Subject> subjectOpt = schoolService.findSubjectById(teacher.getSubjectId());
            String subjectName = subjectOpt.map(Subject::getName).orElse("Неизвестный предмет");

            System.out.printf("%-5d %-25s %-25s%n",
                    teacher.getId(),
                    teacher.getName(),
                    subjectName);
        }
    }

    private void showGradesBySubject() {
        System.out.println("\n=== ОЦЕНКИ ПО ПРЕДМЕТУ ===");

        showAllSubjects();
        System.out.print("Введите ID предмета: ");
        int subjectId = getIntInput();

        Optional<Subject> subjectOpt = schoolService.findSubjectById(subjectId);
        if (subjectOpt.isEmpty()) {
            System.out.println("Предмет не найден");
            return;
        }

        List<Grade> subjectGrades = schoolService.getGradesBySubject(subjectId);

        if (subjectGrades.isEmpty()) {
            System.out.println("Оценки по предмету " + subjectOpt.get().getName() + " не найдены");
            return;
        }

        System.out.println("Оценки по предмету: " + subjectOpt.get().getName());
        System.out.printf("%-25s %-10s %-15s%n", "Студент", "Оценка", "Дата");
        System.out.println("=".repeat(55));

        for (Grade grade : subjectGrades) {
            Optional<Student> studentOpt = schoolService.findStudentById(grade.getStudentId());
            String studentName = studentOpt.map(Student::getName).orElse("Неизвестный студент");

            System.out.printf("%-25s %-10d %-15s%n",
                    studentName,
                    grade.getGrade(),
                    grade.getDate());
        }
    }

    private void showAverageGrade() {
        System.out.println("\n=== СРЕДНИЙ БАЛЛ ===");
        System.out.println("1. Средний балл по одному предмету");
        System.out.println("2. Средний балл по нескольким предметам");
        System.out.print("Выберите вариант: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                showAverageGradeBySubject();
                break;
            case 2:
                showAverageGradeBySubjects();
                break;
            default:
                System.out.println("Неверный выбор");
        }
    }

    private void showAverageGradeBySubject() {
        showAllSubjects();
        System.out.print("Введите ID предмета: ");
        int subjectId = getIntInput();

        Optional<Subject> subjectOpt = schoolService.findSubjectById(subjectId);
        if (subjectOpt.isEmpty()) {
            System.out.println("Предмет не найден");
            return;
        }

        double averageGrade = schoolService.getAverageGradeBySubject(subjectId);

        if (averageGrade == 0.0) {
            System.out.println("Нет оценок по предмету " + subjectOpt.get().getName());
        } else {
            System.out.printf("Средний балл по предмету '%s': %.2f%n",
                    subjectOpt.get().getName(), averageGrade);
        }
    }

    private void showAverageGradeBySubjects() {
        showAllSubjects();
        System.out.println("Введите ID предметов через запятую (например: 1,2,3): ");
        String input = scanner.nextLine();

        List<Integer> subjectIds = new ArrayList<>();
        try {
            String[] parts = input.split(",");
            for (String part : parts) {
                subjectIds.add(Integer.parseInt(part.trim()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ввода");
            return;
        }

        List<String> subjectNames = new ArrayList<>();
        for (Integer subjectId : subjectIds) {
            Optional<Subject> subjectOpt = schoolService.findSubjectById(subjectId);
            if (subjectOpt.isPresent()) {
                subjectNames.add(subjectOpt.get().getName());
            }
        }

        if (subjectNames.isEmpty()) {
            System.out.println("Не найдено ни одного предмета");
            return;
        }

        double averageGrade = schoolService.getAverageGradeBySubjects(subjectIds);

        if (averageGrade == 0.0) {
            System.out.println("Нет оценок по выбранным предметам");
        } else {
            System.out.printf("Средний балл по предметам [%s]: %.2f%n",
                    String.join(", ", subjectNames), averageGrade);
        }
    }

    private void saveData() {
        schoolService.saveDataToJson();
        System.out.println("Данные сохранены в JSON файлы");
    }

    private void loadData() {
        schoolService.loadDataFromJson();
        System.out.println("Данные загружены из JSON файлов");
    }

    private void showAllStudents() {
        List<Student> students = schoolService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("Студенты не найдены");
            return;
        }

        System.out.printf("%-5s %-25s %-15s%n", "ID", "Имя", "Группа");
        System.out.println("=".repeat(50));

        for (Student student : students) {
            Optional<Group> groupOpt = schoolService.findGroupById(student.getGroupId());
            String groupName = groupOpt.map(Group::getName).orElse("Неизвестная группа");

            System.out.printf("%-5d %-25s %-15s%n",
                    student.getId(),
                    student.getName(),
                    groupName);
        }
    }

    private void showAllSubjects() {
        List<Subject> subjects = schoolService.getAllSubjects();

        if (subjects.isEmpty()) {
            System.out.println("Предметы не найдены");
            return;
        }

        System.out.printf("%-5s %-25s %-25s%n", "ID", "Название", "Преподаватель");
        System.out.println("=".repeat(60));

        for (Subject subject : subjects) {
            String teacherName = schoolService.getAllTeachers().stream()
                    .filter(t -> t.getId() == subject.getTeacherId())
                    .map(Teacher::getName)
                    .findFirst()
                    .orElse("Неизвестный преподаватель");

            System.out.printf("%-5d %-25s %-25s%n",
                    subject.getId(),
                    subject.getName(),
                    teacherName);
        }
    }

    private void showAllGroups() {
        List<Group> groups = schoolService.getAllGroups();

        if (groups.isEmpty()) {
            System.out.println("Группы не найдены");
            return;
        }

        System.out.printf("%-5s %-20s%n", "ID", "Название");
        System.out.println("=".repeat(30));

        for (Group group : groups) {
            System.out.printf("%-5d %-20s%n", group.getId(), group.getName());
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите число: ");
            }
        }
    }
}
