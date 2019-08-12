package com.iwei.wxmanagement;

import com.iwei.wxmanagement.util.wxInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "测试接口")
@RestController
public class testBean {

    @Autowired
    private wxInfo wxInfo;

    @ApiOperation(value = "测试hello" ,  notes="测试hello")
    @RequestMapping(value="/testDemo",method= RequestMethod.GET)
    public String testDemo() {
        String name = wxInfo.appID;

        return name;
    }
}
