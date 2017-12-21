package com.huajie.educomponent.exam.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 10070 on 2017/7/25.
 */
@Data
@Entity
@Table(name = "exam_paper_template")
public class ExamPaperTemplateEntity extends IdEntity {
    @Column(name = "exam_id")
    private String examId;
    @Column(name = "paper_id")
    private String paperId;//这里的paperId是试卷的id，但是他不是考试试卷的实例
}
