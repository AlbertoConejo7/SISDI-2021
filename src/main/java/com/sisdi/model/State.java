package com.sisdi.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="t_state")
public class State implements Serializable{
    @Id
    private int id;
    private String description;
    
    public State(){}
    
}
