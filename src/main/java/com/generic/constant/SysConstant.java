package com.generic.constant;

/**
 * 系统常量类
 * @author Yang
 * @version v1.0
 * @date 2016年11月17日
 */
public class SysConstant {
	
	public static final String ESCAPE_KEYWORD = "\n";
	
	//用户详细信息
	public static final String USER_DETAIL = "user_detail_";
	
	//存放用户菜单redis key
	public static final String USER_MENU_PREFIX = "user_menu_";
	
	//存放用户权限redis key
	public static final String USER_PERMISSION_PREFIX = "user_permission_";
	
	//存放用户按钮redis key
	public static final String USER_ELEMENT_PREFIX = "user_element_";

	public static final String REDIS_UTIL_LIST_PREFIX = "redis_util_list_prefix_";

	public static final String REDIS_UTIL_MAP_PREFIX = "redis_util_map_prefix_";

	//存放用户角色redis key
	public static final String USER_ROLE_PREFIX = "user_role_";
	
	//存储用户详细信息过期时间
	public static final int USER_EXPIRE_TIME = 10 * 60;

	//用户id信息字段前缀
	public static final String USER_TOKEN = "user_token_";

	//用户id信息过期时间
	public static final int USER_TOKEN_EXPIRE_TIME = 7 * 24 * 3600;

	//系统错误提示语
	public static final String ERROR_MESSAGE = "系统出现错误, 请联系开发人员.";

	//是否是跳转页面
	public static final String FORWARD_PAGE = "forwardpage";

}
