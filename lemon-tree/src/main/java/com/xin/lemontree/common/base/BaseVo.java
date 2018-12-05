package com.xin.lemontree.common.base;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author creator 11934 2018/3/15 0015 14:30
 * @author updater 11934
 * @version 1.0.0
 * @description
 */
@Data
public class BaseVo {

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人ip
     */
    private String creatorIP;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改人ip
     */
    private String modifierIP;
}
