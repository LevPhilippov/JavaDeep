package lev.filippov.JUnot;

import javafx.collections.transformation.SortedList;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestingClass {

    public static void main(String[] args) {
        start(ClassForTesting.class);
    }

    private static void start(Class<?> clazz) {

        List<Method> methodsList = Arrays.asList(clazz.getDeclaredMethods());

        List<Method> filteredMethods = methodsList.stream().filter(m-> {
            Annotation[] annos = m.getAnnotations();
            for (Annotation anno : annos) {
                return anno instanceof BeforeAll || anno instanceof AfterAll || anno instanceof MyTest;
            }
            return false;
        }).collect(Collectors.toList());

        Long befors = methodsList.stream().filter(m -> {
            Annotation[] annos = m.getDeclaredAnnotations();
            for (Annotation anno : annos) {
                return anno instanceof BeforeAll;
            }
            return false;
        }).count();

        if (befors!=1)
            throw new RuntimeException("Что-то не так с аннотацией BeforeAll");


        Long afters = methodsList.stream().filter(m -> {
            Annotation[] annos = m.getDeclaredAnnotations();
            for (Annotation anno : annos) {
                return anno instanceof AfterAll;
            }
            return false;
        }).count();

        if (afters!=1)
            throw new RuntimeException("Что-то не так с аннотацией AfterAll");


        filteredMethods.sort((o1, o2) -> {
            //в компаратор д. попасть только методы имеющие интерфейс Mytest, BeforeAll и AfterAll
            Annotation annotation1 = o1.getAnnotation(BeforeAll.class);
            if (annotation1 != null ) return 1;

            annotation1 = o1.getAnnotation(AfterAll.class);
            if (annotation1 != null ) return -1;

            annotation1 = o2.getAnnotation(BeforeAll.class);
            if (annotation1 != null ) return -1;

            annotation1 = o2.getAnnotation(AfterAll.class);
            if (annotation1 != null ) return 1;

            MyTest myTest1 = o1.getAnnotation(MyTest.class);
            MyTest myTest2 = o2.getAnnotation(MyTest.class);

            if(myTest1.value()>myTest2.value()) return 1;
            else if (myTest1.value()== myTest2.value()) return 0;
            else return -1;
        });


        try {
            Object obj = clazz.getConstructor().newInstance();

            for (int w=filteredMethods.size()-1; w>=0;w--) {
                filteredMethods.get(w).invoke(obj);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
