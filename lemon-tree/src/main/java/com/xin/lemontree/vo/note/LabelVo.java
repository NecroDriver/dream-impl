package com.xin.lemontree.vo.note;

import com.xin.lemontree.common.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author creator mafh 2018/4/24 15:30
 * @author updater mafh
 * @version 1.0.0
 * @description 标签实体
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class LabelVo extends BaseVo {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 是否删除，0：否 1：是
     */
    private Integer flagDelete;

}
