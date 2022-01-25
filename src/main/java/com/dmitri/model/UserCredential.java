package com.dmitri.model;

import lombok.*;

@Getter
@Builder
public class UserCredential {
    private int id;
    private String userName;
    private String roleName;
}
