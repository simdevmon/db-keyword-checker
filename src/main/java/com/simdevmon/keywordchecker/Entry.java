package com.simdevmon.keywordchecker;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Entry point of the program.
 *
 * @author Simon Narkprasert (simdevmon@gmail.com)
 */
public final class Entry
{

    private Entry()
    {
    }

    public static void main(String... args) throws SQLException, IOException
    {
        new Analyzer(
            new Connector(),
            new Keywords()
        ).analyze();
    }
}
