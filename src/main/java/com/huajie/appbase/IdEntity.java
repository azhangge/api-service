package com.huajie.appbase;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by fangxing on 17-7-3.
 */
@Data
@MappedSuperclass
public abstract class IdEntity implements Serializable{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid") //系统自带的uuid生成器就能达到目的，没有中划线
    @Column(name = "id")
    private String id;
    @Column(name = "deleted")
    private boolean deleted;

}
