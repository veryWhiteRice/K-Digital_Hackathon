package com.project.pill_so_good.member.domain;

public class Member {

    private final String email;
    private final String password;
    private final int age;
    private final Gender gender;
    private final Division division;


    public Member(String email, String password, int age, Gender gender, Division division) {
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.division = division;
    }
}
