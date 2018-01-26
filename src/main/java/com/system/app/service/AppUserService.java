package com.system.app.service;

import com.generic.service.GenericService;
import com.generic.util.core.Dto;

import java.util.Map;

public interface AppUserService extends GenericService  {

	/**
	 * 用户登录
	 * @param dto
	 * @return
	 */
	Map<String, Object> login(Dto dto);

	/**
	 * 根据用户id, 找到用户全部信息
	 * @param id 用户id
	 * @return
	 * @author Yang
	 * @version v1.0
	 * @date 2016年11月21日
	 */
	Map<String, Object> getUserInfo(Long id);

}
