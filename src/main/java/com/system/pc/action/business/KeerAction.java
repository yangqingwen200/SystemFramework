package com.system.pc.action.business;

import com.generic.constant.SQLConstant;
import com.system.common.BasePcAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller("system.pc.action.business.keerAction")
@Scope("prototype")
public class KeerAction extends BasePcAction {

    private static final long serialVersionUID = 1081681641584848916L;
    private static final Logger LOG = LoggerFactory.getLogger(KeerAction.class);
    private String index = "business";
    private String business = "keer";

    public String list1() {

        return SUCCESS;
    }

    public String list() {
        long total = 0;
        List<?> rowsList = new ArrayList<Object>();
        try {
            if (isGetRequest) {
                return SUCCESS;
            }
            page = this.publicService.pagedQuerySqlFreemarker(SQLConstant.PC_FIND_KEER_DRIVINGSCHOOL, dto);
            total = page.getTotalNum();
            rowsList = page.getContent();
        } catch (Exception e) {
            this.checkException(e);
        }
        json.accumulate("total", total);
        json.accumulate("rows", rowsList);
        this.printString(json.toString());
        return NONE;
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
