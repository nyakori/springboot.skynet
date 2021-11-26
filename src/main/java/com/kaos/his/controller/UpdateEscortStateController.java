package com.kaos.his.controller;

import java.util.Date;

import com.kaos.his.entity.credential.EscortCard.EscortState;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.service.EscortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class UpdateEscortStateController {
    /**
     * 陪护证业务
     */
    @Autowired
    EscortService escortService;

    /**
     * 获取陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "updateEscortState", method = RequestMethod.GET)
    public void Run(@RequestParam("escortNo") String escortNo, @RequestParam("newState") EscortStateEnum newState) {
        // 执行更新服务
        this.escortService.UpdateEscortState(escortNo, newState);
    }
}
