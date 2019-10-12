package com.zgl.mybatis.plus.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 测试生成代码
 * @author zgl
 * @date 2019/10/12 下午2:39
 */
public class GeneratorServiceEntity {

	private static final boolean SERVICE_NAME_START_WITH_I = false; //user -> UserService, 设置成true: user -> IUserService
	private static final String PACKAGE_NAME = "com.zgl.mybatis.plus"; //包名
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ssm?characterEncoding=utf-8";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	/**
	 * @param tableNames 表名数组
	 */
	public void generateByTables(String... tableNames) {
		GlobalConfig config = new GlobalConfig();
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL)
				.setUrl(JDBC_URL)
				.setUsername(USERNAME)
				.setPassword(PASSWORD)
				.setDriverName("com.mysql.cj.jdbc.Driver");

		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig
				.setCapitalMode(true)
//                .setSuperEntityClass("com.xes.columbus.commons.db.BaseEntity")
//                .setSuperEntityColumns("creatorId", "creatorName", "createDate", "modifierId", "modifierName", "modifyDate")
				.setEntityLombokModel(true)
				.setRestControllerStyle(true)
//                .setDbColumnUnderline(true)
				.setNaming(NamingStrategy.underline_to_camel)
				.setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
		config.setActiveRecord(false)
				.setEnableCache(false)
				.setAuthor("zgl")
				.setOutputDir("generated")
				.setSwagger2(true)
				.setFileOverride(true);
		if (!SERVICE_NAME_START_WITH_I) {
			config.setServiceName("%sService");
		}
		new AutoGenerator().setGlobalConfig(config)
				.setDataSource(dataSourceConfig)
				.setStrategy(strategyConfig)
				.setCfg(new InjectionConfig() {
					@Override
					public void initMap() {

					}
				})
				.setPackageInfo(
						new PackageConfig()
								.setParent("")
								.setEntity(PACKAGE_NAME + ".entity")
								.setMapper(PACKAGE_NAME + ".mapper")
								.setXml("mapper")
								.setService(PACKAGE_NAME + ".service")
								.setServiceImpl(PACKAGE_NAME + ".service.impl")
								.setController(PACKAGE_NAME + ".controller")
								.setService(PACKAGE_NAME + ".service")
				).execute();
	}
}