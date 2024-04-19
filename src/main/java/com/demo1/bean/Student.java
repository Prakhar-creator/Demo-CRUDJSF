/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.demo1.bean;
import java.io.Serializable;
public class Student implements Serializable {
    
    private static final long serialVersionUID = 1L;
   
    private int id;
    private String name;

    public Student() {}
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    @Override
    public Student clone() {
        return new Student(id, name);
    }
    
    public void restore(Student student) {
        this.id = student.getId();
        this.name = student.getName();
    }
}
