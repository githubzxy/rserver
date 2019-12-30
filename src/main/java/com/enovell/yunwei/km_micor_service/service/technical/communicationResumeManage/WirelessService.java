package com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

public interface WirelessService {
    ResultMsg importWirelessWireless(String publicType, MultipartFile file, String userId, String orgId);
    ResultMsg importWirelessCable(String publicType, MultipartFile file, String userId, String orgId);
    ResultMsg importWirelessRadio(String publicType, MultipartFile file, String userId, String orgId);
    ResultMsg importWirelessIrontower(String publicType, MultipartFile file, String userId, String orgId);
    ResultMsg importWirelessMobile(String publicType, MultipartFile file, String userId, String orgId);
    ResultMsg importWirelessHand(String publicType, MultipartFile file, String userId, String orgId);
}
