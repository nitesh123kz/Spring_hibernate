package com.paypal;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import com.paypal.StudentJDBCTemplate;
import com.google.gson.Gson;
public class MainApp {
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
      Gson gson=new Gson();
	   ApplicationContext context = 
             new ClassPathXmlApplicationContext("Beans.xml");

      StudentJDBCTemplate studentJDBCTemplate = 
      (StudentJDBCTemplate)context.getBean("studentJDBCTemplate");
      studentJDBCTemplate.alter();
      System.out.println("------Records Creation--------" );
      studentJDBCTemplate.create("Nitesh", 11);
      studentJDBCTemplate.create("Dhruv", 2);
      studentJDBCTemplate.create("Sathyam", 15);

      System.out.println("------Listing Multiple Records--------" );
      List<Student> students = studentJDBCTemplate.listStudents();
      for (Student record : students) {
         System.out.print("ID : " + record.getId() );
         System.out.print(", Name : " + record.getName() );
         System.out.println(", Age : " + record.getAge());
      }
      System.out.println("***************************************");
      System.out.println(gson.toJson(students));
      System.out.println("***************************************");
      System.out.println("----Updating Record with some ID -----" );
      studentJDBCTemplate.update(1, 20);

      System.out.println("----Listing Record with some ID -----" );
      Student student = studentJDBCTemplate.getStudent(1);
      System.out.print("ID : " + student.getId() );
      System.out.print(", Name : " + student.getName() );
      System.out.println(", Age : " + student.getAge());
	  
      System.out.println("----Deleting Record with some ID----");
      studentJDBCTemplate.delete(2);
      
      System.out.println("------Listing Multiple Records--------" );
      List<Student> students1 = studentJDBCTemplate.listStudents();
      for (Student record : students1) {
         System.out.print("ID : " + record.getId() );
         System.out.print(", Name : " + record.getName() );
         System.out.println(", Age : " + record.getAge());
      }
   }
}