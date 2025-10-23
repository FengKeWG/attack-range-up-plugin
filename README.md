# attack-range-up-plugin
MC Bukkit 1.16 插件，AttackRangeUp

## 指令

### /attrange [范围] [威力] [冷却时间]
- 为手持武器添加/修改范围攻击的数值。

### /ptc add/remove [粒子代号]
- 为手持武器添加/移除“横扫粒子”。粒子代号请参考 `config.yml` 注释。

### /ptc redstone Color=#RRGGBB Size=1.0
- 为手持武器设置“红石粒子”的颜色与大小（仅当该物品的横扫粒子为 `REDSTONE` 时生效）。
- 颜色支持两种格式：
  - `#RRGGBB` 例如 `#00FFAA`
  - `R,G,B` 例如 `0,255,170`
- 大小推荐范围：`0.1 ~ 4.0`
- 示例：
  - 只改颜色：`/ptc redstone Color=#00FFAA`
  - 只改大小：`/ptc redstone Size=1.5`
  - 同时设置：`/ptc redstone Color=255,0,0 Size=2.0`

说明：若物品 `lore` 中存在形如 `§a红石粒子设置: Color=#RRGGBB Size=1.0` 的行，则以物品设置为准；否则回落到全局配置（见下）。

### /ptc shape HALF|FULL
- 设置该物品的粒子形状：
  - `HALF`：前方半圆（默认）
  - `FULL`：整圈圆形（前后皆有）

### /ptc trigger RIGHT|SWAP
- 设置该物品的“释放方式”：
  - `RIGHT`：右键释放
  - `SWAP`：切换副手释放（按 F 触发，实际切换会被取消）
- 若物品未设置释放方式，则使用全局默认 `Trigger` 配置。

### /ptc effect add/remove/clear
- 为手持武器配置“命中时施加的药水效果”（可添加多个）。
- 用法：
  - 添加：`/ptc effect add <类型> <秒数> <等级>`（类型支持英文或中文，如 `WITHER` 或 `凋零`）
    - 例：`/ptc effect add 凋零 10 5`（写入 Lore 为：`§a药水效果: 凋零 10秒 等级5`）
  - 移除：`/ptc effect remove <类型>`（支持英文或中文）
  - 清空：`/ptc effect clear`
- Lore 中效果名统一写入中文，后两个数字分别表示“持续时间（秒）”与“等级”。
- Tab 补全：第3参数会提示英文与中文效果名；第4/5参数提供常用秒数与等级建议。

## 配置

- `DefaultParticle`: 当物品未设置“横扫粒子”时的默认粒子；设为 `NULL` 表示无默认。
- `Redstone.Color` 与 `Redstone.Size`: `REDSTONE` 粒子的全局默认颜色与大小，仅在物品未单独设置时生效。
- `Shape`: 全局默认形状，`HALF` 或 `FULL`，若物品已在 `lore` 里设置则以物品为准。
- `Trigger`: 全局默认释放方式，`RIGHT_CLICK`（右键）或 `SWAP_HAND`（切换副手）。若物品的 `lore` 存在 `§a释放方式:` 则以物品为准。
- `SneakRightClick`: 玩家潜行+右键时以 OP 身份执行的命令列表（不影响范围攻击逻辑）。

示例（节选）：

```yaml
DefaultParticle: WITCH_MAGIC

Redstone:
  Color: '#FF0000'   # 也可写成 255,0,0
  Size: 1.0          # 0.1 ~ 4.0

Shape: HALF
Trigger: RIGHT_CLICK

# 潜行+右键触发的 OP 命令（按顺序执行）
SneakRightClick:
  Enabled: false
  Commands:
    - "say Hello {player}"
    # - "spawn"
    # - "give {player} diamond 1"
```

## 物品 Lore 约定（给有需要的服主参考）

- 粒子形状：`§a粒子形状: 半圆|全圆`
- 释放方式：`§a释放方式: 右键|切换副手`
- 红石粒子设置：`§a红石粒子设置: Color=#RRGGBB Size=1.0`
- 药水效果（可多行）：`§a药水效果: <中文类型> <秒数>秒 等级<等级>`
  - 例：`§a药水效果: 凋零 10秒 等级5`