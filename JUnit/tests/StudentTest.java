import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void return5() {
        assertEquals(student.return5(), 5);
    }

    private static Student student = null;

    @BeforeAll
    static void setUpStudent() {
        student = new Student();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void return10() {
        assertEquals(student.return10(), 10);
    }

}