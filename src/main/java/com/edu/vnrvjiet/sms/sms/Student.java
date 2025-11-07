package com.edu.vnrvjiet.sms.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private Integer maths;
    private Integer physics;
    private Integer chemistry;
}
