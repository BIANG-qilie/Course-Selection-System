package com.zhuboyang.www.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author BIANG
 */
public interface ResultGet<T> {
    /**
     * 用于确定如何获取返回的数据
     * @param rs
     * @return
     * @throws SQLException
     */
    public T getReturn(ResultSet rs) throws SQLException;
}
