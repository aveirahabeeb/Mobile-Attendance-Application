package model;

import java.io.Serializable;

public class std_Model implements Serializable {

    private String id;
    private String gender;
    private String age;
    private String std_email;
    private String address;
    private String name;


    public std_Model(String address  , String age, String gender, String id, String name, String std_email ) {

        this.gender = address;
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.std_email = std_email;

    }

    public std_Model() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStd_email() {
        return std_email;
    }

    public void setStd_email(String std_email) {
        this.std_email = std_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
