package com.zeus.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ZeusCache {

    private static final Map<String, String> CODE_CACHE = new HashMap(128);

    public static void putCodeCache(String key, String value) {
        CODE_CACHE.put(key, value);
    }

    public static String getCodeCache(String key) {
        return CODE_CACHE.get(key);
    }

    /**
     * 判断传入的key是否跟缓存的相等
     *
     * @return
     */
    public static boolean isDiff(String key, String currentGroovyCode) {
        String originalCode = getCodeCache(key);
        //避免计算哈希
        if (StringUtils.hasText(originalCode)) {
            if (currentGroovyCode.length() != originalCode.length()) {
                return Boolean.FALSE;
            }
        }
        String currentCodeMd5 = DigestUtils.md5DigestAsHex(currentGroovyCode.getBytes(StandardCharsets.UTF_8));
        if (StringUtils.hasText(originalCode) && originalCode.equals(currentCodeMd5)) {
            log.info("groovy脚本[{}]未变更，不编译解析", key);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
