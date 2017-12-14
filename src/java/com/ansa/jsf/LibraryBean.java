/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ansa.jsf;

import db.operations.LibraryOperation;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @library AnsaKhitara
 */
@ManagedBean (name = "libraries")
@RequestScoped
public class LibraryBean implements Serializable {

    private String isbn, title, descriptionOrabstract, publisher, author, pages, published, language, format, type, capacity;

    public ArrayList<LibraryBean> libraryListFromDB;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public ArrayList<LibraryBean> getLibraryListFromDB() {
        return libraryListFromDB;
    }

    public void setLibraryListFromDB(ArrayList<LibraryBean> libraryListFromDB) {
        this.libraryListFromDB = libraryListFromDB;
    }

    /* Method Will Avoid Multiple Calls To DB For Fetching The Students Records. If This Is Not Used & Data Is Fetched From Getter Method, JSF DataTable Will Make Multiple Calls To DB*/
    @PostConstruct
    public void init() {
        libraryListFromDB = LibraryOperation.getLibraryListFromDB();
    }

    /* Method Used To Fetch All Records From The Database */
    public ArrayList<LibraryBean> libraryList() {
        return libraryListFromDB;
    }

    /* Method Used To Save New Student Record */
    public String saveLibraryDetails(LibraryBean newLibraryObj) {
        return LibraryOperation.saveLibraryDetailsInDB(newLibraryObj);
    }

    /* Method Used To Edit Student Record */
    public String editLibraryRecord(String ISBN) {
        return LibraryOperation.editLibraryRecordInDB(ISBN);
    }

    /* Method Used To Update Student Record */
    public String updateLibraryDetails(LibraryBean updateLibraryObj) {
        return LibraryOperation.updateLibraryDetailsInDB(updateLibraryObj);
    }

    /* Method Used To Delete Student Record */
    public String deleteLibraryRecord(String ISBN) {
        return LibraryOperation.deleteLibraryRecordInDB(ISBN);
    }
}
