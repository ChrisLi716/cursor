#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Excel文件C列重复值检查工具
分析Excel文件中C列的值是否存在重复，并列出重复值及其所在的行数
"""

import pandas as pd
from collections import Counter
import sys


def check_duplicates(file_path):
    """
    检查Excel文件C列的重复值
    
    Args:
        file_path (str): Excel文件路径
    """
    try:
        # 读取Excel文件
        df = pd.read_excel(file_path)
        
        # 获取C列数据（第3列，索引为2）
        c_column = df.iloc[:, 2]
        
        # 统计重复值
        value_counts = Counter(c_column.fillna('<空值>'))
        duplicates = {k: v for k, v in value_counts.items() if v > 1}
        
        print(f"📁 文件: {file_path}")
        print(f"📊 总行数: {len(df)}")
        print("-" * 40)
        
        if not duplicates:
            print("✅ C列无重复值")
        else:
            print(f"⚠️  发现 {len(duplicates)} 个重复值:")
            print()
            
            for value, count in duplicates.items():
                # 找到所有出现位置（Excel行号从1开始）
                positions = [i+1 for i, v in enumerate(c_column.fillna('<空值>')) 
                           if str(v) == str(value)]
                print(f"'{value}' - 出现{count}次")
                print(f"  行号: {', '.join(map(str, positions))}")
                print()
    
    except Exception as e:
        print(f"❌ 错误: {e}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        file_path = input("请输入Excel文件路径: ").strip()
    else:
        file_path = sys.argv[1]
    
    check_duplicates(file_path)



