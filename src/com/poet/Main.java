package com.poet;

public class Main {

	public static String SAVE_PATH = "D:\\TEST"; // 保存的根目录
	
	// 包名
	public static String PACKAGE = "com.sq.wms";
	public static String PACKAGE_DOMAIN = "com.sq.wms.domain";
	public static String PACKAGE_REPOSITORY = "com.sq.wms.repository.mysql";
	public static String PACKAGE_SERVICE = "com.sq.wms.service";
	
	// 类名后缀
	public static String REPOSITORY_SUFFIX = "Repository";
	public static String SERVICE_SUFFIX = "Service";
	
	public static void main(String[] args) {
		
		String sql = 
				"CREATE TABLE `hello_cdp` ("+
				"		  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',"+
				"		  `productCode` varchar(255) DEFAULT NULL COMMENT '商品编码',"+
				"		  `orders` int(11) DEFAULT NULL COMMENT '批次',"+
				"		  `totalCount` int(11) DEFAULT NULL COMMENT '打印数量',"+
				"		  PRIMARY KEY (`id`)"+
				"		) ENGINE=InnoDB AUTO_INCREMENT=1329 DEFAULT CHARSET=utf8;";
		
		Parser parser = new Parser();
		TableInfo tableInfo = parser.parse(sql);
		
		PoetCreater poetCreater = new PoetCreater();
		poetCreater.createDomain(tableInfo);
		poetCreater.createRepository(tableInfo);
		poetCreater.createService(tableInfo);
	}
}
