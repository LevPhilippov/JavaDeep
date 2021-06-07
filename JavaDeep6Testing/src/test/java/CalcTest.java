import lev.philippov.MainApp;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


public class CalcTest {
    MainApp.Calculator calculator;




    @BeforeEach
    public void init() {
        calculator = new MainApp.Calculator();
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(5, calculator.add(7,-2));
    }

    @Test()
    public void excTest(){
        Assertions.assertThrows(ArithmeticException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculator.div(5,0);
            }
        });
    }
}
