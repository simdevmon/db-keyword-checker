package com.simdevmon.keywordchecker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Connects to the database and provides its meta data.
 *
 * @author Simon Narkprasert (simdevmon@gmail.com)
 */
public class Connector
{

    private static final String DB_PROPERTIES = "config.database";

    // table types which should be analyzed
    private static final String[] TABLE_TYPES =
    {
        "TABLE", "VIEW", "ALIAS", "SYNONYM"
    };

    private final Connection connection;

    public Connector() throws SQLException
    {
        ResourceBundle dbProperties = ResourceBundle.getBundle(DB_PROPERTIES);
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbProperties.getString("user"));
        connectionProps.put("password", dbProperties.getString("password"));
        this.connection = DriverManager.getConnection(dbProperties.getString("url"), connectionProps);
    }

    public ResultSet getTables() throws SQLException
    {
        return this.connection.getMetaData().getTables(null, null, "%", TABLE_TYPES);
    }

    public ResultSet getColumns(String tableName) throws SQLException
    {
        return this.connection.getMetaData().getColumns(null, null, tableName, null);
    }
}
