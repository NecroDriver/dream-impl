package com.xin.lemontree.entity;

import com.xin.lemontree.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author creator mafh 2018/2/28 18:06
 * @author updater mafh
 * @version 1.0.0
 * @description 文章信息
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "article")
@Entity
@Data
public class ArticleEntity extends BaseEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 文章名称
     */
    private String name;

    /**
     * url地址
     */
    private String url;

    /**
     * 属于哪一期
     */
    private Integer stage;

    /**
     * 收藏数
     */
    private Integer collections;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 所属知识库类别
     */
    private String type;

    /**
     * 类别图片地址
     */
    private String img;

}
