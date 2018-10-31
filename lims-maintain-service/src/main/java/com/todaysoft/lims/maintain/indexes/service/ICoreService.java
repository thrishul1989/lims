package com.todaysoft.lims.maintain.indexes.service;

import com.todaysoft.lims.maintain.commons.Monitor;

public interface ICoreService
{
    void indexForProducts();
    
    Monitor monitorForProducts();
    
    void indexForGenes();
    
    Monitor monitorForGenes();
    
    void indexForDiseases();
    
    Monitor monitorForDiseases();
    
    void indexForPhenotypes();
    
    Monitor monitorForPhenotypes();
}
