package com.ld.intercity.utils.yaml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: LD
 * @date:
 * @description:
 */
@Component
@ConfigurationProperties(prefix = "file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YamlFileUtils {

    private String fileModel;
    private String dowPath;
    private String upPath;
    private String upPrefix;
}
