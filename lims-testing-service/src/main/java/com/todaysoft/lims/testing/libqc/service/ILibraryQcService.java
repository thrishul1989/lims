package com.todaysoft.lims.testing.libqc.service;

import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitModel;
import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitRequest;

public interface ILibraryQcService
{
    LibraryQcSubmitModel getSheet(String id);
    
    void submit(LibraryQcSubmitRequest request, String token);
}
