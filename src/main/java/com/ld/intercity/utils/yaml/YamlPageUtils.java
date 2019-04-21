package com.ld.intercity.utils.yaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: LD
 * @date:
 * @description:
 */
@Component
@ConfigurationProperties(prefix = "page")
public class YamlPageUtils {

    private static int pageSize;

    public static int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
