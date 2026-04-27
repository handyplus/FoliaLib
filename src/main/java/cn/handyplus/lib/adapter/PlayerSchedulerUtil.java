package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

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
     * 调度播放声音
     *
     * @param player 玩家
     * @param sound  声音
     * @param volume 音量
     * @param pitch  音调
     * @since 1.0.7
     */
    public static void playSound(@NotNull Player player, @NotNull Sound sound, float volume, float pitch) {
        runPlayerTask(player, () -> player.playSound(player.getLocation(), sound, volume, pitch), true);
    }

    /**
     * 调度播放声音
     *
     * @param player 玩家
     * @param sound  声音 例: entity.item.pickup
     * @param volume 音量 例: 1F
     * @param pitch  音调 例: 1F
     * @since 1.1.6
     */
    public static void playSound(@NotNull Player player, @NotNull String sound, float volume, float pitch) {
        runPlayerTask(player, () -> player.playSound(player.getLocation(), sound, volume, pitch), true);
    }

    /**
     * 调度玩家执行替换命令
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
        runPlayerTask(player, () -> dropItemList.forEach(item -> player.getWorld().dropItem(player.getLocation(), item)), true);
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
        runPlayerTaskLater(player, () -> dropItemList.forEach(item -> player.getWorld().dropItem(player.getLocation(), item)), delay);
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
        // OP权限变更和命令执行必须在同一个调度任务内完成
        runPlayerTask(player, () -> executeOpCommand(player, command, isChat), false);
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
        runPlayerTask(player, () -> executeCommand(player, command, isChat), false);
    }

    /**
     * 控制台执行命令
     *
     * @param command 命令
     * @since 1.1.5
     */
    public static void dispatchCommand(@NotNull String command) {
        if (HandySchedulerUtil.isFolia()) {
            FoliaScheduler.runTask(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.trim()));
            return;
        }
        // 控制台命令必须在服务端安全线程执行
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
        runPlayerTask(player, () -> player.openInventory(inventory), false);
    }

    /**
     * 玩家关闭gui
     *
     * @param player 玩家
     * @since 1.1.9
     */
    public static void closeInventory(@NotNull Player player) {
        runPlayerTask(player, player::closeInventory, false);
    }

    /**
     * 调度到玩家安全线程执行
     *
     * @param player         玩家
     * @param task           任务
     * @param alwaysSchedule Bukkit 下是否始终投递到主线程任务队列
     */
    private static void runPlayerTask(@NotNull Player player, @NotNull Runnable task, boolean alwaysSchedule) {
        if (HandySchedulerUtil.isFolia()) {
            player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, scheduledTask -> task.run(), () -> {
            });
            return;
        }
        if (alwaysSchedule || !Bukkit.isPrimaryThread()) {
            BukkitScheduler.runTask(task);
            return;
        }
        task.run();
    }

    /**
     * 延迟调度到玩家安全线程执行
     *
     * @param player 玩家
     * @param task   任务
     * @param delay  延迟
     */
    private static void runPlayerTaskLater(@NotNull Player player, @NotNull Runnable task, long delay) {
        if (HandySchedulerUtil.isFolia()) {
            delay = getOneIfNotPositive(delay);
            player.getScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, scheduledTask -> task.run(), () -> {
            }, delay);
            return;
        }
        BukkitScheduler.runTaskLater(task, delay);
    }

    /**
     * Folia异常：时间参数不能<=0
     *
     * @param time 时间
     * @return 时间
     */
    private static long getOneIfNotPositive(long time) {
        return time <= 0 ? 1L : time;
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
