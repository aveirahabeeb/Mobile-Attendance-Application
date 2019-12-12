package model;

public class Attendance_model {


        private String id;
        private String date;
        private String name;
        private String status;
        private String course;
        private int absent;
        private int present;


        public Attendance_model(String id, String name, String status, String date, String course, int present, int absent) {

            this.id = id;
            this.name = name;
            this.status = status;
            this.date = date;
            this.course = course;
            this.present = present;
            this.absent = absent;
        }

        public Attendance_model() {

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCourse() {
            return course;
        }

        public int getAbsent() {
            return absent;
        }

        public void setAbsent(int absent) {
            this.absent = absent;
        }

        public int getPresent() {
            return present;
        }

    public void setPresent(int present) {
        this.present = present;
    }

    public void setCourse(String course) {
            this.course = course;
        }
}


