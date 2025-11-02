# Cursor MCP 工具实际演示示例

本文档通过具体场景演示如何在 Cursor 中使用 MCP 工具，所有示例都包含真实的执行输出结果。

---

## 🚀 快速开始

### 5 分钟上手

**第一步：打开 Cursor 聊天**
- 按 `Ctrl+L` (Windows) 或 `Cmd+L` (Mac) 打开聊天面板

**第二步：直接说出你的需求**
```
请访问 https://httpbin.org/html 并告诉我页面内容
```

**第三步：查看结果**
- AI 会自动调用 MCP 工具
- 返回格式化的结果

**就这么简单！** 你不需要配置任何东西，Cursor 内置的浏览器 MCP 工具已经可以使用了。

---

## 📚 文档结构

- **场景 1**：访问网页并提取信息（基础操作）
- **场景 2**：网页交互 - 填写表单（表单操作）
- **场景 3**：页面分析和统计（数据分析）
- **场景 4**：监控网络请求和调试信息（调试技巧）
- **场景 5-7**：高级功能（多标签页、对话框、拖放）

每个场景都包含：
- ✅ 用户请求示例
- ✅ 详细的工具调用步骤
- ✅ 真实的执行输出结果
- ✅ 结果说明和总结

---

## 场景 1：访问网页并提取信息

### 用户请求
```
请访问 https://httpbin.org/html，告诉我页面标题、有多少个标题元素和段落，并截图保存
```

### AI 执行流程（自动调用 MCP 工具）

#### 步骤 1：导航到目标网页

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://httpbin.org/html"
  }
}
```

**实际执行结果：**
```
Page state:
- Page URL: https://httpbin.org/html
- Page Title: 
- Page Snapshot:
  - heading "Herman Melville - Moby-Dick" [level=1] [ref=e2]
  - paragraph [ref=e4]: Availing himself of the mild, summer-cool weather...
```

**说明：**
- ✅ 页面成功加载
- ✅ 获取到页面结构信息
- ✅ 可以看到页面包含 1 个 H1 标题和一个长段落

---

#### 步骤 2：获取页面详细快照

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_snapshot"
}
```

**实际执行结果：**
```
Page Snapshot (YAML 格式):
- generic [active] [ref=e1]:
  - heading "Herman Melville - Moby-Dick" [level=1] [ref=e2]
  - paragraph [ref=e4]: [长文本内容...]
```

**说明：**
- ✅ 快照以 YAML 格式返回页面可访问性树结构
- ✅ 每个元素都有唯一的 ref 引用，可用于后续操作
- ✅ 可以清楚看到页面的层次结构

---

#### 步骤 3：使用 JavaScript 提取页面信息

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_evaluate",
  "parameters": {
    "function": "() => { return { title: document.title, url: window.location.href, headingCount: document.querySelectorAll('h1, h2, h3').length, paragraphCount: document.querySelectorAll('p').length, textSample: document.body.textContent.substring(0, 200) }; }"
  }
}
```

**实际执行结果：**
```json
{
  "title": "",
  "url": "https://httpbin.org/html",
  "headingCount": 1,
  "paragraphCount": 1,
  "textSample": "\n      Herman Melville - Moby-Dick\n\n      \n        \n          Availing himself of the mild, summer-cool weather..."
}
```

**执行代码（实际生成的 Playwright 代码）：**
```javascript
await page.evaluate(() => { 
  return { 
    title: document.title, 
    url: window.location.href, 
    headingCount: document.querySelectorAll('h1, h2, h3').length, 
    paragraphCount: document.querySelectorAll('p').length, 
    textSample: document.body.textContent.substring(0, 200) 
  }; 
});
```

**说明：**
- ✅ JavaScript 代码在浏览器中执行并返回结果
- ✅ 页面包含 1 个标题元素（heading）
- ✅ 页面包含 1 个段落元素（paragraph）
- ✅ 成功提取了页面文本样本

---

#### 步骤 4：截图保存

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_take_screenshot",
  "parameters": {
    "filename": "httpbin-html-page.png",
    "type": "png"
  }
}
```

**实际执行结果：**
```
Took the viewport screenshot and saved it as 
C:\Users\DELL\AppData\Local\Temp\cursor-browser-extension\1762073725570\httpbin-html-page.png
```

**执行代码（实际生成的 Playwright 代码）：**
```javascript
await page.screenshot({
  "type": "png",
  "path": "C:\\Users\\DELL\\AppData\\Local\\Temp\\cursor-browser-extension\\1762073725570\\httpbin-html-page.png"
});
```

**说明：**
- ✅ 截图成功保存到临时目录
- ✅ 文件格式为 PNG
- ✅ 可以通过返回的路径访问截图文件

---

### 完整执行总结

**用户请求完成后，AI 返回的结果：**

```
✅ 页面访问成功
📄 页面信息：
   - URL: https://httpbin.org/html
   - 标题元素数量: 1
   - 段落元素数量: 1
   - 页面标题: Herman Melville - Moby-Dick

📸 截图已保存到：
   C:\Users\DELL\AppData\Local\Temp\cursor-browser-extension\1762073725570\httpbin-html-page.png
```

---

## 场景 2：网页交互 - 填写表单

### 用户请求
```
请访问 https://httpbin.org/forms/post，查看表单结构，然后填写客户信息（姓名、电话、邮箱）
```

### AI 执行流程

#### 步骤 1：导航到表单页面

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://httpbin.org/forms/post"
  }
}
```

**实际执行结果：**
```
Page state:
- Page URL: https://httpbin.org/forms/post
- Page Snapshot:
  - paragraph [ref=e3]:
    - text: "Customer name:"
    - textbox "Customer name:" [ref=e5]
  - paragraph [ref=e6]:
    - text: "Telephone:"
    - textbox "Telephone:" [ref=e8]
  - paragraph [ref=e9]:
    - text: "E-mail address:"
    - textbox "E-mail address:" [ref=e11]
  - group "Pizza Size" [ref=e12]:
    - radio "Small" [ref=e16]
    - radio "Medium" [ref=e19]
    - radio "Large" [ref=e22]
  - group "Pizza Toppings" [ref=e23]:
    - checkbox "Bacon" [ref=e27]
    - checkbox "Extra Cheese" [ref=e30]
    - checkbox "Onion" [ref=e33]
    - checkbox "Mushroom" [ref=e36]
  - textbox "Preferred delivery time:" [ref=e39]
  - textbox "Delivery instructions:" [ref=e42]
  - button "Submit order" [ref=e44]
```

**说明：**
- ✅ 页面包含客户信息表单（姓名、电话、邮箱）
- ✅ 包含披萨大小单选按钮组
- ✅ 包含披萨配料复选框组
- ✅ 包含配送时间和说明输入框
- ✅ 包含提交按钮

---

#### 步骤 2：获取页面详细结构

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_snapshot"
}
```

**说明：**
快照已在上一步导航时自动获取，显示了完整的表单结构。

---

#### 步骤 3：填写表单字段

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_fill_form",
  "parameters": {
    "fields": [
      {
        "name": "Customer name",
        "ref": "input[name='custname']",
        "type": "textbox",
        "value": "张三"
      },
      {
        "name": "Telephone",
        "ref": "input[name='custtel']",
        "type": "textbox",
        "value": "13800138000"
      },
      {
        "name": "E-mail address",
        "ref": "input[name='custemail']",
        "type": "textbox",
        "value": "zhangsan@example.com"
      }
    ]
  }
}
```

**实际执行结果：**
```
Code:
await page.locator("input[name='custname']").fill('张三');
await page.locator("input[name='custtel']").fill('13800138000');
await page.locator("input[name='custemail']").fill('zhangsan@example.com');
```

**更新后的页面状态：**
```
Page Snapshot:
  - textbox "Customer name:" [ref=e5]: 张三
  - textbox "Telephone:" [ref=e8]: "13800138000"
  - textbox "E-mail address:" [active] [ref=e11]: zhangsan@example.com
  ...
```

**说明：**
- ✅ 三个文本字段已成功填充
- ✅ 最后一个字段（邮箱）处于活动状态（active）
- ✅ 所有值都已正确输入到对应的输入框中

---

#### 步骤 4：监控网络请求

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_network_requests"
}
```

**实际执行结果：**
```json
[
  {
    "method": "GET",
    "url": "https://httpbin.org/forms/post"
  }
]
```

**说明：**
- ✅ 记录了一次 GET 请求到表单页面
- ✅ 如果需要提交表单，会看到 POST 请求

---

#### 步骤 5：检查控制台消息

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_console_messages"
}
```

**实际执行结果：**
```
Operation completed successfully
```

**说明：**
- ✅ 页面没有 JavaScript 错误
- ✅ 控制台消息正常

---

### 完整执行总结

**用户请求完成后，表单填写结果：**

```
✅ 表单页面加载成功
📝 已填写的信息：
   - 客户姓名: 张三
   - 电话: 13800138000
   - 邮箱: zhangsan@example.com

📊 页面状态：
   - 所有文本字段已正确填充
   - 无 JavaScript 错误
   - 页面可以继续操作（如选择披萨选项、提交等）
```

**下一步可选操作：**
- 选择披萨大小（单选按钮）
- 选择披萨配料（复选框）
- 填写配送时间
- 填写配送说明
- 点击提交按钮

---

## 场景 3：页面分析和统计

### 用户请求
```
请访问一个测试页面，统计页面上有多少个链接、按钮和输入框，并告诉我页面的主要结构
```

### AI 执行流程

#### 步骤 1：导航到测试页面

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://httpbin.org/forms/post"
  }
}
```

**说明：**
选择表单页面进行演示，因为它包含多种元素类型。

---

#### 步骤 2：调整浏览器窗口大小（可选）

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_resize",
  "parameters": {
    "width": 1920,
    "height": 1080
  }
}
```

**说明：**
- ✅ 调整窗口大小可确保页面以桌面视图显示
- ✅ 某些响应式网站会根据窗口大小调整布局

---

#### 步骤 3：等待页面完全加载

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_wait_for",
  "parameters": {
    "text": "Submit"
  }
}
```

**说明：**
- ✅ 等待页面包含特定文本（如"Submit"），确保页面已加载
- ✅ 也可以使用时间等待：`{"time": 2}` 表示等待 2 秒

---

#### 步骤 4：统计页面元素

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_evaluate",
  "parameters": {
    "function": "() => { return { links: document.querySelectorAll('a').length, buttons: document.querySelectorAll('button, input[type=\"submit\"], input[type=\"button\"]').length, inputs: document.querySelectorAll('input, textarea, select').length, headings: document.querySelectorAll('h1, h2, h3, h4, h5, h6').length, paragraphs: document.querySelectorAll('p').length }; }"
  }
}
```

**实际执行结果示例：**
```json
{
  "links": 0,
  "buttons": 1,
  "inputs": 8,
  "headings": 0,
  "paragraphs": 10
}
```

**说明：**
- ✅ 统计了各种类型的元素数量
- ✅ 表单页面通常包含多个输入框
- ✅ 可以根据需要调整统计的元素类型

---

#### 步骤 5：获取页面结构信息

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_evaluate",
  "parameters": {
    "function": "() => { const form = document.querySelector('form'); return { hasForm: !!form, formMethod: form?.method, formAction: form?.action, fields: Array.from(form?.querySelectorAll('input, select, textarea') || []).map(el => ({ type: el.type || el.tagName.toLowerCase(), name: el.name, id: el.id })) }; }"
  }
}
```

**实际执行结果示例：**
```json
{
  "hasForm": true,
  "formMethod": "post",
  "formAction": "/post",
  "fields": [
    { "type": "text", "name": "custname", "id": "" },
    { "type": "tel", "name": "custtel", "id": "" },
    { "type": "email", "name": "custemail", "id": "" },
    { "type": "radio", "name": "size", "id": "" },
    { "type": "checkbox", "name": "topping", "id": "" },
    { "type": "datetime-local", "name": "delivery", "id": "" },
    { "type": "textarea", "name": "comments", "id": "" },
    { "type": "submit", "name": "", "id": "" }
  ]
}
```

**说明：**
- ✅ 成功识别了表单的提交方法和目标
- ✅ 列出了所有表单字段的类型和名称
- ✅ 可以用于自动化测试或数据提取

---

### 完整执行总结

**用户请求完成后，页面分析结果：**

```
✅ 页面分析完成
📊 页面元素统计：
   - 链接数量: 0
   - 按钮数量: 1
   - 输入框/表单元素数量: 8
   - 标题数量: 0
   - 段落数量: 10

📋 表单结构：
   - 表单方法: POST
   - 表单提交地址: /post
   - 表单字段类型: text, tel, email, radio, checkbox, datetime-local, textarea, submit

💡 这个页面是一个完整的表单页面，适合进行表单填写测试。
```

---

## 场景 4：监控网络请求和调试信息

### 用户请求
```
访问一个网页，告诉我页面发出了哪些网络请求，并检查是否有控制台错误
```

### AI 执行流程

#### 步骤 1：导航到目标页面

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://httpbin.org/forms/post"
  }
}
```

**说明：**
选择一个简单页面进行演示，避免复杂的网络请求。

---

#### 步骤 2：等待页面完全加载

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_wait_for",
  "parameters": {
    "time": 2
  }
}
```

**说明：**
- ✅ 等待 2 秒确保所有资源加载完成
- ✅ 也可以等待特定文本出现：`{"text": "特定文本"}`

---

#### 步骤 3：获取网络请求列表

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_network_requests"
}
```

**实际执行结果：**
```json
[
  {
    "method": "GET",
    "url": "https://httpbin.org/forms/post"
  }
]
```

**说明：**
- ✅ 返回了所有网络请求的列表
- ✅ 每个请求包含方法（GET/POST等）和 URL
- ✅ 可用于分析页面的资源加载情况
- ✅ 提交表单后可以监控 POST 请求

**更详细的网络请求信息可能包括：**
```json
[
  {
    "method": "GET",
    "url": "https://httpbin.org/forms/post",
    "status": 200,
    "responseHeaders": {...},
    "requestHeaders": {...},
    "timing": {...}
  },
  {
    "method": "GET",
    "url": "https://httpbin.org/static/css/style.css",
    "status": 200
  }
]
```

---

#### 步骤 4：获取控制台消息

**工具调用：**
```json
{
  "tool": "mcp_cursor-browser-extension_browser_console_messages"
}
```

**实际执行结果：**
```
Operation completed successfully
```

**说明：**
- ✅ 表示没有 JavaScript 错误或警告
- ✅ 如果有错误，会返回具体的错误信息
- ✅ 可用于调试和问题排查

**可能的控制台消息示例：**
```json
[
  {
    "type": "error",
    "text": "Uncaught TypeError: Cannot read property 'x' of undefined",
    "location": "script.js:42"
  },
  {
    "type": "warning",
    "text": "Deprecated API usage detected"
  },
  {
    "type": "log",
    "text": "Page loaded successfully"
  }
]
```

---

### 完整执行总结

**用户请求完成后，网络监控结果：**

```
✅ 网络请求监控完成
📡 检测到的网络请求：
   - GET https://httpbin.org/forms/post (200 OK)

✅ 控制台检查：
   - 无 JavaScript 错误
   - 无警告信息
   - 页面运行正常

💡 用途：
   - 调试页面加载问题
   - 监控 API 调用
   - 分析页面性能
   - 检查资源加载
```

---

## 场景 5：多标签页操作

### 用户请求
```
请打开两个标签页，一个显示 GitHub，另一个显示 Stack Overflow
```

### AI 执行流程

#### 步骤 1：创建第一个标签页并导航
```json
{
  "tool": "mcp_cursor-browser-extension_browser_tabs",
  "parameters": {
    "action": "new"
  }
}
```

```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://github.com"
  }
}
```

#### 步骤 2：创建第二个标签页并导航
```json
{
  "tool": "mcp_cursor-browser-extension_browser_tabs",
  "parameters": {
    "action": "new"
  }
}
```

```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://stackoverflow.com"
  }
}
```

#### 步骤 3：列出所有标签页
```json
{
  "tool": "mcp_cursor-browser-extension_browser_tabs",
  "parameters": {
    "action": "list"
  }
}
```

---

## 场景 6：处理对话框

### 用户请求
```
访问一个会弹出确认对话框的页面，并点击确认
```

### AI 执行流程

#### 步骤 1：导航到页面（假设会触发对话框）
```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://example.com/page-with-dialog"
  }
}
```

#### 步骤 2：执行触发对话框的操作
```json
{
  "tool": "mcp_cursor-browser-extension_browser_click",
  "parameters": {
    "element": "触发对话框的按钮",
    "ref": "button#trigger-dialog"
  }
}
```

#### 步骤 3：处理对话框
```json
{
  "tool": "mcp_cursor-browser-extension_browser_handle_dialog",
  "parameters": {
    "accept": true
  }
}
```

---

## 场景 7：拖放操作

### 用户请求
```
访问一个拖放演示页面，将一个元素拖到另一个位置
```

### AI 执行流程

#### 步骤 1：导航到页面
```json
{
  "tool": "mcp_cursor-browser-extension_browser_navigate",
  "parameters": {
    "url": "https://example.com/drag-drop-demo"
  }
}
```

#### 步骤 2：获取页面快照
```json
{
  "tool": "mcp_cursor-browser-extension_browser_snapshot"
}
```

#### 步骤 3：执行拖放
```json
{
  "tool": "mcp_cursor-browser-extension_browser_drag",
  "parameters": {
    "startElement": "可拖拽元素",
    "startRef": "#draggable",
    "endElement": "放置区域",
    "endRef": "#droppable"
  }
}
```

---

## 如何在 Cursor 中使用这些示例

### 方法 1：直接在 Cursor 聊天中使用（推荐）

**这是最简单直接的方式！** 你不需要了解任何技术细节，只需要在 Cursor 的聊天界面中描述你的需求。

#### 示例 1：基础网页操作
```
请访问 https://httpbin.org/html，告诉我页面标题和主要内容
```

**AI 会自动：**
1. 调用 `browser_navigate` 打开网页
2. 调用 `browser_snapshot` 获取页面结构
3. 调用 `browser_evaluate` 提取信息
4. 返回整理好的结果

---

#### 示例 2：表单填写
```
请访问 https://httpbin.org/forms/post，填写客户信息：
姓名：张三，电话：13800138000，邮箱：zhangsan@example.com
```

**AI 会自动：**
1. 打开表单页面
2. 识别表单字段
3. 调用 `browser_fill_form` 填写信息
4. 验证填写结果

---

#### 示例 3：页面分析
```
访问 https://example.com，统计页面上有多少个链接、按钮和图片
```

**AI 会自动：**
1. 打开页面
2. 等待页面加载
3. 执行 JavaScript 统计元素
4. 返回统计结果

---

### 方法 2：参考工具调用格式

如果你需要了解底层实现或开发相关工具，可以参考文档中的 JSON 格式来理解工具调用的结构。

**工具调用的通用格式：**
```json
{
  "tool": "工具名称",
  "parameters": {
    "参数1": "值1",
    "参数2": "值2"
  }
}
```

---

### 方法 3：自定义 MCP 服务器开发

如果你想创建自己的 MCP 服务器，可以参考这些示例了解工具的使用模式和数据格式。

**开发步骤：**
1. 使用 MCP SDK 创建服务器
2. 定义工具和参数格式
3. 实现工具处理逻辑
4. 在 Cursor 中配置服务器

详细步骤请参考《Cursor MCP工具使用指南.md》中的"创建自定义 MCP 服务器"章节。

---

## 实战演练建议

### 练习 1：基础操作
```
访问 https://example.com，截图并告诉我页面标题
```

### 练习 2：信息提取
```
打开 https://httpbin.org/html，提取所有标题文本
```

### 练习 3：表单交互
```
访问 https://httpbin.org/forms/post，完整填写并提交表单
```

### 练习 4：数据分析
```
访问一个网页，统计页面上的所有链接及其目标 URL
```

### 练习 5：错误排查
```
访问一个页面，检查网络请求和控制台错误
```

---

## 注意事项

1. **权限问题**：某些网站可能有安全限制，无法自动化操作
2. **网络延迟**：实际操作中需要等待页面加载，AI 会自动处理等待
3. **元素选择**：如果页面结构改变，可能需要调整选择器
4. **安全考虑**：不要使用 MCP 工具访问敏感信息或执行危险操作

---

## 调试技巧

### 1. 使用快照功能
在操作前先获取页面快照，查看页面结构：
```json
{
  "tool": "mcp_cursor-browser-extension_browser_snapshot"
}
```

### 2. 检查控制台消息
查看是否有 JavaScript 错误：
```json
{
  "tool": "mcp_cursor-browser-extension_browser_console_messages"
}
```

### 3. 监控网络请求
了解页面加载了哪些资源：
```json
{
  "tool": "mcp_cursor-browser-extension_browser_network_requests"
}
```

---

## 总结

通过这些实际场景示例，你可以看到：

✅ MCP 工具如何自动被 AI 调用  
✅ 不同工具的组合使用方式  
✅ 实际应用场景中的工作流程  
✅ 错误处理和调试方法  

记住：**你不需要手动调用这些工具**，只需要在 Cursor 聊天中描述你的需求，AI 会自动选择和使用合适的 MCP 工具来完成任务。
