package cn.halinjin.mybook.biz;

import cn.halinjin.mybook.bean.User;
import cn.halinjin.mybook.dao.UserDao;

import java.sql.SQLException;

public class UserBiz {
    //构建UserDao的对象因为后面都要用到
    UserDao userDao=new UserDao();
    public User getUser(String name,String pwd){
        //传递
        User user=null;                        //构建User对象初始化为空
        try {
            user=userDao.getUser(name, pwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }        //在这里就把异常处理掉 如果用户为空Servlet拿到之后就知道用户名和密码错误就不用再报告给Servlet所以现在就在biz层把它处理掉
        return user;
    }
    public int modifyPwd(long id,String pwd){
        int count=0;
        try {
            count=userDao.modifyPwd(id,pwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

}
