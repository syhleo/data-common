# 平台部署方式、后台代码结构、功能划分点、相关表结构等咨询
#  因担心项目依赖的jar包占用你宝贵的磁盘空间，本次只截取上传了部分jar包。

##打包方式：
application.properties中
spring.profiles.active=#spring.profiles.active#
打包命令如下
mvn clean package -P dev（mvn clean package -P prod）   mvn clean package -Ptest也可以。
mvn clean package的话，默认使用dev环境

可以加上-Dmaven.test.skip=true
mvn clean package -Dmaven.test.skip=true -U
mvn clean package -Pprd  -Dmaven.test.skip=true    

这里<delimiter>#</delimiter>用来增加一个占位符，Maven本身有占位符${xxx}，但这个占位符被SpringBoot占用了，所以我们就再定义一个。<filtering>true</filtering>表示打开过滤器开关，这样application.yml文件中的#spring.profiles.active#部分就会替换为pom.xml里profiles中定义的spring.profiles.active变量值。

这样，在用maven打包的时候，使用mvn package -P prod打包，最后打包后的文件中，application.yml中的spring.profiles.active的值就是prod。这样直接运行java -jar xxx.jar，就是生产环境的配置了。
————————————————
版权声明：本文为CSDN博主「光头强改行写代码」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_33581268/article/details/99694676


==========================================================================================================================

org.quartz.JobPersistenceException:
 Failed to obtain DB connection from data source 'springNonTxDataSource.schedulerFactoryBean': java.sql.SQLException
解决方法：
次的进程还在处理中，没有完毕，然后你重启或者reload。就会出现这个问题，具体的我也不清楚
所以，关闭程序，等下在重启就好了。 

https://q.cnblogs.com/q/93831/ 
是不是之前好好的 项目重启或者修改配置之后reload之后报这个错误吗？

收获园豆：5
吉吉的城 | 小虾三级 |园豆：551 | 2017-05-22 23:35
是的  求解  那里错了啊 我该咋弄啊老大

skateweb | 园豆：170 (初学一级) | 2017-05-23 13:33
@skateweb: 上次的进程还在处理中，没有完毕，然后你重启或者reload。就会出现这个问题，具体的我也不清楚

吉吉的城 | 园豆：551 (小虾三级) | 2017-05-23 15:31
@吉吉的城: 。。。。好吧

=================================================================================================================

Lettuce&Jedis&spring-boot-starter-data-redis三者的关系
Lettuce 和 Jedis 的都是连接Redis Server的客户端程序。
Jedis在实现上是直连redis server，多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接。
Lettuce基于Netty的连接实例（StatefulRedisConnection），可以在多个线程间并发访问，且线程安全，满足多线程环境下的并发访问，同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。
spring-boot-data-redis 内部实现了对Lettuce和Jedis两个客户端的封装，默认使用的是Lettuce

作者：十毛tenmao
链接：https://www.jianshu.com/p/900daecd14e2
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

===============================================================================================================
数据已经持久化了，挂了，重启就行了，缓存是可挂掉的，无非就是系统压力大点。
一旦在运行中redis 挂掉，比如我手动把它停掉，整个使用缓存的查询都用不了了，会报错，可以让 redis server 连接失败时，通知mybatis 去db 查询，而不是死磕redis 相当于让项目直接绕过redis 进行工作。
在进行redis 查询前   调用 ping 命令 得到有效响应 进行查询，不行就执行 数据库语句
也可以定时检测服务是否正常
————————————————
版权声明：本文为CSDN博主「damei2017」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/damei2017/article/details/87882632

=========================================================================================================
新增Redis缓存功能：
1、平台具备Redis缓存总开关，以便适用于不同的项目实施部署。
2、Redis无侵入式地缓存业务查询数据。
3、平台高可用特性。如果Redis服务器挂了 继续往下走的解决方案，在 redis server 挂掉以后保持项目正常运行，
当Redis挂了，平台仍可以正常运行，无非就是穿透过去，直接读取数据库呗，
相对于修改Redis底层异常处理策略（通过捕获异常，访问后台数据还是会有延迟），
所以再加入定期心跳检测会更好一点，一旦发现Redis服务不可用，即停止访问redis缓存，直接跳过去，直接读取数据库。

