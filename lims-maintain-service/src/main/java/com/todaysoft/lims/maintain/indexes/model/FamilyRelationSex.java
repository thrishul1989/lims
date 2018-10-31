package com.todaysoft.lims.maintain.indexes.model;


import com.google.common.collect.Maps;

import java.util.Map;

public class FamilyRelationSex {


    public static final Map<String,String> map = Maps.newHashMap();

    public FamilyRelationSex() {
        map.put("本人","本人");
        map.put("父亲","男");
        map.put("母亲","女");
        map.put("丈夫","男");
        map.put("妻子","女");
        map.put("爷爷","男");
        map.put("奶奶","女");
        map.put("兄弟","男");
        map.put("姐妹","女");
        map.put("儿子","男");
        map.put("女儿","女");
        map.put("外公","男");
        map.put("外婆","女");
        map.put("叔叔","男");
        map.put("姑姑","女");
        map.put("舅舅","男");
        map.put("姨妈","女");
        map.put("表兄弟","男");
        map.put("表姐妹","女");
        map.put("堂兄弟","男");
        map.put("堂姐妹","女");
        map.put("孙子","男");
        map.put("孙女","女");
        map.put("外孙","男");
        map.put("外孙女","女");
        map.put("姑父","男");
        map.put("姨夫","男");
        map.put("侄子","男");
        map.put("侄女","女");
        map.put("外甥","男");
        map.put("外甥女","女");
        map.put("舅爷","男");
        map.put("其他","未知");
    }
}
