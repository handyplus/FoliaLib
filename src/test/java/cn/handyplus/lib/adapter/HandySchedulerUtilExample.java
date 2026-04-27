package cn.handyplus.lib.adapter;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * 使用示例
 *
 * @author handy
 */
class HandySchedulerUtilExample {

    /**
     * 构造器
     */
    private HandySchedulerUtilExample() {

    }

    /**
     * 服务端安全线程调度
     *
     */
    public void runTask() {
        HandySchedulerUtil.runTask(() -> {
            // 执行方法
        });
    }

    /**
     * 异步方法
     */
    public void runTaskAsynchronously() {
        HandySchedulerUtil.runTaskAsynchronously(() -> {
            // 执行方法
        });
    }

    /**
     * 定时方法
     */
    public void runTaskTimerAsynchronously() {
        HandyRunnable handyRunnable = new HandyRunnable() {
            @Override
            public void run() {
                try {
                    // 执行逻辑
                } catch (Exception ignored) {
                    this.cancel();
                }
            }
        };
        HandySchedulerUtil.runTaskTimerAsynchronously(handyRunnable, 20 * 2, 20 * 60);
    }

    /**
     * TP方法
     *
     * @param player   玩家
     * @param location 位置
     */
    public void teleport(Player player, Location location) {
        EntitySchedulerUtil.teleport(player, location);
    }

    /**
     * TP方法 调度执行
     *
     * @param player   玩家
     * @param location 位置
     */
    public void syncTeleport(Player player, Location location) {
        EntitySchedulerUtil.syncTeleport(player, location);
    }

    /**
     * 药水效果
     *
     * @param entity           实体
     * @param potionEffectList 药水效果列表
     * @param potionEffectType 药水效果类型
     */
    public void potionEffect(LivingEntity entity, List<PotionEffect> potionEffectList, PotionEffectType potionEffectType) {
        EntitySchedulerUtil.addPotionEffects(entity, potionEffectList);
        EntitySchedulerUtil.removePotionEffect(entity, potionEffectType);
    }

    /**
     * 播放声音
     *
     * @param player 玩家
     */
    public void playSound(Player player) {
        PlayerSchedulerUtil.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
        PlayerSchedulerUtil.playSound(player, "entity.item.pickup", 1F, 1F);
    }

    /**
     * 执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    public void performCommand(Player player, String command) {
        // 执行命令
        PlayerSchedulerUtil.performCommand(player, command);
        // 执行命令 performCommand方式
        PlayerSchedulerUtil.playerPerformCommand(player, command);
        // op身份执行命令
        PlayerSchedulerUtil.performOpCommand(player, command);
        // op身份执行命令 performCommand方式
        PlayerSchedulerUtil.performOpCommand(player, command, false);
        // 执行控制台命令
        PlayerSchedulerUtil.dispatchCommand(command);
    }

    /**
     * 执行替换命令
     *
     * @param player  玩家
     * @param command 命令
     */
    public void performReplaceCommand(Player player, String command) {
        PlayerSchedulerUtil.performReplaceCommand(player, command);
    }

    /**
     * 掉落物品
     *
     * @param player       玩家
     * @param dropItemList 掉落物品列表
     */
    public void dropItem(Player player, List<ItemStack> dropItemList) {
        PlayerSchedulerUtil.dropItem(player, dropItemList);
        PlayerSchedulerUtil.dropItem(player, dropItemList, 20L);
    }

    /**
     * 打开gui
     *
     * @param player 玩家
     * @param inv    背包
     */
    public void openInventory(Player player, Inventory inv) {
        PlayerSchedulerUtil.openInventory(player, inv);
    }

    /**
     * 关闭gui
     *
     * @param player 玩家
     */
    public void closeInventory(Player player) {
        PlayerSchedulerUtil.closeInventory(player);
    }

    /**
     * 实体安全调度
     *
     * @param entity 实体
     */
    public void runSafeOnEntityScheduler(LivingEntity entity) {
        EntitySchedulerUtil.runSafeOnEntityScheduler(entity, () -> {
            // 执行实体相关逻辑
        });
    }

    /**
     * 实体安全调度 指定Bukkit下是否使用主线程调度
     *
     * @param entity 实体
     */
    public void runSafeOnEntitySchedulerWithSync(LivingEntity entity) {
        EntitySchedulerUtil.runSafeOnEntityScheduler(entity, () -> {
            // 执行实体相关逻辑
        }, true);
    }

    /**
     * 实体安全调度 带返回值
     *
     * @param entity 实体
     */
    public void runSafeOnEntitySchedulerWithResult(LivingEntity entity) {
        EntitySchedulerUtil.runSafeOnEntityScheduler(entity, entity::getHealth, true);
    }

    /**
     * 实体安全调度 带成功回调
     *
     * @param entity 实体
     */
    public void runSafeOnEntitySchedulerWithSuccess(LivingEntity entity) {
        EntitySchedulerUtil.runSafeOnEntityScheduler(entity, entity::getHealth, health -> {
            // 使用返回结果
        }, true);
    }

}
