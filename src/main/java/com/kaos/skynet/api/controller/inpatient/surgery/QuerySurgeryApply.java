package com.kaos.skynet.api.controller.inpatient.surgery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.skynet.core.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/inpatient/surgery")
public class QuerySurgeryApply {
    /**
     * 手术请求接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    @Autowired
    Json json;

    /**
     * 查询手术申请记录
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "querySurgeryApply", method = RequestMethod.POST, produces = MediaType.JSON)
    public Response querySurgeryApply(@RequestBody @Valid Request req) {
        // 入参记录
        log.info(String.format("查询手术申请记录: %s", this.json.toJson(req)));

        // 检索手术信息
        var surgeryInfo = this.metOpsApplyMapper.queryMetOpsApply(req.surgeryNo);
        if (surgeryInfo == null) {
            return null;
        }

        return new Response(surgeryInfo.getIcuFlag());
    }

    /**
     * 请求消息Body
     */
    public static class Request {
        /**
         * 登入科室编码
         */
        @NotNull(message = "手术号不能为空")
        @Getter
        private String surgeryNo = null;
    }

    /**
     * 响应消息Body
     */
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        /**
         * 
         */
        @Setter
        private Boolean icuFlag = null;
    }
}
