/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bd.service;

import com.bd.config.SpringMongoConfig;
import com.bd.dao.SequenceDAO;
import com.bd.model.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 *
 * @author SUMAN
 */
@Service
public class UserService implements SequenceDAO{
    
    @Autowired 
    private MongoOperations mongo;
    ApplicationContext ctx;
    MongoOperations mongoOperation;
    
    public void setMongoOperation()
    {
    ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    }
        
    @Override
    public int getNextSequence(String collectionName) {
      setMongoOperation();
      Counter counter = mongoOperation.findAndModify(
      query(where("_id").is(collectionName)), 
      new Update().inc("seq", 1),
      options().returnNew(true),
      Counter.class);
       
    return counter.getSeq();
  }
    
}
