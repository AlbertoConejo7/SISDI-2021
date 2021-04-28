/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.service;

import com.sisdi.model.FileAct;
import java.util.List;

public interface FileActService {
     List<FileAct> listarFileActs();

    FileAct addFileAct(FileAct file);
    
    
}
