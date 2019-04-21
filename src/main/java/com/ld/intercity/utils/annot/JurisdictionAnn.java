package com.ld.intercity.utils.annot;

import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JurisdictionAnn {
    /**
     * 权限标记
     *
     * @return
     */
    @NotNull String flag() default "";

    /**
     * 是否是管理管专用，默认false
     *
     * @return
     */
    @NotNull boolean types() default false;
}
