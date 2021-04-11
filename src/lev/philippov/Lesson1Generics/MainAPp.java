package lev.philippov.Lesson1Generics;

/*Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
Написать метод, который преобразует массив в ArrayList;
Задача:
Даны классы Fruit, Apple extends Fruit, Orange extends Fruit;
Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
Для хранения фруктов внутри коробки можно использовать ArrayList;
Сделать метод getWeight(), который высчитывает вес коробки, зная вес одного фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f (единицы измерения не важны);
Внутри класса Box сделать метод compare(), который позволяет сравнить текущую коробку с той, которую подадут в compare() в качестве параметра. true – если их массы равны, false в противоположном случае. Можно сравнивать коробки с яблоками и апельсинами;
Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую. Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами. Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в первой;
Не забываем про метод добавления фрукта в коробку.
*/

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class MainAPp {
    public static void main(String[] args) {
        Integer[] intArray = new Integer[5];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i]=i;
        }
        System.out.println(Arrays.asList(intArray));

        arrayChangeElements(intArray, 4,0);

        System.out.println(Arrays.asList(intArray));

        System.out.println(arrayToList(intArray));


        MainAPp.Box<Apple> appleBox = new Box<>();
        MainAPp.Box<Apple> appleBox2 = new Box();

        appleBox.add(new Apple());
        appleBox.add(new Apple());
        appleBox.add(new Apple());
        System.out.println(appleBox.getList().size());

        appleBox2.add(new Apple());
        System.out.println(appleBox2.getList().size());

        MainAPp.Box<Orange> orangeBox = new Box<>();
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());

        System.out.println(appleBox2.compareBoxes(orangeBox));

        System.out.println(appleBox.compareBoxes(orangeBox));

        Box.moveFruitsIntoOneBox(appleBox2,appleBox);

        System.out.println(appleBox2.list.size());

    }

//Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);

    public static <T> void arrayChangeElements(T[] array, int sourceEl, int targetEl) {
        if (sourceEl <= array.length-1 || targetEl <= array.length-1) {
            T t = array[targetEl];
            array[targetEl]= array[sourceEl];
            array[sourceEl]=t;
        }
    }

//Написать метод, который преобразует массив в ArrayList;

    public static <T> List<T> arrayToList (T[] array) {
        List list = new ArrayList();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }


//    Даны классы Fruit, Apple extends Fruit, Orange extends Fruit;
//    Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
//    Для хранения фруктов внутри коробки можно использовать ArrayList;
//    Сделать метод getWeight(), который высчитывает вес коробки, зная вес одного фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f (единицы измерения не важны);
//    Внутри класса Box сделать метод compare(), который позволяет сравнить текущую коробку с той, которую подадут в compare() в качестве параметра. true – если их массы равны, false в противоположном случае. Можно сравнивать коробки с яблоками и апельсинами;
//    Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую. Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами. Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в первой;
//    Не забываем про метод добавления фрукта в коробку.
//
    public static class Box <T extends Fruit>{
        private List<T> list = new ArrayList();

        public List<T> getList(){
            return list;
        }

        public void add(T fruit) {
            list.add(fruit);
        }

        public boolean compareBoxes (@NotNull Box<? extends Fruit> box) {
            double selfWeight = 0.0d;

            selfWeight = list.stream().mapToDouble((ToDoubleFunction<Fruit>) value -> value.getWeight()).sum();
            System.out.println(selfWeight);

            double comparebleWeight = 0.0d;
            comparebleWeight = box.getList().stream().mapToDouble((ToDoubleFunction<Fruit>) value -> value.getWeight()).sum();
            System.out.println(comparebleWeight);

            return Math.abs(selfWeight - comparebleWeight) < 0.0001;

        }

        public static <A extends Fruit> void moveFruitsIntoOneBox(Box <? super A> destBox, Box <? extends A> sourceBox) {
            for (int i = 0; i < sourceBox.getList().size(); i++) {
                destBox.add(sourceBox.getList().get(i));
            }
            sourceBox.getList().clear();

        }



    }


}



