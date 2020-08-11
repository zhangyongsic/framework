package com.zhangyongsic.framework.web.biz;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zhangyongsic.framework.web.lock.Lockable;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/11
 */
@Getter
@Setter
public abstract class AbstractBiz<T extends Model<T>> implements Lockable {
    protected T root;
    public void setRoot() throws IllegalAccessException, InstantiationException {
        try {
           root =  root.selectById();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
