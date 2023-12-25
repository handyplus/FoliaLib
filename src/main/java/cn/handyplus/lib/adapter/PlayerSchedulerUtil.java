package cn.handyplus.lib.adapter;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
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
            return entity.teleportAsync(target, cause).join();
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
            entity.teleportAsync(target, cause).join();
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
     * 玩家添加药水效果 同步
     *
     * @param player           玩家
     * @param potionEffectList 药水效果
     */
    public static void addPotionEffects(Player player, List<PotionEffect> potionEffectList) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.addPotionEffects(potionEffectList), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> player.addPotionEffects(potionEffectList));
    }

    /**
     * 玩家移除药水效果 同步
     *
     * @param player       玩家
     * @param potionEffect 药水效果
     */
    public static void removePotionEffect(Player player, PotionEffectType potionEffect) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.removePotionEffect(potionEffect), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> player.removePotionEffect(potionEffect));
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