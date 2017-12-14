/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ansa.jsf;

import db.operations.AuthorOperation;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author AnsaKhitara
 */
@ManagedBean (name = "authors")
@RequestScoped
public class AuthorBean implements Serializable {

    private int id;
    private String author_name, job_description, email;

    public ArrayList<AuthorBean> authorListFromDB;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<AuthorBean> getAuthorListFromDB() {
        return authorListFromDB;
    }

    public void setAuthorListFromDB(ArrayList<AuthorBean> authorListFromDB) {
        this.authorListFromDB = authorListFromDB;
    }
    
    /* Method Will Avoid Multiple Calls To DB For Fetching The Students Records. If This Is Not Used & Data Is Fetched From Getter Method, JSF DataTable Will Make Multiple Calls To DB*/
    @PostConstruct
    public void init() {
        authorListFromDB = AuthorOperation.getAuthorListFromDB();
    }

    /* Method Used To Fetch All Records From The Database */
    public ArrayList<AuthorBean> authorList() {
        return authorListFromDB;
    }

    /* Method Used To Save New Student Record */
    public String saveAuthorDetails(AuthorBean newAuthorObj) {
        return AuthorOperation.saveAuthorDetailsInDB(newAuthorObj);
    }

    /* Method Used To Edit Student Record */
    public String editAuthorRecord(int Id) {
        return AuthorOperation.editAuthorRecordInDB(Id);
    }

    /* Method Used To Update Student Record */
    public String updateAuthorDetails(AuthorBean updateAuthorObj) {
        return AuthorOperation.updateAuthorDetailsInDB(updateAuthorObj);
    }

    /* Method Used To Delete Student Record */
    public String deleteAuthorRecord(int Id) {
        return AuthorOperation.deleteAuthorRecordInDB(Id);
    }
}
