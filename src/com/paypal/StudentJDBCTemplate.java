package com.paypal;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.List;

import javax.sql.DataSource;



//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class StudentJDBCTemplate implements StudentDAO {
   @SuppressWarnings("unused")
   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
  /* public String getSQL()
    {
	   ApplicationContext context = new ClassPathXmlApplicationContext("query.xml");
       StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate)context.getBean("query");
	   String sql= studentJDBCTemplate.getSQL();
	   return sql ;
    	
    }*/
   
   
  
   
   
   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
   }

   public void create(String name, Integer age) {
      String SQL = "insert into Student (name, age) values (?, ?)";
      
      jdbcTemplateObject.update( SQL, name, age);
      System.out.println("Created Record Name = " + name + " Age = " + age);
      return;
   }

   public Student getStudent(Integer id) throws ParserConfigurationException, SAXException, IOException{
	   File file = new File("src/query.xml");
	   String s="";
	   if(file.exists()){
		   System.out.println("file name: "+file.getName());
	  
		   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

           Document doc;
           DocumentBuilder db=dbf.newDocumentBuilder();
           doc = db.parse(file);
           NodeList elemNodeList = doc.getElementsByTagName("query");
           s=elemNodeList.item(0).getTextContent();
	   
           //System.out.println("the value of s>>>>>>>>>>>>>>>>>>>>>"+s);
	   }else
	   {
		   System.out.println("not going"+file.getAbsolutePath());
	   }
	   
     String SQL=s;
	  //String SQL = "select * from Student where id = ?";
	  Student student = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{id}, new StudentMapper());
      //return student;
	  
	return student;
	   
   }

   public List<Student> listStudents() {
      String SQL = "select * from Student";
      List <Student> students = jdbcTemplateObject.query(SQL, 
                                new StudentMapper());
      return students;
   }

   public void delete(Integer id){
      String SQL = "delete from Student where id = ?";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Record with ID = " + id );
      return;
   }

   public void update(Integer id, Integer age){
      String SQL = "update Student set age = ? where id = ?";
      jdbcTemplateObject.update(SQL, age, id);
      System.out.println("Updated Record with ID = " + id );
      return;
   }
   
   public void alter()
   {
	   String SQL = "ALTER TABLE Student AUTO_INCREMENT = 1";
	   jdbcTemplateObject.execute(SQL);
   }
}