package com.xin.lemontree.vo.novel;

import com.xin.lemontree.common.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author creator 11934 2018/3/2 0002 17:16
 * @author updater 11934
 * @version 1.0.0
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NovelChapterVo extends BaseVo {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 小说id
     */
    private String novelCode;

    /**
     * 章节名称
     */
    private String chapterName;

    /**
     * url
     */
    private String url;

    /**
     * 章节内容
     */
    private String content;

    /**
     * 上一章id
     */
    private Integer prevId;

    /**
     * 下一章id
     */
    private Integer nextId;

    /**
     * 展示顺序
     */
    private Integer dispOrder;
}
