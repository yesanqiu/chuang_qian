package com.ysq.chuang_qian.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization implements Serializable {
    /**
     * 组织id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private String organizationId;

    /**
     * 组织名称
     */
    @Column(name = "organization_name")
    private String organizationName;

    /**
     * 组织队长id
     */
    @Column(name = "organization_leader_id")
    private String organizationLeaderId;

    public static final String ORGANIZATION_ID = "organizationId";

    public static final String ORGANIZATION_NAME = "organizationName";

    public static final String ORGANIZATION_LEADER_ID = "organizationLeaderId";

    public Organization(String oName){
        this.organizationName = oName;
    }
}