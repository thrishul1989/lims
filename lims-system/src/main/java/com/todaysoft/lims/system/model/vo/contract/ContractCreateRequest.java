package com.todaysoft.lims.system.model.vo.contract;

import java.util.ArrayList;
import java.util.List;

public class ContractCreateRequest
{
    private Contract contract;
    
    private ContractContent contractcontent;
    
    private ContractPartyA contractpa;
    
    private ContractPartyB contractpb;
    
    private List<ContractSampleRequest> conSamples = new ArrayList<ContractSampleRequest>();
    
    private List<ContractProduct> conProducts = new ArrayList<ContractProduct>();
    
    private List<ContractUser> conUsers = new ArrayList<ContractUser>();
    
    private String contractSampleJson;
    
    private String contractProductJson;
    
    public String getContractProductJson()
    {
        return contractProductJson;
    }
    
    public void setContractProductJson(String contractProductJson)
    {
        this.contractProductJson = contractProductJson;
    }
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    public ContractContent getContractcontent()
    {
        return contractcontent;
    }
    
    public void setContractcontent(ContractContent contractcontent)
    {
        this.contractcontent = contractcontent;
    }
    
    public ContractPartyA getContractpa()
    {
        return contractpa;
    }
    
    public void setContractpa(ContractPartyA contractpa)
    {
        this.contractpa = contractpa;
    }
    
    public ContractPartyB getContractpb()
    {
        return contractpb;
    }
    
    public void setContractpb(ContractPartyB contractpb)
    {
        this.contractpb = contractpb;
    }
    
    public List<ContractSampleRequest> getConSamples()
    {
        return conSamples;
    }
    
    public void setConSamples(List<ContractSampleRequest> conSamples)
    {
        this.conSamples = conSamples;
    }
    
    public List<ContractProduct> getConProducts()
    {
        return conProducts;
    }
    
    public void setConProducts(List<ContractProduct> conProducts)
    {
        this.conProducts = conProducts;
    }
    
    public List<ContractUser> getConUsers()
    {
        return conUsers;
    }
    
    public void setConUsers(List<ContractUser> conUsers)
    {
        this.conUsers = conUsers;
    }
    
    public String getContractSampleJson()
    {
        return contractSampleJson;
    }
    
    public void setContractSampleJson(String contractSampleJson)
    {
        this.contractSampleJson = contractSampleJson;
    }
    
}
