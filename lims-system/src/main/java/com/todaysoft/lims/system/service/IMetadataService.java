package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.TreeNode;

public interface IMetadataService
{
    String METADATA_DEVICE_EXPERIMENT = "L_DEVICE_EXPERIMENT";
    
    String METADATA_DEVICE_STORAGE = "L_DEVICE_STORAGE";
    
    String METADATA_REAGENT_MATERIAL = "L_REAGENT_MATERIAL";
    
    String METADATA_REAGENT_PROBE = "L_REAGENT_PROBE";
    
    String METADATA_REAGENT_ANALYZE= "L_REAGENT_ANALYZE";
    
    String METADATA_REAGENT_PRIMER = "L_REAGENT_PRIMER";
    
    String METADATA_REAGENT_PRIMERREFERENCELIBRARY = "L_REAGENT_PRIMERREFERENCELIBRARY";
    
    String METADATA_REAGENT_CONNECT = "L_REAGENT_CONNECT";
    
    String METADATA_ESAMPLE_OSAMPLE = "L_ESAMPLE_OSAMPLE";
    
    String METADATA_ESAMPLE_ISAMPLE = "L_ESAMPLE_ISAMPLE";
    
    String METADATA_REAGENT_KIT = "L_REAGENT_KIT";
    
    String METADATA_SUPPLIER = "L_SUPPLIER";
    
    String METADATA_PRODUCTER_MANAGE = "L_PRODUCTER_MANAGE";
    
    String METADATA_FIRM = "L_FIRM";
    String METADATA_CUSTOMER = "L_CUSTOMER";
    
    
    List<TreeNode> getHierarchyMetadataTypes();
}
