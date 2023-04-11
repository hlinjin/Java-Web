package cn.halinjin.mybook.dao;

import cn.halinjin.mybook.bean.Book;
import cn.halinjin.mybook.until.DBHelper;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookDao {
    QueryRunner runner=new QueryRunner();
    /**
     * 根据类型查询对应的书籍信息
     * @param typeId
     * @return
     * @throws SQLException
     */
    public List<Book> getBooksByTypeId(long typeId) throws SQLException {
        Connection conn= DBHelper.getConnection();
        String sql="select *from book where typeId=?";
        List<Book> books=runner.query(conn,sql,new BeanListHandler<>(Book.class),typeId);
        conn.close();
        return books;
    }

    /**
     * 添加
     * @return
     */
    public int add(long typeId,String name,double price,String desc,String pic,
                    String publish,String author,long stock,String address) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="insert into book(typeId,`name`,price,`desc`,pic,publish,author,stock,address) " +
                "values(?,?,?,?,?,?,?,?,?)";
        int count=runner.update(conn,sql,typeId,name,price,desc,pic,publish,author,stock,address);
        conn.close();
        return count;
    }

    /**
     * 修改书籍
     * @param id
     * @param typeId
     * @param name
     * @param price
     * @param desc
     * @param pic
     * @param publish
     * @param author
     * @param stock
     * @param address
     * @return
     */
    public int modify(long id,long typeId,String name,double price,String desc,String pic,
                      String publish,String author,long stock,String address) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="update book set typeId=?,`name`=?,price=?,`desc`=?,pic=?,publish=?,author=?,stock=?,address=? where id=?";
        int count=runner.update(conn,sql,typeId,name,price,desc,pic,publish,author,stock,address,id);
        conn.close();
        return count;
    }
    public int remove(long id) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="delete from book where id=?";
        int count=runner.update(conn,sql,id);
        conn.close();
        return count;
    }

    /**
     *页面查询(暂时不考虑排序问题)
     * @param pageIndex 第几页，从1开始
     * @param pageSize  每页多少行
     * @return 当前页的信息
     * @throws SQLException
     */
    public List<Book> getByPage(int pageIndex,int pageSize) throws SQLException {
        Connection conn= DBHelper.getConnection();
        String sql="select *from book limit ?,?";
        int offset=(pageIndex-1)*pageSize;
        List<Book> books=runner.query(conn,sql,new BeanListHandler<>(Book.class),offset,pageSize);
        conn.close();
        return books;
    }
    public Book getById(long id) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="select *from book where id=?";
        Book book=runner.query(conn,sql,new BeanHandler<>(Book.class),id);
        conn.close();
        return book;
    }

    /**
     * 获取书籍数量
     * @return
     * @throws SQLException
     */
    public int getCount() throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="select count(id) from book";
        Number data=runner.query(conn,sql,new ScalarHandler<>());
        int count=data.intValue();
        conn.close();
        return count;
    }

    public static void main(String[] args) {
        try {
           /* List<Book> books=new BookDao().getBooksByTypeId(2);
            System.out.println(books.size());//[]:books对象有的，但是没有数据,是一个数组所以加上.size,来看到有多少数据
            for(Book book:books){
                System.out.println(book);
            }*/
            BookDao bookDao=new BookDao();
            List<Book> books=bookDao.getByPage(1,3);
            for(Book book:books){
                System.out.println(book);
            }
            //int count=new BookDao().getCount();
            //System.out.println(count);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
