package com.zhuboyang.www.dao;

import com.zhuboyang.www.po.User;
import com.zhuboyang.www.util.ConnectionPool;
import com.zhuboyang.www.util.ConnectionPoolUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author dell
 */
public abstract class BaseDao {
    protected String tableName;
    /**
     * 数据库更新
     * @param sql
     * @param objects
     * @return
     */
    protected final String BASE_SQL ="SELECT * FROM";
    public boolean update(String sql, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ConnectionPool connPool=null;
        int result = 0;
        try {
            connPool= ConnectionPoolUtils.GetPoolInstance();
            connection = connPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //获取参数元数据 获取参数数 也就是问号数
            int parameterNumber=preparedStatement.getParameterMetaData().getParameterCount();
            for (int i = 0; i <parameterNumber ; i++)  {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            result = preparedStatement.executeUpdate();
            connection.commit();
            return (result != 0);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            close(preparedStatement);
            connPool.returnConnection(connection);
        }
        return false;
    }
    /**
     * 数据库更新
     * @param sql
     * @param rg
     * @param objects
     * @param <T>
     * @return
     */
    public <T> T query(String sql, ResultGet rg, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ConnectionPool connPool=null;
        ResultSet rs = null;
        try {
            connPool= ConnectionPoolUtils.GetPoolInstance();
            connection = connPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //获取参数元数据 获取参数数 也就是问号数
            int parameterNumber=preparedStatement.getParameterMetaData().getParameterCount();
            for (int i = 0; i <parameterNumber ; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            rs = preparedStatement.executeQuery();
            connection.commit();
            T t = (T) rg.getReturn(rs);
            return t;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            connPool.returnConnection(connection);
            close(preparedStatement, rs);
        }
        return null;
    }
    /**
     * PreparedStatement，ResultSet的关闭
     * @param psst
     * @param rs
     */
    public void close(PreparedStatement psst, ResultSet... rs) {
        if (rs.length > 0) {
            if (rs[0] != null) {
                try {
                    rs[0].close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (psst != null) {
            try {
                psst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList queryArrayList(String extraSql, Object...objects){
        String sql=BASE_SQL+tableName+extraSql;
        return query(sql, new ResultGet() {
            @Override
            public Object getReturn(ResultSet rs) throws SQLException {
                ArrayList arrayList=new ArrayList();
                while(rs.next()){
                    arrayList.add(getInstance(rs));
                }
                return arrayList;
            }
        },objects);
    }

    public Object queryOneSelf(String extraSql, Object...objects){

        String sql= BASE_SQL+tableName+extraSql;
        return query(sql, new ResultGet() {
            @Override
            public Object getReturn(ResultSet rs) throws SQLException {
                Object object=null;
                if(rs.next()){
                    object= getInstance(rs);
                }
                return object;
            }
        },objects);
    }

    protected abstract Object getInstance(ResultSet rs) throws SQLException;
}
