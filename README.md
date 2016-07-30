根据 sql 创建 Doamin Repository Service java 文件

依赖生成java文件库 https://github.com/square/javapoet

```Java
CREATE TABLE `code_record` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `productCode` varchar(255) DEFAULT NULL COMMENT '商品编码',
  `orders` int(11) DEFAULT NULL COMMENT '批次',
  `totalCount` int(11) DEFAULT NULL COMMENT '打印数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1329 DEFAULT CHARSET=utf8;


```

```Java
package com.sq.wms.domain;

import java.lang.Integer;
import java.lang.String;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
    name = "hello_cdp"
)
public class HelloCdp {
  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO
  )
  private Integer id;

  private String productCode;

  private Integer orders;

  private Integer totalCount;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public Integer getOrders() {
    return orders;
  }

  public void setOrders(Integer orders) {
    this.orders = orders;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }
}
```

```Java
package com.sq.wms.repository.mysql;

import com.sq.wms.domain.HelloCdp;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloCdpRepository extends JpaRepository<HelloCdp, Long> {
  HelloCdp findById(Integer id);

  HelloCdp findByProductCode(String productCode);

  HelloCdp findByOrders(Integer orders);

  HelloCdp findByTotalCount(Integer totalCount);
}
```

```Java
package com.sq.wms.service;

import com.sq.wms.domain.HelloCdp;
import com.sq.wms.repository.mysql.HelloCdpRepository;
import java.lang.Integer;
import java.lang.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloCdpService {
  @Autowired
  private HelloCdpRepository helloCdpRepository;

  public HelloCdp findById(Integer id) {
    return helloCdpRepository.findById(id);
  }

  public HelloCdp findByProductCode(String productCode) {
    return helloCdpRepository.findByProductCode(productCode);
  }

  public HelloCdp findByOrders(Integer orders) {
    return helloCdpRepository.findByOrders(orders);
  }

  public HelloCdp findByTotalCount(Integer totalCount) {
    return helloCdpRepository.findByTotalCount(totalCount);
  }
}
```
