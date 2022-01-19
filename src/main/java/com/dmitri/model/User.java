package com.dmitri.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private Date birthday;
    private String phone;
    private String roleName;
    private String categoryName;
    private String roomNumber;
}