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

## 配置

- `DefaultParticle`: 当物品未设置“横扫粒子”时的默认粒子；设为 `NULL` 表示无默认。
- `Redstone.Color` 与 `Redstone.Size`: `REDSTONE` 粒子的全局默认颜色与大小，仅在物品未单独设置时生效。

示例（节选）：

```yaml
DefaultParticle: WITCH_MAGIC

Redstone:
  Color: '#FF0000'   # 也可写成 255,0,0
  Size: 1.0          # 0.1 ~ 4.0
```