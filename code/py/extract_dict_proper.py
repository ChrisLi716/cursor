#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
从dict.xml文件中提取单词信息并生成表格格式的dict.md文件
正确版：每个词性单独一行，换行符在词性前面
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
    """格式化词意，每个词性单独一行，换行符在词性前面"""
    if not trans:
        return ""
    
    # 按换行符分割，因为原始XML中每个词性都是单独一行
    lines = trans.split('\n')
    
    formatted_lines = []
    for i, line in enumerate(lines):
        line = line.strip()
        if not line:
            continue
        
        # 如果这一行以词性标记开头，保持原样
        if re.match(r'^(adj\.|v\.|n\.|adv\.|prep\.|conj\.|int\.|pron\.|det\.|num\.|art\.|aux\.|modal\.|part\.|inf\.|ger\.|past\.|pp\.|pres\.|3rd\.|pl\.|sing\.|abbr\.|symb\.|prefix\.|suffix\.|comb\.|form\.|【名】|【形】|【动】|【副】|【介】|【连】|【叹】|【代】|【数】|【冠】|【助】|【情】|【分】|【不定】|【动名】|【过去】|【过去分】|【现在】|【第三人称】|【复数】|【单数】|【缩写】|【符号】|【前缀】|【后缀】|【组合】|【形式】)', line):
            if i == 0:
                # 第一行不加换行符
                formatted_lines.append(line)
            else:
                # 其他行前面加换行符
                formatted_lines.append(f"<br>{line}")
        else:
            # 如果这一行不是以词性标记开头，可能是词意的延续
            if formatted_lines:
                # 添加到上一行
                formatted_lines[-1] += f" {line}"
            else:
                # 如果是第一行且没有词性标记，直接添加
                formatted_lines.append(line)
    
    return ''.join(formatted_lines)

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
    
    print("生成正确版本的表格格式Markdown文件...")
    generate_markdown_table(words_data, output_file)
    
    print(f"字典文件已生成: {output_file}")

if __name__ == "__main__":
    main()

