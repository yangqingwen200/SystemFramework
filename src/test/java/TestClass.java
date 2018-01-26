import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试类
 * 
 * @author Administrator
 * 
 *         2015年8月11日 下午3:17:55
 */
public class TestClass {

	@Test
	public void copyCocachPic() {
		String path = "F:/02教练照片";
		File f = new File(path);
		File[] listFiles = f.listFiles();
		for (File file : listFiles) {
			if(file.exists() && file.isDirectory()) {
				File[] listFiles2 = file.listFiles();
				for (File file2 : listFiles2) {
					String fpath = file2.getAbsolutePath();
					if(fpath.toLowerCase().endsWith("jpg")) {
//						movePic(fpath, "F:/02/" + file2.getName());
					}  else {
						System.out.println(fpath);
					}
				}
			}
			
		}
	}
	
	public void movePic(String f, String path) {
		  try {
			  FileInputStream fi=new FileInputStream(f);
			  BufferedInputStream in=new BufferedInputStream(fi);
			  
			  FileOutputStream fo=new FileOutputStream(path);
			  BufferedOutputStream out=new BufferedOutputStream(fo);
			  
			  byte[] buf=new byte[4096];
			  int len=in.read(buf);
			  while(len!=-1) {
			       out.write(buf, 0, len);
			       len=in.read(buf);
			  }
			  out.close();
			  fo.close();
			  in.close();
			  fi.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void strlength() {
		String queryName = "";
		Map map = new HashMap();
		map.put("id", 123);
		map.put("loginname", "456");
		Configuration cfg = new Configuration();  
        //字符串模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();  
        //获得hql或sql模板
        String queryStringTemplate = "select * from sys_user where 1=1 "
        		+ "<#if id??>and id=${id}</#if> "
        		+ "<#if loginname??>and loginname='${loginname}'</#if> ";
        stringTemplateLoader.putTemplate(queryName, queryStringTemplate);  
          
        cfg.setTemplateLoader(stringTemplateLoader);  
        cfg.setDefaultEncoding("UTF-8");  
        Template template;
		try {
			template = cfg.getTemplate(queryName,"UTF-8");
			template.setNumberFormat("#");
			
			StringWriter writer = new StringWriter();   
			
			template.process(map, writer);  
			
			//获得查询字符串
			String queryString = writer.toString().trim();
			System.out.println(queryString);
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
