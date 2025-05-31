package model;

public class Subject {
    private int id;
    private String name;
    private int teacherId;

    public Subject() {}

    public Subject(int id, String name, int teacherId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    @Override
    public String toString() {
        return "Subject{id=" + id + ", name='" + name + "', teacherId=" + teacherId + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subject subject = (Subject) obj;
        return id == subject.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
