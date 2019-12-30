package com.enovell.yunwei.km_micor_service.service;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.enovell.yunwei.km_micor_service.KmMicorServiceApplication;
import com.enovell.yunwei.km_micor_service.service.technical.deviceNameWorkManage.DeviceNameWorkManageServiceImpl;

/**
 * kunmingTXD
 *
 * @author bili
 * @date 18-11-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KmMicorServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserServiceImplTest {
    @Resource(name="userService")
    private UserService service;
    @Resource(name = "deviceNameWorkManageService")
    private DeviceNameWorkManageServiceImpl deviceNameWorkManageService;
    @Test
    public void getUserById() {
        String userId = "8affa0735391f425015483d40be50074";//技术科
        Map<String,Object> user = service.getUserById(userId);
        user.entrySet().forEach(entity->{
            System.out.print(entity.getKey() + " = " + entity.getValue()+",");
        });
    }

    @Test
    public void getOrgbyUserId() {
        String userId = "8affa0735391f425015483d40be50074";//技术科
        Map<String,Object> user = service.getOrgbyUserId(userId);
        user.entrySet().forEach(entity->{
            System.out.print(entity.getKey() + " = " + entity.getValue()+",");
        });
    }
    @Test
    public void getOrgId(){
        String userId = "8affa0735391f425015483d40be50074";//技术科
        String orgId = service.getOrgIdByUser(userId);
        System.out.println("orgId = " + orgId);
    }
    @Test
    public void name() {
    	deviceNameWorkManageService.removeAll();
	}
}