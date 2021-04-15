package com.sisdi.dao;

import com.sisdi.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferDao  extends CrudRepository<Transfer, Integer> {
    
}
