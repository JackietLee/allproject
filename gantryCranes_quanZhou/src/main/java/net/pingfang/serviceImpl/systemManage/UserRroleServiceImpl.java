package net.pingfang.serviceImpl.systemManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.systemManage.UserRroleDao;
import net.pingfang.entity.role.Tuserrole;
import net.pingfang.service.systemManage.UserRroleService;

@Service
public class UserRroleServiceImpl implements UserRroleService {

	@Autowired
	private UserRroleDao userRroleDao;
	
	/**
	 * 根据用户ID获取用户角色
	 * @param id
	 * @return
	 */
	@Override
	public List<Tuserrole> getUserRoleList(int id){
		return userRroleDao.getUserRoleList(id);
	}
	
	/**
	 * 批量新增用户角色
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int addUserRoleList(List<Tuserrole> list){
		int count = 0;
		if(null !=list && list.size() >0) {
			Tuserrole userRole= list.get(0);
			//删除用户角色
			count = userRroleDao.deleteUserRoleList(userRole.getUserId());
			if(userRole.getUserId() >0 && userRole.getRoleId() >0) {
				//分配用户角色
				count = userRroleDao.addUserRoleList(list);
			}
		}
		return count;
	}
	
	/**
	 * 根据用户ID删除用户角色
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteUserRoleList(int id){
		return userRroleDao.deleteUserRoleList(id);
	}

	/**
	 * 根据角色ID查询角色是否被引用
	 * @param id
	 * @return
	 */
	@Override
	public int getCountUrByRoleId(int roleId){
		return userRroleDao.getCountUrByRoleId(roleId);
	}


}
