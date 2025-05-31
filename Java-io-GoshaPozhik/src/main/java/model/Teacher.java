package model;

public class Teacher {
    private int id;
    private String name;
    private int subjectId;

    public Teacher() {}

    public Teacher(int id, String name, int subjectId) {
        this.id = id;
        this.name = name;
        this.subjectId = subjectId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    @Override
    public String toString() {
        return "Teacher{id=" + id + ", name='" + name + "', subjectId=" + subjectId + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return id == teacher.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
