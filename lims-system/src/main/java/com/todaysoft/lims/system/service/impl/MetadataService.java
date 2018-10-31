package com.todaysoft.lims.system.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.TreeNode;
import com.todaysoft.lims.system.service.IMetadataService;


@Service
public class MetadataService implements IMetadataService
{
    @Override
    public List<TreeNode> getHierarchyMetadataTypes()
    {
        TreeNode root = new TreeNode();
        root.setId("P_ROOT");
        root.setText("分类管理");
        
        TreeNode device = new TreeNode();
        device.setId("P_DEVICE");
        device.setText("设备");
        
        TreeNode experiment = new TreeNode();
        experiment.setId("L_DEVICE_EXPERIMENT");
        experiment.setText("实验设备");
        
        TreeNode storage = new TreeNode();
        storage.setId("L_DEVICE_STORAGE");
        storage.setText("存储容器");
        
        device.setChildren(Arrays.asList(experiment, storage));
        
        TreeNode reagent = new TreeNode();
        reagent.setId("P_REAGENT");
        reagent.setText("试剂");
        
        TreeNode material = new TreeNode();
        material.setId("L_REAGENT_MATERIAL");
        material.setText("试剂原料");
        
        TreeNode probe = new TreeNode();
        probe.setId("L_REAGENT_PROBE");
        probe.setText("探针");
        
        TreeNode analyze = new TreeNode();
        analyze.setId("L_REAGENT_ANALYZE");
        analyze.setText("分析方法");
        
        TreeNode primer = new TreeNode();
        primer.setId("L_REAGENT_PRIMER");
        primer.setText("引物");
        
        TreeNode primerReferenceLibrary = new TreeNode();
        primerReferenceLibrary.setId("L_REAGENT_PRIMERREFERENCELIBRARY");
        primerReferenceLibrary.setText("引物参考库");
        
        TreeNode connect = new TreeNode();
        connect.setId("L_REAGENT_CONNECT");
        connect.setText("接头");
        
        TreeNode reagentKit = new TreeNode();
        reagentKit.setId("L_REAGENT_KIT");
        reagentKit.setText("试剂盒分类");
        
        reagent.setChildren(Arrays.asList(material, probe,analyze, primer,reagentKit,connect,primerReferenceLibrary));
        
        TreeNode esample = new TreeNode();
        esample.setId("P_ESAMPLE");
        esample.setText("实验物");
        
        TreeNode osample = new TreeNode();
        osample.setId("L_ESAMPLE_OSAMPLE");
        osample.setText("样本");
        
        TreeNode isample = new TreeNode();
        isample.setId("L_ESAMPLE_ISAMPLE");
        isample.setText("实验产物");
        
        esample.setChildren(Arrays.asList(osample, isample));
        
        TreeNode supplier = new TreeNode();
        supplier.setId("P_SUPPLIER");
        supplier.setText("供应");
        
        TreeNode supplierS = new TreeNode();
        supplierS.setId("L_SUPPLIER");
        supplierS.setText("供应商");
        
        TreeNode producterManage = new TreeNode();
        producterManage.setId("L_PRODUCTER_MANAGE");
        producterManage.setText("生产商");
        
        
        
        supplier.setChildren(Arrays.asList(supplierS,producterManage));
        
        TreeNode cus = new TreeNode();
        cus.setId("P_CUS");
        cus.setText("客户");
        
        TreeNode firm = new TreeNode();
        firm.setId("L_FIRM");
        firm.setText("(客户)单位");
        
        TreeNode customer = new TreeNode();
        customer.setId("L_CUSTOMER");
        customer.setText("客户");
        
        cus.setChildren(Arrays.asList(firm,customer));
        
        root.setChildren(Arrays.asList(device, reagent, esample,supplier,cus));
        return Collections.singletonList(root);
    }
}
