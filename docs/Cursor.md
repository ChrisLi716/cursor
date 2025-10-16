Cursor 是一款以AI为核心的代码编辑器，基于VS CODE开发，继承了VS CODE的强大功能，并在此基础上加入了深度集成的AI功能.

由Anysphere公司开发

AI编程第一选择



另外国内阿里与字节也有基于VS CODE开发的IDE

阿里 Lingma IDE

字节 Trae 独立IDE，免费的国产AI编码工具



版本

>hobby 
>
>pro 
>
>ultra





Settings

Rules & Memories

```
Always respond in Chinese
```



配置环境变量

```
-- E:\Program Files\cursor\resources\app\bin
E:\Program Files\cursor
```





命令

```shell
$ cursor -version

Warning: 'e' is not in the list of known options, but still passed to Electron/Chromium.
Warning: 'i' is not in the list of known options, but still passed to Electron/Chromium.
Warning: 'o' is not in the list of known options, but still passed to Electron/Chromium.

Version:          Cursor 1.5.11 (2f2737de9aa376933d975ae30290447c910fdf40, 2025-09-05T03:48:32.332Z)
OS Version:       Windows_NT x64 10.0.22631
CPUs:             12th Gen Intel(R) Core(TM) i7-12700H (20 x 2688)
Memory (System):  15.69GB (5.32GB free)
VM:               50%
Screen Reader:    no
Process Argv:     
GPU Status:       2d_canvas:                              enabled
                  canvas_oop_rasterization:               enabled_on
                  direct_rendering_display_compositor:    disabled_off_ok
                  gpu_compositing:                        enabled
                  multiple_raster_threads:                enabled_on
                  opengl:                                 enabled_on
                  rasterization:                          enabled
                  raw_draw:                               disabled_off_ok
                  skia_graphite:                          disabled_off
                  video_decode:                           enabled
                  video_encode:                           enabled
                  vulkan:                                 disabled_off
                  webgl:                                  enabled
                  webgl2:                                 enabled
                  webgpu:                                 enabled
                  webnn:                                  disabled_off

CPU %   Mem MB     PID  Process
    0      167   20496  cursor main
    0      121    2044  ptyHost
    0       83   19908       C:\WINDOWS\System32\WindowsPowerShell\v1.0\powershell.exe -noexit -command "try { . \"e:\Program Files\cursor\resources\app\out\vs\workbench\contrib\terminal\common\scripts\shellIntegration.ps1\" } catch {}"
    0        7   27176       conpty-agent
    0      125    2700  shared-process
    0       55    3436     utility-network-service
    0       38   11336     crashpad-handler
    0      164   13828     gpu-process
    0      270   17796  window [1] (py14-quicksort.py - code-python - Cursor)
    0      316   26032  extensionHost [1]
    0      111   26688  fileWatcher [1]

Workspace Stats: 
|  Window (py14-quicksort.py - code-python - Cursor)
|    Folder (code-python): 27 files
|      File types: py(15) xml(6) gitignore(1) iml(1)
|      Conf files:
```



插件

> python



```
ctrl+k 对整个文件或者选择的内容进行操作
ctrl+l 打开对话对整个文件操作
```





坦克大战

```
帮我基于python实现一个坦克大战小游戏，先输出需求文档和开发计划到readme.md文件中
```

```
参考readme.md文件中的需求和开发计划，基于python实现需求
要求：
代码实现输出到名称tank的文件夹中
输出代码实现对应的每一个需求阶段名称
完成实现后自动测试并优化代码性能
运行游戏
```



### 上下文

#### [消息摘要](https://cursor.com/cn/docs/agent/chat/summarization#)

>随着对话变长，Cursor 会自动生成摘要并管理上下文，以保持聊天高效。了解如何使用上下文菜单，以及文件如何被精简以适配模型的上下文窗口。
>
>### [使用 /summarize 命令](https://cursor.com/cn/docs/agent/chat/summarization#summarize)
>
>你可以在聊天中使用 `/summarize` 命令手动触发摘要。当对话过长时，该命令有助于管理上下文，让你在不丢失关键信息的情况下继续高效工作。



### [配置](https://cursor.com/cn/docs/context/codebase-indexing#)

除[忽略文件](https://cursor.com/docs/context/ignore-files)（如 `.gitignore`、`.cursorignore`）中列出的文件外，Cursor 会为所有文件建立索引。

点击 `Show Settings` 可：

- 为新仓库启用自动索引
- 配置需要忽略的文件



> cursor官方文件中有下面一段话：除[忽略文件](https://cursor.com/docs/context/ignore-files)（如 `.gitignore`、`.cursorignore`）中列出的文件外，Cursor 会为所有文件建立索引。
>
> 点击 `Show Settings` 可：
>
> \- 为新仓库启用自动索引
>
> \- 配置需要忽略的文件
>
> 这句话里面的点击 `Show Settings`指的是什么，在哪里操作呢?



#### [查看已编入索引的文件](https://cursor.com/cn/docs/context/codebase-indexing#-1)

要查看已编入索引的文件路径：`Cursor Settings` > `Indexing & Docs` > `View included files`

这将打开一个 `.txt` 文件，列出所有已编入索引的文件。



#### .cursorignore



>`.cursorignore` 中列出的文件将被阻止用于：
>
>- 代码库索引
>- [Tab](https://cursor.com/docs/tab/overview)、[Agent](https://cursor.com/docs/agent/overview) 和 [Inline Edit](https://cursor.com/docs/inline-edit/overview) 的代码访问
>- 通过 [@ 符号引用](https://cursor.com/docs/context/symbols/overview) 的代码访问



>- 由 Agent 发起的工具调用（如终端和 MCP 服务器）无法阻止 访问受 `.cursorignore` 管控的代码



>启用 `Cursor Settings` > `Chat` > `Context` > `Hierarchical Cursor Ignore`，在父目录中查找 `.cursorignore` 文件。
>
>**注意**：注释以 `#` 开头。后面的模式会覆盖前面的模式。模式相对于文件所在位置。

#### 全局忽略文件

> setting-> User -> Cursor -> General:Global Cursor Ignore List



#### @符号

> 1. 选择文件夹后，输入 “/” 以继续下钻并查看所有子文件夹。
> 2. 在设置中启用 **Full Folder Content**。启用后，Cursor 会尝试将该文件夹中的所有文件纳入上下文。



##### [@Docs](https://cursor.com/cn/docs/context/symbols#docs)

> `@Docs` 功能可让你用文档来辅助写代码。Cursor 内置常用文档，也支持添加你自己的文档
>
> 在聊天中输入 `@Docs` 查看可用文档。浏览并从常用框架和库中选择。





- [添加你自己的文档](https://cursor.com/cn/docs/context/symbols#-6)

> 要添加尚未收录的文档：
>
> 1. 输入 `@Docs` 并选择 **Add new doc**
> 2. 粘贴文档站点的 URL
> 3. 添加后，Cursor 会读取并理解该文档，包括所有子页面。像使用其他文档一样使用它。





- [管理文档](https://cursor.com/cn/docs/context/symbols#-7)

>前往 **Cursor Settings** > **Indexing & Docs** 查看你已添加的所有文档。在这里你可以：
>
>- 编辑文档 URL
>- 删除不再需要的文档
>- 添加新文档



##### @Web

> - @Web 是 Cursor IDE 中的一个上下文管理命令，它属于 Cursor 的 AI 聊天助手功能的一部分。Cursor 提供了多种以 @ 开头的特殊命令，用于在聊天中引用不同的上下文信息
> - 使用 `@Web` 时，Cursor 会通过 [exa.ai](https://exa.ai/) 搜索网络以获取最新信息，并将其作为上下文添加。这也支持从直接链接解析 PDF 文件。
> - 默认关闭网络搜索。可在 Settings → Features → Web Search 中启用。



>@Web 命令的主要功能是：
>
>1. 网络搜索集成：允许 AI 助手在回答问题时进行实时的网络搜索
>
>2. 获取最新信息：能够获取最新的技术文档、API 更新、解决方案等
>
>3. 补充上下文：为 AI 提供实时的网络信息来增强回答的准确性和时效性





> 使用示例
>
> 1. @Web 最新的 React 18 有什么新特性？
> 2. @Web 如何解决 Python 中的内存泄漏问题？
> 3. @Web 当前最流行的前端框架有哪些？



### 终端

> Ctrl + ` 打开终端窗口
> Ctrl + 上箭头 Ctrl + 下箭头 查看历史命令



### Cursor Rules

#### 定义

> Cursor Rules 是 Cursor IDE 中的一个强大功能，允许您为 AI 助手设置全局的行为规范和个人偏好。简单来说，就是给 AI 助手制定"行为准则"，让它按照您的习惯和需求来工作。
>
> 规则类型：
>
> 1. User Rules： 全局规则， 适用于所有项目，可在Cursor设置中配置。用于设置通用规则，例如输出语言，响应长度，输出样式，当你希望整个开发环境保持统一的风格或行为时，可以使用User Rules。
>
> 2. Porject Rules： 项目特定规则，存储在项目根目录.cursor/rules文件夹中，适用于特定项目，支持更细粒度的控制，例如不同类型设置不同的规则。



#### 两种规则类型对比

| 规则类型 | User Rules             | Project Rules           |
| :------- | :--------------------- | :---------------------- |
| 作用范围 | 全局所有项目           | 当前项目                |
| 存储位置 | Cursor 设置中          | .cursor/rules/ 文件夹   |
| 优先级   | 低                     | 高（会覆盖 User Rules） |
| 适用场景 | 通用偏好（语言、风格） | 项目特定需求            |



#### User Rule 示例

```
1. 你是一个在软件开发领域的专家级AI编程助手，主要专注于生成清晰，可读性强，扩展性强的代码。
2. 你考虑周到，提供细致，准确，深思熟虑的解决方案，并在推理方面表现出色。
3. 所有解决方案内容以MD格式输出
```



- Porject Rules

> 在Cursor的Agent模式下，AI会自动应用配置的规则，也可以在Curso的对话框中使用 `/Generate Cursor Rules` 命令，让AI 自动生成项目规则。
>
> 规则应用分为四种方式
>
> 1. Always: 每次都会把内容加入到上上文中
> 2. Manual：需要你手动@进去
> 3. Agent：让Cursor根据你的描述自己判断是否加入到上下文中
> 4. Autoattached：根据文件后缀配置是否加入到上下文中





#### Project Rule 示例

- 新增 py-rule会在.cursor/rules/ 目录下生成py-rule.mdc

```
---
description: 规则的描述说明
globs: ["**/*.py", "**/*.ipynb"]  # 应用的文件模式
alwaysApply: false  # 是否总是应用
---

# 这里是具体的规则内容

1. 所有 Python 代码输出为 JSON 格式
2. 使用 type hints 进行类型注解
3. 添加详细的 docstring 文档
4. 遵循 PEP 8 编码规范
```



#### 实用技巧

##### 1. 规则引用方式

> - 在聊天中使用 `@py-rule` 手动引用规则
>
> - 编辑 .py 文件时，规则会自动附加（因为配置了 globs）



##### 2. 查看生效的规则

> 在 Cursor 聊天界面中，你可以看到当前对话应用了哪些规则（会显示在上下文区域）



##### 3. 调试规则

>- 如果规则没有生效，检查 globs 模式是否正确
>
>- 确保 .mdc 文件格式正确（YAML 前置元数据）
>
>- 查看 Cursor Settings → Rules 查看已识别的规则



##### 4. 规则优先级

> Project Rules (alwaysApply: true) 
>
>  ↓ 覆盖
>
> Project Rules (Auto-attached by globs) 
>
>  ↓ 覆盖
>
> User Rules 
>
>  ↓ 覆盖
>
> 默认行为







```
https://mp.weixin.qq.com/s/NyQYJ48kSK1u9A1SWNVO4Q
https://mp.weixin.qq.com/s/jIWRYMi0zsFB03sGMA_QyQ
https://mp.weixin.qq.com/s/HfKBx773I7sADKBJP9UuXg
```

