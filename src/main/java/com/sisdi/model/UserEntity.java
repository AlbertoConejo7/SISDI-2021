package com.sisdi.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="t_user")
public class UserEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int department;
    private String password;
    private String tempuser;
    private int status;
    private int isboss;
    
    public UserEntity(){}
}
