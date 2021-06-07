package lev.philippov;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainApp {

    private static final Logger logger = LogManager.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        logger.error("ErrorLog");
        logger.trace("Tracelog");
        Calculator calculator = new Calculator();
    }

    public static class Calculator {

        public int add(int a, int b) {
            return a + b;
        }

        public int sub(int a, int b) {
            return a - b;
        }

        public int mul(int a, int b) {
            return a * b;
        }

        public int div(int a, int b) {
            return a / b;
        }
    }
}
