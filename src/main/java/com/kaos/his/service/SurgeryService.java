package com.kaos.his.service;

import java.util.ArrayList;
import java.util.Date;

import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.SurgeryStatusEnum;
import com.kaos.his.mapper.surgery.MetOpsApplyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurgeryService {
    /**
     * 手术申请接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 根据科室权限查询可见手术
     * 
     * @param deptCode
     * @param beginDate
     * @param endDate
     */
    public void queryMetOpsApplies(String deptCode, Date beginDate, Date endDate) {
        // 待查状态列表
        var status = new ArrayList<SurgeryStatusEnum>() {
            {
                add(SurgeryStatusEnum.手术申请);
                add(SurgeryStatusEnum.手术安排);
            }
        };

        // 手术室允许查看本院区所有的手术
        switch (deptCode) {
            case "1290":
                this.metOpsApplyMapper.queryDeptOwnMetOpsApplies(DeptOwnEnum.Sourth, beginDate, endDate, status);
                break;

            case "1291":
                this.metOpsApplyMapper.queryDeptOwnMetOpsApplies(DeptOwnEnum.East, beginDate, endDate, status);
                break;

            case "5206":
                this.metOpsApplyMapper.queryDeptOwnMetOpsApplies(DeptOwnEnum.North, beginDate, endDate, status);
                break;

            default:
                this.metOpsApplyMapper.queryDeptMetOpsApplies(deptCode, beginDate, endDate, status);
                break;
        }
    }
}
