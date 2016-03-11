package com.simdevmon.keywordchecker;

import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Test loading of keywords.
 *
 * @author Simon Narkprasert (simdevmon@gmail.com)
 */
public class KeywordsTest
{

    private Keywords cut;

    @Before
    public void init() throws IOException
    {
        this.cut = new Keywords();
    }

    @Test
    public void test()
    {
        assertTrue(
            cut.containsAll(
                Arrays.asList("select", "update", "insert", "delete", "create", "alter", "drop")
            )
        );
    }
}
