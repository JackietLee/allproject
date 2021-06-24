package net.pingfang.dao.web;


import java.util.HashMap;
import java.util.List;

import net.pingfang.entity.role.Tmenu;
import net.pingfang.service.web.MyMapper;
public interface TmenuMapper extends MyMapper<Tmenu> {

    List<Tmenu> selectMenusByRoleId(Integer roleid);

    List<Tmenu> selectByParentIdAndRoleId(HashMap<String,Object> paraMap);

}