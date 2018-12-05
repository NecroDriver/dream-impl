package com.xin.lemontree.vo.novel;

import com.xin.lemontree.common.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author creator 11934 2018/3/2 0002 17:07
 * @author updater 11934
 * @version 1.0.0
 * @description 小说entity
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class NovelVo extends BaseVo {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 小说编号
     */
    private String novelCode;

    /**
     * 小说名称
     */
    private String novelName;

    /**
     * 小说简介
     */
    private String description;

    /**
     * url
     */
    private String url;

    /**
     * 封面图片
     */
    private String coverImg;

    /**
     * 是否更新,0:否 1:是
     */
    private Integer flagUpdate;

    /**
     * 是否完结,0:否 1:是
     */
    private Integer flagEnd;

    /**
     * 是否删除，0：否 1：是
     */
    private Integer flagDelete;
}
