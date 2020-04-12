package com.ysq.chuang_qian.dto;

import com.ysq.chuang_qian.entity.Organization;
import com.ysq.chuang_qian.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationmentsDTO {

    private Organization organization;

    private List<User> users;

}
