package com.example.java8;

import java.util.*;
import java.util.function.*;

/**
 * Java 8 Optional 示例类
 * 展示 Optional 的各种使用场景和最佳实践
 */
public class OptionalExamples {
    
    /**
     * 1. 基本 Optional 创建和操作
     */
    public static void basicOptionalOperations() {
        System.out.println("=== 基本 Optional 操作 ===");
        
        // 创建 Optional
        Optional<String> emptyOptional = Optional.empty();
        Optional<String> presentOptional = Optional.of("Hello World");
        Optional<String> nullableOptional = Optional.ofNullable(null);
        Optional<String> nullablePresentOptional = Optional.ofNullable("Hello Optional");
        
        System.out.println("空 Optional: " + emptyOptional);
        System.out.println("有值的 Optional: " + presentOptional);
        System.out.println("可能为空的 Optional (null): " + nullableOptional);
        System.out.println("可能为空的 Optional (有值): " + nullablePresentOptional);
        
        // 检查是否有值
        System.out.println("emptyOptional.isPresent(): " + emptyOptional.isPresent());
        System.out.println("presentOptional.isPresent(): " + presentOptional.isPresent());
        System.out.println("emptyOptional.isEmpty(): " + emptyOptional.isEmpty());
        System.out.println("presentOptional.isEmpty(): " + presentOptional.isEmpty());
    }
    
    /**
     * 2. 获取 Optional 中的值
     */
    public static void gettingValuesFromOptional() {
        System.out.println("\n=== 获取 Optional 中的值 ===");
        
        Optional<String> optional = Optional.of("Hello Optional");
        Optional<String> emptyOptional = Optional.empty();
        
        // get() - 直接获取值（不推荐，可能抛出异常）
        try {
            String value = optional.get();
            System.out.println("使用 get() 获取值: " + value);
        } catch (Exception e) {
            System.out.println("get() 抛出异常: " + e.getMessage());
        }
        
        // orElse() - 提供默认值
        String value1 = optional.orElse("默认值");
        String value2 = emptyOptional.orElse("默认值");
        System.out.println("orElse() 结果: " + value1);
        System.out.println("空 Optional 的 orElse(): " + value2);
        
        // orElseGet() - 使用 Supplier 提供默认值
        String value3 = optional.orElseGet(() -> "通过 Supplier 提供的默认值");
        String value4 = emptyOptional.orElseGet(() -> "通过 Supplier 提供的默认值");
        System.out.println("orElseGet() 结果: " + value3);
        System.out.println("空 Optional 的 orElseGet(): " + value4);
        
        // orElseThrow() - 抛出异常
        try {
            String value5 = optional.orElseThrow(() -> new RuntimeException("值不存在"));
            System.out.println("orElseThrow() 结果: " + value5);
        } catch (Exception e) {
            System.out.println("orElseThrow() 异常: " + e.getMessage());
        }
    }
    
    /**
     * 3. Optional 的转换操作
     */
    public static void optionalTransformationOperations() {
        System.out.println("\n=== Optional 转换操作 ===");
        
        Optional<String> optional = Optional.of("  Hello World  ");
        Optional<String> emptyOptional = Optional.empty();
        
        // map() - 转换值
        Optional<String> upperCase = optional.map(String::toUpperCase);
        Optional<String> trimmed = optional.map(String::trim);
        Optional<Integer> length = optional.map(String::length);
        
        System.out.println("原始值: " + optional.get());
        System.out.println("转换为大写: " + upperCase.get());
        System.out.println("去除空格: " + trimmed.get());
        System.out.println("获取长度: " + length.get());
        
        // flatMap() - 扁平化转换
        Optional<String> flatMapped = optional.flatMap(s -> Optional.of(s.toUpperCase()));
        System.out.println("flatMap 结果: " + flatMapped.get());
        
        // filter() - 过滤
        Optional<String> filtered = optional.filter(s -> s.length() > 5);
        Optional<String> emptyFiltered = emptyOptional.filter(s -> s.length() > 5);
        
        System.out.println("长度>5的过滤结果: " + filtered);
        System.out.println("空 Optional 过滤结果: " + emptyFiltered);
    }
    
    /**
     * 4. Optional 的条件操作
     */
    public static void optionalConditionalOperations() {
        System.out.println("\n=== Optional 条件操作 ===");
        
        Optional<String> optional = Optional.of("Hello World");
        Optional<String> emptyOptional = Optional.empty();
        
        // ifPresent() - 如果有值则执行操作
        System.out.println("ifPresent() 示例:");
        optional.ifPresent(value -> System.out.println("  值存在: " + value));
        emptyOptional.ifPresent(value -> System.out.println("  这个不会执行"));
        
        // ifPresentOrElse() - 如果有值执行一个操作，否则执行另一个操作
        System.out.println("ifPresentOrElse() 示例:");
        optional.ifPresentOrElse(
            value -> System.out.println("  值存在: " + value),
            () -> System.out.println("  值不存在")
        );
        emptyOptional.ifPresentOrElse(
            value -> System.out.println("  这个不会执行"),
            () -> System.out.println("  值不存在，执行默认操作")
        );
    }
    
    /**
     * 5. 实际应用场景示例
     */
    public static void realWorldScenarios() {
        System.out.println("\n=== 实际应用场景示例 ===");
        
        // 场景1：从 Map 中安全获取值
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "Alice");
        userMap.put("email", "alice@example.com");
        
        String userName = Optional.ofNullable(userMap.get("name"))
            .orElse("未知用户");
        String userPhone = Optional.ofNullable(userMap.get("phone"))
            .orElse("未提供电话");
        
        System.out.println("用户名: " + userName);
        System.out.println("用户电话: " + userPhone);
        
        // 场景2：处理可能为 null 的方法返回值
        String result = findUserById(1)
            .map(User::getName)
            .map(String::toUpperCase)
            .orElse("用户不存在");
        System.out.println("查找用户结果: " + result);
        
        // 场景3：链式调用中的空值处理
        String city = findUserById(1)
            .map(User::getAddress)
            .map(Address::getCity)
            .orElse("未知城市");
        System.out.println("用户所在城市: " + city);
    }
    
    /**
     * 6. Optional 与 Stream 结合使用
     */
    public static void optionalWithStream() {
        System.out.println("\n=== Optional 与 Stream 结合使用 ===");
        
        List<Optional<String>> optionals = Arrays.asList(
            Optional.of("Hello"),
            Optional.empty(),
            Optional.of("World"),
            Optional.empty(),
            Optional.of("Java")
        );
        
        // 过滤出有值的 Optional 并获取值
        List<String> presentValues = optionals.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        System.out.println("有值的元素: " + presentValues);
        
        // 使用 flatMap 处理 Optional
        List<String> flatMappedValues = optionals.stream()
            .flatMap(opt -> opt.map(Stream::of).orElse(Stream.empty()))
            .collect(Collectors.toList());
        System.out.println("flatMap 处理结果: " + flatMappedValues);
        
        // 使用 Optional 作为 Stream 的元素
        List<String> words = Arrays.asList("Hello", null, "World", "", "Java");
        List<String> validWords = words.stream()
            .map(Optional::ofNullable)
            .filter(opt -> opt.isPresent() && !opt.get().isEmpty())
            .map(Optional::get)
            .collect(Collectors.toList());
        System.out.println("有效单词: " + validWords);
    }
    
    /**
     * 7. 自定义 Optional 工具方法
     */
    public static void customOptionalUtilities() {
        System.out.println("\n=== 自定义 Optional 工具方法 ===");
        
        // 安全转换
        Optional<Integer> number = safeParseInt("123");
        Optional<Integer> invalidNumber = safeParseInt("abc");
        
        System.out.println("解析 '123': " + number);
        System.out.println("解析 'abc': " + invalidNumber);
        
        // 条件创建 Optional
        Optional<String> conditional = createIfPositive(5);
        Optional<String> negative = createIfPositive(-1);
        
        System.out.println("正数创建: " + conditional);
        System.out.println("负数创建: " + negative);
        
        // 链式安全调用
        String result = Optional.of("Hello")
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .map(s -> s + "!")
            .orElse("默认值");
        System.out.println("链式调用结果: " + result);
    }
    
    /**
     * 8. Optional 最佳实践和反模式
     */
    public static void bestPracticesAndAntiPatterns() {
        System.out.println("\n=== Optional 最佳实践和反模式 ===");
        
        // 好的做法：使用 Optional 作为方法返回值
        Optional<String> goodPractice = findUserById(1).map(User::getName);
        System.out.println("好的做法: " + goodPractice);
        
        // 好的做法：使用 orElse 提供默认值
        String defaultValue = findUserById(999).map(User::getName).orElse("默认用户");
        System.out.println("提供默认值: " + defaultValue);
        
        // 好的做法：使用 ifPresent 进行条件操作
        findUserById(1).ifPresent(user -> 
            System.out.println("用户存在: " + user.getName())
        );
        
        // 反模式：不要将 Optional 作为字段
        // 反模式：不要将 Optional 作为方法参数
        // 反模式：不要使用 Optional.get() 而不检查 isPresent()
        
        System.out.println("注意：避免将 Optional 作为字段和方法参数");
    }
    
    /**
     * 工具方法：安全解析整数
     */
    private static Optional<Integer> safeParseInt(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    /**
     * 工具方法：条件创建 Optional
     */
    private static Optional<String> createIfPositive(int number) {
        return number > 0 ? Optional.of("正数: " + number) : Optional.empty();
    }
    
    /**
     * 模拟查找用户的方法
     */
    private static Optional<User> findUserById(int id) {
        if (id == 1) {
            return Optional.of(new User("Alice", new Address("北京", "朝阳区")));
        }
        return Optional.empty();
    }
    
    /**
     * 主方法，运行所有示例
     */
    public static void main(String[] args) {
        basicOptionalOperations();
        gettingValuesFromOptional();
        optionalTransformationOperations();
        optionalConditionalOperations();
        realWorldScenarios();
        optionalWithStream();
        customOptionalUtilities();
        bestPracticesAndAntiPatterns();
    }
    
    /**
     * 内部类：用户信息
     */
    static class User {
        private String name;
        private Address address;
        
        public User(String name, Address address) {
            this.name = name;
            this.address = address;
        }
        
        public String getName() { return name; }
        public Address getAddress() { return address; }
        
        @Override
        public String toString() {
            return "User{name='" + name + "', address=" + address + "}";
        }
    }
    
    /**
     * 内部类：地址信息
     */
    static class Address {
        private String city;
        private String district;
        
        public Address(String city, String district) {
            this.city = city;
            this.district = district;
        }
        
        public String getCity() { return city; }
        public String getDistrict() { return district; }
        
        @Override
        public String toString() {
            return "Address{city='" + city + "', district='" + district + "'}";
        }
    }
}
