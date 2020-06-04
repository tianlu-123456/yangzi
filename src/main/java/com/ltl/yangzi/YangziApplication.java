package com.ltl.yangzi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YangziApplication {

  private static Logger log = LoggerFactory.getLogger(YangziApplication.class);

  public static void main(String[] args) {


    SpringApplication.run(YangziApplication.class, args);

    log.info("========================啟動成功====================");
  }

}
