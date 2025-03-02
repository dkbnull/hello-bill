package cn.wbnull.hellobill.dto.cls.response;

import cn.wbnull.hellobill.common.core.constant.ClassTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类信息查询接口响应参数
 *
 * @author null
 * @date 2025-03-02
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
@Data
public class QueryResponse {

    private String uuid;
    private String topClass;
    private String secondClass;
    private String type;
    private String typeName;
    private Integer serialNo;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public void analyse() {
        this.typeName = ClassTypeEnum.getClassType(this.type).getTypeName();
    }
}
