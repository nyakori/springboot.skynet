package com.kaos.his.mapper.common.fee;

import com.kaos.his.entity.common.fee.FinComFeeCodeStat;
import com.kaos.his.enums.impl.common.MinFeeEnum;
import com.kaos.his.enums.impl.common.ReportTypeEnum;

public interface FinComFeeCodeStatMapper {
    /**
     * 查询对照记录
     * 
     * @param reportType 报表类型, 值为 {@code null} 时, 将 IS NULL 作为判断条件
     * @param feeCode    最小类别, 值为 {@code null} 时, 将 IS NULL 作为判断条件
     * @return
     */
    FinComFeeCodeStat queryFeeCodeStat(ReportTypeEnum reportType, MinFeeEnum feeCode);
}
