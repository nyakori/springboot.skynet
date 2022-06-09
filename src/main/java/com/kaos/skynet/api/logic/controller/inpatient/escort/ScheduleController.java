package com.kaos.skynet.api.logic.controller.inpatient.escort;

import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.logic.controller.inpatient.escort.entity.EscortLock;
import com.kaos.skynet.api.logic.service.inpatient.escort.EscortService;
import com.kaos.skynet.core.thread.Threads;
import com.kaos.skynet.core.thread.pool.ThreadPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inpatient/escort/schedule")
public class ScheduleController {
    /**
     * 定时任务主线程池
     */
    final ThreadPool guardPool = Threads.newGuardThreadPool("陪护证守护线程池");

    /**
     * 子线程池
     */
    final ThreadPool taskPool = Threads.newThreadPool("陪护证线程池", 20);

    /**
     * 陪护锁
     */
    @Autowired
    EscortLock escortLock;

    /**
     * 陪护证主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 陪护证服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 定时更新系统内尚未注销的陪护证状态
     */
    @Scheduled(cron = "0 5/10 * * * ?")
    @RequestMapping(value = "updateState", method = RequestMethod.GET)
    public void updateState() {
        guardPool.execute(() -> {
            // 检索尚且有效的陪护证
            var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                    EscortMainInfoMapper.Key.builder()
                            .states(Lists.newArrayList(
                                    StateEnum.无核酸检测结果,
                                    StateEnum.等待院内核酸检测结果,
                                    StateEnum.等待院外核酸检测结果审核,
                                    StateEnum.生效中,
                                    StateEnum.其他))
                            .build());
            // 刷新状态
            taskPool.monitor(escortInfos.size());
            for (EscortMainInfo escortMainInfo : escortInfos) {
                taskPool.execute(() -> {
                    var lock = escortLock.getStateLock().getLock(escortMainInfo.getEscortNo());
                    Threads.newLockExecutor().link(lock).execute(() -> {
                        escortService.updateState(escortMainInfo.getEscortNo(), null, "schedule", null);
                    });
                });
            }
            taskPool.await();
        });
    }

    /**
     * 展示线程池状态
     * 
     * @return
     */
    @RequestMapping(value = "showPoolState", method = RequestMethod.GET)
    public Map<String, ThreadPool.PoolState> showPoolState() {
        Map<String, ThreadPool.PoolState> result = Maps.newConcurrentMap();
        result.put("guardPool", guardPool.show());
        result.put("taskPool", taskPool.show());
        return result;
    }
}
