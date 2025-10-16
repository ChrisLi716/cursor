#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
从dict.xml文件中提取单词信息并生成表格格式的dict.md文件
"""

import xml.etree.ElementTree as ET
import re

def clean_text(text):
    """清理文本，移除CDATA标记和多余空白"""
    if text is None:
        return ""
    # 移除CDATA标记
    text = re.sub(r'<!\[CDATA\[(.*?)\]\]>', r'\1', text, flags=re.DOTALL)
    # 清理多余空白
    text = re.sub(r'\s+', ' ', text).strip()
    return text

def extract_words_from_xml(xml_file):
    """从XML文件中提取单词信息"""
    try:
        tree = ET.parse(xml_file)
        root = tree.getroot()
        
        words_data = []
        
        for item in root.findall('item'):
            word_elem = item.find('word')
            phonetic_elem = item.find('phonetic')
            trans_elem = item.find('trans')
            
            if word_elem is not None:
                word = clean_text(word_elem.text)
                phonetic = clean_text(phonetic_elem.text) if phonetic_elem is not None else ""
                trans = clean_text(trans_elem.text) if trans_elem is not None else ""
                
                if word:  # 只添加非空单词
                    words_data.append({
                        'word': word,
                        'phonetic': phonetic,
                        'trans': trans
                    })
        
        return words_data
    except ET.ParseError as e:
        print(f"XML解析错误: {e}")
        return []
    except FileNotFoundError:
        print(f"文件未找到: {xml_file}")
        return []

def escape_markdown_table(text):
    """转义Markdown表格中的特殊字符"""
    if not text:
        return ""
    # 转义管道符和换行符
    text = text.replace('|', '\\|')
    text = text.replace('\n', '<br>')
    return text

def generate_markdown_table(words_data, output_file):
    """生成表格格式的Markdown字典文件"""
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("# 英语词典\n\n")
        f.write("从dict.xml提取的单词信息\n\n")
        f.write("| 序号 | 单词 | 音标 | 词意 |\n")
        f.write("|------|------|------|------|\n")
        
        for i, word_info in enumerate(words_data, 1):
            word = escape_markdown_table(word_info['word'])
            phonetic = escape_markdown_table(word_info['phonetic'])
            trans = escape_markdown_table(word_info['trans'])
            
            f.write(f"| {i} | {word} | {phonetic} | {trans} |\n")

def main():
    xml_file = "docs/dict.xml"
    output_file = "docs/dict.md"
    
    print("开始提取单词信息...")
    words_data = extract_words_from_xml(xml_file)
    
    if not words_data:
        print("未找到任何单词数据")
        return
    
    print(f"成功提取 {len(words_data)} 个单词")
    
    print("生成表格格式的Markdown文件...")
    generate_markdown_table(words_data, output_file)
    
    print(f"字典文件已生成: {output_file}")

if __name__ == "__main__":
    main()

