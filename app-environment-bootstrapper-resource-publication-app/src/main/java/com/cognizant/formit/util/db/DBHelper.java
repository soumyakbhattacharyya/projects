/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.db;

import com.cognizant.formit.model.RNode;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

/**
 * for performing db interaction
 *
 * @author Cognizant
 */
public class DBHelper {

    private static Logger l = Logger.getLogger(DBHelper.class);
    private ComboPooledDataSource cpds;
    private static DBHelper me;
    private IConnectionPoolInitializer connectionPoolInitializer;

    /*
     * encapsulates a pool of connection
     */
    public static DBHelper newInstance(
            IConnectionPoolInitializer connectionPoolInitializer)
            throws PropertyVetoException {
        if (null != me) {
            return me;
        }
        me = new DBHelper(connectionPoolInitializer);
        return me;
    }

    private DBHelper(IConnectionPoolInitializer connectionPoolInitializer)
            throws PropertyVetoException {
        //cpds = init(connectionPoolInitializer);
        this.connectionPoolInitializer = connectionPoolInitializer;
    }

    /*
     * Initialize connection pool
     */
    private ComboPooledDataSource init(
            IConnectionPoolInitializer connectionPoolInitializer)
            throws PropertyVetoException {
        // Read pool itializing properties
        String dbUrl = connectionPoolInitializer.dbUrl();
        String dbClass = connectionPoolInitializer.dbDriverClass();
        String username = connectionPoolInitializer.dbUid();
        String password = connectionPoolInitializer.dbPwd();
        int maxPoolSize = connectionPoolInitializer.dbMaxPoolSize();
        int minPoolSize = connectionPoolInitializer.dbMinPoolSize();
        int initialPoolSize = connectionPoolInitializer.dbInitialPoolSize();
        int maxIdleTime = connectionPoolInitializer.dbMaxIdleTime();

        // Create pool
        cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(dbClass);
            cpds.setJdbcUrl(dbUrl);
            cpds.setUser(username);
            cpds.setPassword(password);
            cpds.setInitialPoolSize(initialPoolSize);
            cpds.setMinPoolSize(minPoolSize);
            cpds.setMaxPoolSize(maxPoolSize);
            cpds.setMaxIdleTime(maxIdleTime);
        } catch (PropertyVetoException ex) {
            l.error(ex.getMessage());
            throw ex;
        }

        return cpds;
    }

    /*
     * create resource definition
     */
    public int create(List<RNode> nodes) throws SQLException, ClassNotFoundException {
        int numberOfRecordsCreated = 0;
        QueryRunner run = new QueryRunner();
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        try {
            for (RNode node : nodes) {
                int inserts = run
                        .update(connection,
                        "INSERT INTO "
                        + "jpaas_node (name,hostname,username,description,osName,osFamily,osArch,osVersion,tags) "
                        + "VALUES (?,?,?,?,?,?,?,?,?)",
                        node.getName(), node.getHostname(),
                        node.getUsername(), node.getDescription(),
                        node.getOsName(), node.getOsFamily(),
                        node.getOsArch(), node.getOsVersion(),
                        node.getTags());
                numberOfRecordsCreated = numberOfRecordsCreated + inserts;
            }
            connection.commit();

        } catch (SQLException sqle) {
            connection.rollback();
            sqle.printStackTrace();
            throw sqle;
        } finally {
            if (!connection.isClosed()) {
                connection.close();
            }
        }

        return numberOfRecordsCreated;

    }

    /*
     * create a resource definition
     */
    public int create(RNode node) throws SQLException, ClassNotFoundException {
        int numberOfRecordsCreated = 0;
        QueryRunner run = new QueryRunner();
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        try {

            int inserts = run
                    .update(connection,
                    "INSERT INTO "
                    + "jpaas_node (name,hostname,username,description,osName,osFamily,osArch,osVersion,tags) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)",
                    node.getName(), node.getHostname(),
                    node.getUsername(), node.getDescription(),
                    node.getOsName(), node.getOsFamily(),
                    node.getOsArch(), node.getOsVersion(),
                    node.getTags());
            numberOfRecordsCreated = numberOfRecordsCreated + inserts;

            connection.commit();

        } catch (SQLException sqle) {
            connection.rollback();
            sqle.printStackTrace();
            throw sqle;
        } finally {
            if (!connection.isClosed()) {
                connection.close();
            }
        }

        return numberOfRecordsCreated;

    }

    /*
     * query resource(s) against name, hostname and username
     */
    public List<RNode> find(final String name, final String hostname,
            final String username) throws SQLException, ClassNotFoundException {
        QueryRunner run = new QueryRunner();
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        List<RNode> nodes = new ArrayList<RNode>();
        try {
            // Use the BeanListHandler implementation to convert all
            // ResultSet rows into a List of Person JavaBeans.
            ResultSetHandler<List<RNode>> h = new BeanListHandler<RNode>(
                    RNode.class);
            nodes = run
                    .query(connection,
                    "SELECT name,hostname,username,description,osName,osFamily,osArch,osVersion,tags,attribute "
                    + "FROM jpaas_node WHERE name=? AND hostname = ? AND username = ?",
                    new String[]{name, hostname, username}, h);
            l.debug(nodes);
            connection.commit();
        } catch (SQLException sqle) {
            connection.rollback();
            l.error(sqle);
            throw sqle;
        } finally {
            if (!connection.isClosed()) {
                connection.close();
            }
        }

        return nodes;

    }

    /*
     * query resource(s) against name, hostname and username
     */
    public List<RNode> findAll() throws SQLException, ClassNotFoundException {
        QueryRunner run = new QueryRunner();
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        List<RNode> nodes = new ArrayList<RNode>();
        try {
            // Use the BeanListHandler implementation to convert all
            // ResultSet rows into a List of Person JavaBeans.
            ResultSetHandler<List<RNode>> h = new BeanListHandler<RNode>(
                    RNode.class);
            nodes = run
                    .query(connection,
                    "SELECT name,hostname,username,description,osName,osFamily,osArch,osVersion,tags,attribute "
                    + "FROM jpaas_node", h);
            l.debug(nodes);
            connection.commit();
        } catch (SQLException sqle) {
            connection.rollback();
            l.error(sqle);
            throw sqle;
        } finally {
            if (!connection.isClosed()) {
                connection.close();
            }
        }

        return nodes;

    }

    /*
     * delete resource definition
     */
    public int delete(final String name, final String hostname,
            final String username) throws SQLException, ClassNotFoundException {
        int numberOfRecordsCreated = 0;
        QueryRunner run = new QueryRunner();
        Connection connection = this.getConnection();
        connection.setAutoCommit(false);
        try {

            int deletes = run.update(connection, "DELETE FROM " + "jpaas_node "
                    + "WHERE name=? AND hostname = ? AND username = ?", name,
                    hostname, username);
            numberOfRecordsCreated = numberOfRecordsCreated + deletes;

            connection.commit();
        } catch (SQLException sqle) {
            connection.rollback();
            sqle.printStackTrace();
            throw sqle;
        } finally {
            if (!connection.isClosed()) {
                connection.close();
            }
        }
        return numberOfRecordsCreated;
    }

    public void cleanup() throws SQLException { // make sure it's a c3p0
        // PooledDataSource
        if (this.cpds instanceof PooledDataSource) {
            PooledDataSource pds = (PooledDataSource) this.cpds;
            pds.close();
        } else {
            l.info("Not a c3p0 PooledDataSource!");
        }
    }

    private Connection getConnection()
            throws SQLException, ClassNotFoundException {
        Class.forName(connectionPoolInitializer.dbDriverClass());
        l.info(connectionPoolInitializer.dbUrl());
        l.info(connectionPoolInitializer.dbUid());
        l.info(connectionPoolInitializer.dbPwd());
        return DriverManager.getConnection(connectionPoolInitializer.dbUrl(),
                connectionPoolInitializer.dbUid(),
                connectionPoolInitializer.dbPwd());
    }
}
