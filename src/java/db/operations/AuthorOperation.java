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

import com.ansa.jsf.AuthorBean;
import java.util.Map;
import javax.faces.context.FacesContext;
//import static net.bootsfaces.render.RAlert.title;

/**
 *
 * @author AnsaKhitara
 */
public class AuthorOperation {

    public static Statement stmt;
    public static Connection conn;
    public static ResultSet rs;
    public static PreparedStatement pst;

    /* Method To Fetch The Ebook Records From Database */
    public static ArrayList<AuthorBean> getAuthorListFromDB() {
        ArrayList<AuthorBean> authorList = new ArrayList<AuthorBean>();
        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select * from author");
            while (rs.next()) {
                AuthorBean eObj = new AuthorBean();
                eObj.setId(rs.getInt("id"));
                eObj.setAuthor_name(rs.getString("author_name"));
                eObj.setJob_description(rs.getString("job_description"));
                eObj.setEmail(rs.getString("email"));
                authorList.add(eObj);
            }
            System.out.println("Total Records Fetched: " + authorList.size());
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return authorList;
    }
    
    //Searching
    public ArrayList<String> search(String author_name) {
        ArrayList<String>authorList = new ArrayList<String>();
        for(AuthorBean authors : getAuthorListFromDB())
            if(authors.getAuthor_name().toLowerCase().startsWith(author_name.toLowerCase()))
                authorList.add(authors.getAuthor_name());
        return authorList;
    }  

    /* Method Used To Save New Ebook Record In Database */
    public static String saveAuthorDetailsInDB(AuthorBean newAuthorObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {
            pst = getConnection().prepareStatement("insert into author (author_name,job_description,email) values (?, ?, ?)");
            pst.setString(1, newAuthorObj.getAuthor_name());
            pst.setString(2, newAuthorObj.getJob_description());
            pst.setString(3, newAuthorObj.getEmail());
            saveResult = pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "/Home_Author.xhtml?faces-redirect=true";
        } else {
            navigationResult = "/operation/author/createAuthor.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }

    /* Method Used To Edit Student Record In Database */
    public static String editAuthorRecordInDB(int Id) {
        AuthorBean editRecord = null;
        System.out.println("editAuthorRecordInDB() : Author Id: " + Id);

        /* Setting The Particular Student Details In Session */
        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select * from author where id = " + Id);
            if (rs != null) {
                rs.next();
                editRecord = new AuthorBean();
                editRecord.setId(rs.getInt("id"));
                editRecord.setAuthor_name(rs.getString("author_name"));
                editRecord.setJob_description(rs.getString("job_description"));
                editRecord.setEmail(rs.getString("email"));
            }
            sessionMapObj.put("editRecordObj", editRecord);
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/operation/author/editAuthor.xhtml?faces-redirect=true";
    }

    /* Method Used To Update Ebook Record In Database */
    public static String updateAuthorDetailsInDB(AuthorBean updateAuthorObj) {
        try {
            pst = getConnection().prepareStatement("update author set author_name=?, job_description=?, email=? where id=?");
            pst.setString(1, updateAuthorObj.getAuthor_name());
            pst.setString(2, updateAuthorObj.getJob_description());
            pst.setString(3, updateAuthorObj.getEmail());
            pst.setInt(4, updateAuthorObj.getId());
            pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/Home_Author.xhtml?faces-redirect=true";
    }

    /* Method Used To Delete Ebook Record From Database */
    public static String deleteAuthorRecordInDB(int Id) {
        System.out.println("deleteAuthorRecordInDB() : Id: " + Id);
        try {
            pst = getConnection().prepareStatement("delete from author where id = " + Id);
            pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/Home_Author.xhtml?faces-redirect=true";
    }
    private AuthorBean authorBean;
}
