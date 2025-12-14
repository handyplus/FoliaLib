# FoliaLib

-------------------------------------------------------------------------------

[**🇨🇳 中文文档**](README-CH.md)

-------------------------------------------------------------------------------
> Compatible with both Folia and Bukkit cores

## Usage

First: Folia requires adding the configuration `folia-supported: true` in your `plugin.yml`.

1. Maven dependency
   ```xml
   <repository>
      <id>handy-repository-releases</id>
      <name>handy-repository</name>
      <url>https://nexus.handyplus.cn/releases</url>
   </repository>   
   
   <dependency>
      <groupId>cn.handyplus.lib.adapter</groupId>
      <artifactId>FoliaLib</artifactId>
      <version>1.2.5</version>
   </dependency>
   ```

2. Maven Shade (optional)

   ```xml
   <!-- Include the dependency JAR into your plugin JAR -->
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
                   <shadedPattern>your.custom.package</shadedPattern>
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

3. Initialization
   ```java
   public class MyPlugin extends JavaPlugin {
       @Override
       public void onEnable() {
           // Initialize
           HandySchedulerUtil.init(this);
       }
   }
   ```

## Examples

   ```java
   // Synchronous task
   public void test1(Player player) {
       HandySchedulerUtil.runTask(() -> {
           // execute logic
       });
   }

   // Asynchronous task
   public void test2() {
       HandySchedulerUtil.runTaskAsynchronously(() -> {
           // execute logic
       });
   }

   // Scheduled (repeating) task
   public void test3() {
      HandyRunnable handyRunnable = new HandyRunnable() {
         @Override
         public void run() {
            try {
               // execute logic
            } catch (Exception ignored) {
               this.cancel();
            }
         }
      };
      HandySchedulerUtil.runTaskTimerAsynchronously(handyRunnable, 20 * 2, 20 * 60);
   }

   // Teleport helper
   public void test4(Player player, Location location) {
       PlayerSchedulerUtil.teleport(player, location);
   }

   // Execute command
   public void test5(Player player, String command) {
       // execute command
       PlayerSchedulerUtil.performCommand(player, command);
       // execute command on the main thread
       PlayerSchedulerUtil.syncPerformCommand(player, command);
       // execute command as OP
       PlayerSchedulerUtil.performOpCommand(player, command);
       // execute command as OP on the main thread
       PlayerSchedulerUtil.syncPerformOpCommand(player, command);
   }

   // Open inventory (GUI)
   public void test6(Player player, Inventory inv) {
       PlayerSchedulerUtil.syncOpenInventory(player, inv);
   }

   // Close inventory (GUI)
   public void test7(Player player, Inventory inv) {
       PlayerSchedulerUtil.syncOpenInventory(player, inv);
   }
   ```

## Javadoc

[View Javadoc](https://handyplus.github.io/FoliaLib/)

## Issues

You can create issues on the repository: [issues](https://github.com/handyplus/FoliaLib/issues)

## Contributors

[![Contrib](https://contrib.rocks/image?repo=handyplus/FoliaLib)](https://github.com/handyplus/FoliaLib/graphs/contributors)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=handyplus/FoliaLib&type=Date)](https://star-history.com/#handyplus/FoliaLib&Date)

