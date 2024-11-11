package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

/**
 * 玩家相关调度器
 *
 * @author handy
 * @since 1.0.4
 */
public class PlayerSchedulerUtil {

    private PlayerSchedulerUtil() {

    }

    /**
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 目的地
     * @return 传送结果
     */
    public static boolean teleport(Entity entity, Location target) {
        return teleport(entity, target, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     * @return 传送结果
     */
    public static boolean teleport(Entity entity, Location target, PlayerTeleportEvent.TeleportCause cause) {
        if (HandySchedulerUtil.isFolia()) {
            entity.teleportAsync(target, cause);
            return true;
        }
        return entity.teleport(target, cause);
    }

    /**
     * 传送实体 同步
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     */
    public static void syncTeleport(Entity entity, Location target) {
        syncTeleport(entity, target, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * 传送实体 同步
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     */
    public static void syncTeleport(Entity entity, Location target, PlayerTeleportEvent.TeleportCause cause) {
        if (HandySchedulerUtil.isFolia()) {
            entity.teleportAsync(target, cause);
            return;
        }
        BukkitScheduler.runTask(() -> entity.teleport(target, cause));
    }

    /**
     * 实体添加药水效果 同步
     *
     * @param entity           实体
     * @param potionEffectList 药水效果
     */
    public static void addPotionEffects(LivingEntity entity, List<PotionEffect> potionEffectList) {
        if (potionEffectList == null || potionEffectList.isEmpty()) {
            return;
        }
        if (HandySchedulerUtil.isFolia()) {
            entity.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> entity.addPotionEffects(potionEffectList), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> entity.addPotionEffects(potionEffectList));
    }

    /**
     * 实体添加药水效果 同步
     *
     * @param entity       实体
     * @param potionEffect 药水效果
     * @since 1.1.2
     */
    public static void addPotionEffects(LivingEntity entity, PotionEffect potionEffect) {
        addPotionEffects(entity, Collections.singletonList(potionEffect));
    }

    /**
     * 实体添加药水效果 同步
     *
     * @param entity       实体
     * @param potionEffect 药水效果
     */
    public static void removePotionEffect(LivingEntity entity, PotionEffectType potionEffect) {
        if (HandySchedulerUtil.isFolia()) {
            entity.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> entity.removePotionEffect(potionEffect), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> entity.removePotionEffect(potionEffect));
    }

    /**
     * 播放声音 同步
     *
     * @param player 玩家
     * @param sound  声音
     * @param volume 音量
     * @param pitch  音调
     * @since 1.0.7
     */
    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.playSound(player.getLocation(), sound, volume, pitch), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> player.playSound(player.getLocation(), sound, volume, pitch));
    }

    /**
     * 播放声音 同步
     *
     * @param player 玩家
     * @param sound  声音
     * @param volume 音量
     * @param pitch  音调
     * @since 1.1.6
     */
    public static void playSound(Player player, String sound, float volume, float pitch) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.playSound(player.getLocation(), sound, volume, pitch), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> player.playSound(player.getLocation(), sound, volume, pitch));
    }

    /**
     * 玩家执行命令 chat方式
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void performCommand(Player player, String command) {
        performCommand(player, command, true, false);
    }

    /**
     * 玩家执行命令 chat方式 同步
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void syncPerformCommand(Player player, String command) {
        performCommand(player, command, true, true);
    }

    /**
     * 玩家执行命令 performCommand 方式
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.2
     */
    public static void playerPerformCommand(Player player, String command) {
        performCommand(player, command, false, false);
    }

    /**
     * 玩家执行命令 performCommand 方式 同步
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public static void syncPlayerPerformCommand(Player player, String command) {
        performCommand(player, command, false, true);
    }

    /**
     * 玩家已OP身份执行命令 chat方式
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void performOpCommand(Player player, String command) {
        opPerformCommand(player, command, true, false);
    }

    /**
     * 玩家已OP身份执行命令 chat方式 同步
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void syncPerformOpCommand(Player player, String command) {
        opPerformCommand(player, command, true, true);
    }

    /**
     * 玩家已OP身份执行命令 performCommand 方式
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public static void playerPerformOpCommand(Player player, String command) {
        opPerformCommand(player, command, false, false);
    }

    /**
     * 玩家已OP身份执行命令 performCommand 方式 同步
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public static void syncPlayerPerformOpCommand(Player player, String command) {
        opPerformCommand(player, command, false, true);
    }

    /**
     * 控制台执行命令
     *
     * @param command 命令
     * @since 1.1.4
     */
    public static void dispatchCommand(String command) {
        dispatchCommand(command, false);
    }

    /**
     * 控制台执行命令 同步
     *
     * @param command 命令
     * @since 1.1.4
     */
    public static void syncDispatchCommand(String command) {
        dispatchCommand(command, true);
    }

    /**
     * 玩家执行替换命令 同步
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.0.8
     */
    public static void syncPerformReplaceCommand(Player player, String command) {
        if (command.contains("[close]")) {
            HandySchedulerUtil.runTask(player::closeInventory);
            return;
        }
        if (command.contains("[op]")) {
            String newCommand = command.replace("[op]", "");
            syncPerformOpCommand(player, newCommand);
            return;
        }
        if (command.contains("[console]")) {
            syncDispatchCommand(command.replace("[console]", ""));
            return;
        }
        syncPerformCommand(player, command);
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     * @param isChat  是否chat模式
     * @param isSync  是否同步
     * @since 1.1.5
     */
    private static void performCommand(Player player, String command, boolean isChat, boolean isSync) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> performCommand(player, command, isChat), () -> {
            });
            return;
        }
        if (isSync) {
            BukkitScheduler.runTask(() -> performCommand(player, command, isChat));
            return;
        }
        performCommand(player, command, isChat);
    }

    /**
     * 玩家已OP身份执行命令
     *
     * @param player  玩家
     * @param command 命令
     * @param isChat  是否chat模式
     * @param isSync  是否同步
     * @since 1.1.5
     */
    private synchronized static void opPerformCommand(Player player, String command, boolean isChat, boolean isSync) {
        boolean op = player.isOp();
        try {
            if (!op) {
                player.setOp(true);
            }
            performCommand(player, command, isChat, isSync);
        } finally {
            player.setOp(op);
        }
    }

    /**
     * 玩家执行命令 底层逻辑
     *
     * @param player  玩家
     * @param command 命令
     * @param isChat  是否chat模式
     */
    private static void performCommand(Player player, String command, boolean isChat) {
        if (isChat) {
            player.chat("/" + command.trim());
            return;
        }
        player.performCommand(command.trim());
    }

    /**
     * 控制台执行命令 底层方法
     *
     * @param command 命令
     * @param isSync  是否同步
     * @since 1.1.5
     */
    private static void dispatchCommand(String command, boolean isSync) {
        if (HandySchedulerUtil.isFolia()) {
            HandySchedulerUtil.runTask(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.trim()));
            return;
        }
        if (isSync) {
            HandySchedulerUtil.runTask(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.trim()));
            return;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.trim());
    }

}
