/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bd.dao;

import com.bd.exception.SequenceException;

/**
 *
 * @author SUMAN
 */
public interface SequenceDAO  {
    
    int getNextSequence(String collectionName) throws SequenceException;
}
