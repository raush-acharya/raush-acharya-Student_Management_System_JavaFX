package com.example.javaassignment;

public class Result {
    private int sn;
    private String testFileName;
    private int totalMarks;
    private int achievedMarks;
    private String grade;

    public Result(int sn, String testFileName, int totalMarks, int achievedMarks, String grade) {
        this.sn = sn;
        this.testFileName = testFileName;
        this.totalMarks = totalMarks;
        this.achievedMarks = achievedMarks;
        this.grade = grade;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getTestFileName() {
        return testFileName;
    }

    public void setTestFileName(String testFileName) {
        this.testFileName = testFileName;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getAchievedMarks() {
        return achievedMarks;
    }

    public void setAchievedMarks(int achievedMarks) {
        this.achievedMarks = achievedMarks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
