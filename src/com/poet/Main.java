package com.poet;

public class Main {

	public static String SAVE_PATH = "D:\\TEST"; // ����ĸ�Ŀ¼
	
	// ����
	public static String PACKAGE = "com.sq.wms";
	public static String PACKAGE_DOMAIN = "com.sq.wms.domain";
	public static String PACKAGE_REPOSITORY = "com.sq.wms.repository.mysql";
	public static String PACKAGE_SERVICE = "com.sq.wms.service";
	
	// ������׺
	public static String REPOSITORY_SUFFIX = "Repository";
	public static String SERVICE_SUFFIX = "Service";
	
	public static void main(String[] args) {
		
		String sql = 
				"CREATE TABLE `hello_cdp` ("+
				"		  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '����',"+
				"		  `productCode` varchar(255) DEFAULT NULL COMMENT '��Ʒ����',"+
				"		  `orders` int(11) DEFAULT NULL COMMENT '����',"+
				"		  `totalCount` int(11) DEFAULT NULL COMMENT '��ӡ����',"+
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
