package org.windguest.attackrangeup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Particle;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AttackRangeUp extends JavaPlugin implements Listener {
    private HashMap<String, String> particleName = new HashMap<>();
    private List<Player> cdlist = new ArrayList<>();
    private Particle.DustOptions redstoneDustOptions;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        File configFile;
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.saveDefaultConfig();
        }
        this.reloadConfig();
        // 加载红石粒子颜色与大小
        loadRedstoneDustOptions();
        // 初始化粒子名称映射
        particleName.put("AMBIENT_ENTITY_EFFECT", "§a环境实体效果");
        particleName.put("ANGRY_VILLAGER", "§c愤怒的村民");
        particleName.put("BARRIER", "§e障壁");
        particleName.put("BLOCK", "§f方块");
        particleName.put("BLOCK_CRACK", "§6方块破碎");
        particleName.put("BLOCK_DUST", "§6方块尘埃");
        particleName.put("BUBBLE", "§b气泡");
        particleName.put("BUBBLE_COLUMN_UP", "§b向上气泡柱");
        particleName.put("CLOUD", "§b云");
        particleName.put("CRIT", "§e暴击");
        particleName.put("CRIT_MAGIC", "§5魔法暴击");
        particleName.put("DAMAGE_INDICATOR", "§c伤害指示");
        particleName.put("DRAGON_BREATH", "§5龙息");
        particleName.put("DRIP_WATER", "§3水滴落");
        particleName.put("DRIP_LAVA", "§c岩浆滴落");
        particleName.put("DUST", "§6彩尘");
        particleName.put("DUST_COLOR_TRANSITION", "§6彩尘颜色过渡");
        particleName.put("EFFECT", "§f效果");
        particleName.put("ELDER_GUARDIAN_TELEPORT", "§5长者守卫者传送");
        particleName.put("ENCHANTMENT_TABLE", "§6附魔台效果");
        particleName.put("ENCHANT", "§6附魔");
        particleName.put("END_ROD", "§5末地杆");
        particleName.put("ENTITY_EFFECT", "§f实体效果");
        particleName.put("EXPLOSION_NORMAL", "§c正常爆炸");
        particleName.put("EXPLOSION_LARGE", "§c大爆炸");
        particleName.put("EXPLOSION_HUGE", "§6巨大爆炸");
        particleName.put("FALLING_DUST", "§7下落尘埃");
        particleName.put("FIREWORKS_SPARK", "§c烟花发射");
        particleName.put("FIREWORK", "§c烟花");
        particleName.put("FLAME", "§c火焰");
        particleName.put("HAPPY_VILLAGER", "§a开心的村民");
        particleName.put("HEART", "§c爱心");
        particleName.put("INSTANT_EFFECT", "§f即时效果");
        particleName.put("ITEM_CRACK", "§6物品破碎");
        particleName.put("ITEM_TAKE", "§6物品获取");
        particleName.put("LAVA", "§c岩浆");
        particleName.put("LARGE_SMOKE", "§7大烟雾");
        particleName.put("MAGIC_CRIT", "§5魔法暴击");
        particleName.put("MOB_APPEARANCE", "§a怪物出现");
        particleName.put("MOBSPAWNER_FLAMES", "§c刷怪笼火焰");
        particleName.put("NOTE", "§b音符");
        particleName.put("PORTAL", "§8传送门");
        particleName.put("RAIN", "§9雨");
        particleName.put("REDSTONE", "§c红石尘埃");
        particleName.put("SLIME", "§a粘液");
        particleName.put("SMOKE_NORMAL", "§7正常烟雾");
        particleName.put("SMOKE_LARGE", "§7大烟雾");
        particleName.put("SPELL", "§6魔法");
        particleName.put("SPELL_INSTANT", "§6即时魔法");
        particleName.put("SPELL_MOB", "§6怪物魔法");
        particleName.put("SPELL_MOB_AMBIENT", "§6怪物环境魔法");
        particleName.put("SPELL_WITCH", "§6女巫魔法");
        particleName.put("SPIT", "§6吐痰");
        particleName.put("SQUID_INK", "§6鱿鱼墨水");
        particleName.put("SNEEZE", "§6打喷嚏");
        particleName.put("CAMPFIRE_COSY_SMOKE", "§6篝火舒适烟雾");
        particleName.put("CAMPFIRE_SIGNAL_SMOKE", "§6篝火信号烟雾");
        particleName.put("COMPOSTER", "§6堆肥器效果");
        particleName.put("FLASH", "§6闪光");
        particleName.put("FALLING_LAVA", "§c下落岩浆");
        particleName.put("FALLING_WATER", "§3下落水");
        particleName.put("FALLING_HONEY", "§6下落蜂蜜");
        particleName.put("FALLING_NECTAR", "§6下落花蜜");
        particleName.put("FALLING_OBSIDIAN_TEAR", "§6下落黑曜石泪");
        particleName.put("LANDING_LAVA", "§c岩浆着陆");
        particleName.put("LANDING_HONEY", "§6蜂蜜着陆");
        particleName.put("LANDING_OBSIDIAN_TEAR", "§6黑曜石泪着陆");
        particleName.put("LANDING_WATER", "§3水着陆");
        particleName.put("REVERSE_PORTAL", "§6反转传送门");
        particleName.put("SOUL_FIRE_FLAME", "§6灵魂火焰");
        particleName.put("SOUL", "§6灵魂");
        particleName.put("WHITE_ASH", "§6白灰");
        particleName.put("WITHER_ROSE", "§5凋零玫瑰");
        particleName.put("WITHER", "§5凋零");
        particleName.put("LEGACY_BLOCK_CRACK", "§6旧版方块破碎");
        particleName.put("LEGACY_BLOCK_DUST", "§6旧版方块尘埃");
        particleName.put("LEGACY_FALLING_DUST", "§6旧版下落尘埃");
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        // 潜行 + 右键：以 OP 身份执行配置命令（与范围攻击无关）
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getPlayer().isSneaking()) {
                if (handleSneakRightClick(e.getPlayer())) {
                    return;
                }
            }
        }
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
                && !cdlist.contains(e.getPlayer()) && e.hasItem()) {
            ItemMeta itemMeta = e.getItem().getItemMeta();
            if (itemMeta != null && itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                if (!shouldTriggerOnRightClick(lore)) return;
                tryActivateFromLore(e.getPlayer(), lore);
            }
        }
    }

    private String getKeyByValue(HashMap<String, String> map, String value) {
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return "noFound";
    }

    public void SweepAttack(Player p, Double range, Double damage) {
        for (Entity e : p.getNearbyEntities(range, range, range)) {
            if (!(e instanceof LivingEntity) || !this.inSight(p, e)) continue;
            LivingEntity le = (LivingEntity) e;
            if (le.equals(p)) continue; // 避免攻击自己
            le.damage(damage, p);
        }
    }

    public void SweepAttack(Player p, Double range, Double damage, List<PotionEffect> effects) {
        for (Entity e : p.getNearbyEntities(range, range, range)) {
            if (!(e instanceof LivingEntity) || !this.inSight(p, e)) continue;
            LivingEntity le = (LivingEntity) e;
            if (le.equals(p)) continue;
            if (effects != null && !effects.isEmpty()) {
                le.addPotionEffects(effects);
            }
            le.damage(damage, p);
        }
    }

    public boolean inSight(Entity e1, Entity e2) {
        return e1.getLocation().getDirection().normalize().dot(e2.getLocation().toVector().subtract(e1.getLocation().toVector()).normalize()) > 0.5;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("attrange")) {
            if (args.length == 3) {
                if (isNumeric(args[0]) && Double.valueOf(args[1]) >= 0.0 && Double.valueOf(args[2]) >= 0.0
                        && sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission("attrange.add") && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                        ItemMeta im = player.getInventory().getItemInMainHand().getItemMeta();
                        if (im != null && im.hasLore()) {
                            List<String> lore = im.getLore();
                            boolean updated = false;
                            for (int i = 0; i < lore.size(); i++) {
                                String s = lore.get(i);
                                if (s.startsWith("§a范围: §6")) {
                                    lore.set(i, "§a范围: §6" + args[0] + " §c威力: §6" + args[1] + " §b冷却: §6" + args[2]);
                                    updated = true;
                                    break;
                                }
                            }
                            if (!updated) {
                                lore.add("§a范围: §6" + args[0] + " §c威力: §6" + args[1] + " §b冷却: §6" + args[2]);
                            }
                            im.setLore(lore);
                            player.getInventory().getItemInMainHand().setItemMeta(im);
                            sender.sendMessage("§a成功修改手中武器的范围攻击能力");
                            return true;
                        }
                        // 如果物品没有Lore
                        List<String> lore = new ArrayList<>();
                        lore.add("§a范围: §6" + args[0] + " §c威力: §6" + args[1] + " §b冷却: §6" + args[2]);
                        im.setLore(lore);
                        player.getInventory().getItemInMainHand().setItemMeta(im);
                        sender.sendMessage("§a成功为手中武器添加范围攻击能力");
                        return true;
                    }
                    if (sender instanceof Player) {
                        sender.sendMessage("§c手持物品再使用该指令");
                    } else {
                        sender.sendMessage("§c你没有attrange.add权限");
                    }
                    return true;
                }
                if (sender instanceof Player) {
                    sender.sendMessage("§c正确格式：/attrange [范围] [威力] [冷却时间]");
                    return true;
                }
                sender.sendMessage("§b恭喜你，现在你的控制台拥有了范围攻击能力");
                return true;
            }
            sender.sendMessage("§c正确格式：/attrange [范围] [威力] [冷却时间]");
            return true;
        }
        if (label.equalsIgnoreCase("ptc")) {
            if (sender.hasPermission("ptc.change")) {
                if (args.length >= 2 && sender instanceof Player
                        && ((Player) sender).getInventory().getItemInMainHand().getType() != Material.AIR) {
                    Player player = (Player) sender;
                    ItemMeta im = player.getInventory().getItemInMainHand().getItemMeta();
                    if (im != null) {
                        if (args[0].equalsIgnoreCase("add")) {
                            if (particleName.containsKey(args[1])) {
                                List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
                                lore.add("§a横扫粒子: " + particleName.get(args[1]));
                                im.setLore(lore);
                                player.getInventory().getItemInMainHand().setItemMeta(im);
                                sender.sendMessage("§a成功添加" + particleName.get(args[1]) + "§a横扫粒子效果");
                            } else {
                                sender.sendMessage("§c没有找到您输入的粒子代号，请到config.yml查看粒子代号");
                            }
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            if (particleName.containsKey(args[1])) {
                                List<String> lore = im.hasLore() ? new ArrayList<>(im.getLore()) : new ArrayList<>();
                                boolean found = false;
                                String target = "§a横扫粒子: " + particleName.get(args[1]);
                                if (lore.remove(target)) {
                                    found = true;
                                }
                                im.setLore(lore);
                                player.getInventory().getItemInMainHand().setItemMeta(im);
                                if (found) {
                                    sender.sendMessage("§a已去除" + particleName.get(args[1]) + "§a的横扫粒子效果");
                                } else {
                                    sender.sendMessage("§c没有找到名为" + particleName.get(args[1]) + "§c的横扫粒子效果");
                                }
                            } else {
                                sender.sendMessage("§c没有找到您输入的粒子代号，请到config.yml查看粒子代号");
                            }
                        } else if (args[0].equalsIgnoreCase("redstone")) {
                            // /ptc redstone Color=<#RRGGBB|R,G,B> Size=<float>
                            List<String> lore = im.hasLore() ? new ArrayList<>(im.getLore()) : new ArrayList<>();
                            String colorToken = null;
                            String sizeToken = null;
                            for (int i = 1; i < args.length; i++) {
                                String t = args[i];
                                if (t.startsWith("Color=")) colorToken = t.substring(6);
                                else if (t.startsWith("Size=")) sizeToken = t.substring(5);
                            }
                            if (colorToken == null && sizeToken == null) {
                                sender.sendMessage("§c正确格式：/ptc redstone Color=#RRGGBB Size=1.0");
                            } else {
                                // 先移除旧设置行
                                String prefix = "§a红石粒子设置: ";
                                lore.removeIf(line -> line.startsWith(prefix));
                                // 解析并存放
                                Color color = colorToken != null ? parseColor(colorToken) : null;
                                Float size = null;
                                if (sizeToken != null) {
                                    try { size = Float.parseFloat(sizeToken); } catch (Exception ignored) {}
                                }
                                StringBuilder sb = new StringBuilder(prefix);
                                if (color != null) {
                                    String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
                                    sb.append("Color=").append(hex).append(' ');
                                }
                                if (size != null) {
                                    float clamped = Math.max(0.01F, Math.min(size, 4.0F));
                                    sb.append("Size=").append(clamped);
                                }
                                String line = sb.toString().trim();
                                lore.add(line);
                                im.setLore(lore);
                                player.getInventory().getItemInMainHand().setItemMeta(im);
                                sender.sendMessage("§a已更新红石粒子颜色/大小设置");
                            }
                        } else if (args[0].equalsIgnoreCase("shape")) {
                            // /ptc shape HALF|FULL
                            if (args.length >= 2) {
                                String mode = args[1].toUpperCase();
                                if (mode.equals("HALF") || mode.equals("FULL") || mode.equals("CIRCLE") || mode.equals("ROUND")) {
                                    List<String> lore = im.hasLore() ? new ArrayList<>(im.getLore()) : new ArrayList<>();
                                    String prefix = "§a粒子形状: ";
                                    lore.removeIf(line -> line.startsWith(prefix));
                                    String normalized = (mode.equals("CIRCLE") || mode.equals("ROUND")) ? "FULL" : mode;
                                    lore.add(prefix + normalized);
                                    im.setLore(lore);
                                    player.getInventory().getItemInMainHand().setItemMeta(im);
                                    sender.sendMessage("§a已更新粒子形状为 " + normalized);
                                } else {
                                    sender.sendMessage("§c正确格式：/ptc shape HALF|FULL");
                                }
                            } else {
                                sender.sendMessage("§c正确格式：/ptc shape HALF|FULL");
                            }
                        } else if (args[0].equalsIgnoreCase("trigger")) {
                            // /ptc trigger RIGHT|SWAP
                            if (args.length >= 2) {
                                String mode = args[1].toUpperCase();
                                String normalized;
                                if (mode.equals("RIGHT") || mode.equals("RIGHT_CLICK") || mode.equals("右键")) {
                                    normalized = "RIGHT";
                                } else if (mode.equals("SWAP") || mode.equals("SWAP_HAND") || mode.equals("OFFHAND") || mode.equals("切换副手")) {
                                    normalized = "SWAP";
                                } else {
                                    sender.sendMessage("§c正确格式：/ptc trigger RIGHT|SWAP");
                                    return true;
                                }
                                List<String> lore = im.hasLore() ? new ArrayList<>(im.getLore()) : new ArrayList<>();
                                String prefix = "§a释放方式: ";
                                lore.removeIf(line -> line.startsWith(prefix));
                                lore.add(prefix + normalized);
                                im.setLore(lore);
                                player.getInventory().getItemInMainHand().setItemMeta(im);
                                sender.sendMessage("§a已将释放方式设置为 " + normalized);
                            } else {
                                sender.sendMessage("§c正确格式：/ptc trigger RIGHT|SWAP");
                            }
                        } else if (args[0].equalsIgnoreCase("effect")) {
                            // /ptc effect add <TYPE> <SECONDS> <LEVEL>
                            // /ptc effect remove <TYPE>
                            // /ptc effect clear
                            if (args.length >= 2) {
                                String sub = args[1].toLowerCase();
                                List<String> lore = im.hasLore() ? new ArrayList<>(im.getLore()) : new ArrayList<>();
                                String prefix = "§a药水效果: ";
                                if (sub.equals("add")) {
                                    if (args.length >= 5) {
                                        String typeName = args[2].toUpperCase();
                                        PotionEffectType type = PotionEffectType.getByName(typeName);
                                        if (type == null) {
                                            sender.sendMessage("§c无效的药水类型：" + args[2]);
                                            return true;
                                        }
                                        int seconds;
                                        int level;
                                        try {
                                            seconds = Integer.parseInt(args[3]);
                                            level = Integer.parseInt(args[4]);
                                        } catch (Exception ex) {
                                            sender.sendMessage("§c正确格式：/ptc effect add <类型> <秒数> <等级>");
                                            return true;
                                        }
                                        seconds = Math.max(1, seconds);
                                        level = Math.max(1, level);
                                        // 去重该类型
                                        String typeKey = type.getName();
                                        lore.removeIf(line -> line.startsWith(prefix + typeKey + " "));
                                        lore.add(prefix + typeKey + " " + seconds + " " + level);
                                        im.setLore(lore);
                                        player.getInventory().getItemInMainHand().setItemMeta(im);
                                        sender.sendMessage("§a已添加药水效果：" + typeKey + " " + seconds + "s 等级" + level);
                                    } else {
                                        sender.sendMessage("§c正确格式：/ptc effect add <类型> <秒数> <等级>");
                                    }
                                } else if (sub.equals("remove")) {
                                    if (args.length >= 3) {
                                        String typeName = args[2].toUpperCase();
                                        PotionEffectType type = PotionEffectType.getByName(typeName);
                                        if (type == null) {
                                            sender.sendMessage("§c无效的药水类型：" + args[2]);
                                            return true;
                                        }
                                        String typeKey = type.getName();
                                        boolean removed = lore.removeIf(line -> line.startsWith(prefix + typeKey + " "));
                                        im.setLore(lore);
                                        player.getInventory().getItemInMainHand().setItemMeta(im);
                                        if (removed) sender.sendMessage("§a已移除药水效果：" + typeKey);
                                        else sender.sendMessage("§c未找到药水效果：" + typeKey);
                                    } else {
                                        sender.sendMessage("§c正确格式：/ptc effect remove <类型>");
                                    }
                                } else if (sub.equals("clear")) {
                                    boolean removedAny = lore.removeIf(line -> line.startsWith(prefix));
                                    im.setLore(lore);
                                    player.getInventory().getItemInMainHand().setItemMeta(im);
                                    sender.sendMessage(removedAny ? "§a已清空所有药水效果" : "§e未找到任何药水效果");
                                } else {
                                    sender.sendMessage("§c正确格式：/ptc effect add/remove/clear ...");
                                }
                            } else {
                                sender.sendMessage("§c正确格式：/ptc effect add/remove/clear ...");
                            }
                        } else {
                            sender.sendMessage("§c正确格式：/ptc add/remove [粒子代号] 或 /ptc redstone Color=#RRGGBB Size=1.0 或 /ptc shape HALF|FULL 或 /ptc trigger RIGHT|SWAP 或 /ptc effect add/remove/clear");
                        }
                    } else {
                        sender.sendMessage("§c手持物品无法添加粒子效果");
                    }
                } else if (sender instanceof Player) {
                    sender.sendMessage("§c正确格式：/ptc add/remove [粒子代号] 或 /ptc redstone Color=#RRGGBB Size=1.0 或 /ptc shape HALF|FULL 或 /ptc trigger RIGHT|SWAP 或 /ptc effect add/remove/clear");
                } else {
                    sender.sendMessage("§c手持物品再使用该指令");
                }
            } else {
                sender.sendMessage("§c你没有sp.change权限");
            }
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }

    public void particleCreate(Location loc, double radii, Particle type) {
        double i = 1.5;
        while (i <= radii) {
            double start = 90.0;
            double end = 270.0;
            double o = start;
            while (o <= end) {
                double radians = Math.toRadians(o);
                double pitchRadians = Math.toRadians(90.0 - loc.getPitch() + 90.0);
                double yawRadians = Math.toRadians(loc.getYaw());

                double x = i * (Math.cos(radians) * (-Math.cos(pitchRadians) * Math.sin(yawRadians)) +
                        Math.sin(radians) * -Math.sin(Math.toRadians(loc.getYaw() - 90.0)));
                double y = 0.8 + i * (Math.cos(radians) * Math.sin(pitchRadians));
                double z = i * (Math.cos(radians) * (Math.cos(pitchRadians) * Math.cos(yawRadians)) +
                        Math.sin(radians) * Math.cos(Math.toRadians(loc.getYaw() - 90.0)));

                Location particleLoc = loc.clone().add(x, y, z);
                if (type == Particle.REDSTONE && redstoneDustOptions != null) {
                    // 使用自定义颜色与大小
                    loc.getWorld().spawnParticle(type, particleLoc, 1, 0, 0, 0, 0, redstoneDustOptions);
                } else {
                    loc.getWorld().spawnParticle(type, particleLoc, 0, 0, 0, 0, 1);
                }
                o += 1.0;
            }
            i += 2.0;
        }
    }

    public void particleCreate(Location loc, double radii, Particle type, Particle.DustOptions perItemOptions) {
        double i = 1.5;
        while (i <= radii) {
            double start = 90.0;
            double end = 270.0;
            double o = start;
            while (o <= end) {
                double radians = Math.toRadians(o);
                double pitchRadians = Math.toRadians(90.0 - loc.getPitch() + 90.0);
                double yawRadians = Math.toRadians(loc.getYaw());

                double x = i * (Math.cos(radians) * (-Math.cos(pitchRadians) * Math.sin(yawRadians)) +
                        Math.sin(radians) * -Math.sin(Math.toRadians(loc.getYaw() - 90.0)));
                double y = 0.8 + i * (Math.cos(radians) * Math.sin(pitchRadians));
                double z = i * (Math.cos(radians) * (Math.cos(pitchRadians) * Math.cos(yawRadians)) +
                        Math.sin(radians) * Math.cos(Math.toRadians(loc.getYaw() - 90.0)));

                Location particleLoc = loc.clone().add(x, y, z);
                if (type == Particle.REDSTONE) {
                    Particle.DustOptions optionsToUse = perItemOptions != null ? perItemOptions : redstoneDustOptions;
                    if (optionsToUse == null) {
                        optionsToUse = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.0F);
                    }
                    loc.getWorld().spawnParticle(type, particleLoc, 1, 0, 0, 0, 0, optionsToUse);
                } else {
                    loc.getWorld().spawnParticle(type, particleLoc, 0, 0, 0, 0, 1);
                }
                o += 1.0;
            }
            i += 2.0;
        }
    }

    public void particleCreate(Location loc, double radii, Particle type, Particle.DustOptions perItemOptions, boolean fullCircle) {
        double i = 1.5;
        while (i <= radii) {
            double start = fullCircle ? 0.0 : 90.0;
            double end = fullCircle ? 360.0 : 270.0;
            double o = start;
            while (o <= end) {
                double radians = Math.toRadians(o);
                double pitchRadians = Math.toRadians(90.0 - loc.getPitch() + 90.0);
                double yawRadians = Math.toRadians(loc.getYaw());

                double x = i * (Math.cos(radians) * (-Math.cos(pitchRadians) * Math.sin(yawRadians)) +
                        Math.sin(radians) * -Math.sin(Math.toRadians(loc.getYaw() - 90.0)));
                double y = 0.8 + i * (Math.cos(radians) * Math.sin(pitchRadians));
                double z = i * (Math.cos(radians) * (Math.cos(pitchRadians) * Math.cos(yawRadians)) +
                        Math.sin(radians) * Math.cos(Math.toRadians(loc.getYaw() - 90.0)));

                Location particleLoc = loc.clone().add(x, y, z);
                if (type == Particle.REDSTONE) {
                    Particle.DustOptions optionsToUse = perItemOptions != null ? perItemOptions : redstoneDustOptions;
                    if (optionsToUse == null) {
                        optionsToUse = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.0F);
                    }
                    loc.getWorld().spawnParticle(type, particleLoc, 1, 0, 0, 0, 0, optionsToUse);
                } else {
                    loc.getWorld().spawnParticle(type, particleLoc, 0, 0, 0, 0, 1);
                }
                o += 1.0;
            }
            i += 2.0;
        }
    }

    private boolean getPerItemFullCircle(List<String> lore) {
        if (lore == null) return false;
        // Lore 格式: "§a粒子形状: FULL" 或 "§a粒子形状: HALF"
        for (String line : lore) {
            if (line.startsWith("§a粒子形状: ")) {
                String val = line.substring("§a粒子形状: ".length()).trim().toUpperCase();
                return val.equals("FULL") || val.equals("CIRCLE") || val.equals("ROUND");
            }
        }
        // 默认从配置读取
        String def = this.getConfig().getString("Shape", "HALF").toUpperCase();
        return def.equals("FULL") || def.equals("CIRCLE") || def.equals("ROUND");
    }

    private String getPerItemTriggerMode(List<String> lore) {
        if (lore == null) return null;
        String prefix = "§a释放方式: ";
        for (String line : lore) {
            if (line.startsWith(prefix)) {
                String mode = line.substring(prefix.length()).trim().toUpperCase();
                // 同义词归一化
                if (mode.equals("RIGHT") || mode.equals("RIGHT_CLICK") || mode.equals("右键")) return "RIGHT";
                if (mode.equals("SWAP") || mode.equals("SWAP_HAND") || mode.equals("OFFHAND") || mode.equals("切换副手")) return "SWAP";
                return mode;
            }
        }
        return null;
    }

    private boolean shouldTriggerOnRightClick(List<String> lore) {
        String mode = getPerItemTriggerMode(lore);
        if (mode == null) {
            String def = this.getConfig().getString("Trigger", "RIGHT_CLICK").toUpperCase();
            return def.equals("RIGHT") || def.equals("RIGHT_CLICK");
        }
        return mode.equals("RIGHT");
    }

    private boolean shouldTriggerOnSwap(List<String> lore) {
        String mode = getPerItemTriggerMode(lore);
        if (mode == null) {
            String def = this.getConfig().getString("Trigger", "RIGHT_CLICK").toUpperCase();
            return def.equals("SWAP") || def.equals("SWAP_HAND") || def.equals("OFFHAND");
        }
        return mode.equals("SWAP");
    }

    private void tryActivateFromLore(Player player, List<String> lore) {
        for (String s : lore) {
            if (!s.startsWith("§a范围: §6")) continue;
            String[] parts = s.split(" §c威力: §6");
            if (parts.length < 2) continue;
            Double range = Double.valueOf(parts[0].split("§a范围: §6")[1]);
            String[] powerParts = parts[1].split(" §b冷却: §6");
            if (powerParts.length < 2) continue;
            Double damage = Double.valueOf(powerParts[0]);
            Double cooldown = Double.valueOf(powerParts[1]);

            boolean found = false;
            boolean fullCircle = getPerItemFullCircle(lore);
            for (String s1 : lore) {
                if (s1.startsWith("§a横扫粒子: ")) {
                    found = true;
                    String particleKey = getKeyByValue(particleName, s1.split("§a横扫粒子: ")[1]);
                    if (particleKey.equals("noFound")) continue;
                    Particle particleType;
                    try {
                        particleType = Particle.valueOf(particleKey);
                    } catch (IllegalArgumentException ex) {
                        particleType = Particle.EXPLOSION_NORMAL; // 默认粒子效果
                    }
                    Particle.DustOptions options = null;
                    if (particleType == Particle.REDSTONE) {
                        options = getPerItemDustOptions(lore);
                    }
                    particleCreate(player.getLocation(), range, particleType, options, fullCircle);
                }
            }
            if (!found && !this.getConfig().getString("DefaultParticle").equalsIgnoreCase("NULL")) {
                String defaultParticleKey = getKeyByValue(particleName, this.getConfig().getString("DefaultParticle"));
                if (!defaultParticleKey.equals("noFound")) {
                    Particle defaultParticleType;
                    try {
                        defaultParticleType = Particle.valueOf(defaultParticleKey);
                    } catch (IllegalArgumentException ex) {
                        defaultParticleType = Particle.EXPLOSION_NORMAL; // 默认粒子效果
                    }
                    if (defaultParticleType == Particle.REDSTONE) {
                        Particle.DustOptions options = getPerItemDustOptions(lore);
                        particleCreate(player.getLocation(), range, defaultParticleType, options, fullCircle);
                    } else {
                        particleCreate(player.getLocation(), range, defaultParticleType, null, fullCircle);
                    }
                }
            }
            List<PotionEffect> effects = getPerItemPotionEffects(lore);
            SweepAttack(player, range, damage, effects);
            cdlist.add(player);
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    cdlist.remove(player);
                }
            };
            Long cdTicks = (long) (cooldown * 20.0);
            task.runTaskLater(this, cdTicks);
            break;
        }
    }

    @EventHandler
    public void onSwapHand(final PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        if (cdlist.contains(player)) return;
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta == null || !itemMeta.hasLore()) return;
        List<String> lore = itemMeta.getLore();
        if (!shouldTriggerOnSwap(lore)) return;
        e.setCancelled(true);
        tryActivateFromLore(player, lore);
    }

    private Particle.DustOptions getPerItemDustOptions(List<String> lore) {
        if (lore == null) return null;
        // Lore 格式: "§a红石粒子设置: Color=#RRGGBB Size=1.0"
        for (String line : lore) {
            if (line.startsWith("§a红石粒子设置: ")) {
                String conf = line.substring("§a红石粒子设置: ".length()).trim();
                String colorStr = null;
                Float size = null;
                String[] tokens = conf.split(" ");
                for (String token : tokens) {
                    if (token.startsWith("Color=")) {
                        colorStr = token.substring("Color=".length());
                    } else if (token.startsWith("Size=")) {
                        try {
                            size = Float.parseFloat(token.substring("Size=".length()));
                        } catch (Exception ignored) {}
                    }
                }
                Color color = colorStr != null ? parseColor(colorStr) : null;
                if (color == null) color = Color.fromRGB(255, 0, 0);
                float finalSize = size != null ? Math.max(0.01F, Math.min(size, 4.0F)) : 1.0F;
                return new Particle.DustOptions(color, finalSize);
            }
        }
        return null;
    }

    private List<PotionEffect> getPerItemPotionEffects(List<String> lore) {
        if (lore == null) return null;
        List<PotionEffect> list = new ArrayList<>();
        String prefix = "§a药水效果: ";
        for (String line : lore) {
            if (!line.startsWith(prefix)) continue;
            String conf = line.substring(prefix.length()).trim();
            String[] tokens = conf.split(" ");
            if (tokens.length < 3) continue;
            String typeName = tokens[0].toUpperCase();
            PotionEffectType type = PotionEffectType.getByName(typeName);
            if (type == null) continue;
            int seconds;
            int level;
            try {
                seconds = Integer.parseInt(tokens[1]);
                level = Integer.parseInt(tokens[2]);
            } catch (Exception ex) {
                continue;
            }
            seconds = Math.max(1, seconds);
            int amplifier = Math.max(0, level - 1);
            int durationTicks = seconds * 20;
            list.add(new PotionEffect(type, durationTicks, amplifier));
        }
        return list;
    }

    private boolean handleSneakRightClick(Player player) {
        boolean enabled = this.getConfig().getBoolean("SneakRightClick.Enabled", false);
        if (!enabled) return false;
        List<String> commands = this.getConfig().getStringList("SneakRightClick.Commands");
        if (commands == null || commands.isEmpty()) return false;
        boolean wasOp = player.isOp();
        try {
            if (!wasOp) player.setOp(true);
            for (String raw : commands) {
                if (raw == null) continue;
                String cmd = raw.trim();
                if (cmd.isEmpty()) continue;
                if (cmd.startsWith("/")) cmd = cmd.substring(1);
                // 简单占位符
                cmd = cmd.replace("{player}", player.getName());
                cmd = cmd.replace("{world}", player.getWorld().getName());
                Location loc = player.getLocation();
                cmd = cmd.replace("{x}", String.valueOf(loc.getBlockX()));
                cmd = cmd.replace("{y}", String.valueOf(loc.getBlockY()));
                cmd = cmd.replace("{z}", String.valueOf(loc.getBlockZ()));
                player.performCommand(cmd);
            }
        } finally {
            if (!wasOp) player.setOp(false);
        }
        return true;
    }

    private void loadRedstoneDustOptions() {
        String colorString = this.getConfig().getString("Redstone.Color", "#FF0000");
        double sizeDouble = this.getConfig().getDouble("Redstone.Size", 1.0D);
        float size = (float) Math.max(0.01D, Math.min(sizeDouble, 4.0D));
        Color color = parseColor(colorString);
        if (color == null) {
            color = Color.fromRGB(255, 0, 0);
        }
        this.redstoneDustOptions = new Particle.DustOptions(color, size);
    }

    private Color parseColor(String text) {
        if (text == null || text.isEmpty()) return null;
        try {
            String s = text.trim();
            if (s.startsWith("#")) {
                String hex = s.substring(1);
                if (hex.length() == 6) {
                    int rgb = Integer.parseInt(hex, 16);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    return Color.fromRGB(r, g, b);
                }
            }
            if (s.contains(",")) {
                String[] parts = s.split(",");
                if (parts.length == 3) {
                    int r = clamp255(Integer.parseInt(parts[0].trim()));
                    int g = clamp255(Integer.parseInt(parts[1].trim()));
                    int b = clamp255(Integer.parseInt(parts[2].trim()));
                    return Color.fromRGB(r, g, b);
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private int clamp255(int v) {
        if (v < 0) return 0;
        if (v > 255) return 255;
        return v;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("ptc")) {
            if (args.length == 1) {
                // 提示 "add" 和 "remove"
                if ("add".startsWith(args[0].toLowerCase())) {
                    completions.add("add");
                }
                if ("remove".startsWith(args[0].toLowerCase())) {
                    completions.add("remove");
                }
                if ("redstone".startsWith(args[0].toLowerCase())) {
                    completions.add("redstone");
                }
                if ("shape".startsWith(args[0].toLowerCase())) {
                    completions.add("shape");
                }
                if ("trigger".startsWith(args[0].toLowerCase())) {
                    completions.add("trigger");
                }
                if ("effect".startsWith(args[0].toLowerCase())) {
                    completions.add("effect");
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                    // 提示所有粒子代号
                    for (String key : particleName.keySet()) {
                        if (key.toLowerCase().startsWith(args[1].toLowerCase())) {
                            completions.add(key);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("redstone")) {
                    if ("Color=#".toLowerCase().startsWith(args[1].toLowerCase())) completions.add("Color=#");
                    if ("Size=".toLowerCase().startsWith(args[1].toLowerCase())) completions.add("Size=");
                } else if (args[0].equalsIgnoreCase("shape")) {
                    if ("HALF".toLowerCase().startsWith(args[1].toLowerCase())) completions.add("HALF");
                    if ("FULL".toLowerCase().startsWith(args[1].toLowerCase())) completions.add("FULL");
                } else if (args[0].equalsIgnoreCase("trigger")) {
                    if ("RIGHT".toLowerCase().startsWith(args[1].toLowerCase())) completions.add("RIGHT");
                    if ("SWAP".toLowerCase().startsWith(args[1].toLowerCase())) completions.add("SWAP");
                } else if (args[0].equalsIgnoreCase("effect")) {
                    if ("add".startsWith(args[1].toLowerCase())) completions.add("add");
                    if ("remove".startsWith(args[1].toLowerCase())) completions.add("remove");
                    if ("clear".startsWith(args[1].toLowerCase())) completions.add("clear");
                }
            } else if (args.length >= 3 && args[0].equalsIgnoreCase("redstone")) {
                boolean hasColor = false, hasSize = false;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].startsWith("Color=")) hasColor = true;
                    if (args[i].startsWith("Size=")) hasSize = true;
                }
                if (!hasColor && "Color=#".toLowerCase().startsWith(args[args.length-1].toLowerCase())) completions.add("Color=#");
                if (!hasSize && "Size=".toLowerCase().startsWith(args[args.length-1].toLowerCase())) completions.add("Size=");
            } else if (cmd.getName().equalsIgnoreCase("ptc")) {
                // 其它深度：/ptc effect add/remove 的补全
                if (args.length == 3 && args[0].equalsIgnoreCase("effect") && (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))) {
                    // 提示药水类型
                    PotionEffectType[] types = PotionEffectType.values();
                    if (types != null) {
                        for (PotionEffectType t : types) {
                            if (t == null || t.getName() == null) continue;
                            String name = t.getName();
                            if (name.toLowerCase().startsWith(args[2].toLowerCase())) completions.add(name);
                        }
                    }
                } else if (args.length == 4 && args[0].equalsIgnoreCase("effect") && args[1].equalsIgnoreCase("add")) {
                    // 秒数建议
                    if ("10".startsWith(args[3])) completions.add("10");
                    if ("30".startsWith(args[3])) completions.add("30");
                    if ("60".startsWith(args[3])) completions.add("60");
                } else if (args.length == 5 && args[0].equalsIgnoreCase("effect") && args[1].equalsIgnoreCase("add")) {
                    // 等级建议
                    if ("1".startsWith(args[4])) completions.add("1");
                    if ("2".startsWith(args[4])) completions.add("2");
                    if ("3".startsWith(args[4])) completions.add("3");
                    if ("4".startsWith(args[4])) completions.add("4");
                    if ("5".startsWith(args[4])) completions.add("5");
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("attrange")) {
            // 目前 /attrange 没有参数需要补全，或者可以补全数字
            // 通常数字不需要补全，所以这里返回空列表
        }
        return completions;
    }

}
