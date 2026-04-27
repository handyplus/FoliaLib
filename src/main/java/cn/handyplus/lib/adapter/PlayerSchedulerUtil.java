package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

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
    public static boolean teleport(@NotNull Entity entity, @NotNull Location target) {
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
    public static boolean teleport(@NotNull Entity entity, @NotNull Location target, @NotNull PlayerTeleportEvent.TeleportCause cause) {
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
    public static void syncTeleport(@NotNull Entity entity, @NotNull Location target) {
        syncTeleport(entity, target, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * 传送实体 同步
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     */
    public static void syncTeleport(@NotNull Entity entity, @NotNull Location target, @NotNull PlayerTeleportEvent.TeleportCause cause) {
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
    public static void addPotionEffects(@NotNull LivingEntity entity, @NotNull List<PotionEffect> potionEffectList) {
        if (potionEffectList.isEmpty()) {
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
    public static void addPotionEffects(@NotNull LivingEntity entity, @NotNull PotionEffect potionEffect) {
        addPotionEffects(entity, Collections.singletonList(potionEffect));
    }

    /**
     * 实体添加药水效果 同步
     *
     * @param entity       实体
     * @param potionEffect 药水效果
     */
    public static void removePotionEffect(@NotNull LivingEntity entity, @NotNull PotionEffectType potionEffect) {
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
    public static void playSound(@NotNull Player player, @NotNull Sound sound, float volume, float pitch) {
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
     * @param sound  声音 例: entity.item.pickup
     * @param volume 音量 例: 1F
     * @param pitch  音调 例: 1F
     * @since 1.1.6
     */
    public static void playSound(@NotNull Player player, @NotNull String sound, float volume, float pitch) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.playSound(player.getLocation(), sound, volume, pitch), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> player.playSound(player.getLocation(), sound, volume, pitch));
    }

    /**
     * 玩家执行替换命令 同步
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.0.8
     */
    public static void performReplaceCommand(@NotNull Player player, @NotNull String command) {
        if (command.contains("[close]")) {
            closeInventory(player);
            return;
        }
        // 1.1.7 玩家名替换
        command = command.replace("${player}", player.getName());
        if (command.contains("[op]")) {
            String newCommand = command.replace("[op]", "");
            performOpCommand(player, newCommand, true);
            return;
        }
        if (command.contains("[console]")) {
            dispatchCommand(command.replace("[console]", ""));
            return;
        }
        performCommand(player, command, true);
    }

    /**
     * 掉落物品处理
     *
     * @param player       玩家
     * @param dropItemList 掉落物品
     * @since 1.2.0
     */
    public static void dropItem(@NotNull Player player, @NotNull List<ItemStack> dropItemList) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, task -> dropItemList.forEach(dropItem -> player.getWorld().dropItem(player.getLocation(), dropItem)), () -> {
            });
            return;
        }
        BukkitScheduler.runTask(() -> dropItemList.forEach(item -> player.getWorld().dropItem(player.getLocation(), item)));
    }

    /**
     * 掉落物品处理
     *
     * @param player       玩家
     * @param dropItemList 掉落物品
     * @param delay        延迟时间
     * @since 1.2.6
     */
    public static void dropItem(@NotNull Player player, @NotNull List<ItemStack> dropItemList, long delay) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, task -> dropItemList.forEach(dropItem -> player.getWorld().dropItem(player.getLocation(), dropItem)), () -> {
            }, delay);
            return;
        }
        BukkitScheduler.runTaskLater(() -> dropItemList.forEach(item -> player.getWorld().dropItem(player.getLocation(), item)), delay);
    }

    /**
     * 玩家已OP身份执行命令
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public synchronized static void performOpCommand(@NotNull Player player, @NotNull String command) {
        performOpCommand(player, command, true);
    }

    /**
     * 玩家已OP身份执行命令
     *
     * @param player  玩家
     * @param command 命令
     * @param isChat  是否chat模式
     * @since 1.1.5
     */
    public synchronized static void performOpCommand(@NotNull Player player, @NotNull String command, boolean isChat) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> executeOpCommand(player, command, isChat), () -> {
            });
            return;
        }
        // OP权限变更和命令执行必须在同一个同步任务内完成
        if (!Bukkit.isPrimaryThread()) {
            BukkitScheduler.runTask(() -> executeOpCommand(player, command, isChat));
            return;
        }
        executeOpCommand(player, command, isChat);
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public static void performCommand(@NotNull Player player, @NotNull String command) {
        performCommand(player, command, true);
    }

    /**
     * 玩家执行命令 performCommand 方式
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.2
     */
    public static void playerPerformCommand(@NotNull Player player, @NotNull String command) {
        performCommand(player, command, false);
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     * @param isChat  是否chat模式
     * @since 1.1.5
     */
    public static void performCommand(@NotNull Player player, @NotNull String command, boolean isChat) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> executeCommand(player, command, isChat), () -> {
            });
            return;
        }
        // 执行命令必须是同步情况执行
        if (!Bukkit.isPrimaryThread()) {
            BukkitScheduler.runTask(() -> executeCommand(player, command, isChat));
            return;
        }
        executeCommand(player, command, isChat);
    }


    /**
     * 控制台执行命令
     *
     * @param command 命令
     * @since 1.1.5
     */
    public static void dispatchCommand(@NotNull String command) {
        if (HandySchedulerUtil.isFolia()) {
            HandySchedulerUtil.runTask(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.trim()));
            return;
        }
        // 控制台执行命令必须是同步情况执行
        if (!Bukkit.isPrimaryThread()) {
            BukkitScheduler.runTask(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.trim()));
            return;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.trim());
    }

    /**
     * 玩家打开gui
     *
     * @param player    玩家
     * @param inventory 背包
     * @since 1.1.8
     */
    public static void openInventory(@NotNull Player player, @NotNull Inventory inventory) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.openInventory(inventory), () -> {
            });
            return;
        }
        // 打开 GUI 必须是同步情况执行
        if (!Bukkit.isPrimaryThread()) {
            BukkitScheduler.runTask(() -> player.openInventory(inventory));
            return;
        }
        player.openInventory(inventory);
    }

    /**
     * 玩家关闭gui
     *
     * @param player 玩家
     * @since 1.1.9
     */
    public static void closeInventory(@NotNull Player player) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.closeInventory(), () -> {
            });
            return;
        }
        // 关闭 GUI 必须是同步情况执行
        if (!Bukkit.isPrimaryThread()) {
            BukkitScheduler.runTask(player::closeInventory);
            return;
        }
        player.closeInventory();
    }

    /**
     * 玩家已OP身份执行命令 底层逻辑
     *
     * @param player  玩家
     * @param command 命令
     * @param isChat  是否chat模式
     */
    private synchronized static void executeOpCommand(@NotNull Player player, @NotNull String command, boolean isChat) {
        boolean op = player.isOp();
        try {
            if (!op) {
                player.setOp(true);
            }
            executeCommand(player, command, isChat);
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
    private static void executeCommand(@NotNull Player player, @NotNull String command, boolean isChat) {
        if (isChat) {
            player.chat("/" + command.trim());
            return;
        }
        player.performCommand(command.trim());
    }

}
