Here’s how to actually use it right:

1. Set 5-10 clear project rules upfront so Cursor knows your structure and constraints. Try /generate rules for existing codebases.
2. Be specific in prompts. Spell out tech stack, behavior, and constraints like a mini spec.
3. Work file by file; generate, test, and review in small, focused chunks.
4. Write tests first, lock them, and generate code until all tests pass.
5. Always review AI output and hard‑fix anything that breaks, then tell Cursor to use them as examples.
6. Use @ file, @ folders, @ git to scope Cursor’s attention to the right parts of your codebase.
7. Keep design docs and checklists in .cursor/ so the agent has full context on what to do next.
8. If code is wrong, just write it yourself. Cursor learns faster from edits than explanations.
9. Use chat history to iterate on old prompts without starting over.
10. Choose models intentionally. Gemini for precision, Claude for breadth.
11. In new or unfamiliar stacks, paste in link to documentation. Make Cursor explain all errors and fixes line by line.
12.Let big projects index overnight and limit context scope to keep performance snappy.

Structure and control wins (for now)
Treat Cursor agent like a powerful junior — it can go far, fast, if you show it the way

https://www.bilibili.com/video/BV1nfVozjEkH?spm_id_from=333.788.player.switch&vd_source=d5219f7796f600867d78e4b27b0f6350