/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.operations;

import static com.ansa.connection.Database.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.ansa.jsf.LibraryBean;
import java.util.Map;
import javax.faces.context.FacesContext;
//import static net.bootsfaces.render.RAlert.title;

/**
 *
 * @author AnsaKhitara
 */
public class LibraryOperation {

    public static Statement stmt;
    public static Connection conn;
    public static ResultSet rs;
    public static PreparedStatement pst;

    /* Method To Fetch The Ebook Records From Database */
    public static ArrayList<LibraryBean> getLibraryListFromDB() {
        ArrayList<LibraryBean> libraryList = new ArrayList<LibraryBean>();
        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select * from library");
            while (rs.next()) {
                LibraryBean eObj = new LibraryBean();
                eObj.setIsbn(rs.getString("isbn"));
                eObj.setTitle(rs.getString("title"));
                eObj.setDescriptionOrabstract(rs.getString("descriptionOrabstract"));                
                eObj.setPublisher(rs.getString("publisher"));
                eObj.setAuthor(rs.getString("author"));
                eObj.setPages(rs.getString("pages"));
                eObj.setPublished(rs.getString("published"));
                eObj.setLanguage(rs.getString("language"));
                eObj.setFormat(rs.getString("format"));                
                eObj.setType(rs.getString("type"));
                eObj.setCapacity(rs.getString("capacity"));
                libraryList.add(eObj);
            }
            System.out.println("Total Records Fetched: " + libraryList.size());
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return libraryList;
    }

    /* Method Used To Save New Ebook Record In Database */
    public static String saveLibraryDetailsInDB(LibraryBean newLibraryObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {
            pst = getConnection().prepareStatement("insert into library (isbn, title, descriptionOrabstract, publisher, author, pages, published, language, format, type, capacity) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, newLibraryObj.getIsbn());
            pst.setString(2, newLibraryObj.getTitle());
            pst.setString(3, newLibraryObj.getDescriptionOrabstract());
            pst.setString(4, newLibraryObj.getPublisher());
            pst.setString(5, newLibraryObj.getAuthor());
            pst.setString(6, newLibraryObj.getPages());
            pst.setString(7, newLibraryObj.getPublished());
            pst.setString(8, newLibraryObj.getLanguage());
            pst.setString(9, newLibraryObj.getFormat());
            pst.setString(10, newLibraryObj.getType());
            pst.setString(11, newLibraryObj.getCapacity());
            saveResult = pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "/Home_Library.xhtml?faces-redirect=true";
        } else {
            navigationResult = "/operation/library/createLibrary.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }

    /* Method Used To Edit Student Record In Database */
    public static String editLibraryRecordInDB(String ISBN) {
        LibraryBean editRecord = null;
        System.out.println("editLibraryRecordInDB() : Library ISBN: " + ISBN);

        /* Setting The Particular Student Details In Session */
        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select * from library where isbn = " + ISBN);
            if (rs != null) {
                rs.next();
                editRecord = new LibraryBean();
                editRecord.setIsbn(rs.getString("isbn"));
                editRecord.setTitle(rs.getString("title"));
                editRecord.setDescriptionOrabstract(rs.getString("descriptionOrabstract"));
                editRecord.setPublisher(rs.getString("publisher"));
                editRecord.setAuthor(rs.getString("author"));
                editRecord.setPages(rs.getString("pages"));
                editRecord.setPublished(rs.getString("published"));
                editRecord.setLanguage(rs.getString("language"));
                editRecord.setFormat(rs.getString("format"));
                editRecord.setType(rs.getString("type"));
                editRecord.setCapacity(rs.getString("capacity"));
            }
            sessionMapObj.put("editRecordObj", editRecord);
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/operation/library/editLibrary.xhtml?faces-redirect=true";
    }

    /* Method Used To Update Ebook Record In Database */
    public static String updateLibraryDetailsInDB(LibraryBean updateLibraryObj) {
        try {
            pst = getConnection().prepareStatement("update library set title=?, "
                    + "descriptionOrabstract=?, publisher=?, author=?, pages=?, published=?, "
                    + "language=?, format=?, type=?, capacity=? where isbn=?");
            pst.setString(1, updateLibraryObj.getTitle());
            pst.setString(2, updateLibraryObj.getDescriptionOrabstract());
            pst.setString(3, updateLibraryObj.getPublisher());
            pst.setString(4, updateLibraryObj.getAuthor());
            pst.setString(5, updateLibraryObj.getPages());
            pst.setString(6, updateLibraryObj.getPublished());
            pst.setString(7, updateLibraryObj.getLanguage());
            pst.setString(8, updateLibraryObj.getFormat());
            pst.setString(9, updateLibraryObj.getType());
            pst.setString(10, updateLibraryObj.getCapacity());
            pst.setString(11, updateLibraryObj.getIsbn());
            pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/Home_Library.xhtml?faces-redirect=true";
    }

    /* Method Used To Delete Ebook Record From Database */
    public static String deleteLibraryRecordInDB(String ISBN) {
        System.out.println("deleteLibraryRecordInDB() : ISBN: " + ISBN);
        try {
            pst = getConnection().prepareStatement("delete from library where isbn = " + ISBN);
            pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/Home_Library.xhtml?faces-redirect=true";
    }
    private LibraryBean libraryBean;
}
