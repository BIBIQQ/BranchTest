package com.ff.anno;

import java.lang.annotation.*;

/**
 * @author FF
 * @date 2021/11/21
 * @TIME:11:42
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
}
