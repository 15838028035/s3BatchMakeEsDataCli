#s3 制作数据

#目录结构
    s3BatchMakeEsDataCli(项目名称)
      |-----bin	(存放程序运行的相关脚本)
      |    |----startup.sh  (启动脚本)
      |-----config  (存放程序配置文件)
      |    |----application.properties  (程序配置文件)
      |-----doc  (存放程序更新说明文件)
      |    |----UPDATE.txt  (更新说明文件)
      |-----scripts(存放SQL更新脚本)
      |    |----s3BatchMakeEsData_Up原版本号_新版本号.sql
      |    |----UPDATE.txt  (更新说明文件)
      |-----s3BatchMakeEsDataCli-1.0.0.20210118.alpha.jar  (程序jar包)

#程序处理流程：
    
#版本打包说明
    1.比如v1.0.2是版本号——1表示有非常大的改动，0表示bug比较多或者更新了模型，这两个版本级别如果不更新影响程序性能或者程序使用不正常，2表示改了些小问题，不更新也不会影响到程序使用；
    
#打包后命名规范，包名称命名规范
    s3BatchMakeEsDataCli-1.0.0.20210118.alpha.tar.gz
    
#打包操作步驟
    1.修改src/main/resources/application.properties地址为127.0.0.1
    2.修改pom.xml中<version>v1.0.0.20210118.alpha</version>版本信息
    3.执行maven打包命令mvn clean install -Dmaven.test.skip=true -X
    
#git tag 打包规范
    1.发布版本以后，请进行git tag打包，tag命名规范，分支名称-打包日期-三位流水号码，例如：dev-20210118001


