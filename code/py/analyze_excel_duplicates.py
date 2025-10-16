#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Excel文件C列重复值分析工具
分析指定Excel文件中C列的值是否存在重复，并列出重复值及其所在的行数
"""

import pandas as pd
import sys
import os
from collections import defaultdict
import argparse


def analyze_excel_duplicates(file_path, sheet_name=None, column_name='C'):
    """
    分析Excel文件中指定列的重复值
    
    Args:
        file_path (str): Excel文件路径
        sheet_name (str, optional): 工作表名称，如果为None则使用第一个工作表
        column_name (str): 要分析的列名，默认为'C'
    
    Returns:
        dict: 包含重复值分析结果的字典
    """
    try:
        # 读取Excel文件
        if sheet_name:
            df = pd.read_excel(file_path, sheet_name=sheet_name)
        else:
            df = pd.read_excel(file_path)
        
        print(f"📊 文件信息:")
        print(f"   文件路径: {file_path}")
        print(f"   工作表: {sheet_name if sheet_name else '第一个工作表'}")
        print(f"   总行数: {len(df)}")
        print(f"   总列数: {len(df.columns)}")
        print()
        
        # 获取列名
        if column_name.isalpha():
            # 如果column_name是字母，转换为列索引
            col_index = ord(column_name.upper()) - ord('A')
            if col_index >= len(df.columns):
                print(f"❌ 错误: 列 '{column_name}' 不存在，文件只有 {len(df.columns)} 列")
                return None
            actual_column = df.columns[col_index]
        else:
            # 如果column_name是列名
            if column_name not in df.columns:
                print(f"❌ 错误: 列 '{column_name}' 不存在")
                print(f"   可用的列: {list(df.columns)}")
                return None
            actual_column = column_name
        
        print(f"🔍 分析列: {actual_column} (第{column_name}列)")
        print()
        
        # 获取C列的数据
        column_data = df[actual_column]
        
        # 统计每个值的出现次数和行号
        value_positions = defaultdict(list)
        for index, value in enumerate(column_data, start=1):  # 从1开始计数（Excel行号）
            # 处理NaN值
            if pd.isna(value):
                value_positions['<空值>'].append(index)
            else:
                value_positions[str(value)].append(index)
        
        # 找出重复值
        duplicates = {value: positions for value, positions in value_positions.items() 
                     if len(positions) > 1}
        
        # 输出结果
        print("=" * 60)
        print("📋 分析结果")
        print("=" * 60)
        
        if not duplicates:
            print("✅ 未发现重复值！C列中的所有值都是唯一的。")
        else:
            print(f"⚠️  发现 {len(duplicates)} 个重复值:")
            print()
            
            for i, (value, positions) in enumerate(duplicates.items(), 1):
                print(f"{i}. 重复值: '{value}'")
                print(f"   出现次数: {len(positions)}")
                print(f"   所在行号: {', '.join(map(str, positions))}")
                print(f"   行号范围: {min(positions)} - {max(positions)}")
                print()
        
        # 统计信息
        total_values = len(column_data)
        unique_values = len(value_positions)
        duplicate_values = len(duplicates)
        
        print("📈 统计信息:")
        print(f"   总行数: {total_values}")
        print(f"   唯一值数量: {unique_values}")
        print(f"   重复值数量: {duplicate_values}")
        print(f"   重复率: {duplicate_values/unique_values*100:.2f}%")
        
        return {
            'total_rows': total_values,
            'unique_values': unique_values,
            'duplicate_values': duplicate_values,
            'duplicates': duplicates
        }
        
    except FileNotFoundError:
        print(f"❌ 错误: 文件 '{file_path}' 不存在")
        return None
    except Exception as e:
        print(f"❌ 错误: {str(e)}")
        return None


def main():
    """主函数"""
    parser = argparse.ArgumentParser(description='分析Excel文件中C列的重复值')
    parser.add_argument('file_path', help='Excel文件路径')
    parser.add_argument('-s', '--sheet', help='工作表名称（可选）')
    parser.add_argument('-c', '--column', default='C', help='要分析的列名或列字母（默认：C）')
    
    args = parser.parse_args()
    
    # 检查文件是否存在
    if not os.path.exists(args.file_path):
        print(f"❌ 错误: 文件 '{args.file_path}' 不存在")
        return
    
    # 分析重复值
    result = analyze_excel_duplicates(args.file_path, args.sheet, args.column)
    
    if result and result['duplicates']:
        print("\n" + "=" * 60)
        print("💡 建议:")
        print("   1. 检查重复值是否为预期结果")
        print("   2. 如果是数据错误，请修正相应的行")
        print("   3. 如果是预期结果，可以考虑去重处理")


if __name__ == "__main__":
    # 如果没有命令行参数，提供交互式输入
    if len(sys.argv) == 1:
        print("🔍 Excel文件C列重复值分析工具")
        print("=" * 40)
        
        file_path = input("请输入Excel文件路径: ").strip()
        if not file_path:
            print("❌ 未输入文件路径")
            sys.exit(1)
        
        sheet_name = input("请输入工作表名称（可选，直接回车跳过）: ").strip()
        if not sheet_name:
            sheet_name = None
        
        column = input("请输入要分析的列（默认C列，直接回车使用C）: ").strip()
        if not column:
            column = 'C'
        
        result = analyze_excel_duplicates(file_path, sheet_name, column)
        
        if result and result['duplicates']:
            print("\n" + "=" * 60)
            print("💡 建议:")
            print("   1. 检查重复值是否为预期结果")
            print("   2. 如果是数据错误，请修正相应的行")
            print("   3. 如果是预期结果，可以考虑去重处理")
    else:
        main()


