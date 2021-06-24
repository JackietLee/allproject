package net.pingfang.realm;

//import net.pingfang.dao.web.TmenuMapper;
//import net.pingfang.dao.web.TroleMapper;
import net.pingfang.dao.web.TuserMapper;
//import net.pingfang.dao.web.TuserroleMapper;
import net.pingfang.entity.role.Trole;
import net.pingfang.entity.role.Tuser;
import net.pingfang.entity.systemManage.PermissionsVo;
import net.pingfang.service.systemManage.PermissionsService;
import net.pingfang.service.systemManage.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义Realm
 * @author zjt
 *
 */
public class MyRealm extends AuthorizingRealm{
	@Resource
	private TuserMapper tuserMapper;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionsService permissionsService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		
		String userName=(String) SecurityUtils.getSubject().getPrincipal();
		//根据用户名查询出用户记录
		Example tuserExample=new Example(Tuser.class);
		tuserExample.or().andEqualTo("userName",userName);
		Tuser user=tuserMapper.selectByExample(tuserExample).get(0);
		//获取用户角色
		List<Trole> roleList = roleService.getRoleListByUserId(user.getId());

		Set<String> roles=new HashSet<String>();
		Set<String> permissions=null;
		if(null !=roleList && roleList.size()>0){
			for(Trole role:roleList){
				roles.add(role.getName());
			}
			List<PermissionsVo> permissionsList = permissionsService.getPermissionsListByRole(roleList);
			if(null !=permissionsList && permissionsList.size() >0) {
				permissions=new HashSet<String>();
				String url = null;
				for(PermissionsVo p : permissionsList){
					//info.addStringPermission(p.getName()); // 添加权限
					url = p.getUrl();
					if(null !=url && !"".equals(url)) {
						info.addStringPermission(url); // 添加权限
						permissions.add(url);
					}
				}
			}
		}
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		return info;
	}

	   /**
	 	* 权限认证
		*/
		@Override
		protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
			String userName=(String)token.getPrincipal();
			//User user=userRepository.findByUserName(userName);
			Example tuserExample=new Example(Tuser.class);
			tuserExample.or().andEqualTo("userName",userName);
			Tuser user=tuserMapper.selectByExample(tuserExample).get(0);
			if(user!=null){
				AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xxx");
				return authcInfo;
			}else{
				return null;
			}
	}

}
