package model;

public class course_model {

    private String course_name;
    private String course_code;
    private int course_mark;

    public course_model(String course_name, String course_code, int course_mark) {
        this.course_name = course_name;
        this.course_code = course_code;
        this.course_mark = course_mark;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getCourse_mark() {
        return course_mark;
    }

    public void setCourse_mark(int course_mark) {
        this.course_mark = course_mark;
    }
}
