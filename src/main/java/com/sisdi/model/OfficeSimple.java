package com.sisdi.model;

import lombok.Data;

@Data
public class OfficeSimple {

    private int id;
    private String offnumber;
    private String reason;
    private String dateCreate;
    private String emisor;
    private String emisorDep;
    private String receptor;
    private String receptorDep;
    private int type_id;
    private String observations;
    private String dateLimit;
    private int file;
}
