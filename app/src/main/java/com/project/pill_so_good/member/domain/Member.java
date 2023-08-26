package com.project.pill_so_good.member.domain;

public class Member {

    private String idToken;
    private String emailId;

    private final String email;
    private final String password;
    private final int age;
    private final Gender gender;
    private final Division division;


    public Member(String email, String password, String passwordCheck, int age, String gender, String division) {
        this.email = email;
        this.password = checkPassword(password, passwordCheck);
        this.age = checkAge(age);
        this.gender = Gender.getInstance(gender);
        this.division = Division.getInstance(division);
    }

    private String checkPassword(String password, String passwordCheck) {
        if (password!=null && passwordCheck!=null  && password.equals(passwordCheck))
            return password;
        throw new IllegalArgumentException();
    }

    private int checkAge(int age) {
        if (age >=0 && age <120)
            return age;
        throw new IllegalArgumentException();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender.getGender();
    }

    public String getDivision() {
        return division.getDBKey();
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
