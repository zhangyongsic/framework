package com.zhangyongsic.framework.web.lock;

import java.util.List;

/**
 * 可锁定的
 *
 */
public interface Lockable {

    /**
     * @return
     */
    List<String> listLocks();

}
