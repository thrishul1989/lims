package com.todaysoft.lims.system.modules.bpm.dtdata.model;


import com.google.common.collect.Maps;

import java.util.Map;

public class DTDataSubmitTaskSuccessArgs {

    private String combineCode;

    private String sampleCode;

    private boolean valid;

    private String message;

    private Map<String,String> map = Maps.newHashMap();//其他多余字段不涉及代码的放map里面

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
