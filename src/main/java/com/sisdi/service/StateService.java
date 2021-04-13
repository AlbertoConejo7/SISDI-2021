package com.sisdi.service;

import com.sisdi.model.State;
import java.util.List;
import java.util.Optional;


public interface StateService {
    List<State> listStates();
    Optional<State> getState(int id);
    State getState(String name);
}
