package com.kaos.his.controller.cache.inpatient.surgery;

import javax.validation.constraints.NotBlank;

import com.kaos.his.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.inf.ICache;
import com.kaos.inf.ICache.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/inpatient/surgery/room")
public class MetOpsRoomCacheController {
    /**
     * 实体信息服务
     */
    @Autowired
    ICache<String, MetOpsRoom> roomCache;

    /**
     * 检索开关变量的值
     */
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View<String, ?> show() {
        return this.roomCache.show();
    }

    /**
     * 刷新缓存
     */
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refresh(@NotBlank(message = "键值不能为空") String key) {
        this.roomCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    /**
     * 清空缓存
     */
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.roomCache.invalidateAll();
        return "清空缓存成功";
    }
}
