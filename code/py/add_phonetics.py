#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
为缺失音标的单词添加英式英语音标
"""

import re
import requests
import time
from bs4 import BeautifulSoup

def get_british_phonetic(word):
    """获取单词的英式英语音标"""
    try:
        # 使用剑桥词典获取英式发音
        url = f"https://dictionary.cambridge.org/dictionary/english/{word.lower()}"
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
        }
        
        response = requests.get(url, headers=headers, timeout=10)
        if response.status_code == 200:
            soup = BeautifulSoup(response.content, 'html.parser')
            
            # 查找英式发音
            uk_phonetic = soup.find('span', {'class': 'uk dpron-i'})
            if uk_phonetic:
                phonetic_text = uk_phonetic.get_text().strip()
                # 提取音标部分，通常在/.../中
                phonetic_match = re.search(r'/([^/]+)/', phonetic_text)
                if phonetic_match:
                    return f"[{phonetic_match.group(1)}]"
            
            # 如果没找到英式发音，尝试查找通用发音
            phonetic = soup.find('span', {'class': 'dpron-i'})
            if phonetic:
                phonetic_text = phonetic.get_text().strip()
                phonetic_match = re.search(r'/([^/]+)/', phonetic_text)
                if phonetic_match:
                    return f"[{phonetic_match.group(1)}]"
        
        return None
    except Exception as e:
        print(f"获取 {word} 音标时出错: {e}")
        return None

def add_phonetics_to_dict():
    """为dict.md中缺失音标的单词添加音标"""
    
    # 手动添加一些常见单词的英式音标
    phonetic_dict = {
        'idle': '[ˈaɪd(ə)l]',
        'overwhelm': '[ˌəʊvəˈwelm]',
        'crunch': '[krʌntʃ]',
        'sentiment': '[ˈsentɪmənt]',
        'obstacle': '[ˈɒbstək(ə)l]',
        'drown': '[draʊn]',
        'legitimate': '[lɪˈdʒɪtɪmət]',
        'forecast': '[ˈfɔːkɑːst]',
        'qualify': '[ˈkwɒlɪfaɪ]',
        'decapitate': '[dɪˈkæpɪteɪt]',
        'halve': '[hɑːv]',
        'swelling': '[ˈswelɪŋ]',
        'contemporary': '[kənˈtemp(ə)rəri]',
        'netizens': '[ˈnetɪzənz]',
        'penis': '[ˈpiːnɪs]',
        'Genius': '[ˈdʒiːniəs]',
        'syndrome': '[ˈsɪndrəʊm]',
        'imposter': '[ɪmˈpɒstə]',
        'waterfront': '[ˈwɔːtəfrʌnt]',
        'condense': '[kənˈdens]',
        'obnoxious': '[əbˈnɒkʃəs]',
        'coalesce': '[ˌkəʊəˈles]',
        'divide': '[dɪˈvaɪd]',
        'intermediate': '[ˌɪntəˈmiːdiət]',
        'regulatory': '[ˈreɡjələtəri]',
        'voluntary': '[ˈvɒləntri]',
        'involuntary': '[ɪnˈvɒləntri]',
        'celibate': '[ˈselɪbət]',
        'copper': '[ˈkɒpə]',
        'perpetual': '[pəˈpetʃuəl]',
        'notch': '[nɒtʃ]',
        'quirk': '[kwɜːk]',
        'haze': '[heɪz]',
        'tragically': '[ˈtrædʒɪkli]',
        'blimey': '[ˈblaɪmi]',
        'natter': '[ˈnætə]',
        'chuffed': '[tʃʌft]',
        'tickle': '[ˈtɪk(ə)l]',
        'cheeky': '[ˈtʃiːki]',
        'mischievous': '[ˈmɪstʃɪvəs]',
        'tweak': '[twiːk]',
        'sober': '[ˈsəʊbə]'
    }
    
    # 读取dict.md文件
    with open('docs/dict.md', 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 查找所有缺失音标的单词
    pattern = r'\| (\d+) \| (\w+) \| \[\] \|'
    matches = re.findall(pattern, content)
    
    print(f"找到 {len(matches)} 个缺失音标的单词")
    
    # 为每个单词添加音标
    for line_num, word in matches:
        if word in phonetic_dict:
            phonetic = phonetic_dict[word]
            # 替换空音标
            old_pattern = f'| {line_num} | {word} | [] |'
            new_pattern = f'| {line_num} | {word} | {phonetic} |'
            content = content.replace(old_pattern, new_pattern)
            print(f"已为 {word} 添加音标: {phonetic}")
        else:
            print(f"未找到 {word} 的音标")
    
    # 写回文件
    with open('docs/dict.md', 'w', encoding='utf-8') as f:
        f.write(content)
    
    print("音标添加完成！")

if __name__ == "__main__":
    add_phonetics_to_dict()

