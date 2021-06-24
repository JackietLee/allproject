package net.pingfang.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.pingfang.entity.user.UserVo;

public interface UserDao {
	/**
	 * 根据ID获取一条用户数据
	 * @return
	 */
	public UserVo getUserById(int id);
	/**
	 * 根据用户名和密码获取一条用户数据
	 * @return
	 */
	public UserVo getUser(@Param("userName") String userName, @Param("password") String password);
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<UserVo> getListUser();
	/**
	 * 新增用户
	 * @return
	 */
	public int addUser(UserVo user);
	/**
	 * 更新用户
	 * @return
	 */
	public int updateUser(UserVo user);
	/**
	 * 删除用户
	 * @return
	 */
	public int deleteUser(int id);


}
