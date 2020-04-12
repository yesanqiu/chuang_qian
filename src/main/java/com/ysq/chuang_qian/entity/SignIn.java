package com.ysq.chuang_qian.entity;

import javax.persistence.*;

import com.alibaba.druid.sql.dialect.odps.ast.OdpsAddStatisticStatement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sign_in")
public class SignIn implements Serializable {
    /**
     * 签到id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "sign_in_id")
    private String signInId;

    /**
     * 组织id
     */
    @Column(name = "organization_id")
    private String organizationId;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 维度
     */
    private Double latitude;

    public static final String SIGN_IN_ID = "signInId";

    public static final String ORGANIZATION_ID = "organizationId";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public SignIn(String oId){
        this.organizationId = oId;
    }
}