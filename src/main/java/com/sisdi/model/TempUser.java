package com.sisdi.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="t_tempuser")
public class TempUser implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int INDX;
    private String name;
    private String email;
    
    public TempUser(){}
    public TempUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
