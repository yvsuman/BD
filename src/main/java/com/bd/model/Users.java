/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bd.model;

/**
 *
 * @author SUMAN
 */
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;


@Document(collection = "users")
public class Users {

    @Id
    private int id;
    @Size(min = 1, max = 128)
    @Pattern(regexp = "[A-Za-z. ]*", message = "First name must contain valid character")
    @NotNull(message = "First name can not be null")
    @NotEmpty(message = "First name can not be empty")
    private String name;
    @Size(min = 1, max = 128)
    @Pattern(regexp = "[A-Za-z. ]*", message = "First name must contain valid character")
    @NotNull(message = "First name can not be null")
    @NotEmpty(message = "First name can not be empty")
    private String lastname;
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 32, message = "Password must be six character long")
    private String password;
    private String phoneno;
    private String bdgroup; 
    private Category categoryId;

    public Users() {
    }

    public Users(int userId) {
        this.id = userId;
    }

    public Users(int id, String name, String email, String password, String bdgroup) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bdgroup = bdgroup;
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int userId) {
        this.id = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phoneno;
    }

    public void setPhone(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getbdgroup() {
        return bdgroup;
    }

    public void setbdgroup(String bdgroup) {
        this.bdgroup = bdgroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

   
}
