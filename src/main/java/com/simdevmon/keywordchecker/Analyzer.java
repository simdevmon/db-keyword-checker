package com.simdevmon.keywordchecker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

/**
 * Compares the database meta data with the keywords and prints out violations.
 *
 * @author Simon Narkprasert (simdevmon@gmail.com)
 */
public class Analyzer
{

    private final static String TABLE = "TABLE_NAME";
    private final static String COLUMN = "COLUMN_NAME";

    private final Connector connector;

    private final Set<String> keywords;

    public Analyzer(Connector connector, Set<String> reservedKeywords)
    {
        this.connector = connector;
        this.keywords = reservedKeywords;
    }

    public void analyze() throws SQLException
    {
        analyze(this.connector.getTables(), TABLE, null);
    }

    private void analyze(ResultSet rs, String itemType, String tableName) throws SQLException
    {
        while (rs.next())
        {
            String itemName = rs.getString(itemType);
            if (this.keywords.contains(itemName.toLowerCase().trim()))
            {
                System.out.println(
                    String.format("Found keyword for %s %s [%s]",
                        itemType.toLowerCase(),
                        Optional.ofNullable(tableName).orElse(""),
                        itemName
                    )
                );
            }
            if (itemType.equalsIgnoreCase(TABLE))
            {
                analyze(this.connector.getColumns(itemName), COLUMN, itemName);
            }
        }
    }
}
