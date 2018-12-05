package com.xin.lemontree.entity.note;

import com.xin.lemontree.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author creator mafh 2018/4/24 15:30
 * @author updater mafh
 * @version 1.0.0
 * @description 标签实体
 */
@SuppressWarnings(value = "serial")
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "label")
public class LabelEntity extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
