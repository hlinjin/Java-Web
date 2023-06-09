package cn.halinjin.mybook.dao;

import cn.halinjin.mybook.bean.Type;
import cn.halinjin.mybook.until.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */

public class TypeDao {
    //构建QueryRunner对象
    QueryRunner runner=new QueryRunner();

    /**
     *添加图书类型
     * 主键id自增列
     * @param name
     * @param parentId
     * @return
     */
    public int add(String name,long parentId) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="insert into type values(null,?,?)";
        int count= runner.update(conn,sql,name,parentId);
        conn.close();
        return count;
    }

    /**
     * 获取所有的类型
     * @return
     */
    public List<Type> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select id,name,parentId from type ";//查询全部数据
        List<Type> types = runner.query(conn,sql,new BeanListHandler<Type>(Type.class));//所以要用集合BeanListHandler来完成
        conn.close();
        return types;
    }

    /**
     * 根据类型编号获取类型对象
     * @param typeId
     * @return
     */
    public Type getById(long typeId) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="select id,name,parentId from type where id=?";
        Type type=runner.query(conn,sql,new BeanHandler<>(Type.class),typeId);
        conn.close();
        return type;
    }

    /**
     * 修改图书类型
     * @param id 需要修改的类型编号
     * @param name
     * @param parentId
     * @return
     */
    public int modify(long id,String name,long parentId) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="update type set name=?,parentId=? where id=?";
        int count= runner.update(conn,sql,name,parentId,id);
        conn.close();
        return count;
    }

    /**
     * 删除图书类型
     * @param id
     * @return
     */
    public int remove(long id) throws SQLException {
        Connection conn=DBHelper.getConnection();
        String sql="delete from type where id=?";  //直接物理删除
        int count= runner.update(conn,sql,id);
        conn.close();
        return count;
    }

    public static void main(String[] args) {
        List<Type>  types = null;
        try {
            types = new TypeDao().getAll();
            new TypeDao().remove(14);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(types);
    }

}
