package com.zhangyongsic.framework.web.lock;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    boolean CopyRoot() default true;

}
