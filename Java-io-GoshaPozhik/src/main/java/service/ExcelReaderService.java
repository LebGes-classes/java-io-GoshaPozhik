package service;

import model.*;
import util.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelReaderService {

    public List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(FileUtils.getFilePath("students.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell groupIdCell = row.getCell(2);

                if (idCell != null && nameCell != null && groupIdCell != null) {
                    int id = (int) idCell.getNumericCellValue();
                    String name = nameCell.getStringCellValue();
                    int groupId = (int) groupIdCell.getNumericCellValue();

                    students.add(new Student(id, name, groupId));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading students.xlsx: " + e.getMessage());
        }
        return students;
    }

    public List<Teacher> readTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(FileUtils.getFilePath("teachers.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell subjectIdCell = row.getCell(2);

                if (idCell != null && nameCell != null && subjectIdCell != null) {
                    int id = (int) idCell.getNumericCellValue();
                    String name = nameCell.getStringCellValue();
                    int subjectId = (int) subjectIdCell.getNumericCellValue();

                    teachers.add(new Teacher(id, name, subjectId));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading teachers.xlsx: " + e.getMessage());
        }
        return teachers;
    }

    public List<Subject> readSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(FileUtils.getFilePath("subjects.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell teacherIdCell = row.getCell(2);

                if (idCell != null && nameCell != null && teacherIdCell != null) {
                    int id = (int) idCell.getNumericCellValue();
                    String name = nameCell.getStringCellValue();
                    int teacherId = (int) teacherIdCell.getNumericCellValue();

                    subjects.add(new Subject(id, name, teacherId));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading subjects.xlsx: " + e.getMessage());
        }
        return subjects;
    }

    public List<Group> readGroups() {
        List<Group> groups = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(FileUtils.getFilePath("groups.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);

                if (idCell != null && nameCell != null) {
                    int id = (int) idCell.getNumericCellValue();
                    String name = nameCell.getStringCellValue();

                    groups.add(new Group(id, name));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading groups.xlsx: " + e.getMessage());
        }
        return groups;
    }

    public List<Grade> readGrades() {
        List<Grade> grades = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(FileUtils.getFilePath("grades.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell idCell = row.getCell(0);
                Cell studentIdCell = row.getCell(1);
                Cell subjectIdCell = row.getCell(2);
                Cell gradeCell = row.getCell(3);
                Cell dateCell = row.getCell(4);

                if (idCell != null && studentIdCell != null && subjectIdCell != null &&
                        gradeCell != null && dateCell != null) {
                    int id = (int) idCell.getNumericCellValue();
                    int studentId = (int) studentIdCell.getNumericCellValue();
                    int subjectId = (int) subjectIdCell.getNumericCellValue();
                    int grade = (int) gradeCell.getNumericCellValue();
                    String date = dateCell.getStringCellValue();

                    grades.add(new Grade(id, studentId, subjectId, grade, date));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading grades.xlsx: " + e.getMessage());
        }
        return grades;
    }

    public List<Schedule> readSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(FileUtils.getFilePath("schedule.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell idCell = row.getCell(0);
                Cell groupIdCell = row.getCell(1);
                Cell subjectIdCell = row.getCell(2);
                Cell teacherIdCell = row.getCell(3);
                Cell dayOfWeekCell = row.getCell(4);
                Cell timeSlotCell = row.getCell(5);

                if (idCell != null && groupIdCell != null && subjectIdCell != null &&
                        teacherIdCell != null && dayOfWeekCell != null && timeSlotCell != null) {
                    int id = (int) idCell.getNumericCellValue();
                    int groupId = (int) groupIdCell.getNumericCellValue();
                    int subjectId = (int) subjectIdCell.getNumericCellValue();
                    int teacherId = (int) teacherIdCell.getNumericCellValue();
                    String dayOfWeek = dayOfWeekCell.getStringCellValue();
                    String timeSlot = timeSlotCell.getStringCellValue();

                    schedules.add(new Schedule(id, groupId, subjectId, teacherId, dayOfWeek, timeSlot));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading schedule.xlsx: " + e.getMessage());
        }
        return schedules;
    }
}
