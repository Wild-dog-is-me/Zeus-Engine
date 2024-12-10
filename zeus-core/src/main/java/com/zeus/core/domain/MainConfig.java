package com.zeus.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainConfig {

    /**
     * 全限定类名(包名+类名)
     */
    private List<String> instanceNames;

    /**
     * 更新时间
     */
    private Long updateTime;

}
