package com.demo1.bean;

/**
 *
 * @author prakhami
 */
import javax.faces.bean.ManagedBean;
import java.util.*;

@ManagedBean(name = "helloWorld")
public class HelloWorld {
    
   private ArrayList<String> fruits = new ArrayList<>();

    public ArrayList<String> getFruits() {
        return fruits;
    }

    public void setFruits(ArrayList<String> fruits) {
        this.fruits = fruits;
    }
   
   public HelloWorld()
   {
       fruits.add("Apple");
       fruits.add("Banana");
   }
	
   public String getMessage() {
      return "Hello Prakhar!";
   }
}
