package model;

public class Grade {
    private int id;
    private int studentId;
    private int subjectId;
    private int grade;
    private String date;

    public Grade() {}

    public Grade(int id, int studentId, int subjectId, int grade, String date) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.grade = grade;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return "Grade{id=" + id + ", studentId=" + studentId +
                ", subjectId=" + subjectId + ", grade=" + grade +
                ", date='" + date + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grade gradeObj = (Grade) obj;
        return id == gradeObj.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
