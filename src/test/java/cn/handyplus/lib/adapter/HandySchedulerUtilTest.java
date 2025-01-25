package cn.handyplus.lib.adapter;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * 测试方法
 *
 * @author handy
 */
public class HandySchedulerUtilTest {

    // 同步方法
    public void test1(Player player) {
        HandySchedulerUtil.runTask(() -> {
            // 执行方法
        });
    }

    // 异步方法
    public void test2() {
        HandySchedulerUtil.runTaskAsynchronously(() -> {
            // 执行方法
        });
    }

    // 定时方法
    public void test3() {
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

    // TP方法
    public void test4(Player player, Location location) {
        PlayerSchedulerUtil.teleport(player, location);
    }

    // 执行命令
    public void test5(Player player, String command) {
        // 执行命令
        PlayerSchedulerUtil.performCommand(player, command);
        // 执行命令 指定同步
        PlayerSchedulerUtil.syncPerformCommand(player, command);
        // op身份执行命令
        PlayerSchedulerUtil.performOpCommand(player, command);
        // op身份执行命令 指定同步
        PlayerSchedulerUtil.syncPerformOpCommand(player, command);
    }

    // 打开gui
    public void test6(Player player, Inventory inv) {
        PlayerSchedulerUtil.syncOpenInventory(player, inv);
    }

    // 关闭gui
    public void test7(Player player, Inventory inv) {
        PlayerSchedulerUtil.syncOpenInventory(player, inv);
    }

}
