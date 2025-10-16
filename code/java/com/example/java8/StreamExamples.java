package com.example.java8;

import java.util.*;
import java.util.stream.*;

/**
 * Java 8 Stream API 示例类
 * 展示各种 Stream 操作的使用场景和语法
 */
public class StreamExamples {
    
    /**
     * 1. 基本 Stream 创建和操作
     */
    public static void basicStreamOperations() {
        System.out.println("=== 基本 Stream 操作 ===");
        
        // 从集合创建 Stream
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        Stream<String> nameStream = names.stream();
        
        // 从数组创建 Stream
        String[] array = {"Java", "Python", "C++", "JavaScript"};
        Stream<String> arrayStream = Arrays.stream(array);
        
        // 直接创建 Stream
        Stream<String> directStream = Stream.of("Hello", "World", "Stream");
        
        // 使用 Stream.builder()
        Stream<String> builderStream = Stream.<String>builder()
            .add("First")
            .add("Second")
            .add("Third")
            .build();
        
        System.out.println("从集合创建: " + names);
        System.out.println("从数组创建: " + Arrays.toString(array));
    }
    
    /**
     * 2. 中间操作示例
     */
    public static void intermediateOperations() {
        System.out.println("\n=== 中间操作示例 ===");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // filter - 过滤
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("偶数: " + evenNumbers);
        
        // map - 转换
        List<String> numberStrings = numbers.stream()
            .map(n -> "Number: " + n)
            .collect(Collectors.toList());
        System.out.println("转换后: " + numberStrings);
        
        // distinct - 去重
        List<Integer> duplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 5);
        List<Integer> unique = duplicates.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("去重后: " + unique);
        
        // sorted - 排序
        List<String> unsorted = Arrays.asList("Charlie", "Alice", "Bob");
        List<String> sorted = unsorted.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("排序后: " + sorted);
        
        // limit - 限制数量
        List<Integer> limited = numbers.stream()
            .limit(5)
            .collect(Collectors.toList());
        System.out.println("前5个: " + limited);
        
        // skip - 跳过元素
        List<Integer> skipped = numbers.stream()
            .skip(3)
            .collect(Collectors.toList());
        System.out.println("跳过前3个: " + skipped);
    }
    
    /**
     * 3. 终端操作示例
     */
    public static void terminalOperations() {
        System.out.println("\n=== 终端操作示例 ===");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // forEach - 遍历
        System.out.println("遍历所有名字:");
        names.stream().forEach(name -> System.out.println("  " + name));
        
        // collect - 收集
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("大写名字: " + upperNames);
        
        // toArray - 转换为数组
        String[] nameArray = names.stream()
            .filter(name -> name.length() > 4)
            .toArray(String[]::new);
        System.out.println("长度>4的名字数组: " + Arrays.toString(nameArray));
        
        // reduce - 归约
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
            .reduce(0, (a, b) -> a + b);
        System.out.println("数字总和: " + sum);
        
        // count - 计数
        long count = names.stream()
            .filter(name -> name.length() > 4)
            .count();
        System.out.println("长度>4的名字数量: " + count);
        
        // anyMatch - 任意匹配
        boolean hasLongName = names.stream()
            .anyMatch(name -> name.length() > 6);
        System.out.println("是否有长度>6的名字: " + hasLongName);
        
        // allMatch - 全部匹配
        boolean allShortNames = names.stream()
            .allMatch(name -> name.length() < 10);
        System.out.println("所有名字长度都<10: " + allShortNames);
        
        // findFirst - 查找第一个
        Optional<String> firstLongName = names.stream()
            .filter(name -> name.length() > 4)
            .findFirst();
        System.out.println("第一个长度>4的名字: " + firstLongName.orElse("未找到"));
    }
    
    /**
     * 4. 复杂 Stream 操作示例
     */
    public static void complexStreamOperations() {
        System.out.println("\n=== 复杂 Stream 操作示例 ===");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, "IT", 50000),
            new Employee("Bob", 30, "HR", 45000),
            new Employee("Charlie", 35, "IT", 60000),
            new Employee("David", 28, "Finance", 55000),
            new Employee("Eve", 32, "IT", 58000)
        );
        
        // 按部门分组
        Map<String, List<Employee>> byDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println("按部门分组: " + byDepartment);
        
        // 计算每个部门的平均工资
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        System.out.println("各部门平均工资: " + avgSalaryByDept);
        
        // 找出工资最高的员工
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        System.out.println("工资最高的员工: " + highestPaid.orElse(null));
        
        // 按工资排序，取前3名
        List<Employee> top3 = employees.stream()
            .sorted(Comparator.comparing(Employee::getSalary).reversed())
            .limit(3)
            .collect(Collectors.toList());
        System.out.println("工资前3名: " + top3);
        
        // 统计信息
        IntSummaryStatistics stats = employees.stream()
            .mapToInt(Employee::getAge)
            .summaryStatistics();
        System.out.println("年龄统计: " + stats);
    }
    
    /**
     * 5. 并行 Stream 示例
     */
    public static void parallelStreamExamples() {
        System.out.println("\n=== 并行 Stream 示例 ===");
        
        List<Integer> numbers = IntStream.rangeClosed(1, 1000000)
            .boxed()
            .collect(Collectors.toList());
        
        // 顺序处理
        long startTime = System.currentTimeMillis();
        long sequentialSum = numbers.stream()
            .mapToLong(Integer::longValue)
            .sum();
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        // 并行处理
        startTime = System.currentTimeMillis();
        long parallelSum = numbers.parallelStream()
            .mapToLong(Integer::longValue)
            .sum();
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("顺序处理结果: " + sequentialSum + ", 耗时: " + sequentialTime + "ms");
        System.out.println("并行处理结果: " + parallelSum + ", 耗时: " + parallelTime + "ms");
    }
    
    /**
     * 6. 自定义收集器示例
     */
    public static void customCollectorExamples() {
        System.out.println("\n=== 自定义收集器示例 ===");
        
        List<String> words = Arrays.asList("Hello", "World", "Java", "Stream", "API");
        
        // 自定义收集器：连接字符串
        String joined = words.stream()
            .collect(Collector.of(
                StringBuilder::new,
                (sb, s) -> sb.append(s).append(" "),
                (sb1, sb2) -> sb1.append(sb2),
                StringBuilder::toString
            ));
        System.out.println("连接结果: " + joined);
        
        // 自定义收集器：统计字符数
        int totalChars = words.stream()
            .collect(Collector.of(
                () -> new int[1],
                (count, word) -> count[0] += word.length(),
                (count1, count2) -> {
                    count1[0] += count2[0];
                    return count1;
                },
                count -> count[0]
            ));
        System.out.println("总字符数: " + totalChars);
    }
    
    /**
     * 7. 无限 Stream 示例
     */
    public static void infiniteStreamExamples() {
        System.out.println("\n=== 无限 Stream 示例 ===");
        
        // 生成随机数
        Random random = new Random();
        List<Integer> randomNumbers = random.ints()
            .limit(5)
            .boxed()
            .collect(Collectors.toList());
        System.out.println("5个随机数: " + randomNumbers);
        
        // 生成斐波那契数列
        List<Long> fibonacci = Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
            .map(f -> f[0])
            .limit(10)
            .collect(Collectors.toList());
        System.out.println("斐波那契数列前10项: " + fibonacci);
        
        // 生成等差数列
        List<Integer> arithmetic = Stream.iterate(0, n -> n + 2)
            .limit(10)
            .collect(Collectors.toList());
        System.out.println("等差数列(0,2,4...): " + arithmetic);
    }
    
    /**
     * 8. Stream 与 Optional 结合使用
     */
    public static void streamWithOptionalExamples() {
        System.out.println("\n=== Stream 与 Optional 结合使用 ===");
        
        List<String> names = Arrays.asList("Alice", "Bob", null, "Charlie", "", "David");
        
        // 过滤空值并处理
        List<String> validNames = names.stream()
            .filter(Objects::nonNull)
            .filter(name -> !name.isEmpty())
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("有效名字: " + validNames);
        
        // 使用 Optional 处理可能为空的值
        Optional<String> firstValidName = names.stream()
            .filter(Objects::nonNull)
            .filter(name -> !name.isEmpty())
            .findFirst();
        
        firstValidName.ifPresent(name -> 
            System.out.println("第一个有效名字: " + name)
        );
        
        // 使用 orElse 提供默认值
        String result = names.stream()
            .filter(Objects::nonNull)
            .filter(name -> name.length() > 5)
            .findFirst()
            .orElse("未找到长度>5的名字");
        System.out.println("结果: " + result);
    }
    
    /**
     * 主方法，运行所有示例
     */
    public static void main(String[] args) {
        basicStreamOperations();
        intermediateOperations();
        terminalOperations();
        complexStreamOperations();
        parallelStreamExamples();
        customCollectorExamples();
        infiniteStreamExamples();
        streamWithOptionalExamples();
    }
    
    /**
     * 内部类：员工信息
     */
    static class Employee {
        private String name;
        private int age;
        private String department;
        private double salary;
        
        public Employee(String name, int age, String department, double salary) {
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        
        @Override
        public String toString() {
            return name + "(" + age + ", " + department + ", $" + salary + ")";
        }
    }
}

