package com.sisdi.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Usuario{
    private TempUser tempUser;
    private String password;
    private Department department;
    private Boolean status;
    private Boolean isBoss;

    public Usuario(TempUser tempUser, String password, Department department, Boolean status, Boolean isBoss) {
        this.tempUser = tempUser;
        this.password = password;
        this.department = department;
        this.status = status;
        this.isBoss = isBoss;
    }
    
    public Usuario(TempUser tempUser, String password, Department department, Boolean status) {
        this.tempUser = tempUser;
        this.password = password;
        this.department = department;
        this.status = status;
        this.isBoss = isBoss;
    }
}
