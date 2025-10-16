#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
快速Excel C列重复值检查工具
简化版本，用于快速检查Excel文件中C列的重复值
"""

import pandas as pd
import sys
from collections import Counter


def quick_check_duplicates(file_path, column='C'):
    """
    快速检查Excel文件C列的重复值
    
    Args:
        file_path (str): Excel文件路径
        column (str): 列名，默认为'C'
    """
    try:
        # 读取Excel文件
        df = pd.read_excel(file_path)
        
        print(f"📁 文件: {file_path}")
        print(f"📊 总行数: {len(df)}")
        
        # 获取C列数据
        if column.isalpha():
            col_index = ord(column.upper()) - ord('A')
            if col_index >= len(df.columns):
                print(f"❌ 列 '{column}' 不存在")
                return
            c_data = df.iloc[:, col_index]
        else:
            if column not in df.columns:
                print(f"❌ 列 '{column}' 不存在")
                return
            c_data = df[column]
        
        # 统计重复值
        value_counts = Counter(c_data.fillna('<空值>'))
        duplicates = {k: v for k, v in value_counts.items() if v > 1}
        
        print(f"🔍 分析列: {column}")
        print("-" * 50)
        
        if not duplicates:
            print("✅ 无重复值")
        else:
            print(f"⚠️  发现 {len(duplicates)} 个重复值:")
            print()
            
            for value, count in duplicates.items():
                # 找到所有出现位置
                positions = []
                for i, v in enumerate(c_data.fillna('<空值>'), 1):
                    if str(v) == str(value):
                        positions.append(i)
                
                print(f"'{value}' - 出现 {count} 次")
                print(f"  行号: {', '.join(map(str, positions))}")
                print()
    
    except Exception as e:
        print(f"❌ 错误: {e}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        file_path = input("请输入Excel文件路径: ").strip()
    else:
        file_path = sys.argv[1]
    
    quick_check_duplicates(file_path)


