package com.sisdi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "t_office")
public class Office implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int INDX;
    private String OFFNUMBER;
    private String REASON;
    private String NAME;
    private Date INCORDATE;
    private Date INCORTIME;
    private Date DEADLINE;
    private Date SESSIONDATE;
    private String OBSERVATIONS;
    private boolean PUBLIC;
    private boolean NOTIFIED;
    private int STATE;
    private int TYPE_ID;
    private String USER_ID;
    private String RECEIVER_ID;
    private int TIMEOUTS_ID;
    private int EXPEDIENTE;
}
