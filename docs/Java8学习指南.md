# Java 8 学习指南：Lambda、Stream 和 Optional

## 📚 目录

- [概述](#概述)
- [Lambda 表达式](#lambda-表达式)
- [Stream API](#stream-api)
- [Optional](#optional)
- [综合应用示例](#综合应用示例)
- [最佳实践](#最佳实践)
- [常见问题](#常见问题)

## 概述

Java 8 引入了许多重要的新特性，其中 Lambda 表达式、Stream API 和 Optional 是最核心的三个特性。这些特性使 Java 代码更加简洁、可读性更强，并且支持函数式编程风格。

### 主要特性对比

| 特性 | 传统方式 | Java 8 方式 | 优势 |
|------|----------|-------------|------|
| 匿名内部类 | 冗长的语法 | Lambda 表达式 | 简洁、易读 |
| 集合操作 | 循环遍历 | Stream API | 声明式、链式调用 |
| 空值处理 | null 检查 | Optional | 类型安全、明确意图 |

## Lambda 表达式

### 什么是 Lambda 表达式？

Lambda 表达式是 Java 8 引入的一种简洁的匿名函数语法，它允许你将函数作为方法的参数，或者将代码本身当作数据来处理。

### 基本语法

```java
// 基本语法：(参数) -> { 表达式或语句块 }
(parameter) -> expression
(parameter) -> { statements; }
```

### 典型使用场景

#### 1. 替代匿名内部类

**传统方式：**
```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
};
```

**Lambda 方式：**
```java
Runnable r = () -> System.out.println("Hello World!");
```

#### 2. 集合操作

**传统方式：**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
for (String name : names) {
    System.out.println(name);
}
```

**Lambda 方式：**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(name -> System.out.println(name));
```

#### 3. 函数式接口

Java 8 提供了许多内置的函数式接口：

- `Predicate<T>` - 判断条件
- `Function<T, R>` - 转换函数
- `Consumer<T>` - 消费数据
- `Supplier<T>` - 提供数据

**示例代码：**
```java
// 参考：LambdaExamples.java
Predicate<String> isEmpty = s -> s.isEmpty();
Function<String, Integer> stringLength = s -> s.length();
Consumer<String> printer = s -> System.out.println("打印: " + s);
Supplier<String> stringSupplier = () -> "Hello from Supplier!";
```

### 方法引用

方法引用是 Lambda 表达式的简化写法：

```java
// 静态方法引用
names.forEach(System.out::println);

// 实例方法引用
names.forEach(String::toUpperCase);

// 构造器引用
Supplier<List<String>> listSupplier = ArrayList::new;
```

### 实际应用示例

参考 `LambdaExamples.java` 文件中的完整示例，包括：
- 基本语法示例
- 函数式接口使用
- 集合操作中的 Lambda
- 比较器中的 Lambda
- 线程中的 Lambda
- 自定义函数式接口

## Stream API

### 什么是 Stream？

Stream 是 Java 8 引入的一个新的抽象层，用于处理数据集合。它提供了一种高效且易于使用的处理数据的方式。

### 核心概念

- **Stream** - 数据流
- **中间操作** - 返回 Stream 的操作（如 filter、map）
- **终端操作** - 产生结果或副作用的操作（如 collect、forEach）

### 基本操作

#### 1. 创建 Stream

```java
// 从集合创建
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();

// 从数组创建
String[] array = {"a", "b", "c"};
Stream<String> stream2 = Arrays.stream(array);

// 直接创建
Stream<String> stream3 = Stream.of("a", "b", "c");
```

#### 2. 中间操作

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// filter - 过滤
List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());

// map - 转换
List<String> numberStrings = numbers.stream()
    .map(n -> "Number: " + n)
    .collect(Collectors.toList());

// distinct - 去重
List<Integer> unique = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
```

#### 3. 终端操作

```java
// forEach - 遍历
numbers.stream().forEach(System.out::println);

// collect - 收集
List<Integer> result = numbers.stream()
    .filter(n -> n > 5)
    .collect(Collectors.toList());

// reduce - 归约
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);
```

### 复杂操作示例

#### 分组和聚合

```java
// 参考：StreamExamples.java
List<Employee> employees = Arrays.asList(
    new Employee("Alice", 25, "IT", 50000),
    new Employee("Bob", 30, "HR", 45000),
    new Employee("Charlie", 35, "IT", 60000)
);

// 按部门分组
Map<String, List<Employee>> byDepartment = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));

// 计算每个部门的平均工资
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
```

#### 并行处理

```java
// 并行 Stream
long parallelSum = numbers.parallelStream()
    .mapToLong(Integer::longValue)
    .sum();
```

### 实际应用示例

参考 `StreamExamples.java` 文件中的完整示例，包括：
- 基本 Stream 操作
- 中间操作和终端操作
- 复杂 Stream 操作
- 并行 Stream
- 自定义收集器
- 无限 Stream
- Stream 与 Optional 结合使用

### Stream 高阶用法

#### 自定义函数式接口与 Stream

```java
// 自定义函数式接口：条件处理器
@FunctionalInterface
interface ConditionProcessor<T> {
    boolean process(T item, Predicate<T> condition);
}

// 在 Stream 中使用
ConditionProcessor<Person> ageProcessor = (person, condition) -> {
    if (condition.test(person)) {
        System.out.println("处理: " + person.getName());
        return true;
    }
    return false;
};

people.stream()
    .filter(person -> ageProcessor.process(person, p -> p.getAge() > 30))
    .forEach(System.out::println);
```

#### 复杂分组和聚合

```java
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

// 复杂聚合：计算每个类别的统计信息
Map<String, OrderStats> categoryStats = orders.stream()
    .collect(Collectors.groupingBy(
        Order::getCategory,
        Collectors.collectingAndThen(
            Collectors.toList(),
            orderList -> {
                double totalValue = orderList.stream().mapToDouble(Order::getPrice).sum();
                double avgPrice = orderList.stream().mapToDouble(Order::getPrice).average().orElse(0);
                return new OrderStats(totalValue, avgPrice, orderList.size());
            }
        )
    ));
```

#### 高级收集器操作

```java
// 自定义收集器：计算学生的综合评分
Collector<Student, StudentScoreAccumulator, List<StudentScore>> studentScoreCollector = 
    Collector.of(
        StudentScoreAccumulator::new,
        StudentScoreAccumulator::accumulate,
        StudentScoreAccumulator::combine,
        StudentScoreAccumulator::finish
    );

// 分区收集器：按成绩等级分区
Map<Boolean, List<StudentScore>> partitionedByGrade = studentScores.stream()
    .collect(Collectors.partitioningBy(score -> score.getAverage() >= 85));
```

#### 流式处理管道与错误处理

```java
// 使用 Optional 处理可能的异常
List<Integer> validNumbers = data.stream()
    .map(StreamAdvancedExamples::safeParseInt)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());

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
```

#### 动态流构建和条件处理

```java
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
```

#### 无限流和生成器模式

```java
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
```

#### 流式API的组合和链式操作

```java
// 复杂的流式处理管道
Map<String, TransactionSummary> currencySummary = transactions.stream()
    .filter(t -> t.getAmount() > 100)
    .collect(Collectors.groupingBy(
        Transaction::getCurrency,
        Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                double total = list.stream().mapToDouble(Transaction::getAmount).sum();
                double average = list.stream().mapToDouble(Transaction::getAmount).average().orElse(0);
                return new TransactionSummary(total, average, list.size());
            }
        )
    ));
```

## Optional

### 什么是 Optional？

Optional 是 Java 8 引入的一个容器类，用于表示可能为 null 的值。它提供了一种更安全、更明确的方式来处理可能为空的值。

### 基本使用

#### 1. 创建 Optional

```java
// 创建空的 Optional
Optional<String> empty = Optional.empty();

// 创建有值的 Optional
Optional<String> present = Optional.of("Hello");

// 创建可能为空的 Optional
Optional<String> nullable = Optional.ofNullable(null);
Optional<String> nullablePresent = Optional.ofNullable("World");
```

#### 2. 检查值是否存在

```java
Optional<String> optional = Optional.of("Hello");

// 检查是否有值
if (optional.isPresent()) {
    System.out.println("值存在: " + optional.get());
}

// Java 8+ 推荐方式
optional.ifPresent(value -> System.out.println("值存在: " + value));
```

#### 3. 获取值

```java
Optional<String> optional = Optional.of("Hello");

// 获取值，如果不存在则使用默认值
String value = optional.orElse("默认值");

// 获取值，如果不存在则通过 Supplier 提供
String value2 = optional.orElseGet(() -> "通过 Supplier 提供的默认值");

// 获取值，如果不存在则抛出异常
String value3 = optional.orElseThrow(() -> new RuntimeException("值不存在"));
```

### 转换操作

```java
Optional<String> optional = Optional.of("  Hello World  ");

// map - 转换值
Optional<String> upperCase = optional.map(String::toUpperCase);
Optional<String> trimmed = optional.map(String::trim);

// flatMap - 扁平化转换
Optional<String> flatMapped = optional.flatMap(s -> Optional.of(s.toUpperCase()));

// filter - 过滤
Optional<String> filtered = optional.filter(s -> s.length() > 5);
```

### 实际应用场景

#### 1. 安全获取 Map 中的值

```java
Map<String, String> userMap = new HashMap<>();
userMap.put("name", "Alice");

String userName = Optional.ofNullable(userMap.get("name"))
    .orElse("未知用户");
```

#### 2. 链式调用中的空值处理

```java
String city = findUserById(1)
    .map(User::getAddress)
    .map(Address::getCity)
    .orElse("未知城市");
```

#### 3. 与 Stream 结合使用

```java
List<Optional<String>> optionals = Arrays.asList(
    Optional.of("Hello"),
    Optional.empty(),
    Optional.of("World")
);

List<String> presentValues = optionals.stream()
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());
```

### 实际应用示例

参考 `OptionalExamples.java` 文件中的完整示例，包括：
- 基本 Optional 操作
- 获取值的方法
- 转换操作
- 条件操作
- 实际应用场景
- 与 Stream 结合使用
- 自定义工具方法
- 最佳实践和反模式

## 综合应用示例

### 数据处理管道

```java
public class DataProcessor {
    public List<String> processUsers(List<User> users) {
        return users.stream()
            .filter(user -> user.getAge() >= 18)
            .map(User::getName)
            .filter(Objects::nonNull)
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList());
    }
}
```

### 异常处理

```java
public Optional<Integer> safeParseInt(String str) {
    try {
        return Optional.of(Integer.parseInt(str));
    } catch (NumberFormatException e) {
        return Optional.empty();
    }
}
```

### 链式操作

```java
public String getUserCity(int userId) {
    return findUserById(userId)
        .map(User::getAddress)
        .map(Address::getCity)
        .filter(city -> !city.isEmpty())
        .orElse("未知城市");
}
```

## 最佳实践

### Lambda 表达式最佳实践

1. **保持简洁**：Lambda 表达式应该简洁明了
2. **使用方法引用**：当 Lambda 只是调用一个方法时，使用方法引用
3. **避免副作用**：Lambda 表达式应该是无副作用的
4. **合理使用变量**：注意变量的作用域和 final 性

```java
// 好的做法
names.forEach(System.out::println);

// 避免的做法
names.forEach(name -> {
    System.out.println(name);
    // 避免在 Lambda 中修改外部变量
});
```

### Stream 最佳实践

1. **避免在循环中使用 Stream**：Stream 本身就是为了替代循环
2. **合理使用并行 Stream**：只有在数据量大且操作复杂时才使用
3. **及时收集结果**：避免多次遍历同一个 Stream
4. **使用合适的收集器**：选择合适的 Collectors 方法

```java
// 好的做法
List<String> result = names.stream()
    .filter(name -> name.length() > 3)
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// 避免的做法
names.stream().forEach(name -> {
    if (name.length() > 3) {
        // 在 forEach 中进行复杂操作
    }
});
```

### Optional 最佳实践

1. **不要将 Optional 作为字段**：Optional 不是序列化的
2. **不要将 Optional 作为方法参数**：使用重载方法代替
3. **避免使用 Optional.get()**：使用 orElse() 等方法代替
4. **合理使用链式调用**：避免过深的嵌套

```java
// 好的做法
public String getUserName(int userId) {
    return findUserById(userId)
        .map(User::getName)
        .orElse("未知用户");
}

// 避免的做法
public Optional<String> getUserName(Optional<Integer> userId) {
    return userId.map(this::findUserById)
        .map(user -> user.map(User::getName))
        .orElse(Optional.empty());
}
```

## 常见问题

### Q: 什么时候使用 Lambda 表达式？

A: 当你需要传递一个简单的函数作为参数时，特别是与函数式接口一起使用时。例如：集合的 forEach、filter、map 等操作。

### Q: Stream 和传统循环有什么区别？

A: Stream 提供声明式的编程风格，代码更简洁易读。传统循环是命令式的，更直接但代码可能更冗长。Stream 还支持并行处理。

### Q: Optional 能完全避免 NullPointerException 吗？

A: Optional 不能完全避免 NPE，但它提供了一种更明确、更安全的方式来处理可能为空的值。正确使用 Optional 可以大大减少 NPE 的发生。

### Q: 什么时候使用并行 Stream？

A: 当数据量大（通常 > 10000 条记录）且操作复杂时，可以考虑使用并行 Stream。但要注意线程开销和同步问题。

### Q: Lambda 表达式的性能如何？

A: Lambda 表达式在首次调用时会有一些初始化开销，但后续调用性能与普通方法调用相当。对于简单操作，性能差异可以忽略不计。

## 总结

Java 8 的 Lambda 表达式、Stream API 和 Optional 是现代 Java 开发的重要工具。掌握这些特性可以：

1. **提高代码质量**：代码更简洁、可读性更强
2. **减少错误**：特别是空指针异常
3. **提高开发效率**：减少样板代码
4. **支持函数式编程**：更灵活的编程范式

建议在实际项目中逐步引入这些特性，从简单的场景开始，逐步掌握更高级的用法。

## 参考资源

- [Oracle Java 8 官方文档](https://docs.oracle.com/javase/8/docs/)
- [Java 8 实战](https://book.douban.com/subject/26772632/)
- [示例代码文件](#示例代码文件)

## 示例代码文件

本学习指南包含以下示例代码文件：

1. **LambdaExamples.java** - Lambda 表达式完整示例
2. **StreamExamples.java** - Stream API 基础示例  
3. **StreamAdvancedExamples.java** - Stream API 高阶用法示例
4. **OptionalExamples.java** - Optional 完整示例

每个文件都包含详细的注释和多种使用场景的示例，可以直接运行学习。

### Stream 高阶用法特色

**StreamAdvancedExamples.java** 展示了以下高级特性：

- **自定义函数式接口**：与 Stream 结合使用的复杂场景
- **复杂分组和聚合**：多级分组、自定义统计信息
- **高级收集器操作**：自定义收集器、分区收集器
- **流式处理管道**：错误处理、异常安全
- **动态流构建**：条件性操作、动态转换链
- **并行流高级用法**：性能优化、线程安全
- **无限流和生成器**：斐波那契数列、质数生成器
- **流式API组合**：操作链式组合、复杂处理管道