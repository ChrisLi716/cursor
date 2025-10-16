#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
从dict.xml文件中提取单词信息并生成表格格式的dict.md文件
简单版：确保换行符<br>在词性前面
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

def format_translation(trans):
    """格式化词意，让不同词性换行显示，换行符<br>在词性前面"""
    if not trans:
        return ""
    
    # 简单的词性标记列表
    pos_markers = [
        'adj.', 'v.', 'n.', 'adv.', 'prep.', 'conj.', 'int.', 'pron.', 'det.', 
        'num.', 'art.', 'aux.', 'modal.', 'part.', 'inf.', 'ger.', 'past.', 
        'pp.', 'pres.', '3rd.', 'pl.', 'sing.', 'abbr.', 'symb.', 'prefix.', 
        'suffix.', 'comb.', 'form.', '【名】', '【形】', '【动】', '【副】', 
        '【介】', '【连】', '【叹】', '【代】', '【数】', '【冠】', '【助】', 
        '【情】', '【分】', '【不定】', '【动名】', '【过去】', '【过去分】', 
        '【现在】', '【第三人称】', '【复数】', '【单数】', '【缩写】', '【符号】', 
        '【前缀】', '【后缀】', '【组合】', '【形式】'
    ]
    
    # 构建正则表达式模式
    pattern = r'\s+(' + '|'.join(re.escape(marker) for marker in pos_markers) + r')'
    
    # 分割文本，保留分隔符
    parts = re.split(pattern, trans)
    
    if len(parts) <= 1:
        # 如果没有明确的词性标记，尝试按分号分割
        parts = trans.split(';')
        if len(parts) <= 1:
            # 如果也没有分号，尝试按句号分割
            parts = trans.split('.')
            if len(parts) <= 1:
                return trans
    
    formatted_parts = []
    is_first = True
    
    for i in range(0, len(parts), 2):
        if i + 1 < len(parts):
            # 有词性标记的情况
            content = parts[i].strip()
            pos = parts[i + 1].strip()
            
            if content and pos:
                if is_first:
                    formatted_parts.append(f"{pos} {content}")
                    is_first = False
                else:
                    formatted_parts.append(f"<br>{pos} {content}")
        else:
            # 没有词性标记的情况
            content = parts[i].strip()
            if content:
                if is_first:
                    formatted_parts.append(content)
                    is_first = False
                else:
                    formatted_parts.append(f"<br>{content}")
    
    return ''.join(formatted_parts)

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
    # 转义管道符（但保留<br>标签用于换行）
    text = text.replace('|', '\\|')
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
            trans = format_translation(word_info['trans'])
            trans = escape_markdown_table(trans)
            
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
    
    print("生成简单版本的表格格式Markdown文件...")
    generate_markdown_table(words_data, output_file)
    
    print(f"字典文件已生成: {output_file}")

if __name__ == "__main__":
    main()

