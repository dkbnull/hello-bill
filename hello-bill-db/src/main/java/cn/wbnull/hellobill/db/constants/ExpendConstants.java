/*
 * Copyright (c) 2017-2026 null. All rights reserved.
 */

package cn.wbnull.hellobill.db.constants;

import java.util.Arrays;
import java.util.List;

/**
 * ExpendConstants
 *
 * @author null
 * @date 2026-02-01
 * @link <a href="">...</a>
 */
public class ExpendConstants {

    // 支出分类常量
    public static final String CLASS_DAILY = "日常支出";
    public static final String CLASS_LIFE = "生活支出";
    public static final String CLASS_CHILDREN = "子女支出";
    public static final String CLASS_CHILDREN_EDU = "子女教育";

    // 排除分类常量
    public static final List<String> EXCLUDED_EXPEND_CLASSES = Arrays.asList("人情往来", "五险一金");
    public static final List<String> EXCLUDED_INCOME_CLASSES = Arrays.asList("人情往来", "父母补贴");
}
