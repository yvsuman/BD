/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.bd.config.SpringMongoConfig;
import com.bd.model.Users;
import com.bd.model.Category;
import com.bd.service.UserService;
import com.bd.util.VerificationGenerator;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *
 * @author SUMAN
 */
@Controller
public final class UserController {
    
    ApplicationContext ctx;
    MongoOperations mongoOperation;
    Users newUser;
    public void setMongoOperation()
    {
    ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    }
        
    @RequestMapping(value="/newUser", method=RequestMethod.GET)
    @ResponseBody
    public String newUser(HttpServletRequest request, HttpServletResponse response){
        String success = "";
        setMongoOperation();
        String firstName = request.getParameter("name");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phoneNo = request.getParameter("phoneno");
        String bdGroup = request.getParameter("bdgroup");
        String password = request.getParameter("password"); 
        UserService userService = new UserService();        	   
        newUser = new Users();
        Query searchEmailQuery = new Query(Criteria.where("email").is(email));
        Query searchPhoneQuery = new Query(Criteria.where("phoneno").is(phoneNo));
        Users emailUser = mongoOperation.findOne(searchEmailQuery, Users.class);
        Users phoneUser = mongoOperation.findOne(searchPhoneQuery, Users.class);
        try{
	if(emailUser == null && phoneUser == null)
        {
        newUser.setUserId(userService.getNextSequence("_id"));
        newUser.setName(firstName);
        newUser.setLastname(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phoneNo);
        newUser.setbdgroup(bdGroup); 
        newUser.setCategoryId(new Category(2));
        mongoOperation.save(newUser);
        success = "Saved User";
        }
        if(emailUser != null && phoneUser == null)
        {
        success = "Email Already Exists";
        }
        if(emailUser == null && phoneUser != null)
        {
        success = "Phoneno Already Exists";
        }
        else if (emailUser != null && phoneUser != null)
        {
          success = "Email&Phoneno Already Exists";
        }
        }
        catch(Exception e)
        {
         success = e.getMessage();
        }
        
        return success;
    }  
    
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    @ResponseBody
    public String checkLogin(HttpServletRequest request, HttpServletResponse response){
        String success = "";
        setMongoOperation();
        String email = request.getParameter("email");               
        String password = request.getParameter("password"); 
        Query searchEmailQuery = new Query(Criteria.where("email").is(email));         
        Users emailUser = mongoOperation.findOne(searchEmailQuery, Users.class);
        if(emailUser == null){
        Query searchPhoneQuery = new Query(Criteria.where("phoneno").is(email)); 
        Users phoneUser = mongoOperation.findOne(searchPhoneQuery, Users.class); 
        if(phoneUser != null)
            {
                String dbPassword =  phoneUser.getPassword();
                if(password.equals(dbPassword))
                   {
                 success = "valid user";
                   }
                   else
                   {
                  success = "Invalid Password";
                     }                
            }
         else 
         {
            success = "Invalid user";
          }
        }
        if(emailUser != null )
        {
         String dbPassword =  emailUser.getPassword();
         if(password.equals(dbPassword))
         {
         success = "valid user";
         }
         else
         {
         success = "Invalid Password";
         }      
        }
               	    
       return success;
    } 
    
    @RequestMapping(value="/passwordRecovery", method=RequestMethod.GET)
    @ResponseBody
    public String passwordRecovery(HttpServletRequest request, HttpServletResponse response){
        String success = "";
        setMongoOperation();
        VerificationGenerator generator = new VerificationGenerator();
        String email = request.getParameter("email");
        String phoneNo = request.getParameter("phoneno");       
        Query searchEmailQuery = new Query(Criteria.where("email").is(email));
        Query searchPhoneQuery = new Query(Criteria.where("phoneno").is(phoneNo));   
        Users emailUser = mongoOperation.findOne(searchEmailQuery, Users.class);
        Users phoneUser = mongoOperation.findOne(searchPhoneQuery, Users.class);   
        if(emailUser != null  )
        {
         String dbPassword =  emailUser.getPassword();
         String dbUser = emailUser.getName();
         success = generator.sendPassword(dbUser, email, dbPassword);      
        }
        else if(emailUser == null)
        {
          if(phoneUser != null)
          {
           String dbPassword =  emailUser.getPassword();
           String dbUser = emailUser.getName();
           success = generator.sendPassword(dbUser, email, dbPassword);           
          }
          else{
           success = "Invalid Email/Phone number";
          }       
        }        	    
       return success;
    } 
  
    
    @RequestMapping(value="/update", method=RequestMethod.GET)
    @ResponseBody
    public String updateUser(HttpServletRequest request, HttpServletResponse response){
        String success = "";
        setMongoOperation();    
        String firstName = request.getParameter("name");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phoneNo = request.getParameter("phoneno");
        String bdGroup = request.getParameter("bdgroup");
        String password = request.getParameter("password");        
        Query searchEmailQuery = new Query(Criteria.where("email").is(email));              
        Users emailUser = mongoOperation.findOne(searchEmailQuery, Users.class);         
        if(emailUser != null)
        {
        mongoOperation.updateFirst(searchEmailQuery,Update.update("name", firstName),Users.class);
        mongoOperation.updateFirst(searchEmailQuery,Update.update("lastname", lastName),Users.class);
        mongoOperation.updateFirst(searchEmailQuery,Update.update("phoneno", phoneNo),Users.class);
        mongoOperation.updateFirst(searchEmailQuery,Update.update("password", password),Users.class);
        mongoOperation.updateFirst(searchEmailQuery,Update.update("bdgroup", bdGroup),Users.class);  
        success = "Updated Successfully";
        }
        else if(emailUser == null)
        {
          success = "Failed to update";
        }        	    
       return success;
    }
       
        
    @RequestMapping(value="/users", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody Users getJsonEnergyTotal(HttpServletRequest request, HttpServletResponse response){             
             setMongoOperation();
             String email = request.getParameter("email");
             Query searchEmailQuery = new Query(Criteria.where("email").is(email));     
             Users emailUser = mongoOperation.findOne(searchEmailQuery, Users.class); 
             return emailUser;
        
    }
    
    @RequestMapping(value="/latlong", method=RequestMethod.GET)
    public void getLatAndLong(HttpServletRequest request, HttpServletResponse response){             
             setMongoOperation();   
    }
    
}
