package com.simdevmon.keywordchecker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.mockito.Mockito.mock;

/**
 * Test analyzer with different inputs.
 *
 * @author Simon Narkprasert (simdevmon@gmail.com)
 */
@RunWith(Parameterized.class)
public class DBAnalyzerTest
{

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final Connector connector;

    private final String expectedResult;

    public DBAnalyzerTest(String tableName, String columnName, String expectedResult) throws SQLException
    {
        this.expectedResult = expectedResult;
        this.connector = mock(Connector.class);

        ResultSet tables = mock(ResultSet.class);
        when(tables.next()).thenReturn(true).thenReturn(false);
        when(tables.getString("TABLE_NAME")).thenReturn(tableName);
        when(connector.getTables()).thenReturn(tables);

        ResultSet columns = mock(ResultSet.class);
        when(columns.next()).thenReturn(true).thenReturn(false);
        when(columns.getString("COLUMN_NAME")).thenReturn(columnName);
        when(connector.getColumns(tableName)).thenReturn(columns);
    }

    @Parameters()
    public static Iterable<Object[]> data()
    {
        return Arrays.asList(
            new Object[][]
            {
                {
                    "GOOD_TABLE",
                    "GOOD_COLUMN",
                    ""
                },
                {
                    "select",
                    "GOOD_COLUMN",
                    "[select]"
                },
                {
                    "GOOD_TABLE",
                    "DROP",
                    "GOOD_TABLE [DROP]"
                },
                {
                    "uPdAtE",
                    "CreATe",
                    "uPdAtE [CreATe]"
                },
                {
                    "UPDATE",
                    "CREATE",
                    "[UPDATE]"
                }
            }
        );
    }

    @Before
    public void init()
    {
        System.setOut(new PrintStream(out));
    }

    @After
    public void cleanUp()
    {
        System.setOut(null);
    }

    @Test
    public void test() throws IOException, SQLException
    {
        new Analyzer(connector, new Keywords()).analyze();
        assertTrue(out.toString().contains(this.expectedResult));
    }
}
