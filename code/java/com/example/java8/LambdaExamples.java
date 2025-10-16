package com.example.java8;

import java.util.*;
import java.util.function.*;

/**
 * Java 8 Lambda 表达式示例类
 * 展示各种 Lambda 表达式的使用场景和语法
 */
public class LambdaExamples {
    
    /**
     * 1. 基本 Lambda 表达式语法示例
     */
    public static void basicLambdaSyntax() {
        System.out.println("=== 基本 Lambda 表达式语法 ===");
        
        // 传统匿名内部类方式
        Runnable oldWay = new Runnable() {
            @Override
            public void run() {
                System.out.println("传统方式：Hello World!");
            }
        };
        
        // Lambda 表达式方式
        Runnable newWay = () -> System.out.println("Lambda方式：Hello World!");
        
        // 执行
        oldWay.run();
        newWay.run();
    }
    
    /**
     * 2. 函数式接口示例
     */
    public static void functionalInterfaceExamples() {
        System.out.println("\n=== 函数式接口示例 ===");
        
        // Predicate<T> - 判断条件
        Predicate<String> isEmpty = s -> s.isEmpty();
        Predicate<String> isLong = s -> s.length() > 5;
        
        System.out.println("isEmpty.test(\"\"): " + isEmpty.test(""));
        System.out.println("isLong.test(\"Hello\"): " + isLong.test("Hello"));
        
        // Function<T, R> - 转换函数
        Function<String, Integer> stringLength = s -> s.length();
        Function<String, String> toUpperCase = s -> s.toUpperCase();
        
        System.out.println("stringLength.apply(\"Java\"): " + stringLength.apply("Java"));
        System.out.println("toUpperCase.apply(\"lambda\"): " + toUpperCase.apply("lambda"));
        
        // Consumer<T> - 消费数据
        Consumer<String> printer = s -> System.out.println("打印: " + s);
        printer.accept("Hello Lambda!");
        
        // Supplier<T> - 提供数据
        Supplier<String> stringSupplier = () -> "Hello from Supplier!";
        System.out.println("Supplier: " + stringSupplier.get());
    }
    
    /**
     * 3. 集合操作中的 Lambda 表达式
     */
    public static void collectionLambdaExamples() {
        System.out.println("\n=== 集合操作中的 Lambda 表达式 ===");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // 使用 forEach
        System.out.println("使用 forEach 遍历:");
        names.forEach(name -> System.out.println("  " + name));
        
        // 使用 removeIf
        List<String> mutableNames = new ArrayList<>(names);
        mutableNames.removeIf(name -> name.length() > 4);
        System.out.println("移除长度>4的名字后: " + mutableNames);
        
        // 使用 replaceAll
        List<String> upperNames = new ArrayList<>(names);
        upperNames.replaceAll(String::toUpperCase);
        System.out.println("转换为大写: " + upperNames);
    }
    
    /**
     * 4. 方法引用示例
     */
    public static void methodReferenceExamples() {
        System.out.println("\n=== 方法引用示例 ===");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // 静态方法引用
        names.forEach(System.out::println);
        
        // 实例方法引用
        names.forEach(String::toUpperCase);
        
        // 构造器引用
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> newList = listSupplier.get();
        System.out.println("创建新列表: " + newList);
    }
    
    /**
     * 5. 比较器中的 Lambda 表达式
     */
    public static void comparatorLambdaExamples() {
        System.out.println("\n=== 比较器中的 Lambda 表达式 ===");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 20)
        );
        
        // 按年龄排序
        people.sort((p1, p2) -> p1.getAge() - p2.getAge());
        System.out.println("按年龄排序: " + people);
        
        // 按姓名排序
        people.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        System.out.println("按姓名排序: " + people);
        
        // 使用方法引用
        people.sort(Comparator.comparing(Person::getAge));
        System.out.println("使用方法引用按年龄排序: " + people);
    }
    
    /**
     * 6. 线程中的 Lambda 表达式
     */
    public static void threadLambdaExamples() {
        System.out.println("\n=== 线程中的 Lambda 表达式 ===");
        
        // 创建线程
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("线程1: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("线程2: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        // 启动线程
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 7. 自定义函数式接口
     */
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }
    
    public static void customFunctionalInterfaceExamples() {
        System.out.println("\n=== 自定义函数式接口 ===");
        
        // 加法
        Calculator add = (a, b) -> a + b;
        System.out.println("5 + 3 = " + add.calculate(5, 3));
        
        // 乘法
        Calculator multiply = (a, b) -> a * b;
        System.out.println("5 * 3 = " + multiply.calculate(5, 3));
        
        // 复杂计算
        Calculator complex = (a, b) -> (a + b) * (a - b);
        System.out.println("(5+3)*(5-3) = " + complex.calculate(5, 3));
    }
    
    /**
     * 8. Lambda 表达式与异常处理
     */
    public static void lambdaExceptionHandling() {
        System.out.println("\n=== Lambda 表达式与异常处理 ===");
        
        List<String> numbers = Arrays.asList("1", "2", "abc", "4", "5");
        
        // 处理可能抛出异常的情况
        numbers.forEach(s -> {
            try {
                int num = Integer.parseInt(s);
                System.out.println("数字: " + num);
            } catch (NumberFormatException e) {
                System.out.println("无效数字: " + s);
            }
        });
    }
    
    /**
     * 主方法，运行所有示例
     */
    public static void main(String[] args) {
        basicLambdaSyntax();
        functionalInterfaceExamples();
        collectionLambdaExamples();
        methodReferenceExamples();
        comparatorLambdaExamples();
        threadLambdaExamples();
        customFunctionalInterfaceExamples();
        lambdaExceptionHandling();
    }
    
    /**
     * 内部类：用于演示比较器
     */
    static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
}
