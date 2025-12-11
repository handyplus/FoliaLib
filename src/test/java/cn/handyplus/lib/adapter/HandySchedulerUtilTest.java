package cn.handyplus.lib.adapter;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * 测试方法
 *
 * @author handy
 */
class HandySchedulerUtilTest {

    /**
     * 构造器
     */
    private HandySchedulerUtilTest() {

    }

    /**
     * 同步方法
     *
     */
    private void test1() {
        HandySchedulerUtil.runTask(() -> {
            // 执行方法
        });
    }

    /**
     * 异步方法
     */
    private void test2() {
        HandySchedulerUtil.runTaskAsynchronously(() -> {
            // 执行方法
        });
    }

    /**
     * 定时方法
     */
    private void test3() {
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
    private void test4(Player player, Location location) {
        PlayerSchedulerUtil.teleport(player, location);
    }

    /**
     * 执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    private void test5(Player player, String command) {
        // 执行命令
        PlayerSchedulerUtil.performCommand(player, command);
        // 执行命令 指定同步
        PlayerSchedulerUtil.syncPerformCommand(player, command);
        // op身份执行命令
        PlayerSchedulerUtil.performOpCommand(player, command);
        // op身份执行命令 指定同步
        PlayerSchedulerUtil.syncPerformOpCommand(player, command);
    }

    /**
     * 打开gui
     *
     * @param player 玩家
     * @param inv    背包
     */
    private void test6(Player player, Inventory inv) {
        PlayerSchedulerUtil.syncOpenInventory(player, inv);
    }

    /**
     * 关闭gui
     *
     * @param player 玩家
     * @param inv    背包
     */
    private void test7(Player player, Inventory inv) {
        PlayerSchedulerUtil.syncOpenInventory(player, inv);
    }

}
