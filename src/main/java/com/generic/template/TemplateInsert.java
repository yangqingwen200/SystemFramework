package com.generic.template;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author 阳庆文
 * @version v1.0
 * @date 2017年7月21日
 */
public class TemplateInsert {

    private static String JDBC_URL;
    private static String USER;
    private static String PASSWORD;
    private static String JSPPATH;
    private static String ACTIONPATH;
    private static String JSPTEMPLATEPATH;
    private static String ACTIONTEMPLATEPATH;
    
    
    static {
    	try {
			Properties pps = new Properties();
			//idea下需要将配置文件config.properties 手动复制到编译输出的文件夹中, 与TemplateInsert类在同级目录
			pps.load(new InputStreamReader(TemplateInsert.class.getResourceAsStream("config.properties"), "UTF-8"));
			JDBC_URL = String.valueOf(pps.get("JDBC_URL"));
			USER = String.valueOf(pps.get("USER"));
			PASSWORD = String.valueOf(pps.get("PASSWORD"));
			JSPPATH = String.valueOf(pps.get("JSPPATH"));
			ACTIONPATH = String.valueOf(pps.get("ACTIONPATH"));
			JSPTEMPLATEPATH = String.valueOf(pps.get("JSPTEMPLATEPATH"));
			ACTIONTEMPLATEPATH = String.valueOf(pps.get("ACTIONTEMPLATEPATH"));
		} catch (Exception e) {
			System.out.println("读取配置文件错误...");
			System.exit(0);
		}
    }

    public static void main(String[] args) {

    	String moduleName = "Coach";
    	String moduleShowName = "教练";
    	Integer insertPermission = insertPermission(moduleName, moduleShowName); //插入权限
    	insertElement(moduleName, moduleShowName, insertPermission); //插入按钮
    	
    	 // 插入菜单
    	 // 注意: 如果是新建父子两个菜单, 第三个参数传0
    	 // 		如果是仅仅是插入子菜单, 第三个参数传父菜单的id
    	 
    	insertMenu(moduleName, moduleShowName, 95);

    	createJsp(moduleName, moduleShowName);
    	createAction(moduleName, moduleShowName);
    	System.out.println("Run over...");
    	
    	
    }

	/**
	 * 插入权限信息
	 * @param moduleName
	 * @param moduleShowName
	 * @return
	 */
	private static Integer insertPermission(String moduleName, String moduleShowName) {
		String sqlPermission = "insert into sys_permission values (null, ?, ?, ?, ? ,?)";
		Object[] objParent = {moduleShowName + "管理", moduleShowName + "管理", "#", 0, 0};
    	Object[] get = {"查看" + moduleShowName, "查看" + moduleShowName + "页面", getGetModuleNameList(moduleName), 1, 1};
    	Object[] add = {"增加" + moduleShowName, "增加" + moduleShowName, getAddModuleName(moduleName), 1, 0};
    	Object[] edit = {"修改" + moduleShowName, "修改" + moduleShowName, getEditModuleName(moduleName), 1, 0};
    	Object[] delete = {"删除" + moduleShowName, "删除" + moduleShowName, getDeleteModuleName(moduleName), 1, 0};
    	
    	return getPrimaryId(sqlPermission, 3, objParent, get, add, edit, delete);
	}
	
	/**
	 * 插入按钮信息
	 * @param moduleName
	 * @param moduleShowName
	 * @return
	 */
	private static Integer insertElement(String moduleName, String moduleShowName, Object permissionId) {
		String lowerName = captureName(moduleName);
		String sqlElement = "insert into sys_element values (null, ?, ?, ?, 1, ?, ?, ?, " + permissionId + ", now())";
		Object[] objParent = {"#", "#", moduleShowName + "管理", 0, "none", moduleShowName + "管理"};
		
		Object[] add = {lowerName + "_view" + moduleName + "List_add", "commonAdd('${ctxweb}/add" + moduleName + "_" + lowerName + ".do')", "添加" + moduleShowName, 0, "icon-add", "新增"};
		Object[] edit = {lowerName + "_view" + moduleName + "List_edit", "commonEdit('${ctxweb}/edit" + moduleName + "_" + lowerName + ".do')", "编辑" + moduleShowName, 0, "icon-edit", "修改"};
		Object[] delete = {lowerName + "_view" + moduleName + "List_delete", "commonDelete('${ctxweb}/delete" + moduleName + "_" + lowerName + ".do')", "删除" + moduleShowName, 0, "icon-remove", "删除"};
		
		return getPrimaryId(sqlElement, 3, objParent, add, edit, delete);
	}
	
	/**
	 * 插入菜单信息
	 * @param moduleName
	 * @param moduleShowName
	 * @param parentId
	 * @return
	 */
	private static Integer insertMenu(String moduleName, String moduleShowName, Integer parentId) {
		String lowerName = captureName(moduleName);
		String sqlMenu = "insert into sys_menu values (null, ?, ?, ?, 1, ?, now())";
		Object[] objParent = {moduleShowName + "管理", "#", moduleShowName + "管理", parentId};
		
		Object[] add = {moduleShowName + "管理", lowerName + "/" + getJspName(moduleName), moduleShowName + "管理", parentId};
		if(parentId > 0) {
			return getPrimaryId(sqlMenu, 3, add);
		}
		return getPrimaryId(sqlMenu, 3, objParent, add);
	}

	/**
	 * 创建jsp页面
	 * @param moduleName
	 * @param moduleShowName
	 */
	public static void createJsp(String moduleName, String moduleShowName) {
		createFile(moduleName, moduleShowName, JSPTEMPLATEPATH, JSPPATH + getJspName(moduleName));
	}
	
	/**
	 * 创建Action文件
	 * @param moduleName
	 * @param moduleShowName
	 */
	public static void createAction(String moduleName, String moduleShowName) {
		createFile(moduleName, moduleShowName, ACTIONTEMPLATEPATH, ACTIONPATH + moduleName + "Action.java");
	}
	
	private static void createFile(String moduleName, String moduleShowName, String templatePath, String outputPath) {
		String lowerModuleName = captureName(moduleName);
		try {
			File f = new File(templatePath);
			if(f != null && f.exists() && f.isFile()) {
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
				String data = null;
				while((data = br.readLine())!=null) {
					if(data.contains("template")) {
						data = data.replace("template", lowerModuleName);
					}
					if(data.contains("Template")) {
						data = data.replace("Template", moduleName);
					}
					
					if(data.contains("addTemplate")) {
						data = data.replace("addTemplate", getAddModuleName(moduleName));
					}
					if(data.contains("editTemplate")) {
						data = data.replace("editTemplate", getEditModuleName(moduleName));
					}
					if(data.contains("deleteTemplate")) {
						data = data.replace("deleteTemplate", getDeleteModuleName(moduleName));
					}
					if(data.contains("getTemplateList")) {
						data = data.replace("getTemplateList", getGetModuleNameList(moduleName));
					}
					if(data.contains("模板")) {
						data = data.replace("模板", moduleShowName);
					}
					sb.append(data + "\r\n");
				}
				
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));
				writer.write(sb.toString());
				writer.flush();
				writer.close();
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Integer getPrimaryId(String sqlPermission, Integer parentPostion, Object[]... param) {
		Integer permissionId = null;
		Integer parentId = null;
		
		List<Object[]> o = new ArrayList<>();
    	for(Object[] obj : param) {
    		o.add(obj);
    	}
    	for (int i = 0; i < o.size(); i++) {
    		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    		String methodName = trace[2].getMethodName();
    		if("insertElement".equals(methodName) || "insertMenu".equals(methodName)) {
    			try {
    				//避免插入时间create_time相同, 延迟插入数据库
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    			}
    		}
    		Object[] objects = o.get(i);
    		if(i > 0) {
    			objects[parentPostion] = parentId;  //更改父节点
    		}
    		Integer insert = insert(sqlPermission, Arrays.asList(objects));
    		if(i == 0) {
    			parentId = insert;  //第一条数据为插入父节点
    		} 
    		if(i == 1) {
    			permissionId = insert;
    		}
    		System.out.println(insert);
		}
    	return permissionId;
	}
    
    /**
     * 插入数据库
     * @param sql
     * @param param
     * @return
     */
    public static Integer insert(String sql, List<Object> param) {
        Connection conn = null;
        PreparedStatement stmt = null;
        Integer id = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int i = 1;
            for (Object object : param) {
            	stmt.setObject(i, object);
            	i++;
			}
            stmt.executeUpdate(); 
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            
            while (generatedKeys.next()) {
            	id = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return id;
    }
    
    public static String getJspName(String moduleName) {
    	return captureName(moduleName) + "_view" + moduleName + "List.jsp";
    }
    
    public static String getGetModuleNameList(String moduleName) {
    	return "get" + moduleName + "List";
    }
    
    public static String getAddModuleName(String moduleName) {
    	return "add" + moduleName;
    }
    
    public static String getEditModuleName(String moduleName) {
    	return "edit" + moduleName;
    }
    
    public static String getDeleteModuleName(String moduleName) {
    	return "delete" + moduleName;
    }
    
    /**
     * 首字母小写
     * @param name
     * @return
     */
    public static String captureName(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
       return  name;
      
    }
}
