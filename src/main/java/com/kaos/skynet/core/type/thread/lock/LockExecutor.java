package com.kaos.skynet.core.type.thread.lock;

import com.kaos.skynet.core.type.Returnable;

public interface LockExecutor {
    /**
     * 关联目标锁
     * 
     * @param lock
     */
    LockExecutor link(Lock lock);

    /**
     * 执行内容
     * 
     * @param <T>
     * @param runnable
     * @return
     */
    void execute(Runnable runnable);

    /**
     * 执行内容
     * 
     * @param <T>
     * @param runnable
     * @return
     */
    <T> T execute(Returnable<T> returnable);
}
