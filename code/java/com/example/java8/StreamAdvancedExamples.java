package com.example.java8;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.concurrent.ForkJoinPool;

/**
 * Java 8 Stream 高阶用法示例类
 * 展示 Stream 中函数式接口的高级应用和复杂场景
 */
public class StreamAdvancedExamples {
    
    /**
     * 1. 自定义函数式接口与 Stream 结合使用
     */
    public static void customFunctionalInterfacesWithStream() {
        System.out.println("=== 自定义函数式接口与 Stream 结合使用 ===");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineer", 75000),
            new Person("Bob", 30, "Manager", 90000),
            new Person("Charlie", 35, "Engineer", 80000),
            new Person("David", 28, "Designer", 65000),
            new Person("Eve", 32, "Manager", 95000)
        );
        
        // 使用预定义的条件处理器
        BiFunction<Person, Predicate<Person>, Boolean> conditionProcessor = (person, condition) -> {
            if (condition.test(person)) {
                System.out.println("处理: " + person.getName() + " (年龄: " + person.getAge() + ")");
                return true;
            }
            return false;
        };
        
        // 使用预定义的转换器链
        BiFunction<Person, Function<Person, String>[], String> transformChain = (person, functions) -> {
            String result = person.getName();
            for (Function<Person, String> func : functions) {
                result = func.apply(person);
            }
            return result;
        };
        
        // 使用条件处理器
        System.out.println("年龄大于30的人员:");
        people.stream()
            .filter(person -> conditionProcessor.apply(person, p -> p.getAge() > 30))
            .forEach(System.out::println);
        
        // 使用转换器链
        System.out.println("\n转换链结果:");
        people.stream()
            .map(person -> {
                String result = person.getName();
                result = result.toUpperCase();
                result = "[" + result + "]";
                result = result + " - " + person.getJobTitle();
                return result;
            })
            .forEach(System.out::println);
    }
    
    /**
     * 2. 复杂的分组和聚合操作
     */
    public static void complexGroupingAndAggregation() {
        System.out.println("\n=== 复杂的分组和聚合操作 ===");
        
        List<Order> orders = Arrays.asList(
            new Order("A001", "Laptop", 1200.0, "Electronics", "2024-01-15"),
            new Order("A002", "Mouse", 25.0, "Electronics", "2024-01-16"),
            new Order("A003", "Desk", 300.0, "Furniture", "2024-01-17"),
            new Order("A004", "Keyboard", 80.0, "Electronics", "2024-01-18"),
            new Order("A005", "Chair", 150.0, "Furniture", "2024-01-19"),
            new Order("A006", "Monitor", 400.0, "Electronics", "2024-01-20")
        );
        
        // 多级分组：按类别分组，然后按价格范围分组
        Map<String, Map<String, List<Order>>> multiLevelGrouping = orders.stream()
            .collect(Collectors.groupingBy(
                Order::getCategory,
                Collectors.groupingBy(order -> {
                    if (order.getPrice() < 100) return "低价";
                    else if (order.getPrice() < 500) return "中价";
                    else return "高价";
                })
            ));
        
        System.out.println("多级分组结果:");
        multiLevelGrouping.forEach((category, priceGroups) -> {
            System.out.println("类别: " + category);
            priceGroups.forEach((priceRange, orderList) -> {
                System.out.println("  价格范围: " + priceRange + " - 订单数: " + orderList.size());
            });
        });
        
        // 复杂聚合：计算每个类别的统计信息
        Map<String, OrderStats> categoryStats = orders.stream()
            .collect(Collectors.groupingBy(
                Order::getCategory,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    orderList -> {
                        double totalValue = orderList.stream().mapToDouble(Order::getPrice).sum();
                        double avgPrice = orderList.stream().mapToDouble(Order::getPrice).average().orElse(0);
                        double maxPrice = orderList.stream().mapToDouble(Order::getPrice).max().orElse(0);
                        double minPrice = orderList.stream().mapToDouble(Order::getPrice).min().orElse(0);
                        return new OrderStats(totalValue, avgPrice, maxPrice, minPrice, orderList.size());
                    }
                )
            ));
        
        System.out.println("\n类别统计信息:");
        categoryStats.forEach((category, stats) -> {
            System.out.printf("类别: %s - 总价值: %.2f, 平均价格: %.2f, 最高价: %.2f, 最低价: %.2f, 订单数: %d%n",
                category, stats.getTotalValue(), stats.getAvgPrice(), 
                stats.getMaxPrice(), stats.getMinPrice(), stats.getCount());
        });
    }
    
    /**
     * 3. 高级收集器操作
     */
    public static void advancedCollectorOperations() {
        System.out.println("\n=== 高级收集器操作 ===");
        
        List<Student> students = Arrays.asList(
            new Student("Alice", Arrays.asList(85, 90, 78, 92)),
            new Student("Bob", Arrays.asList(76, 88, 95, 82)),
            new Student("Charlie", Arrays.asList(91, 87, 89, 94)),
            new Student("David", Arrays.asList(68, 75, 80, 77))
        );
        
        // 自定义收集器：计算学生的综合评分
        Collector<Student, StudentScoreAccumulator, List<StudentScore>> studentScoreCollector = 
            Collector.of(
                StudentScoreAccumulator::new,
                StudentScoreAccumulator::accumulate,
                StudentScoreAccumulator::combine,
                StudentScoreAccumulator::finish
            );
        
        List<StudentScore> studentScores = students.stream()
            .collect(Collectors.mapping(
                student -> new StudentScore(
                    student.getName(),
                    student.getScores().stream().mapToInt(Integer::intValue).average().orElse(0),
                    student.getScores().stream().mapToInt(Integer::intValue).sum(),
                    student.getScores().size()
                ),
                Collectors.toList()
            ));
        
        System.out.println("学生评分:");
        studentScores.forEach(score -> 
            System.out.printf("学生: %s - 平均分: %.2f, 总分: %d, 科目数: %d%n",
                score.getName(), score.getAverage(), score.getTotal(), score.getSubjectCount())
        );
        
        // 分区收集器：按成绩等级分区
        Map<Boolean, List<StudentScore>> partitionedByGrade = studentScores.stream()
            .collect(Collectors.partitioningBy(score -> score.getAverage() >= 85));
        
        System.out.println("\n按成绩等级分区:");
        System.out.println("优秀学生 (>=85分): " + 
            partitionedByGrade.get(true).stream().map(StudentScore::getName).collect(Collectors.toList()));
        System.out.println("其他学生 (<85分): " + 
            partitionedByGrade.get(false).stream().map(StudentScore::getName).collect(Collectors.toList()));
    }
    
    /**
     * 4. 流式处理管道与错误处理
     */
    public static void streamPipelineWithErrorHandling() {
        System.out.println("\n=== 流式处理管道与错误处理 ===");
        
        List<String> data = Arrays.asList("123", "456", "abc", "789", "def", "101112");
        
        // 使用 Optional 处理可能的异常
        List<Integer> validNumbers = data.stream()
            .map(StreamAdvancedExamples::safeParseInt)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        System.out.println("有效数字: " + validNumbers);
        
        // 使用 Try-Catch 模式处理异常
        List<String> processedData = data.stream()
            .map(item -> {
                try {
                    int num = Integer.parseInt(item);
                    return "数字: " + num;
                } catch (NumberFormatException e) {
                    return "非数字: " + item;
                }
            })
            .collect(Collectors.toList());
        
        System.out.println("处理结果: " + processedData);
        
        // 使用自定义异常处理函数
        Function<String, Optional<String>> safeProcessor = item -> {
            try {
                int num = Integer.parseInt(item);
                return Optional.of("处理成功: " + (num * 2));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        };
        
        List<String> processedResults = data.stream()
            .map(safeProcessor)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        System.out.println("安全处理结果: " + processedResults);
    }
    
    /**
     * 5. 动态流构建和条件处理
     */
    public static void dynamicStreamBuilding() {
        System.out.println("\n=== 动态流构建和条件处理 ===");
        
        List<Product> products = Arrays.asList(
            new Product("Laptop", 1200.0, "Electronics", true),
            new Product("Mouse", 25.0, "Electronics", false),
            new Product("Desk", 300.0, "Furniture", true),
            new Product("Keyboard", 80.0, "Electronics", true),
            new Product("Chair", 150.0, "Furniture", false)
        );
        
        // 动态构建过滤条件
        List<Predicate<Product>> filters = Arrays.asList(
            p -> p.getPrice() > 100,
            p -> p.isInStock(),
            p -> p.getCategory().equals("Electronics")
        );
        
        // 应用所有过滤条件
        List<Product> filteredProducts = products.stream()
            .filter(filters.stream().reduce(Predicate::and).orElse(x -> true))
            .collect(Collectors.toList());
        
        System.out.println("应用所有过滤条件后的产品: " + filteredProducts);
        
        // 条件性应用操作
        boolean applyPriceFilter = true;
        boolean applyStockFilter = false;
        
        Stream<Product> stream = products.stream();
        
        if (applyPriceFilter) {
            stream = stream.filter(p -> p.getPrice() > 50);
        }
        
        if (applyStockFilter) {
            stream = stream.filter(Product::isInStock);
        }
        
        List<Product> conditionallyFiltered = stream.collect(Collectors.toList());
        System.out.println("条件性过滤结果: " + conditionallyFiltered);
        
        // 动态转换链
        List<Function<String, String>> transformers = Arrays.asList(
            name -> "产品: " + name,
            name -> name.toUpperCase()
        );
        
        List<String> transformedNames = products.stream()
            .map(product -> {
                String result = product.getName();
                for (Function<String, String> transformer : transformers) {
                    result = transformer.apply(result);
                }
                return result;
            })
            .collect(Collectors.toList());
        
        System.out.println("动态转换结果: " + transformedNames);
    }
    
    /**
     * 6. 并行流的高级用法
     */
    public static void advancedParallelStreamUsage() {
        System.out.println("\n=== 并行流的高级用法 ===");
        
        List<Integer> numbers = IntStream.rangeClosed(1, 1000000)
            .boxed()
            .collect(Collectors.toList());
        
        // 并行流性能测试
        long startTime = System.currentTimeMillis();
        long sequentialSum = numbers.stream()
            .mapToLong(Integer::longValue)
            .sum();
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        long parallelSum = numbers.parallelStream()
            .mapToLong(Integer::longValue)
            .sum();
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("顺序处理结果: " + sequentialSum + ", 耗时: " + sequentialTime + "ms");
        System.out.println("并行处理结果: " + parallelSum + ", 耗时: " + parallelTime + "ms");
        
        // 并行流中的线程安全操作
        List<String> words = Arrays.asList("Hello", "World", "Java", "Stream", "Parallel", "Processing");
        
        // 使用线程安全的收集器
        Map<String, Integer> wordLengthMap = words.parallelStream()
            .collect(Collectors.toConcurrentMap(
                word -> word,
                String::length,
                (existing, replacement) -> existing
            ));
        
        System.out.println("单词长度映射: " + wordLengthMap);
        
        // 并行流中的自定义线程池（注意：这里只是演示概念）
        System.out.println("并行流线程数: " + ForkJoinPool.commonPool().getParallelism());
    }
    
    /**
     * 7. 无限流和生成器模式
     */
    public static void infiniteStreamAndGeneratorPatterns() {
        System.out.println("\n=== 无限流和生成器模式 ===");
        
        // 斐波那契数列生成器
        Supplier<Long> fibonacciGenerator = new Supplier<Long>() {
            private long prev = 0;
            private long curr = 1;
            
            @Override
            public Long get() {
                long next = prev + curr;
                prev = curr;
                curr = next;
                return prev;
            }
        };
        
        List<Long> fibonacci = Stream.generate(fibonacciGenerator)
            .limit(10)
            .collect(Collectors.toList());
        
        System.out.println("斐波那契数列前10项: " + fibonacci);
        
        // 随机数生成器
        Random random = new Random();
        List<Double> randomNumbers = Stream.generate(() -> random.nextGaussian())
            .limit(5)
            .collect(Collectors.toList());
        
        System.out.println("随机正态分布数: " + randomNumbers);
        
        // 自定义生成器：质数生成器
        Supplier<Integer> primeGenerator = new Supplier<Integer>() {
            private int current = 2;
            
            @Override
            public Integer get() {
                while (!isPrime(current)) {
                    current++;
                }
                return current++;
            }
            
            private boolean isPrime(int n) {
                if (n < 2) return false;
                for (int i = 2; i * i <= n; i++) {
                    if (n % i == 0) return false;
                }
                return true;
            }
        };
        
        List<Integer> primes = Stream.generate(primeGenerator)
            .limit(10)
            .collect(Collectors.toList());
        
        System.out.println("前10个质数: " + primes);
    }
    
    /**
     * 8. 流式API的组合和链式操作
     */
    public static void streamApiCompositionAndChaining() {
        System.out.println("\n=== 流式API的组合和链式操作 ===");
        
        List<Transaction> transactions = Arrays.asList(
            new Transaction("T001", 100.0, "USD", "2024-01-01"),
            new Transaction("T002", 200.0, "EUR", "2024-01-02"),
            new Transaction("T003", 150.0, "USD", "2024-01-03"),
            new Transaction("T004", 300.0, "GBP", "2024-01-04"),
            new Transaction("T005", 250.0, "USD", "2024-01-05")
        );
        
        // 复杂的流式处理管道
        Map<String, TransactionSummary> currencySummary = transactions.stream()
            .filter(t -> t.getAmount() > 100) // 过滤金额大于100的交易
            .collect(Collectors.groupingBy(
                Transaction::getCurrency,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        double total = list.stream().mapToDouble(Transaction::getAmount).sum();
                        double average = list.stream().mapToDouble(Transaction::getAmount).average().orElse(0);
                        int count = list.size();
                        return new TransactionSummary(total, average, count);
                    }
                )
            ));
        
        System.out.println("货币汇总:");
        currencySummary.forEach((currency, summary) -> 
            System.out.printf("货币: %s - 总金额: %.2f, 平均金额: %.2f, 交易数: %d%n",
                currency, summary.getTotal(), summary.getAverage(), summary.getCount())
        );
        
        // 流式API的组合：多个操作的组合
        Function<Stream<Transaction>, Stream<Transaction>> highValueFilter = 
            stream -> stream.filter(t -> t.getAmount() > 150);
        
        Function<Stream<Transaction>, Stream<String>> currencyExtractor = 
            stream -> stream.map(Transaction::getCurrency);
        
        Function<Stream<Transaction>, Stream<Transaction>> usdOnlyFilter = 
            stream -> stream.filter(t -> "USD".equals(t.getCurrency()));
        
        // 组合多个操作
        List<String> result = transactions.stream()
            .filter(t -> t.getAmount() > 150)
            .filter(t -> "USD".equals(t.getCurrency()))
            .map(Transaction::getCurrency)
            .distinct()
            .collect(Collectors.toList());
        
        System.out.println("高价值USD交易货币: " + result);
    }
    
    // 辅助方法
    private static Optional<Integer> safeParseInt(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    /**
     * 主方法，运行所有示例
     */
    public static void main(String[] args) {
        customFunctionalInterfacesWithStream();
        complexGroupingAndAggregation();
        advancedCollectorOperations();
        streamPipelineWithErrorHandling();
        dynamicStreamBuilding();
        advancedParallelStreamUsage();
        infiniteStreamAndGeneratorPatterns();
        streamApiCompositionAndChaining();
    }
    
    // 内部类定义
    static class Person {
        private String name;
        private int age;
        private String jobTitle;
        private double salary;
        
        public Person(String name, int age, String jobTitle, double salary) {
            this.name = name;
            this.age = age;
            this.jobTitle = jobTitle;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getJobTitle() { return jobTitle; }
        public double getSalary() { return salary; }
        
        @Override
        public String toString() {
            return name + "(" + age + ", " + jobTitle + ", $" + salary + ")";
        }
    }
    
    static class Order {
        private String id;
        private String product;
        private double price;
        private String category;
        private String date;
        
        public Order(String id, String product, double price, String category, String date) {
            this.id = id;
            this.product = product;
            this.price = price;
            this.category = category;
            this.date = date;
        }
        
        public String getId() { return id; }
        public String getProduct() { return product; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public String getDate() { return date; }
        
        @Override
        public String toString() {
            return id + " - " + product + " ($" + price + ")";
        }
    }
    
    static class OrderStats {
        private double totalValue;
        private double avgPrice;
        private double maxPrice;
        private double minPrice;
        private int count;
        
        public OrderStats(double totalValue, double avgPrice, double maxPrice, double minPrice, int count) {
            this.totalValue = totalValue;
            this.avgPrice = avgPrice;
            this.maxPrice = maxPrice;
            this.minPrice = minPrice;
            this.count = count;
        }
        
        public double getTotalValue() { return totalValue; }
        public double getAvgPrice() { return avgPrice; }
        public double getMaxPrice() { return maxPrice; }
        public double getMinPrice() { return minPrice; }
        public int getCount() { return count; }
    }
    
    static class Student {
        private String name;
        private List<Integer> scores;
        
        public Student(String name, List<Integer> scores) {
            this.name = name;
            this.scores = scores;
        }
        
        public String getName() { return name; }
        public List<Integer> getScores() { return scores; }
    }
    
    static class StudentScore {
        private String name;
        private double average;
        private int total;
        private int subjectCount;
        
        public StudentScore(String name, double average, int total, int subjectCount) {
            this.name = name;
            this.average = average;
            this.total = total;
            this.subjectCount = subjectCount;
        }
        
        public String getName() { return name; }
        public double getAverage() { return average; }
        public int getTotal() { return total; }
        public int getSubjectCount() { return subjectCount; }
    }
    
    static class StudentScoreAccumulator {
        private List<StudentScore> scores = new ArrayList<>();
        
        public void accumulate(Student student) {
            double average = student.getScores().stream().mapToInt(Integer::intValue).average().orElse(0);
            int total = student.getScores().stream().mapToInt(Integer::intValue).sum();
            scores.add(new StudentScore(student.getName(), average, total, student.getScores().size()));
        }
        
        public StudentScoreAccumulator combine(StudentScoreAccumulator other) {
            scores.addAll(other.scores);
            return this;
        }
        
        public List<StudentScore> finish() {
            return scores;
        }
    }
    
    static class Product {
        private String name;
        private double price;
        private String category;
        private boolean inStock;
        
        public Product(String name, double price, String category, boolean inStock) {
            this.name = name;
            this.price = price;
            this.category = category;
            this.inStock = inStock;
        }
        
        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public boolean isInStock() { return inStock; }
        
        @Override
        public String toString() {
            return name + " ($" + price + ", " + category + ", " + (inStock ? "有库存" : "无库存") + ")";
        }
    }
    
    static class Transaction {
        private String id;
        private double amount;
        private String currency;
        private String date;
        
        public Transaction(String id, double amount, String currency, String date) {
            this.id = id;
            this.amount = amount;
            this.currency = currency;
            this.date = date;
        }
        
        public String getId() { return id; }
        public double getAmount() { return amount; }
        public String getCurrency() { return currency; }
        public String getDate() { return date; }
        
        @Override
        public String toString() {
            return id + " - " + amount + " " + currency;
        }
    }
    
    static class TransactionSummary {
        private double total;
        private double average;
        private int count;
        
        public TransactionSummary(double total, double average, int count) {
            this.total = total;
            this.average = average;
            this.count = count;
        }
        
        public double getTotal() { return total; }
        public double getAverage() { return average; }
        public int getCount() { return count; }
    }
}

