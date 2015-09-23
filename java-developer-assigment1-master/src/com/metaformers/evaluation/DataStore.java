/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metaformers.evaluation;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author boblarsen
 */
public class DataStore implements AutoCloseable {

    private final Statement s;

    /**
     * Creates a Database containing the <code>PRODUCTS</code> table.
     */
    public DataStore() {
        try {
            s = DriverManager.getConnection("jdbc:derby:evalDB;create=true").createStatement();
            s.executeUpdate("create table PRODUCTS(PRODUCT_ID int, MANUFACTURER varchar(64), PRODUCT_CODE varchar(8), PURCHASE_COST float, QUANTITY_ON_HAND int, MARKUP float, AVAILABLE boolean, DESCRIPTION varchar(64))");
        } catch (SQLException ex) {
            throw new RuntimeException("Could not create database", ex);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            s.executeUpdate("drop table PRODUCTS");
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            if (!((se.getErrorCode() == 50000) && ("XJ015".equals(se.getSQLState())))) {
                throw se;
            }

        }
    }

    /**
     * Executes the given SQL statement, which may be an <code>INSERT</code>,
     * <code>UPDATE</code>, or <code>DELETE</code> statement or an SQL statement
     * that returns nothing, such as an SQL DDL statement.
     * <p>
     * @param query an SQL Data Manipulation Language (DML) statement, such as
     * <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code>; or an
     * SQL statement that returns nothing, such as a DDL statement.
     *
     * @return either (1) the row count for SQL Data Manipulation Language (DML)
     * statements or (2) 0 for SQL statements that return nothing
     *
     * @exception SQLException if a database access error occurs or the given
     * SQL statement produces a <code>ResultSet</code> object
     */
    public int update(String query) throws SQLException {
        return s.executeUpdate(query);
    }

    /**
     * Executes the given SQL statement, which returns a single
     * <code>ResultSet</code> object.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * <code>PreparedStatement</code> or <code>CallableStatement</code>.
     *
     * @param query an SQL statement to be sent to the database, typically a
     * static SQL <code>SELECT</code> statement
     * @return a <code>ResultSet</code> object that contains the data produced
     * by the given query; never <code>null</code>
     * @exception SQLException if a database access error occurs or the given SQL
     * statement produces anything other than a single <code>ResultSet</code>
     * object
     */
    public ResultSet query(String query) throws SQLException {
        return s.executeQuery(query);
    }
}
