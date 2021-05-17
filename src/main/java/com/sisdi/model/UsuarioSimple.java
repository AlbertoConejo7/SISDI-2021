package com.sisdi.model;

import lombok.Data;

@Data
public class UsuarioSimple {
    private String name;
    private String email;
    private String password;
    private String department;
    private String certificateId;  
}
