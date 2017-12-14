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

import com.ansa.jsf.ArticleBean;
import java.sql.SQLException;
import java.util.Map;
import javax.faces.context.FacesContext;
//import static net.bootsfaces.render.RAlert.title;

/**
 *
 * @author AnsaKhitara
 */
public class ArticleOperation {

    public static Statement stmt;
    public static Connection conn;
    public static ResultSet rs;
    public static PreparedStatement pst;

    /* Method To Fetch The Ebook Records From Database */
    public static ArrayList<ArticleBean> getArticleListFromDB() {
        ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select * from article");
            while (rs.next()) {
                ArticleBean eObj = new ArticleBean();
                eObj.setId(rs.getInt("id"));
                eObj.setTitle(rs.getString("title"));
                eObj.setDescriptionOrabstract(rs.getString("descriptionOrabstract"));
                eObj.setPublisher(rs.getString("publisher"));
                eObj.setAuthor(rs.getString("author"));
                eObj.setPublished(rs.getString("published"));
                articleList.add(eObj);
            }
            System.out.println("Total Records Fetched: " + articleList.size());
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return articleList;
    }

    /* Method Used To Save New Ebook Record In Database */
    public static String saveArticleDetailsInDB(ArticleBean newLibraryObj) {
        int saveResult = 0;
        String navigationResult = "";
        try {
            pst = getConnection().prepareStatement("insert into article (title, descriptionOrabstract, publisher, author, published) values (?, ?, ?, ?, ?)");
            pst.setString(1, newLibraryObj.getTitle());
            pst.setString(2, newLibraryObj.getDescriptionOrabstract());
            pst.setString(3, newLibraryObj.getPublisher());
            pst.setString(4, newLibraryObj.getAuthor());
            pst.setString(5, newLibraryObj.getPublished());
            saveResult = pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (saveResult != 0) {
            navigationResult = "/Home_Article.xhtml?faces-redirect=true";
        } else {
            navigationResult = "/operation/article/createArticle.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }

    /* Method Used To Edit Student Record In Database */
    public static String editArticleRecordInDB(int Id) {
        ArticleBean editRecord = null;
        System.out.println("editArticleRecordInDB() : Article Id: " + Id);

        /* Setting The Particular Student Details In Session */
        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select * from article where id = " + Id);
            if (rs != null) {
                rs.next();
                editRecord = new ArticleBean();
                editRecord.setId(rs.getInt("id"));
                editRecord.setTitle(rs.getString("title"));
                editRecord.setDescriptionOrabstract(rs.getString("descriptionOrabstract"));
                editRecord.setPublisher(rs.getString("publisher"));
                editRecord.setAuthor(rs.getString("author"));
                editRecord.setPublished(rs.getString("published"));
            }
            sessionMapObj.put("editRecordObj", editRecord);
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/operation/article/editArticle.xhtml?faces-redirect=true";
    }

    /* Method Used To Update Ebook Record In Database */
    public static String updateArticleDetailsInDB(ArticleBean updateArticleObj) {
        try {
            pst = getConnection().prepareStatement("update article set title=?, "
                    + "descriptionOrabstract=?, publisher=?, author=?, published=?, ");
            pst.setString(1, updateArticleObj.getTitle());
            pst.setString(2, updateArticleObj.getDescriptionOrabstract());
            pst.setString(3, updateArticleObj.getPublisher());
            pst.setString(4, updateArticleObj.getAuthor());
            pst.setString(5, updateArticleObj.getPublished());
            pst.setInt(6, updateArticleObj.getId());
            pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/Home_Article.xhtml?faces-redirect=true";
    }

    /* Method Used To Delete Ebook Record From Database */
    public static String deleteArticleRecordInDB(int Id) {
        System.out.println("deleteArticleRecordInDB() : Id: " + Id);
        try {
            pst = getConnection().prepareStatement("delete from article where id = " + Id);
            pst.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
        return "/Home_Article.xhtml?faces-redirect=true";
    }
    private ArticleBean articleBean;

    //Search Operation;
    public ArrayList<ArticleBean> selectFromArticle() throws SQLException, ClassNotFoundException {
        stmt = getConnection().createStatement();
        String searchQuery = "SELECT * FROM article WHERE id = ?";
        ArticleBean ab = new ArticleBean();

        pst = conn.prepareStatement(searchQuery);
        pst.setString(1, ab.getTitle());

        rs = pst.executeQuery();
        ArrayList<ArticleBean> list = new ArrayList<ArticleBean>();

        while (rs.next()) {
            ArticleBean abean = new ArticleBean();
            abean.setId(rs.getInt("id"));
            abean.setTitle(rs.getString("title"));
            abean.setDescriptionOrabstract(rs.getString("descriptionOrabstract"));
            abean.setPublisher(rs.getString("publisher"));
            abean.setAuthor(rs.getString("author"));
            abean.setPublished(rs.getString("published"));
            list.add(abean);
        }

        if (pst != null) {
            pst.close();
        }

        if (conn != null) {
            conn.close();
        }
        return list;
    }
}
