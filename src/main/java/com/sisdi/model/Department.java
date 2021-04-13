package com.sisdi.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="t_department")
public class Department implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String cod;
    
    public Department(){}
    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        this.cod = cod;
        
    }    
}
