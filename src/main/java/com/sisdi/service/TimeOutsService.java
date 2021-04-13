package com.sisdi.service;


import com.sisdi.model.TimeOuts;
import java.util.List;


public interface TimeOutsService {
    List<TimeOuts> listTimeOuts();
    TimeOuts searchTimeOut(int id);
}
