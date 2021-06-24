package net.pingfang.dao.web;

import net.pingfang.entity.role.Trole;
import net.pingfang.service.web.MyMapper;

import java.util.List;

public interface TroleMapper extends MyMapper<Trole> {

    List<Trole> selectRolesByUserId(Integer userid);

}