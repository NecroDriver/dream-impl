package com.xin.lemontree.entity.novel;

import com.xin.lemontree.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author creator 11934 2018/3/2 0002 17:16
 * @author updater 11934
 * @version 1.0.0
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "novel_chapter")
@Entity
public class NovelChapterEntity extends BaseEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
     * 展示顺序
     */
    private Integer dispOrder;
}
