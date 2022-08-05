package com.springcurd.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer empId;

    private String name;

    private String department;

    private String grade;

}
