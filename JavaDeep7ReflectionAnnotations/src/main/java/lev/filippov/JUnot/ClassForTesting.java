package lev.filippov.JUnot;

public class ClassForTesting {

    public ClassForTesting() {

    }

    @MyTest(6)
    public void method1(){
        System.out.println("Метод 1, приритет 6");
    }

    @MyTest(3)
    public void method2(){
        System.out.println("Метод 2, приритет 3");
    }

    @MyTest(8)
    public void method3(){
        System.out.println("Метод 3, приритет 8");
    }

    @MyTest(6)
    public void method4(){
        System.out.println("Метод 4 , приритет 6");
    }

    @BeforeAll
    public void firstMethod(){
        System.out.println("Первый метод");
    }

    @AfterAll
    public void lastMethod(){
        System.out.println("Последний метод");
    }

}
