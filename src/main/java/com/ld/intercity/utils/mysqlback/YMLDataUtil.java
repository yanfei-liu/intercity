package com.ld.intercity.utils.mysqlback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@ConfigurationProperties(prefix = "blackup")
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YMLDataUtil implements Serializable {

    private String mysqldump;
    private String databaseHost;
    private String username;
    private String password;
    private String databaseName;
}
