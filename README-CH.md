# FoliaLib

-------------------------------------------------------------------------------

[**🇬🇧 English Documentation**](README.md)

-------------------------------------------------------------------------------
> 同时适配Folia和bukkit核心

## 使用方法

[![Maven Central](https://img.shields.io/maven-central/v/cn.handyplus.lib.adapter/FoliaLib.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/cn.handyplus.lib.adapter/FoliaLib)

首先: Folia要求 在plugin.yml 中添加配置 `folia-supported: true`

1. maven引入
   ```xml 
   <dependency>
      <groupId>cn.handyplus.lib.adapter</groupId>
      <artifactId>FoliaLib</artifactId>
      <version>1.3.2</version>
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

完整示例见 [HandySchedulerUtilExample.java](src/test/java/cn/handyplus/lib/adapter/HandySchedulerUtilExample.java)。

## javadoc

[点击查看](https://handyplus.github.io/FoliaLib/)

## 建议

可以在[issues](https://github.com/handyplus/FoliaLib/issues)提出

## 贡献者

[![Contrib](https://contrib.rocks/image?repo=handyplus/FoliaLib)](https://github.com/handyplus/FoliaLib/graphs/contributors)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=handyplus/FoliaLib&type=Date)](https://star-history.com/#handyplus/FoliaLib&Date)
