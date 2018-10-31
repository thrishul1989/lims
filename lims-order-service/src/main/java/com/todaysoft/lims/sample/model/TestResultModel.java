package com.todaysoft.lims.sample.model;

import lombok.*;

/**
 * Created by HSHY-032 on 2016/8/8.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResultModel {

    private String code;

    private String familyName;

    private String longtermStorageLocation;

    private String name;


}
