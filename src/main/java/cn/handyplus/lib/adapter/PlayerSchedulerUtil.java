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
        }
        BukkitScheduler.runTask(() -> entity.teleport(target, cause));
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void performCommand(Player player, String command) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> chat(player, command), () -> {
            });
            return;
        }
        chat(player, command);
    }

    /**
     * 玩家执行命令 同步
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void syncPerformCommand(Player player, String command) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> chat(player, command), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> chat(player, command));
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
            String trimCommand = command.replace("[console]", "").trim();
            HandySchedulerUtil.runTask(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), trimCommand));
            return;
        }
        syncPerformCommand(player, command);
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void performOpCommand(Player player, String command) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> opChat(player, command), () -> {
            });
            return;
        }
        opChat(player, command);
    }

    /**
     * 玩家执行命令 同步
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void syncPerformOpCommand(Player player, String command) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> opChat(player, command), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> opChat(player, command));
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
     * 播放声音
     *
     * @param player 玩家
     * @param sound  声音
     * @param volume 音量
     * @param pitch  音调
     * @since 1.0.7
     */
    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> {
                player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
            }, () -> {
            });
            return;
        }
        player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * 播放声音
     *
     * @param player 玩家
     * @param sound  声音
     * @param volume 音量
     * @param pitch  音调
     * @since 1.0.7
     */
    public static void syncPlaySound(Player player, Sound sound, float volume, float pitch) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> {
                player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
            }, () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> player.getWorld().playSound(player.getLocation(), sound, volume, pitch));
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    private static void chat(Player player, String command) {
        player.chat("/" + command.trim());
    }

    /**
     * 玩家已OP身份执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    private static void opChat(Player player, String command) {
        boolean op = player.isOp();
        try {
            if (!op) {
                player.setOp(true);
            }
            chat(player, command);
        } finally {
            player.setOp(op);
        }
    }

}