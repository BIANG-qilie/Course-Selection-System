package com.zhuboyang.www.util;

import javax.sql.PooledConnection;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * @author BIANG
 */
public class ConnectionPool {
    private String user ;
    private String password;
    private String driverName ;
    private String url ;

    private String testTable;

    /**
     * 连接池的初始大小
     */
    private int initialConnections;
    /**
     * 连接池每次自动增加的大小
     */
    private int incrementalConnections;
    /**
     * 连接池连接上限
     */
    private int maxConnections;

    /**
     * 存放连接的Vector
     */
    private ArrayList connections = null;

    /**
     * 由配置文件读取各种参数
     */
    public void poolInitialization(){
        InputStream input = null;

        Properties prop = new Properties();
        String propFileName = "config.properties";

        input = getClass().getClassLoader().getResourceAsStream(propFileName);
        
        if (input != null) {
            try {
                prop.load(input);
                user = prop.getProperty("user");
                password= prop.getProperty("password");
                driverName= prop.getProperty("driverName");
                url= prop.getProperty("url");
                testTable=prop.getProperty("testTable");

                initialConnections=Integer.parseInt(prop.getProperty("initialConnections"));
                incrementalConnections=Integer.parseInt(prop.getProperty("incrementalConnections"));
                maxConnections=Integer.parseInt(prop.getProperty("maxConnections"));

                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }


    }

    /**
     * 创建连接池
     * @throws Exception
     */
    public synchronized void createPool() throws Exception {
        // 验证连接池是否创建
        if (connections != null) {
            return; // 如果己经创建，则返回
        }
        //从配置文件中读取数据库连接参数和连接池参数
        poolInitialization();
        // 注册驱动程序
        Driver driver = (Driver) (Class.forName(this.driverName).newInstance());
        DriverManager.registerDriver(driver);
        // 创建保存连接的向量 , 初始时有0个元素
        /*connections = new Vector();*/
        connections = new ArrayList();
        // 根据 initialConnections 中设置的值，创建连接。
        createConnections(this.initialConnections);
    }

    /**
     * 调用newConnection（）创建连接
     * @param numberToCreateConnections
     * @throws SQLException
     */
    private void createConnections(int numberToCreateConnections) throws SQLException {
        // 循环创建指定数目的数据库连接
        for (int x = 0; x < numberToCreateConnections; x++) {
            if (this.maxConnections > 0
                    && this.connections.size() >= this.maxConnections) {
                break;
            }
            try {
                /*connections.addElement(new com.zhuboyang.www.util.PooledConnection(newConnection()));*/
                connections.add(new com.zhuboyang.www.util.PooledConnection(newConnection()));
            } catch (SQLException e) {
                System.out.println(" 创建数据库连接失败 " + e.getMessage());
                throw new SQLException();
            }
            System.out.println(" 数据库连接成功创建"+this.connections.size()+"个");
        }
    }

    /**
     * 创建连接
     * @return
     * @throws SQLException
     */
    private Connection newConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        if (connections.size() == 0) {
            // DatabaseMetaData类是java.sql包中的类，利用它可以获取我们连接到的数据库的结构、存储等很多信息 比如数据库最大连接数
            DatabaseMetaData metaData = connection.getMetaData();
            int driverMaxConnections = metaData.getMaxConnections();
            //有一些数据库有连接上限（driverMaxConnections>0） 有一些没有（driverMaxConnections=0）
            if (driverMaxConnections > 0
                    && this.maxConnections > driverMaxConnections) {
                this.maxConnections = driverMaxConnections;
            }
        }
        return connection;
    }

    /**
     * 获取连接
     */
    public synchronized Connection getConnection() throws SQLException  {
        // 确保连接池己被创建
        if (connections == null) {
            return null; // 连接池还没创建，则返回 null
        }
        // 获得一个可用的数据库连接
        Connection connection = getFreeConnection();
        // 如果目前没有可以使用的连接，即所有的连接都在使用中
        while (connection == null) {
            // 等待一会再试
            wait(300);
            // 重新再试，直到获得可用的连接，如果getFreeConnection() 返回的为 null 则表明创建一批连接后也不可获得可用连接
            connection = getFreeConnection();
        }
        return connection;// 返回获得的可用的连接
    }

    /**
     * 获取空闲连接，（寻找不到就新键）
     *
     * @return 返回一个可用的数据库连接
     */
    private Connection getFreeConnection() throws SQLException {
        Connection conn = findFreeConnection();
        if (conn == null) {
            createConnections(incrementalConnections);
            conn = findFreeConnection();
            if (conn == null) {
                return null;
            }
        }
        return conn;
    }

    /**
     * 寻找空闲连接
     *
     * @return 返回一个可用的数据库连接
     */
    private Connection findFreeConnection() throws SQLException {
        Connection connection = null;
        com.zhuboyang.www.util.PooledConnection pooledConnection = null;
        // 获得连接池向量中所有的对象
        // 遍历所有的对象，看是否有可用的连接
        for(int i=0;i<connections.size();i++) {
            pooledConnection = (com.zhuboyang.www.util.PooledConnection) connections.get(i);
            if (!pooledConnection.isBusy()) {
                // 如果此对象不忙，则获得它的数据库连接并把它设为忙
                connection = pooledConnection.getConnection();
                pooledConnection.setBusy(true);
                // 测试此连接是否可用
                if (!testConnection(connection)) {
                    // 如果此连接不可再用了，则创建一个新的连接，
                    // 并替换此不可用的连接对象，如果创建失败，返回 null
                    try {
                        connection = newConnection();
                    } catch (SQLException e) {
                        System.out.println(" 创建数据库连接失败 " + e.getMessage());
                        return null;
                    }
                    pooledConnection.setConnection(connection);
                }
                break; // 己经找到一个可用的连接，退出
            }
        }
        return connection;// 返回找到到的可用连接
    }

    /**
     * 测试一个连接是否可用，若不可用将关掉
     *
     * @param connection 需要测试的数据库连接
     * @return 返回 true 表示此连接可用， false 表示不可用
     */
    private boolean testConnection(Connection connection) {
        try {
            // 判断测试表是否存在
            if (testTable.equals("")) {
                // 如果测试表为空，试着使用此连接的 setAutoCommit() 方法
                // 来判断连接否可用（此方法只在部分数据库可用，如果不可用 ,
                // 抛出异常）。注意：使用测试表的方法更可靠
                connection.setAutoCommit(true);
            } else {// 有测试表的时候使用测试表测试
                // check if this connection is valid
                Statement stmt = connection.createStatement();
                stmt.execute("select count(*) from " + testTable);
            }
        } catch (SQLException e) {
            // 上面抛出异常，此连接己不可用，关闭它，并返回 false;
            closeConnection(connection);
            return false;
        }
        // 连接可用，返回 true
        return true;
    }

    /**
     * 返回连接
     *
     * @param connection 需返回到连接池中的连接对象
     */
    public void returnConnection(Connection connection) {
        // 确保连接池存在，如果连接没有创建（不存在），直接返回
        if (connections == null) {
            System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
            return;
        }
        com.zhuboyang.www.util.PooledConnection pConn = null;
        // 遍历连接池中的所有连接，找到这个要返回的连接对象并统计空闲连接个数
        int freeConnection=0;
        for(int i=0;i<connections.size();i++) {
            pConn = (com.zhuboyang.www.util.PooledConnection) connections.get(i);
            // 先找到连接池中的要返回的连接对象
            if (connection == pConn.getConnection()) {
                // 找到了 , 设置此连接为空闲状态
                pConn.setBusy(false);
                break;
            }
            if(!pConn.isBusy()){
                freeConnection++;
            }
        }
        if(freeConnection>incrementalConnections*2) {
            removeConnection(freeConnection);
        }
    }

    /**
     * 刷新连接池中所有的连接对象
     *
     */
    public synchronized void refreshConnections() throws SQLException {
        // 确保连接池己创新存在
        if (connections == null) {
            System.out.println(" 连接池不存在，无法刷新 !");
            return;
        }
        com.zhuboyang.www.util.PooledConnection pConn = null;
        for (int i=0;i<connections.size();i++) {
            // 获得一个连接对象
            pConn = (com.zhuboyang.www.util.PooledConnection) connections.get(i);
            // 如果对象忙则等 5 秒 ,5 秒后直接刷新
            if (pConn.isBusy()) {
                wait(5000); // 等 5 秒
            }
            // 关闭此连接，用一个新的连接代替它。
            closeConnection(pConn.getConnection());
            pConn.setConnection(newConnection());
            pConn.setBusy(false);
        }
    }

    /**
     * 关闭连接池中所有的连接，并清空连接池。
     */
    public synchronized void closeConnectionPool() throws SQLException {
        // 确保连接池存在，如果不存在，返回
        if (connections == null) {
            System.out.println(" 连接池不存在，无法关闭 !");
            return;
        }
        com.zhuboyang.www.util.PooledConnection pConn = null;
        for (int i=0;i<connections.size();i++) {
            pConn = (com.zhuboyang.www.util.PooledConnection) connections.get(1);
            // 如果忙，等 5 秒
            if (pConn.isBusy()) {
                wait(5000); // 等 5 秒
            }
            // 5 秒后直接关闭它
            closeConnection(pConn.getConnection());
            // 从连接池向量中删除它
            connections.remove(pConn);
        }
        // 置连接池为空
        connections = null;
    }

    /**
     * 关闭一个数据库连接
     *
     * @param connection 需要关闭的数据库连接
     */
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(" 关闭数据库连接出错： " + e.getMessage());
        }
    }

    /**
     * 使程序等待给定的毫秒数
     *
     * @param seconds 给定的毫秒数
     */
    private void wait(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 缩容连接池
     * @param freeConnection
     */
    private void removeConnection(int freeConnection){
        // 确保连接池存在，如果不存在，返回
        if (connections == null) {
            System.out.println(" 连接池不存在，无法缩容!");
            return;
        }
        com.zhuboyang.www.util.PooledConnection pConn = null;
        for (int i=0;i<connections.size()&&freeConnection>2*incrementalConnections&&connections.size()>initialConnections;i++) {
            pConn = (com.zhuboyang.www.util.PooledConnection) connections.get(i);
            if(!pConn.isBusy()){
                closeConnection(pConn.getConnection());
                connections.remove(pConn);
                System.out.println("空闲连接还有"+(--freeConnection)+"个");
            }
        }
    }

}
