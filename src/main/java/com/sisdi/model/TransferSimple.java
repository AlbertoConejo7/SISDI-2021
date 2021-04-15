package com.sisdi.model;

import java.util.Date;
import lombok.Data;

@Data
public class TransferSimple {
    private int id;
    private int numeroExpediente;
    private String nombreExpediente;
    private String emisor;
    private String receptor;
    private Date fechaTraslado;
    private int estado;
    private int oficios;
    private String observaciones;
    
}
