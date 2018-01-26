package com.system.pc.action.index;

import com.system.common.BasePcAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller("system.pc.action.index.indexAction")
@Scope("prototype")
public class IndexAction extends BasePcAction {

    private static final long serialVersionUID = 1081681641584848916L;
    private static final List<String> bus = new ArrayList<>();
    private String index = "index";

    public String index() {
        return "index";
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
