package net.pingfang.serviceImpl.web;

import net.pingfang.dao.web.TmenuMapper;
import net.pingfang.entity.role.Tmenu;
import net.pingfang.service.web.TmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href=""mailto:zhaojt@gtmap.cn></a>
 * @version 1.0, 2017/11/10
 * @description
 */
@Service("tmenuService")
public class TmenuServieImpl extends BaseService<Tmenu> implements TmenuService {
    @Autowired
    private TmenuMapper tmenuMapper;

    @Override
    public List<Tmenu> selectMenusByRoleId(Integer roleid) {
        return tmenuMapper.selectMenusByRoleId(roleid);
    }

    @Override
    public List<Tmenu> selectByParentIdAndRoleId(HashMap<String, Object> paraMap) {
        return tmenuMapper.selectByParentIdAndRoleId(paraMap);
    }
}
