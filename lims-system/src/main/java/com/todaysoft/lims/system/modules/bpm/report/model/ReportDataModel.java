package com.todaysoft.lims.system.modules.bpm.report.model;

import java.util.List;

import com.google.common.collect.Lists;

public class ReportDataModel
{
    List<BaseInfo> baseInfoList = Lists.newArrayList();//基本信息
    
    List<NgsInfo> ngsInfoList = Lists.newArrayList();//NGS测序数据

    List<NgsInfo> capNgsList = Lists.newArrayList();//NGS测序数据

    List<VerifyData> mlpaVerifyDataList = Lists.newArrayList();//mlpa验证数据结果明细

    List<VerifyData> sangerVerifyDataList = Lists.newArrayList();//sanger验证数据结果明细

    List<VerifyData> qpcrVerifyDataList = Lists.newArrayList();//qpcr验证数据结果明细

    List<VerifyData> pcrNgsVerifyDataList = Lists.newArrayList();//pcrngs验证数据结果明细
    
    List<NgsInfo> multiplePcrList = Lists.newArrayList();//多重PCR
    
    List<NgsInfo> longPcrList = Lists.newArrayList();//Long-PCR
    
    List<MlpaDataInfo> mlpaDataInfoList = Lists.newArrayList();//MLPA结果明细
    
    List<DtDataInfo> dtDataInfoList = Lists.newArrayList();//动态突变结果明细
    
    List<FlouDataInfo> flouDataInfoList = Lists.newArrayList();//荧光检测结果明细
    
    List<SangerDataInfo> sangerDataInfoList = Lists.newArrayList();//Sanger检测
    
    public List<BaseInfo> getBaseInfoList()
    {
        return baseInfoList;
    }
    
    public void setBaseInfoList(List<BaseInfo> baseInfoList)
    {
        this.baseInfoList = baseInfoList;
    }
    
    public List<NgsInfo> getNgsInfoList()
    {
        return ngsInfoList;
    }
    
    public void setNgsInfoList(List<NgsInfo> ngsInfoList)
    {
        this.ngsInfoList = ngsInfoList;
    }
    
    public List<NgsInfo> getMultiplePcrList()
    {
        return multiplePcrList;
    }
    
    public void setMultiplePcrList(List<NgsInfo> multiplePcrList)
    {
        this.multiplePcrList = multiplePcrList;
    }
    
    public List<NgsInfo> getLongPcrList()
    {
        return longPcrList;
    }
    
    public void setLongPcrList(List<NgsInfo> longPcrList)
    {
        this.longPcrList = longPcrList;
    }
    
    public List<MlpaDataInfo> getMlpaDataInfoList()
    {
        return mlpaDataInfoList;
    }
    
    public void setMlpaDataInfoList(List<MlpaDataInfo> mlpaDataInfoList)
    {
        this.mlpaDataInfoList = mlpaDataInfoList;
    }
    
    public List<DtDataInfo> getDtDataInfoList()
    {
        return dtDataInfoList;
    }
    
    public void setDtDataInfoList(List<DtDataInfo> dtDataInfoList)
    {
        this.dtDataInfoList = dtDataInfoList;
    }
    
    public List<FlouDataInfo> getFlouDataInfoList()
    {
        return flouDataInfoList;
    }
    
    public void setFlouDataInfoList(List<FlouDataInfo> flouDataInfoList)
    {
        this.flouDataInfoList = flouDataInfoList;
    }
    
    public List<SangerDataInfo> getSangerDataInfoList()
    {
        return sangerDataInfoList;
    }
    
    public void setSangerDataInfoList(List<SangerDataInfo> sangerDataInfoList)
    {
        this.sangerDataInfoList = sangerDataInfoList;
    }

    public List<VerifyData> getMlpaVerifyDataList() {
        return mlpaVerifyDataList;
    }

    public void setMlpaVerifyDataList(List<VerifyData> mlpaVerifyDataList) {
        this.mlpaVerifyDataList = mlpaVerifyDataList;
    }

    public List<VerifyData> getSangerVerifyDataList() {
        return sangerVerifyDataList;
    }

    public void setSangerVerifyDataList(List<VerifyData> sangerVerifyDataList) {
        this.sangerVerifyDataList = sangerVerifyDataList;
    }

    public List<VerifyData> getQpcrVerifyDataList() {
        return qpcrVerifyDataList;
    }

    public void setQpcrVerifyDataList(List<VerifyData> qpcrVerifyDataList) {
        this.qpcrVerifyDataList = qpcrVerifyDataList;
    }

    public List<VerifyData> getPcrNgsVerifyDataList() {
        return pcrNgsVerifyDataList;
    }

    public void setPcrNgsVerifyDataList(List<VerifyData> pcrNgsVerifyDataList) {
        this.pcrNgsVerifyDataList = pcrNgsVerifyDataList;
    }

    public List<NgsInfo> getCapNgsList() {
        return capNgsList;
    }

    public void setCapNgsList(List<NgsInfo> capNgsList) {
        this.capNgsList = capNgsList;
    }
}
