# Excel文件C列重复值分析工具使用说明

## 📋 工具概述

本工具包含两个Python脚本，用于分析Excel文件中指定列（默认C列）的重复值：

1. **`analyze_excel_duplicates.py`** - 完整功能版本
2. **`quick_excel_duplicate_check.py`** - 快速检查版本

## 🚀 快速开始

### 方法一：使用快速检查工具

```bash
python quick_excel_duplicate_check.py
```

然后按提示输入Excel文件路径。

### 方法二：使用完整功能工具

```bash
python analyze_excel_duplicates.py your_file.xlsx
```

## 📖 详细使用说明

### 1. 快速检查工具 (`quick_excel_duplicate_check.py`)

**特点：**
- 简单易用
- 快速分析
- 适合日常检查

**使用方法：**
```bash
# 交互式使用
python quick_excel_duplicate_check.py

# 命令行使用
python quick_excel_duplicate_check.py your_file.xlsx
```

**输出示例：**
```
📁 文件: data.xlsx
📊 总行数: 100
🔍 分析列: C
--------------------------------------------------
⚠️  发现 2 个重复值:

'张三' - 出现 3 次
  行号: 5, 12, 28

'李四' - 出现 2 次
  行号: 8, 15
```

### 2. 完整功能工具 (`analyze_excel_duplicates.py`)

**特点：**
- 功能完整
- 支持多工作表
- 详细统计信息
- 支持不同列分析

**使用方法：**

```bash
# 基本使用
python analyze_excel_duplicates.py data.xlsx

# 指定工作表
python analyze_excel_duplicates.py data.xlsx -s "Sheet2"

# 分析其他列（如D列）
python analyze_excel_duplicates.py data.xlsx -c D

# 使用列名
python analyze_excel_duplicates.py data.xlsx -c "姓名"
```

**参数说明：**
- `file_path`: Excel文件路径（必需）
- `-s, --sheet`: 工作表名称（可选）
- `-c, --column`: 要分析的列名或列字母（默认：C）

## 📊 输出信息说明

### 文件信息
- 文件路径
- 工作表名称
- 总行数和列数

### 分析结果
- 重复值列表
- 每个重复值的出现次数
- 重复值所在的具体行号
- 行号范围

### 统计信息
- 总行数
- 唯一值数量
- 重复值数量
- 重复率

## 🔧 安装依赖

确保安装了必要的Python包：

```bash
pip install pandas openpyxl
```

## 📝 注意事项

1. **文件格式支持**：支持 `.xlsx` 和 `.xls` 格式
2. **列标识**：可以使用列字母（A, B, C...）或列名
3. **空值处理**：空值会被标记为 `<空值>`
4. **行号**：显示的是Excel中的实际行号（从1开始）

## 🎯 使用场景

- **数据清洗**：检查数据中的重复项
- **数据验证**：确保关键字段的唯一性
- **质量检查**：定期检查数据质量
- **问题排查**：快速定位重复数据

## 💡 建议

1. **定期检查**：建议定期运行重复值检查
2. **数据修正**：发现重复值后及时修正
3. **预防措施**：在数据录入时设置唯一性约束
4. **备份数据**：分析前建议备份原始数据

## 🆘 常见问题

**Q: 如何处理空值？**
A: 空值会被标记为 `<空值>`，如果多个空值存在，也会被识别为重复。

**Q: 支持哪些Excel格式？**
A: 支持 `.xlsx` 和 `.xls` 格式，推荐使用 `.xlsx` 格式。

**Q: 如何分析其他列？**
A: 使用 `-c` 参数指定列名或列字母，如 `-c D` 或 `-c "姓名"`。

**Q: 工具运行出错怎么办？**
A: 检查文件路径是否正确，确保文件未被其他程序占用，检查文件格式是否支持。



