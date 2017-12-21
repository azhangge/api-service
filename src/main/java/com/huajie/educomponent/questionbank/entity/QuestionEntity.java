package com.huajie.educomponent.questionbank.entity;

import com.huajie.appbase.BaseEntity;
import com.huajie.educomponent.questionbank.bo.QuestionBo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 10070 on 2017/7/20.
 */
@Data
@Entity
@Table(name = "qb_question")
public class QuestionEntity extends BaseEntity{
    @Column(name = "type")
    private Integer type;
    @Column(name = "has_children")
    private Boolean hasChildren;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "sub_index")
    private Integer subIndex;
    @Column(name = "main_category_id")
    private String mainCategoryId;
    @Column(name = "sub_category_id")
    private String subCategoryId;
    @Column(name= "public")
    private Integer isPublic;
    @Column(name = "statement")
    private String statement;//题干
    @Column(name = "tags")
    private String tags;
    @Column(name = "answer")
    private String answer;
    @Column(name = "analysis")
    private String analysis;
    @Column(name = "context_a")
    private String contextA;
    @Column(name = "context_b")
    private String contextB;
    @Column(name = "context_c")
    private String contextC;
    @Column(name = "context_d")
    private String contextD;
    @Column(name = "context_e")
    private String contextE;
    @Column(name = "context_f")
    private String contextF;
    @Column(name = "context_g")
    private String contextG;
    @Column(name = "context_h")
    private String contextH;
}
