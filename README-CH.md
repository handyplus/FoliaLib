# FoliaLib

-------------------------------------------------------------------------------

[**🇬🇧 English Documentation**](README.md)

-------------------------------------------------------------------------------
> 同时适配Folia和bukkit核心

## 使用方法

首先: Folia要求 在plugin.yml 中添加配置 `folia-supported: true`

1. maven引入
   ```xml
   <repository>
      <id>handy-repository-releases</id>
      <name>handy-repository</name>
      <url>https://nexus.handyplus.cn/releases</url>
   </repository>   
   
   <dependency>
      <groupId>cn.handyplus.lib.adapter</groupId>
      <artifactId>FoliaLib</artifactId>
      <version>1.2.7</version>
   </dependency>
   ```

2. maven shade

   ```xml
   <!--将依赖的jar包打包到当前jar包-->
   <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-shade-plugin</artifactId>
       <version>3.5.3</version>
       <configuration>
           <createDependencyReducedPom>false</createDependencyReducedPom>
           <artifactSet>
               <includes>
                   <include>cn.handyplus.lib.adapter:*:*:*</include>
               </includes>
           </artifactSet>
           <relocations>
               <relocation>
                   <pattern>cn.handyplus.lib.adapter</pattern>
                   <shadedPattern>您的自定义包</shadedPattern>
               </relocation>
           </relocations>
       </configuration>
       <executions>
           <execution>
               <phase>package</phase>
               <goals>
                   <goal>shade</goal>
               </goals>
           </execution>
       </executions>
   </plugin>
   ```

3. 初始化;
   ```java
   public class MyPlugin extends JavaPlugin {
       @Override
       public void onEnable() {
           // 初始化
           HandySchedulerUtil.init(this);
       }
   }
   ```

## 使用示例

   ```java
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
   ```

## javadoc

[点击查看](https://handyplus.github.io/FoliaLib/)

## 建议

可以在[issues](https://github.com/handyplus/FoliaLib/issues)提出

## 贡献者

[![Contrib](https://contrib.rocks/image?repo=handyplus/FoliaLib)](https://github.com/handyplus/FoliaLib/graphs/contributors)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=handyplus/FoliaLib&type=Date)](https://star-history.com/#handyplus/FoliaLib&Date)
