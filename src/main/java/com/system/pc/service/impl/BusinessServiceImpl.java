package com.system.pc.service.impl;

import com.generic.service.GenericServiceImpl;
import com.system.pc.service.BusinessService;

public class BusinessServiceImpl extends GenericServiceImpl implements BusinessService {

    @Override
    public void update() {
        System.out.println(123);
    }
}
