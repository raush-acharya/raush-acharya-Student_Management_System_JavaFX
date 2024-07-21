package com.example.javaassignment;

public class Student {
    private int sn;
    private String name;
    private String phone;
    private String email;
    private String faculty;
    private String gender;

    public Student(int sn, String name, String phone, String email, String faculty, String gender) {
        this.sn = sn;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.faculty = faculty;
        this.gender = gender;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
