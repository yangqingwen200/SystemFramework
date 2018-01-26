import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPagination {

	/**
	 * 分页导航显示, 连续显示数字, 模拟Mybatis PageHepler插件功能
	 *
	 * 连续显示页数固定5页
	 */
	@Test
	public void testPageFive() {
		
		List<Object> list  = new ArrayList<>();
		
		int continuePage = 5;  //连续显示页数固定5页
		int pageNow = 11;
		int pageCount = 12;
		
		
		System.out.println("pageNow: " + pageNow + ", pageCount: " + pageCount);
		this.checkPageMore(list, pageNow, pageCount,1);
		
		if(pageNow > 2) {
			if(pageCount - pageNow > continuePage/2) {
				list.add(pageNow - 2);
				list.add(pageNow - 1);
				list.add(pageNow);
				list.add(pageNow + 1);
				list.add(pageNow + 2);
			}
			if(pageCount - pageNow < continuePage/2) {
				for (int i = 0; i < pageCount - pageNow; i++) {
					for (int j = continuePage; j >0; j--) {
						list.add(pageNow - j + i + 2);
					}
				}
			}
			if(pageCount - pageNow == continuePage/2) {
				list.add(pageNow - 2);
				list.add(pageNow - 1);
				list.add(pageNow);
				for (int i = 0; i < pageCount - pageNow; i++) {
					list.add(pageNow + i + 1);
				}
			}
			
			if(pageCount == pageNow) {
				for (int i = continuePage; i > 0; i--) {
					list.add(pageNow - i + 1);
				}
			}
			
		} else {
			if(pageNow == 1) {
				if(pageCount >= continuePage) {
					for (int i = 0; i < continuePage; i++) {
						list.add(i + 1);
					}
				} else {
					for (int i = 0; i < pageCount; i++) {
						list.add(i + 1);
					}
				}
			}
			if(pageNow == 2) {
				list.add(pageNow - 1);
				list.add(pageNow);
				if(pageCount >= continuePage) {
					for (int i = 0; i < continuePage - pageNow; i++) {
						list.add(i + 1 + pageNow);
					}
				} else {
					for (int i = 0; i < pageCount - pageNow; i++) {
						list.add(i + 1 + pageNow);
					}
				}
			}
		}

		this.checkPageMore(list, pageNow, pageCount,2);
		System.out.println(list);
	}


	/**
	 * 分页导航显示, 连续显示数字, 模拟Mybatis PageHepler插件功能
	 *
	 * 连续显示页数随意, 改变continuePage参数值即可.
	 *
	 * 始终以pageNow为中心, 两边等值扩展
	 */
	@Test
	public void testPageVar() {
		
		List<Object> list  = new ArrayList<>();
		
		int continuePage = 9;  //连续显示页数, 可以为变量
		int pageNow = 1;
		int pageCount = 6;
		
		if(continuePage <= 0 || continuePage % 2 == 0) {
			System.out.println("连续显示页数必须为奇数且大于0.");
			return;
		}
		
		if(pageNow > pageCount) {
			System.out.println("当前页数必须小于等于总页数.");
			return;
		}
		
		if(pageCount == 0) {
			pageCount = 1;
		}
		
		System.out.println("continuePage: " + continuePage + ", pageNow: " + pageNow + ", pageCount: " + pageCount);

		//是否为首页  是否有上一页
		this.checkPageMore(list, pageNow, pageCount ,1);

		if(pageCount <= continuePage) {
			for (int i = 0; i < pageCount; i++) {
				list.add(i + 1);
			}
		} else {
			int half = continuePage / 2;
			
			if(pageCount - pageNow >= half) {
				if(pageNow <= half + 1) {
					for (int i = 0; i < continuePage; i++) {
						list.add(i + 1);
					}
				} else {
					for (int i = 0; i < half; i++) {
						list.add(pageNow - half + i);
					}
					list.add(pageNow);
					for (int i = 0; i < half; i++) {
						list.add(pageNow + i + 1);
					}
				}
			} else {
				int end = pageCount - pageNow;
				for (int i = continuePage - end; i > 0; i--) {
					list.add(pageNow - i + 1);
				}
				for (int i = 0; i < end; i++) {
					list.add(pageNow + i + 1);
				}
			}
		}

		this.checkPageMore(list, pageNow, pageCount,2);
		System.out.println(list);
	}

	@Test
	public void gettestPageVar2() {
		testPageVar2(15, 20, 10);
	}

	public void testPageVar2(int pageNow, int pageCount, int continuePage) {
		List<Object> list = new ArrayList();
		Integer falg = 0;

		if(continuePage > pageCount) {
			continuePage = pageCount;
		}

		this.checkPageMore(list, pageNow, pageCount,1);
		if (pageNow > pageCount){
			pageNow = pageCount;
		}
		if (pageNow > (continuePage/2 + 1) && pageNow <= (pageCount - continuePage/2)) {

			falg = pageNow - (continuePage/2 + 1);

		}else if (pageNow > (pageCount - continuePage/2)){

			falg = pageNow -(continuePage - (pageCount - pageNow));

		}

		for (int i = 0; i < continuePage; i++) {
			falg += 1;
			list.add(falg);
		}

		this.checkPageMore(list, pageNow, pageCount,2);
		System.out.println(list);
	}

	private void checkPageMore(List<Object> list, Integer pageNow, Integer pageCount, Integer flag) {
		if(flag == 1) {
			if(pageNow == 1) {
				list.add(false);  //是否为首页
				list.add(false);  //是否有上一页
			} else {
				list.add(true);
				list.add(true);
			}
		}
		if(flag == 2) {
			if(pageNow == pageCount) {
				list.add(false);  //是否还有下一页
				list.add(false);  //是否为末页
			} else {
				list.add(true);
				list.add(true);
			}
		}
	}

}
