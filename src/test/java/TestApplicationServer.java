import com.generic.service.GenericService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestApplicationServer {

	private static ApplicationContext context = new FileSystemXmlApplicationContext("D:/Workspaces/SystemFramework/WebRoot/WEB-INF/applicationContext.xml");
	private static GenericService publicService = (GenericService) context.getBean("publicService");

	@Test
	public void sqlParamMap() {
		String sql = "update sys_user set email=:mail where id=:id";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("mail", "123@qq.com");
		parameterMap.put("id", "57");

		int executeUpdateBySql = publicService.executeUpdateSql(sql, parameterMap);
		System.out.println(executeUpdateBySql);
		
	}
	
	@Test
	public void sqlFindParaMap() {
		String sql = "select id, name from sys_user where id=:id and email=:mail";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("mail", "123@qq.com");
		parameterMap.put("id", "57");
		List<Object[]> findBySql = publicService.findSqlList(sql, parameterMap, 1, 10);
		for (Object[] objects : findBySql) {
			System.out.println(objects[0]);
			System.out.println(objects[1]);
		}
		
	}

}
