import lev.philippov.MainApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MassTest {
    MainApp.Calculator calculator;

    @BeforeEach
    public void init() {
        calculator = new MainApp.Calculator();
    }

    @CsvSource({
            "1, 1, 2",
            "2, 3, 5",
            "7, 5, 12",
            "12, 12, 24"
    })
    @ParameterizedTest
    public void massAddTest(int a, int b, int r) {
        Assertions.assertEquals(r,calculator.add(a,b));
    }

}
