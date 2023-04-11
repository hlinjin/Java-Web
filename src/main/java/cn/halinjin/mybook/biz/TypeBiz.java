package cn.halinjin.mybook.biz;

import cn.halinjin.mybook.bean.Book;
import cn.halinjin.mybook.bean.Type;
import cn.halinjin.mybook.dao.BookDao;
import cn.halinjin.mybook.dao.TypeDao;

import java.sql.SQLException;
import java.util.List;

//写相关的方法和类
public class TypeBiz {
    TypeDao typeDao=new TypeDao();
    public List<Type> getAll(){
        try {
            return typeDao.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public int add(String name,long parentId){
        int count =0;
        try {
            count=typeDao.add(name,parentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    public int modify(long id,String name,long parentId){
        int count=0;            //初始值为零
        try {
            count=typeDao.modify(id,name,parentId);//定义一个count类型返回结果
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    /**
     * 删除:
     * 表与表之间关系：逻辑关系
     * type是book的一个外键
     * @param id
     * @return 0:删除失败  >0:Exception:提示用户的信息
     */
    public int remove(long id) throws Exception{
        //如果有子项，是不能删除
        BookDao bookDao=new BookDao();   //因为这个不是经常调，只有现在这个方法里面调一次，所以作为局部变量
        int count=0;            //初始值为零
        try {
            List<Book> books=bookDao.getBooksByTypeId(id);   //用这个bookDao去调id
            if (books.size()>0){    //如果找到的id有子信息就不能删除
                //不能删除
                throw new Exception("删除的类型有子信息,删除失败"); //业务往外抛
            }
            count=typeDao.remove(id);//定义一个count类型返回结果
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;

    }
    public Type getById(long id){
        try {
            return typeDao.getById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
