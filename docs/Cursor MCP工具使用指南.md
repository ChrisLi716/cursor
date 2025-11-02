# Cursor MCP 工具使用指南

## 🎯 快速开始（30秒）

**无需任何配置！** 直接在 Cursor 聊天中输入：

```
请访问 https://httpbin.org/html 并截图
```

AI 会自动调用 MCP 工具完成任务！

**就这么简单！** Cursor 内置的浏览器扩展 MCP 工具已经可以使用。

---

## 📖 什么是 MCP？

**MCP (Model Context Protocol)** 是 Cursor 中用于扩展 AI 助手能力的协议，允许 AI 助手通过标准化接口调用外部工具和服务。

### MCP 的核心概念

1. **MCP 服务器**：提供工具和资源的服务端
2. **MCP 客户端**：Cursor 中的 AI 助手，调用 MCP 服务器的工具
3. **工具（Tools）**：MCP 服务器提供的具体功能，如文件操作、API 调用、浏览器控制等

### MCP 的优势

- **扩展能力**：为 AI 助手添加文件系统、网络、浏览器等能力
- **标准化接口**：统一的协议，易于集成
- **安全性**：可控的权限管理
- **灵活性**：可以自定义开发 MCP 服务器

---

## 如何在 Cursor 中配置 MCP

### 1. 配置位置

MCP 配置位于 Cursor 的设置文件中，通常在以下位置：

**Windows:**
```
%APPDATA%\Cursor\User\globalStorage\rooveterinaryinc.roo-cline\settings\cline_mcp_settings.json
```

或者通过 Cursor Settings 界面配置：
```
Settings → Features → MCP Servers
```

### 2. 配置文件格式

MCP 配置文件使用 JSON 格式，示例结构如下：

```json
{
  "mcpServers": {
    "server-name": {
      "command": "node",
      "args": ["path/to/server.js"],
      "env": {
        "API_KEY": "your-api-key"
      }
    }
  }
}
```

### 3. 常见 MCP 服务器配置示例

#### 示例 1：文件系统 MCP 服务器

```json
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "/path/to/allowed/directory"
      ]
    }
  }
}
```

#### 示例 2：GitHub MCP 服务器

```json
{
  "mcpServers": {
    "github": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-github"
      ],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "your-token"
      }
    }
  }
}
```

#### 示例 3：SQLite MCP 服务器

```json
{
  "mcpServers": {
    "sqlite": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-sqlite",
        "--db-path",
        "./database.db"
      ]
    }
  }
}
```

#### 示例 4：浏览器扩展 MCP 服务器（Cursor 内置）

Cursor 内置了浏览器扩展 MCP 服务器，通常自动配置，无需手动设置。

---

## MCP 工具调用示例

### 示例 1：浏览器导航和控制（带实际输出）

以下示例演示如何使用 Cursor 内置的浏览器扩展 MCP 工具，包含真实的执行结果：

#### 场景：打开网页并获取信息

**用户在 Cursor 中输入：**
```
请帮我访问 https://httpbin.org/html，告诉我页面标题和主要内容
```

**AI 助手执行步骤：**

##### 步骤 1：导航到网页
```
工具调用：mcp_cursor-browser-extension_browser_navigate
参数：{ "url": "https://httpbin.org/html" }

实际输出：
Page state:
- Page URL: https://httpbin.org/html
- Page Title: 
- Page Snapshot:
  - heading "Herman Melville - Moby-Dick" [level=1]
  - paragraph: [长文本内容...]
```

##### 步骤 2：获取页面快照
```
工具调用：mcp_cursor-browser-extension_browser_snapshot

实际输出：
Page Snapshot (YAML 格式):
- generic [active]:
  - heading "Herman Melville - Moby-Dick" [level=1] [ref=e2]
  - paragraph [ref=e4]: [完整文本内容...]
```

##### 步骤 3：提取页面信息
```
工具调用：mcp_cursor-browser-extension_browser_evaluate
参数：{
  "function": "() => { return { title: document.title, url: window.location.href, headingCount: document.querySelectorAll('h1, h2, h3').length, paragraphCount: document.querySelectorAll('p').length, textSample: document.body.textContent.substring(0, 200) }; }"
}

实际输出：
{
  "title": "",
  "url": "https://httpbin.org/html",
  "headingCount": 1,
  "paragraphCount": 1,
  "textSample": "\n      Herman Melville - Moby-Dick\n\n      ..."
}
```

**AI 最终返回给用户的结果：**
```
✅ 页面访问成功！

📄 页面信息：
   - URL: https://httpbin.org/html
   - 页面标题: Herman Melville - Moby-Dick
   - 标题元素数量: 1
   - 段落元素数量: 1

📝 主要内容：
   这是一段来自《白鲸记》(Moby-Dick) 的文本内容...
```

**说明：**
- ✅ 用户只需要描述需求，不需要了解技术细节
- ✅ AI 自动选择合适的工具并调用
- ✅ 每个步骤都有实际的执行输出
- ✅ 最终结果以用户友好的格式呈现

### 示例 2：文件系统操作

假设配置了文件系统 MCP 服务器：

```markdown
用户请求：在当前项目目录下创建一个新文件夹 `docs/api`，并在其中创建一个 README.md 文件
```

MCP 工具调用：

```
1. filesystem_create_directory({
     "path": "./docs/api"
   })

2. filesystem_write_file({
     "path": "./docs/api/README.md",
     "content": "# API Documentation\n\n..."
   })
```

### 示例 3：GitHub 操作

假设配置了 GitHub MCP 服务器：

```markdown
用户请求：查看我的 GitHub 仓库列表
```

MCP 工具调用：

```
github_list_repositories({
  "username": "your-username"
})
```

---

## 如何验证 MCP 是否工作

### 方法 1：在 Cursor 中直接使用

直接在 Cursor 聊天中请求需要 MCP 工具的操作，例如：

```
请帮我打开 https://www.example.com 并截图
```

如果 MCP 配置正确，AI 会自动调用浏览器工具。

### 方法 2：查看工具列表

在 Cursor 的 Agent 模式下，可以查看可用的 MCP 工具。通常会在上下文区域显示可用的工具列表。

### 方法 3：检查日志

查看 Cursor 的开发者工具或日志，确认 MCP 服务器是否正常连接。

---

## 创建自定义 MCP 服务器

### 步骤 1：创建 MCP 服务器项目

```bash
mkdir my-mcp-server
cd my-mcp-server
npm init -y
npm install @modelcontextprotocol/sdk
```

### 步骤 2：编写服务器代码

创建 `server.js`：

```javascript
import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import {
  CallToolRequestSchema,
  ListToolsRequestSchema,
} from "@modelcontextprotocol/sdk/types.js";

const server = new Server(
  {
    name: "my-custom-server",
    version: "1.0.0",
  },
  {
    capabilities: {
      tools: {},
    },
  }
);

// 定义工具
server.setRequestHandler(ListToolsRequestSchema, async () => ({
  tools: [
    {
      name: "calculate",
      description: "执行数学计算",
      inputSchema: {
        type: "object",
        properties: {
          expression: {
            type: "string",
            description: "要计算的数学表达式",
          },
        },
        required: ["expression"],
      },
    },
  ],
}));

// 处理工具调用
server.setRequestHandler(CallToolRequestSchema, async (request) => {
  const { name, arguments: args } = request.params;

  if (name === "calculate") {
    try {
      // 安全地评估数学表达式
      const result = eval(args.expression);
      return {
        content: [
          {
            type: "text",
            text: `计算结果: ${result}`,
          },
        ],
      };
    } catch (error) {
      return {
        content: [
          {
            type: "text",
            text: `计算错误: ${error.message}`,
          },
        ],
        isError: true,
      };
    }
  }

  throw new Error(`未知工具: ${name}`);
});

// 启动服务器
async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
  console.error("MCP 服务器已启动");
}

main().catch(console.error);
```

### 步骤 3：在 Cursor 中配置

在 Cursor 的 MCP 配置文件中添加：

```json
{
  "mcpServers": {
    "my-custom-server": {
      "command": "node",
      "args": ["D:/code-cursor/my-mcp-server/server.js"]
    }
  }
}
```

### 步骤 4：测试使用

在 Cursor 聊天中：

```
请使用 calculate 工具计算 123 * 456
```

---

## 常用 MCP 服务器推荐

### 1. **文件系统服务器**
- 包名：`@modelcontextprotocol/server-filesystem`
- 功能：文件读写、目录操作
- 安装：`npx -y @modelcontextprotocol/server-filesystem`

### 2. **GitHub 服务器**
- 包名：`@modelcontextprotocol/server-github`
- 功能：GitHub API 操作
- 安装：`npx -y @modelcontextprotocol/server-github`

### 3. **SQLite 服务器**
- 包名：`@modelcontextprotocol/server-sqlite`
- 功能：SQLite 数据库操作
- 安装：`npx -y @modelcontextprotocol/server-sqlite`

### 4. **Brave Search 服务器**
- 包名：`@modelcontextprotocol/server-brave-search`
- 功能：网络搜索
- 安装：`npx -y @modelcontextprotocol/server-brave-search`

### 5. **PostgreSQL 服务器**
- 包名：`@modelcontextprotocol/server-postgres`
- 功能：PostgreSQL 数据库操作
- 安装：`npx -y @modelcontextprotocol/server-postgres`

---

## 故障排除

### 问题 1：MCP 服务器无法启动

**解决方案：**
1. 检查配置文件路径是否正确
2. 确认命令和参数是否正确
3. 查看 Cursor 的日志输出

### 问题 2：工具调用失败

**解决方案：**
1. 验证 MCP 服务器是否正常运行
2. 检查工具名称和参数是否正确
3. 查看错误消息获取详细信息

### 问题 3：权限问题

**解决方案：**
1. 确保 MCP 服务器有足够的权限访问所需资源
2. 检查环境变量配置
3. 验证 API 密钥或令牌是否有效

---

## 最佳实践

### 1. 安全性
- 限制 MCP 服务器的访问范围
- 使用环境变量管理敏感信息（API 密钥等）
- 定期更新 MCP 服务器包

### 2. 性能
- 避免频繁调用耗时的工具
- 使用缓存机制减少重复操作
- 合理设置超时时间

### 3. 可维护性
- 使用有意义的服务器名称
- 为工具添加清晰的描述
- 记录自定义 MCP 服务器的配置

---

## 总结

MCP 工具为 Cursor 提供了强大的扩展能力，通过配置和使用 MCP 服务器，可以让 AI 助手执行更多类型的任务。关键步骤包括：

1. ✅ 理解 MCP 的基本概念
2. ✅ 配置 MCP 服务器
3. ✅ 验证配置是否正确
4. ✅ 在聊天中直接使用（AI 会自动调用）
5. ✅ 根据需要创建自定义服务器

通过 MCP，Cursor 的 AI 助手可以：
- 🌐 浏览网页并交互
- 📁 操作文件系统
- 🗄️ 访问数据库
- 🔍 执行网络搜索
- 🔧 调用外部 API
- 以及其他更多功能...

---

## 参考资源

- [Model Context Protocol 官方文档](https://modelcontextprotocol.io/)
- [Cursor MCP 文档](https://cursor.com/docs/mcp)
- [MCP 服务器示例](https://github.com/modelcontextprotocol/servers)

