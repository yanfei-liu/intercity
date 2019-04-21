package com.ld.intercity.utils.mysqlback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@Slf4j
public class ScheduledUtil {

    @Value("${file.backPath}")
    private String databaseSvcePath;

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    @Scheduled(cron = "* * 0/1 ? * *")
    public void bf() {
        YMLDataUtil ymlDataUtil = new YMLDataUtil();
        try {
            boolean b = MySQLDatabaseBackup.exportDatabaseTool(ymlDataUtil.getDatabaseHost(),
                    ymlDataUtil.getUsername(),
                    ymlDataUtil.getPassword(),
                    databaseSvcePath,
                    format.format(System.currentTimeMillis()) + ".sql",
                    ymlDataUtil.getDatabaseName(),
                    ymlDataUtil.getMysqldump());
            if (b) {
                log.info("数据库成功备份！！！");
            } else {
                log.info("数据库备份失败！！！");
            }
        } catch (Exception e) {
            log.info("数据库备份失败！！！");
        }
    }
}
