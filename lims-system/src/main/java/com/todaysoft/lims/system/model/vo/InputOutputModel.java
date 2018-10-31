package com.todaysoft.lims.system.model.vo;

/**
 * Created by HSHY-032 on 2016/6/28.
 */
public class InputOutputModel {
    private int id;

    private String name;

    public InputOutputModel(){};

    public InputOutputModel(int id,String name){this.id=id;this.name=name;};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
