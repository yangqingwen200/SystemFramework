package com.system.pc.action.business;

import com.generic.redis.RedisUtil;
import com.system.common.BasePcAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller("system.pc.action.business.timeAction")
@Scope("prototype")
public class TimeAction extends BasePcAction {

    private static final long serialVersionUID = 1081681641584848916L;
    private static final Logger LOG = LoggerFactory.getLogger(TimeAction.class);
    private String index = "business";
    private String business = "time";

    public String list() {
        try {
            //表格数据
            String sql = "select id, name, link_man, link_tel, lng, lat, DATE_FORMAT(create_date, '%Y-%m-%d') AS create_date, address from ds_driving_school where disabled=1 ";
            String name = dto.getAsStringTrim("name");
            BigDecimal schoolId = dto.getAsBigDecimal("schoolId");
            if(name != null) {
                sql += "and link_man like ? ";
                fuzzySearch.add("%" + name + "%");
            }

            if(schoolId != null) {
                sql += "and id = ? ";
                fuzzySearch.add(schoolId);
            }
            page = this.publicService.pagedQuerySql(page, sql, fuzzySearch.toArray());

            //搜索栏下拉框数据
            String findSchool = "SELECT id, name FROM `ds_driving_school` WHERE disabled=? AND STATUS =?";
            List<Object> sqlListMap = RedisUtil.getListByKey("schoolList", findSchool, new Object[]{1, 3}, 3 * 3600);
            this.request.setAttribute("schoolList", sqlListMap);
        } catch (Exception e) {
            this.checkActionError(e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
