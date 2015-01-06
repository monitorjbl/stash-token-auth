package ut.com.thundermoose;

import com.thundermoose.plugins.tokenauth.TokenAuthenticationHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        TokenAuthenticationHandler component = new TokenAuthenticationHandler(null,null);
        assertEquals("names do not match!", "myComponent","myComponent");
    }
}