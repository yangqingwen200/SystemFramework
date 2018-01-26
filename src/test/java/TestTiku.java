import com.generic.util.core.BL3Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;

/**
 * 测试类
 * 
 * @author Administrator
 * 
 *         2015年8月11日 下午3:17:55
 */
public class TestTiku {

	@Test
	public void testMethod() {
		String numToRMBStr = BL3Utils.numToRMBStr(2356.32);
		System.out.println(numToRMBStr);

		System.out.println(BL3Utils.getCurrentTime());
		System.out.println(BL3Utils.getCurrentTimeAsNumber());
		System.out.println(BL3Utils.getCurrentTime("yyyy-MM-dd"));
		System.out.println(BL3Utils.getCurDate());
		System.out.println(BL3Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
		System.out.println(BL3Utils.getCurrentTimestamp());
	}

	@Test
	public void testStringUtils() {
		System.out.println(RandomUtils.nextInt(100, 999));
		System.out.println(RandomStringUtils.random(10));
		System.out.println(RandomStringUtils.randomNumeric(3));
		System.out.println(RandomStringUtils.randomAscii(10));
		System.out.println(RandomStringUtils.randomAlphanumeric(100));
		System.out.println(StringUtils.substringAfter("121.32", "."));
	}

	@Test
	public void calauteList() {
		/*String encodeBase64 = EncodeUtils.encodeBase64("4564561325123123"
				.getBytes());
		System.out.println(encodeBase64);
		byte[] decodeBase64 = EncodeUtils.decodeBase64(encodeBase64);
		System.out.println(new String(decodeBase64));*/
		String dir = "D:\\apache-tomcat-7.0.62\\logs\\jroo\\AccessLogFile.log.2016-05-20";
		dir  = dir.substring(dir.lastIndexOf("\\") + 1);
		System.out.println(dir);
	}

	@Test
	public void testDom4j() {
		try {
			// 创建saxReader对象
			SAXReader reader = new SAXReader();
			// 通过read方法读取一个文件 转换成Document对象
			Document document;
			document = reader.read(new File("src/query.xml"));

			// 获取根节点元素对象
			Element node = document.getRootElement();
			Element element = node.element("msLog");
			System.out.println(element.getTextTrim());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void connTiku(String title, String answer, String riAnswer, String lawer, int post, String img, Integer type, Integer subject, String belong) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiku", "root", "root");
			PreparedStatement stmt = conn.prepareStatement("insert into four values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, title);
			stmt.setString(2, answer);
			stmt.setString(3, riAnswer);
			stmt.setString(4, lawer);
			stmt.setInt(5, post);
			stmt.setString(6, img);
			stmt.setInt(7, type);
			stmt.setInt(8, subject);
			stmt.setString(9, belong);
			stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 *  科目一试题
	 * 
	 *  2016年5月5日 上午9:33:24
	 */
	@Test
	public void  tikuone() {
		for(int i=2600001; i<2601231; i++) {
			BufferedReader in = null;
			
			URL realUrl;
			try {
				realUrl = new URL("http://tieba.jxedt.com/posts_" + i + ".html#BestAnswer");
				URLConnection conn = realUrl.openConnection(); // 打开和URL之间的连接
				
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				String title = null;
				String answer = null;
				String riAnser = null;
				String lawer = null;
				String img = null;
				while ((line = in.readLine()) != null) {
					if(line.contains("<title>"))  {
						title = line.substring(7, line.lastIndexOf("_"));
						System.out.println(title);
					}
					if(line.contains("firstcontent"))  {
						riAnser = line.substring(line.indexOf("<b>") + 3, line.lastIndexOf("</b>"));
						System.out.println(riAnser);
						if("正确".equals(riAnser) || "错误".equals(riAnser)) {
							answer = "";
							img = "";
						} else {
							answer = line.substring(line.indexOf("A"), line.lastIndexOf("答案："));
							String aa = answer;
							answer = answer.replace("<br><br>", "<br>");
							answer = answer.replace("<br><br>", "<br>");
							if(answer.contains("<img")) {
								answer = answer.substring(0, answer.indexOf("<img"));
								img = aa.substring(aa.indexOf("src=\"")+5, aa.indexOf(".jpg")+4);
							} else {
								img = "";
							}
						}
						System.out.println(answer);
					}
					
					String key = "<div class=\"bcon\">";
					if(line.contains(key))  {
						int last = line.lastIndexOf("</div>");
						if(last != -1) {
							lawer = line.substring(line.indexOf(key) + key.length(), last);
						} else {
							lawer = line.substring(line.indexOf(key) + key.length());
						}
						System.out.println(lawer);
					}
					
				}
				if(title != null && answer != null && riAnser != null && lawer != null) {
//					this.connTiku(title, answer, riAnser, lawer, i, img);
					System.out.println(i);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Test
	public void  tikufour() {
			BufferedReader in = null;
			
			URL realUrl;
			try {
				realUrl = new URL("http://file.open.jiakaobaodian.com/tiku/res/880.jpg");
				URLConnection conn = realUrl.openConnection(); // 打开和URL之间的连接
				
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.println("this is not file");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	}
	
	@Test
	public void re() {
		
		String title = "<title>考驾照五次没过怎么办_科目二小路考_驾考宝典</title>";
		title = title.substring(title.indexOf("<title>") + 7, title.indexOf("_"));
		System.out.println(title);
		String str = "http://image.cms.jiaxiaozhijia.com/jiaxiao-cms/2016/04/27/18/3a23461d3f1d4eada510358aada405f3_448X294.jpg\" alt=\"\">";
		String src = str.substring(0, str.indexOf("\""));
		System.out.println(src);
	}
	
	@Test
	public void  tikufourjiakaobaodian() {
		BufferedReader in = null;
		/*
		1-1065
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=909800
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=986100
		
		1066-1080 
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1152400
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1165800
		
		
		1081-1115
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=986300
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=989700
		
		1116-1121
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1153600
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1154100
		*/
		/*
		http://www.cnblogs.com/luotinghao/p/3800054.html  HtmlUnit
		
		
			
		java爬虫项目，如何获取js执行后的完整网页源代码	http://bbs.csdn.net/topics/390040684
			*/
		try {
			for(int i=1153600; i<=1154100; i+=100) {
				URL realUrl = new URL("http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=" + i);
				URLConnection conn = realUrl.openConnection(); // 打开和URL之间的连接
				conn.addRequestProperty("Host", "www.jiakaobaodian.com");
				conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
				conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
				conn.addRequestProperty("Cookie", "USERINFO=%7B%22cityCode%22%3A%22440300%22%2C%22course%22%3A%22kemu3%22%2C%22daihao%22%3A%22c1%22%2C%22vary%22%3A1%7D; pgv_pvi=35992576; pgv_si=s1108407296; Hm_lvt_b931ce72adfb8fbad984e048bfe1a945=1463404092; Hm_lpvt_b931ce72adfb8fbad984e048bfe1a945=1463404092");
				
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				String key = "<div class=\"for-oes hide\" data-item=\"oes-container\">";
				while ((line = in.readLine()) != null) {
					if(line.contains(key)) {
						String asn = line.substring(line.indexOf("<h2>"), line.indexOf("</h4>"));
						String quest = asn.substring(asn.indexOf("<h2>") + 4, asn.indexOf("</h2>"));
						String answ_opt = asn.substring(asn.indexOf("<p>"), asn.lastIndexOf("</p>"));
						String lawer = asn.substring(asn.indexOf("<h4>"));
						this.connTiku(quest, answ_opt, "", lawer, i, "", 1, 4, "1");
						System.out.println(asn);
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void  tikufoursVedio() {
		BufferedReader in = null;
		/*
		1-1065
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=909800
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=986100
		
		1066-1080 
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1152400
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1165800
		
		
		1081-1115
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=986300
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=989700
		
		1116-1121
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1153600
		http://www.jiakaobaodian.com/mnks/exercise/0-c1-kemu3-shenzhen.html?id=1154100
		 */
		/*
		http://www.cnblogs.com/luotinghao/p/3800054.html  HtmlUnit
		
		
			
		java爬虫项目，如何获取js执行后的完整网页源代码	http://bbs.csdn.net/topics/390040684
		 */
		String url = "";
		URL realUrl;
		URLConnection conn;
		for(int i=1153600; i<=1154100; i+=100) {
			boolean flag = false;
			try {
				url = "http://file.open.jiakaobaodian.com/tiku/res/" + i + ".mp4";
				realUrl = new URL(url);
				conn = realUrl.openConnection(); // 打开和URL之间的连接
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				flag = true;
			} catch (Exception e) {
				try {
					System.out.println("找不到视频");
					url = "http://file.open.jiakaobaodian.com/tiku/res/" + i + ".jpg";
					realUrl = new URL(url);
					conn = realUrl.openConnection(); // 打开和URL之间的连接
					in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					flag = true;
				} catch (Exception e1) {
					System.out.println("找不到图片");
				}
			}
			
			if(flag) {
				this.connTikufour(i, url);
				System.out.println(url);
			}
		}
	}
	
	public void connTikufour(int post, String img) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiku", "root", "root");
			PreparedStatement stmt = conn.prepareStatement("update four set img=? where postion=?");
			stmt.setString(1, img);
			stmt.setInt(2, post);
			stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void replaceSpan() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiku1", "root", "root");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select id, lawer from four where lawer like '%span%'");
			while(rs.next()) {
				String string = rs.getString(2);
				String span = string.substring(string.indexOf("<"), string.lastIndexOf(">") + 1);
				
				string = string.replace(span, "");
				System.out.println(string);
				PreparedStatement stmt = conn.prepareStatement("update four set lawer=? where id=?");
				stmt.setString(1, string);
				stmt.setInt(2, rs.getInt(1));
				stmt.executeUpdate();
				
			}
		} catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void getFilePro() {
		File f = new File("F://daocheruku.mp4");
		long totalSpace = f.getTotalSpace();
		System.out.println(totalSpace);
		System.out.println(totalSpace/1024/1024/100);
	}

	@Test
	public void  tikuTwoSkill() {
		BufferedReader in = null;
		try {
			
			for(int i=66400; i<66452; i++) {
				URL realUrl = new URL("http://www.jiakaobaodian.com/news/detail/" + i + ".html");
				URLConnection conn = realUrl.openConnection(); // 打开和URL之间的连接
				conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
				conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				StringBuffer sb = new StringBuffer("<!DOCTYPE html>");
				sb.append("<html><head>");
				sb.append("<meta name=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body>");
				
				String img1 = "";
				int flag = 2;
				String img2 = "";
				String file = "";
				String line;
				String title = "";
				String key = "<div class=\"article-content\">";
				while ((line = in.readLine()) != null) {
					if(line.contains("<title>"))  {
						title = line.substring(line.indexOf("<title>") + 7, line.indexOf("_"));
						if(title.contains("科目三")) {
							file = "F:/skillthree/" + i + ".html";
							flag = 3;
						} else if(title.contains("科目二")) {
							file = "F:/skilltwo/" + i + ".html";
						} else {
							file = "F:/skillother/" + i + ".html";
							flag = 4;
						}
						File f = new File(file);
						if(!f.exists()) {
							f.createNewFile();
						}
						sb.append("<h1>" + title + "</h1>");
						sb.append("<div>" + BL3Utils.getCurDate("yyyy-MM-dd HH:mm:ss") + "</div>");
						
					}
					if(line.contains(key) && line.contains("<div class=\"article-fotter\">")) {
						String cont = line.substring(line.indexOf(key) + key.length(), line.indexOf("<div class=\"article-fotter\">") - 7);
						sb.append(cont);
						
						if(cont.contains("src=")) {
							String img11 = cont.substring(cont.indexOf("src=") + 5);
							img1 = img11.substring(0, img11.indexOf("\""));
							System.out.println(img1);
							
							if(img11.contains("</img>")) {
								String img22 = cont.substring(img11.indexOf("</img>"));
								if(img22.contains("src=")) {
									String img222 = cont.substring(img22.indexOf("src=") + 5);
									img2 = img222.substring(0, img222.indexOf("\""));
									System.out.println(img2);
								}
							}
						}
						break;
					}
					
				}
				
				sb.append("</body>");
				sb.append("</html>");
				PrintWriter pw = new PrintWriter(new FileWriter(file));  
				pw.write(sb.toString());
				pw.flush();
				pw.close();
				
				if(flag != 4) {
					//this.twoSkillConn(flag, title, i + ".html", img1, "", "");
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void twoSkillConn() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.199:3306/tiku1?characterEncoding=utf-8", "dev", "123456");
			PreparedStatement stmt = conn.prepareStatement("select id, answer, lawer from ds_tk_comm");
			ResultSet executeQuery = stmt.executeQuery();
			while (executeQuery.next()) {
				int int1 = executeQuery.getInt(1);
				String aswer = executeQuery.getString(2);
				String lawer = executeQuery.getString(3);
				if(aswer.contains(lawer) && int1 != 2871) {
					System.out.println(int1);
					System.out.println(aswer);
					System.out.println(lawer);
					String result = aswer.substring(0, aswer.indexOf(lawer)-4);
					System.out.println(result);
					PreparedStatement stmt1 = conn.prepareStatement("update ds_tk_comm set answer=? where id=?");
					stmt1.setString(1, result);
					stmt1.setInt(2, int1);
					stmt1.executeUpdate();
				}
				
			}
				
		} catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void updateSchoolLngLat() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.198:3306/dap?characterEncoding=utf-8", "root", "root");
			PreparedStatement stmt = conn.prepareStatement("select id, name, address from ds_driving_school");
			ResultSet executeQuery = stmt.executeQuery();
			while (executeQuery.next()) {
				int id = executeQuery.getInt(1);
				String name = executeQuery.getString(2);
				String address = executeQuery.getString(2);
				System.out.println(id + ": " + name + ": " + address);
				
			}
			
		} catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
