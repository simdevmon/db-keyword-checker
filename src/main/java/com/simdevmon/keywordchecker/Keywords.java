package com.simdevmon.keywordchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Representation of reserved SQL keywords as a set.
 *
 * @author Simon Narkprasert (simdevmon@gmail.com)
 */
public class Keywords extends HashSet<String>
{

    private static final long serialVersionUID = -7620759659282662797L;

    private static final String RES_KEYWORDS = "/config/keywords.txt";

    public Keywords() throws IOException
    {
        InputStream is = Keywords.class.getResourceAsStream(RES_KEYWORDS);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                super.add(line.toLowerCase().trim());
            }
        }
    }
}
