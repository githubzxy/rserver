package com.enovell.yunwei.km_micor_service.service.technical.communicationResumeManage;

import org.springframework.web.multipart.MultipartFile;

import com.enovell.yunwei.km_micor_service.dto.ResultMsg;

public interface LineService {
	
    public ResultMsg importLineLight(String publicType, MultipartFile file, String userId, String orgId);
  
    public ResultMsg importLineElectricity(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importLineLine(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importLineLineAndLight(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importLinePipeline(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importLineHole(String publicType, MultipartFile file, String userId, String orgId);
    public ResultMsg importLineOther(String publicType, MultipartFile file, String userId, String orgId);
}

