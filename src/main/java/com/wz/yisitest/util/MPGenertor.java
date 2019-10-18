package com.wz.yisitest.util;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author：wzxgd
 * @date：16/10/2019 --------------
 */
public class MPGenertor {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("yisi") // 作者
                .setOutputDir("D:\\InteljiWorkspace\\yisi-test\\yisiTest\\src\\main\\java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setEnableCache(false) //是否开启二级缓存
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
                // IEmployeeService
                .setBaseResultMap(true)
                .setBaseColumnList(true);


        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://39.108.184.79:3306/yisi")
                .setUsername("yisiadmin")
                .setPassword("wzyisi");

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setTablePrefix("yisi_")
                .setInclude(new String[]{"yisi_user","yisi_context","yisi_permission","yisi_role"});  // 生成的表


        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.wz.yisitest")
                .setMapper("dao")
                .setService("service")
                .setController("controller")
                .setEntity("entity")
                .setXml("dao");

        mpg.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        // 执行生成
        mpg.execute();
    }
}
