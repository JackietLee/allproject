package net.pingfang.serviceImpl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pingfang.dao.user.UserDao;
import net.pingfang.entity.user.UserVo;
import net.pingfang.service.systemManage.UserRroleService;
import net.pingfang.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRroleService userRroleService;

	/**
	 * 根据ID获取一条用户数据
	 * @return
	 */
	@Override
	public UserVo getUserById(int id) {
		return userDao.getUserById(id);
	}
	/**
	 * 根据用户名和密码获取一条用户数据
	 * @return
	 */
	@Override
	public UserVo getUser(String userName,String password) {
		return userDao.getUser(userName,password);
	}
	/**
	 * 获取所有用户
	 * @return
	 */
	@Override
	public List<UserVo> getListUser() {
		List<UserVo> userList = userDao.getListUser();
		if(null !=userList && userList.size() >0) {
			Map<Integer,UserVo> map = new HashMap<Integer, UserVo>();
			UserVo user = null;
			for(int i=0; i<userList.size(); i++) {
				if(map.containsKey(userList.get(i).getId())) {
					user = map.get(userList.get(i).getId());
					user.setUserRole(user.getUserRole()+"、"+userList.get(i).getUserRole());
					map.put(userList.get(i).getId(), user);					
				}else {
					map.put(userList.get(i).getId(), userList.get(i));
				}
			}
			userList.clear();
			for(Map.Entry<Integer, UserVo> entry : map.entrySet()){
			    userList.add(entry.getValue());
			}
		}
		return userList;
	}
	/**
	 * 新增用户
	 * @return
	 */
	@Transactional
	@Override
	public int addUser(UserVo user) {
		return userDao.addUser(user);
	}
	/**
	 * 更新用户
	 * @return
	 */
	@Transactional
	@Override
	public int updateUser(UserVo user) {
		return userDao.updateUser(user);
	}
	/**
	 * 删除用户
	 * @return
	 */
	@Transactional
	@Override
	public int deleteUser(int id) {
		//删除用户引用的角色
		int count = userRroleService.deleteUserRoleList(id);
		//删除用户
		count = userDao.deleteUser(id);
		return count;
	}


}
