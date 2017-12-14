/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ansa.jsf;

import db.operations.ArticleOperation;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @article AnsaKhitara
 */
@ManagedBean (name = "articles")
@RequestScoped
public class ArticleBean implements Serializable {

    private int id;
    private String title, descriptionOrabstract, publisher, author, published;

    public ArrayList<ArticleBean> articleListFromDB;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionOrabstract() {
        return descriptionOrabstract;
    }

    public void setDescriptionOrabstract(String descriptionOrabstract) {
        this.descriptionOrabstract = descriptionOrabstract;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public ArrayList<ArticleBean> getArticleListFromDB() {
        return articleListFromDB;
    }

    public void setArticleListFromDB(ArrayList<ArticleBean> articleListFromDB) {
        this.articleListFromDB = articleListFromDB;
    }
    
    /* Method Will Avoid Multiple Calls To DB For Fetching The Students Records. If This Is Not Used & Data Is Fetched From Getter Method, JSF DataTable Will Make Multiple Calls To DB*/
    @PostConstruct
    public void init() {
        articleListFromDB = ArticleOperation.getArticleListFromDB();
    }

    /* Method Used To Fetch All Records From The Database */
    public ArrayList<ArticleBean> articleList() {
        return articleListFromDB;
    }

    /* Method Used To Save New Student Record */
    public String saveArticleDetails(ArticleBean newArticleObj) {
        return ArticleOperation.saveArticleDetailsInDB(newArticleObj);
    }

    /* Method Used To Edit Student Record */
    public String editArticleRecord(int Id) {
        return ArticleOperation.editArticleRecordInDB(Id);
    }

    /* Method Used To Update Student Record */
    public String updateArticleDetails(ArticleBean updateArticleObj) {
        return ArticleOperation.updateArticleDetailsInDB(updateArticleObj);
    }

    /* Method Used To Delete Student Record */
    public String deleteArticleRecord(int Id) {
        return ArticleOperation.deleteArticleRecordInDB(Id);
    }
}
