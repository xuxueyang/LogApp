package example;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
//"C:\Program Files\Java\jdk1.8.0_144\bin\java" -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53908,suspend=y,server=n -Dfile.encoding=GBK -classpath "C:\Program Files\Java\jdk1.8.0_144\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_144\jre\lib\rt.jar;C:\;D:\xxy\LogApp_Git\target\classes;D:\xxy\LogApp_Git\extends\JTattoo.jar;D:\xxy\LogApp_Git\extends\substance.jar;D:\xxy\LogApp_Git\extends\gson-2.7.jar;D:\xxy\LogApp_Git\extends\qtjambi-4.8.7.jar;D:\xxy\LogApp_Git\extends\jacob.jar;D:\xxy\LogApp_Git\extends\zip4j_1.3.1.jar;D:\xxy\LogApp_Git\extends\commons-io-2.6.jar;C:\Users\Administrator\.m2\repository\org\apache\tomcat\tomcat-jdbc\8.5.15\tomcat-jdbc-8.5.15.jar;C:\Users\Administrator\.m2\repository\org\apache\tomcat\tomcat-juli\8.5.15\tomcat-juli-8.5.15.jar;C:\Users\Administrator\.m2\repository\com\jcraft\jsch\0.1.51\jsch-0.1.51.jar;C:\Users\Administrator\.m2\repository\org\apache\commons\commons-lang3\3.5\commons-lang3-3.5.jar;C:\Users\Administrator\.m2\repository\commons-net\commons-net\2.2\commons-net-2.2.jar;C:\Users\Administrator\.m2\repository\ch\ethz\ganymed\ganymed-ssh2\build210\ganymed-ssh2-build210.jar;C:\Users\Administrator\.m2\repository\commons-io\commons-io\2.4\commons-io-2.4.jar;C:\Users\Administrator\.m2\repository\com\google\code\gson\gson\2.7\gson-2.7.jar;D:\Program Files\IntelliJ IDEA Community Edition 2017.2.6\lib\idea_rt.jar" TomcatJDBCTest
//        Connected to the target VM, address: '127.0.0.1:53908', transport: 'socket'
//        三月 19, 2018 2:55:34 下午 org.apache.tomcat.jdbc.pool.ConnectionPool checkPoolConfiguration
//        警告: minIdle is larger than maxActive, setting minIdle to: 5
//        三月 19, 2018 2:55:35 下午 org.apache.tomcat.jdbc.pool.ConnectionPool checkPoolConfiguration
//        警告: maxIdle is larger than maxActive, setting maxIdle to: 5
//        三月 19, 2018 2:55:35 下午 org.apache.tomcat.jdbc.pool.ConnectionPool init
//        严重: Unable to create initial connections of pool.
//        java.sql.SQLException: Unable to load class: com.mysql.jdbc.Driver from ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2;ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2
//        at org.apache.tomcat.jdbc.pool.PooledConnection.connectUsingDriver(PooledConnection.java:283)
//        at org.apache.tomcat.jdbc.pool.PooledConnection.connect(PooledConnection.java:203)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.createConnection(ConnectionPool.java:735)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.borrowConnection(ConnectionPool.java:667)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.init(ConnectionPool.java:482)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.<init>(ConnectionPool.java:154)
//        at org.apache.tomcat.jdbc.pool.DataSourceProxy.pCreatePool(DataSourceProxy.java:118)
//        at org.apache.tomcat.jdbc.pool.DataSourceProxy.createPool(DataSourceProxy.java:107)
//        at org.apache.tomcat.jdbc.pool.DataSourceProxy.getConnection(DataSourceProxy.java:131)
//        at TomcatJDBCTest.main(TomcatJDBCTest.java:33)
//        Caused by: java.lang.ClassNotFoundException: Unable to load class: com.mysql.jdbc.Driver from ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2;ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2
//        at org.apache.tomcat.jdbc.pool.ClassLoaderUtil.loadClass(ClassLoaderUtil.java:56)
//        at org.apache.tomcat.jdbc.pool.PooledConnection.connectUsingDriver(PooledConnection.java:271)
//        ... 9 more
//        Caused by: java.lang.ClassNotFoundException: com.mysql.jdbc.Driver
//        at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
//        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
//        at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:335)
//        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
//        at java.lang.Class.forName0(Native Method)
//        at java.lang.Class.forName(Class.java:348)
//        at org.apache.tomcat.jdbc.pool.ClassLoaderUtil.loadClass(ClassLoaderUtil.java:38)
//        ... 10 more
//
//        java.sql.SQLException: Unable to load class: com.mysql.jdbc.Driver from ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2;ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2
//        at org.apache.tomcat.jdbc.pool.PooledConnection.connectUsingDriver(PooledConnection.java:283)
//        at org.apache.tomcat.jdbc.pool.PooledConnection.connect(PooledConnection.java:203)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.createConnection(ConnectionPool.java:735)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.borrowConnection(ConnectionPool.java:667)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.init(ConnectionPool.java:482)
//        at org.apache.tomcat.jdbc.pool.ConnectionPool.<init>(ConnectionPool.java:154)
//        at org.apache.tomcat.jdbc.pool.DataSourceProxy.pCreatePool(DataSourceProxy.java:118)
//        at org.apache.tomcat.jdbc.pool.DataSourceProxy.createPool(DataSourceProxy.java:107)
//        at org.apache.tomcat.jdbc.pool.DataSourceProxy.getConnection(DataSourceProxy.java:131)
//        at TomcatJDBCTest.main(TomcatJDBCTest.java:33)
//        Caused by: java.lang.ClassNotFoundException: Unable to load class: com.mysql.jdbc.Driver from ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2;ClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2
//        at org.apache.tomcat.jdbc.pool.ClassLoaderUtil.loadClass(ClassLoaderUtil.java:56)
//        at org.apache.tomcat.jdbc.pool.PooledConnection.connectUsingDriver(PooledConnection.java:271)
//        ... 9 more
//        Caused by: java.lang.ClassNotFoundException: com.mysql.jdbc.Driver
//        at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
//        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
//        at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:335)
//        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
//        at java.lang.Class.forName0(Native Method)
//        at java.lang.Class.forName(Class.java:348)
//        at org.apache.tomcat.jdbc.pool.ClassLoaderUtil.loadClass(ClassLoaderUtil.java:38)
//        ... 10 more
//        Disconnected from the target VM, address: '127.0.0.1:53908', transport: 'socket'
//
//        Process finished with exit code 0

public class TomcatJDBCTest {
    public static void main(String[] args){
        try{
            // 设置连接池的参数
            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:mysql://127.0.0.1/test?characterEncoding=UTF-8");
            p.setUsername("root");
            p.setPassword("Xuxueyang1");
            p.setDriverClassName("com.mysql.jdbc.Driver");

            p.setMinIdle(5); //始终保留的连接的最小连接数，默认10
            p.setMinIdle(6); //设置池内最大空闲连接数
            p.setInitialSize(5); //启动时初始连接数 默认10
            p.setMaxActive(5); //最大活跃连接数
            p.setTestOnBorrow(true); //取出连接时是否对其进行验证
            p.setValidationQuery("select 1"); //如果上面设置为true，则这里要设置

            // 创建DataSource
            DataSource dataSource = new DataSource();
            dataSource.setPoolProperties(p);

            // 获取数据库连接
            Connection connection = dataSource.getConnection();

            // 执行一个简单的查询
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM users");

            while (resultSet.next()){ //循环打印结果集

                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("email"));
            }

            statement.close();


            TimeUnit.SECONDS.sleep(5); //这句是为了模拟卡顿
            connection.close(); //关闭数据库连接
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
