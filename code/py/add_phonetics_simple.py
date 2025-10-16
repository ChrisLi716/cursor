#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
为缺失音标的单词添加英式英语音标（使用内置字典）
"""

import re

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
        'sober': '[ˈsəʊbə]',
        'at': '[æt]',
        'a': '[ə]',
        'time': '[taɪm]'
    }
    
    # 读取dict.md文件
    with open('docs/dict.md', 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 查找所有缺失音标的单词
    pattern = r'\| (\d+) \| (\w+) \| \[\] \|'
    matches = re.findall(pattern, content)
    
    print(f"找到 {len(matches)} 个缺失音标的单词")
    
    # 为每个单词添加音标
    updated_count = 0
    for line_num, word in matches:
        if word in phonetic_dict:
            phonetic = phonetic_dict[word]
            # 替换空音标
            old_pattern = f'| {line_num} | {word} | [] |'
            new_pattern = f'| {line_num} | {word} | {phonetic} |'
            content = content.replace(old_pattern, new_pattern)
            print(f"已为 {word} 添加音标: {phonetic}")
            updated_count += 1
        else:
            print(f"未找到 {word} 的音标")
    
    # 写回文件
    with open('docs/dict.md', 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"音标添加完成！共更新了 {updated_count} 个单词")

if __name__ == "__main__":
    add_phonetics_to_dict()

