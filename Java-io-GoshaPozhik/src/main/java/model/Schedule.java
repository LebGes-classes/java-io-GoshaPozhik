package model;

public class Schedule {
    private int id;
    private int groupId;
    private int subjectId;
    private int teacherId;
    private String dayOfWeek;
    private String timeSlot;

    public Schedule() {}

    public Schedule(int id, int groupId, int subjectId, int teacherId, String dayOfWeek, String timeSlot) {
        this.id = id;
        this.groupId = groupId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = timeSlot;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGroupId() { return groupId; }
    public void setGroupId(int groupId) { this.groupId = groupId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    @Override
    public String toString() {
        return "Schedule{id=" + id + ", groupId=" + groupId +
                ", subjectId=" + subjectId + ", teacherId=" + teacherId +
                ", dayOfWeek='" + dayOfWeek + "', timeSlot='" + timeSlot + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Schedule schedule = (Schedule) obj;
        return id == schedule.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
