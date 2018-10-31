package com.todaysoft.lims.persist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with idea
 * User: lvdong
 * Date: 2016-08-08
 * Time: 15:38
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryField {

    enum QueryType{
        eq,like,eqOrIsNull,ne,neOrIsNotNull,gt,lt,le,ge,isNull,isNotNull
    }

    String alias() default "";

    String name() default "";

    QueryType type() default QueryType.eq;

}
