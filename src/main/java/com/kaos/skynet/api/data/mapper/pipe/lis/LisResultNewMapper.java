package com.kaos.skynet.api.data.mapper.pipe.lis;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.entity.pipe.lis.LisResultNew;

import lombok.Data;

public interface LisResultNewMapper {
    /**
     * 查询检验结果
     */
    List<LisResultNew> queryInspectResults(Key key);

    /**
     * 查询的Key集合
     */
    @Data
    public static class Key {
        /**
         * 住院号或卡号, 值为 {@code null} 时，将 IS NULL 作为判断条件
         */
        private String patientId = null;

        /**
         * 检验项目代码列表, 值为 {@code null} 时，不作为判断条件
         */
        private List<String> itemCodes = null;

        /**
         * 开始时间, 值为 {@code null} 时，不作为判断条件
         */
        private LocalDateTime beginDate = null;

        /**
         * 结束时间, 值为 {@code null} 时，不作为判断条件
         */
        private LocalDateTime endDate = null;
    }
}
