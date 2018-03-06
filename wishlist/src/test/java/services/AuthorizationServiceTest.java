package services;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import services.exceptions.*;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorizationServiceTest {
    private static AuthorizationService testObject;
    private static String l = "Anton";
    private static String p = "123";
    private static String token = null;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            testObject = new AuthorizationService("test.db");
        } catch (DbConnectionException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        try {
            testObject.reset();
        } catch (InternalDbException ignored) {
            fail();
        }
    }

    @Test
    public void authenticate00() throws ServiceException {
        testObject.register(l, p);
    }

    @Test
    public void authenticate01() throws ServiceException {
        token = testObject.authenticate(l, p);
        assertNotNull(token);
    }

    @Test(expected = DuplicatedLoginException.class)
    public void authenticate02() throws ServiceException {
        testObject.register(l, p);
    }

    @Test
    public void authenticate03() throws ServiceException {
        assertEquals(token, testObject.authenticate(l, " " + p + " "));
    }

    @Test(expected = NullFieldException.class)
    public void authenticate04() throws ServiceException {
        testObject.authenticate(null, p);
    }

    @Test(expected = EmptyFieldException.class)
    public void authenticate05() throws ServiceException {
        testObject.authenticate("", p);
    }

    @Test()
    public void authenticate06() throws ServiceException {
        assertNull(testObject.authenticate(l, p + 1));
    }

    @Test(expected = InvalidFieldException.class)
    public void authenticate07() throws ServiceException {
        testObject.register("asdk^4", "asd");
    }

    @Test
    public void authenticate08() throws ServiceException {
        assertEquals(l, testObject.authorize(token));
    }

    @Test
    public void authenticate09() throws ServiceException {
        assertNull(testObject.authorize("OtherToken"));
    }

    @Test
    public void authenticate10() throws ServiceException {
        assertNull(testObject.authorize("OtherToken"));
    }

    @Test
    public void authenticate11() throws ServiceException {
        testObject.logout(token);
        String newToken = testObject.authenticate(l, p);
        assertNotNull(newToken);
        assertNotEquals(token, newToken);
        token = newToken;
    }


}