package cn.halinjin.mybook.dao;

import cn.halinjin.mybook.bean.Record;
import cn.halinjin.mybook.until.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RecordDao {
    QueryRunner runner=new QueryRunner();
    public List<Record> getRecordByBookId(long bookId) throws SQLException {

        Connection conn= DBHelper.getConnection();
        String sql="select *from record where bookId=?";
        List<Record> records=runner.query(conn,sql,new BeanListHandler<>(Record.class),bookId);
        return records;


    }

    public static void main(String[] args) {
        try {
            List<Record> records= new RecordDao().getRecordByBookId(3);
            System.out.println(records);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
