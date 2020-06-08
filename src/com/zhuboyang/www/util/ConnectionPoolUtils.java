package com.zhuboyang.www.util;

/**
 * @author dell
 */
public class ConnectionPoolUtils {
    private ConnectionPoolUtils() {
    }

    /**
     * 连接池实体
     */
    private static ConnectionPool poolInstance = null;

    public static ConnectionPool GetPoolInstance() {
        if (poolInstance == null) {
            poolInstance = new ConnectionPool();
            try {
                poolInstance.createPool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return poolInstance;
    }
}
