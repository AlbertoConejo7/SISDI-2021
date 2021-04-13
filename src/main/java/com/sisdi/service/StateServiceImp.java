package com.sisdi.service;

import com.sisdi.dao.StateDao;
import com.sisdi.model.State;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


public class StateServiceImp implements StateService{
    @Autowired
    private StateDao stateDao;
    
    @Override
    public List<State> listStates() {
        return (List<State>) stateDao.findAll();
    }

    @Override
    public Optional<State> getState(int id) {
        return stateDao.findById(id);
    }

    @Override
    public State getState(String name) {
        List<State> list=this.listStates();
        State aux=null;
        for(State s:list){
            if(s.getDescription().equals(name)){
                aux=s;
            }
        }
        return aux;
    }
    
}
