package cn.halinjin.mybook.dao;

import cn.halinjin.mybook.bean.User;
import cn.halinjin.mybook.until.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用户表的数据操作对象
 */

public class UserDao {
//    创建QueryRunner对象（JDBC———>DBUtils:是Apache组织提供的一个对JDBC进行简单封装的开源工具类库使用它能够简化JDBC应用程序的开发同时
//    也不会影响程序的性能）
    QueryRunner runner=new QueryRunner();
    public User getUser(String name, String pwd) throws SQLException {

//        1.调用DBHelper获取连接对象
        Connection conn = DBHelper.getConnection();
//        2.准备执行的sql语句
        String sql="select *from user where name=? and pwd=? and state=1";

//        3.调用查询方法，将上方查询的一行数据封装成User对象 new BeanHandler<User>(User.class) 参数 name,pwd
        //代表一个JavaBean的对象的我们叫它BeanHandler
        User user=runner.query(conn,sql,new BeanHandler<>(User.class),name,pwd);

//        4.关闭连接对象
        conn.close();

//        5.返回user
        return user;
    }
    /**
     * 修改密码
     * @param id 需要修改密码的用户编号
     * @param pwd  新的密码
     * @return 修改的数据行
     */

    public int modifyPwd(long id,String pwd) throws SQLException {
        String sql="update user set pwd=? where id=?";
        Connection conn=DBHelper.getConnection();
        int count= runner.update(conn,sql,pwd,id);
        conn.close();
        return count;
    }

    public static void main(String[] args) throws SQLException {
        User user = new UserDao().getUser("super","123");
        System.out.println(user);
    }
}