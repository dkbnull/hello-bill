package cn.wbnull.hellobill.db.param;

import lombok.Data;

/**
 * 批量查询参数
 *
 * @author null
 * @date 2025-03-02
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
@Data
public class QueryListParam {

    private String beginDate;
    private String endDate;
    private String topClass;
    private String secondClass;
    private String detail;
    private String order;

    public boolean orderByAsc() {
        return !"2".equals(this.order);
    }
}
