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
     * 玩家执行命令 chat方式
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void performCommand(@NotNull Player player, @NotNull String command) {
        performCommand(player, command, true, false);
    }

    /**
     * 玩家执行命令 chat方式 同步
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void syncPerformCommand(@NotNull Player player, @NotNull String command) {
        performCommand(player, command, true, true);
    }

    /**
     * 玩家执行命令 performCommand 方式
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.2
     */
    public static void playerPerformCommand(@NotNull Player player, @NotNull String command) {
        performCommand(player, command, false, false);
    }

    /**
     * 玩家执行命令 performCommand 方式 同步
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public static void syncPlayerPerformCommand(@NotNull Player player, @NotNull String command) {
        performCommand(player, command, false, true);
    }

    /**
     * 玩家打开gui
     *
     * @param player    玩家
     * @param inventory gui
     * @since 1.1.8
     */
    public static void openInventory(@NotNull Player player, @NotNull Inventory inventory) {
        openInventory(player, inventory, false);
    }

    /**
     * 玩家打开gui  使用同步
     *
     * @param player    玩家
     * @param inventory gui
     * @since 1.1.8
     */
    public static void syncOpenInventory(@NotNull Player player, @NotNull Inventory inventory) {
        openInventory(player, inventory, true);
    }

    /**
     * 玩家关闭gui
     *
     * @param player 玩家
     * @since 1.1.9
     */
    public static void closeInventory(@NotNull Player player) {
        closeInventory(player, false);
    }

    /**
     * 玩家关闭gui  使用同步
     *
     * @param player 玩家
     * @since 1.1.9
     */
    public static void syncCloseInventory(@NotNull Player player) {
        closeInventory(player, true);
    }

    /**
     * 玩家已OP身份执行命令 chat方式
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void performOpCommand(@NotNull Player player, @NotNull String command) {
        opPerformCommand(player, command, true, false);
    }

    /**
     * 玩家已OP身份执行命令 chat方式 同步
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void syncPerformOpCommand(@NotNull Player player, @NotNull String command) {
        opPerformCommand(player, command, true, true);
    }

    /**
     * 玩家已OP身份执行命令 performCommand 方式
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public static void playerPerformOpCommand(@NotNull Player player, @NotNull String command) {
        opPerformCommand(player, command, false, false);
    }

    /**
     * 玩家已OP身份执行命令 performCommand 方式 同步
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.1.5
     */
    public static void syncPlayerPerformOpCommand(@NotNull Player player, @NotNull String command) {
        opPerformCommand(player, command, false, true);
    }

    /**
     * 控制台执行命令
     *
     * @param command 命令
     * @since 1.1.4
     */
    public static void dispatchCommand(@NotNull String command) {
        dispatchCommand(command, false);
    }

    /**
     * 控制台执行命令 同步
     *
     * @param command 命令
     * @since 1.1.4
     */
    public static void syncDispatchCommand(@NotNull String command) {
        dispatchCommand(command, true);
    }

    /**
     * 玩家执行替换命令 同步
     *
     * @param player  玩家
     * @param command 命令
     * @since 1.0.8
     */
    public static void syncPerformReplaceCommand(@NotNull Player player, @NotNull String command) {
        if (command.contains("[close]")) {
            syncCloseInventory(player);
            return;
        }
        // 1.1.7 玩家名替换
        command = command.replace("${player}", player.getName());
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
        HandySchedulerUtil.runTask(() -> dropItemList.forEach(item -> player.getWorld().dropItem(player.getLocation(), item)));
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
        HandySchedulerUtil.runTaskLater(() -> dropItemList.forEach(item -> player.getWorld().dropItem(player.getLocation(), item)), delay);
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
    private static void performCommand(@NotNull Player player, @NotNull String command, boolean isChat, boolean isSync) {
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
    private synchronized static void opPerformCommand(@NotNull Player player, @NotNull String command, boolean isChat, boolean isSync) {
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
    private static void performCommand(@NotNull Player player, @NotNull String command, boolean isChat) {
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
    private static void dispatchCommand(@NotNull String command, boolean isSync) {
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

    /**
     * 玩家打开gui
     *
     * @param player    玩家
     * @param inventory 背包
     * @param isSync    是否指定同步
     * @since 1.1.8
     */
    private static void openInventory(@NotNull Player player, @NotNull Inventory inventory, boolean isSync) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.openInventory(inventory), () -> {
            });
            return;
        }
        if (isSync) {
            BukkitScheduler.runTask(() -> player.openInventory(inventory));
            return;
        }
        player.openInventory(inventory);
    }

    /**
     * 玩家关闭gui
     *
     * @param player 玩家
     * @param isSync 是否指定同步
     * @since 1.1.9
     */
    private static void closeInventory(@NotNull Player player, boolean isSync) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.closeInventory(), () -> {
            });
            return;
        }
        if (isSync) {
            BukkitScheduler.runTask(player::closeInventory);
            return;
        }
        player.closeInventory();
    }

}
