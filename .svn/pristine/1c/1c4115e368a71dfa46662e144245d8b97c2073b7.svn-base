////////////////////////////////////////年月报表命令开始//////////////////////////////////////
var YearMonthWorkAreaStatus={
		//工区年月表编制流程的状态
		WRITE_WORKAREA_NOTWRITED : "-1",         //工区未填报
		WRITE_WORKAREA_UNLOCK : "0",             //段科室已解锁
		WRITE_WORKAREA_DRAFT : "1",              //工区_草稿
		WRITE_WORKAREA_REPORT : "2",     		 //工区_上报
		WRITE_WORKAREA_WORKSHOP_PASS : "3",      //工区_车间通过
		WRITE_WORKAREA_WORKSHOP_FAIL : "4",      //工区_车间不通过
		WRITE_WORKAREA_SEGMENT_FAIL : "6",       //工区_段科室不通过
		WRITE_WORKAREA_SEGMENT_PASS : "7",		 //工区_段科室通过
		
		WRITE_WORKAREA_NOTWRITED_NAME : "工区未填报",             //工区未填报
		WRITE_WORKAREA_UNLOCK_NAME : "段科室已解锁_编制",             //段科室已解锁
		WRITE_WORKAREA_DRAFT_NAME : "草稿_编制",              //工区_草稿
		WRITE_WORKAREA_REPORT_NAME : "工区上报_编制",     		 //工区_上报
		WRITE_WORKAREA_WORKSHOP_PASS_NAME : "车间审核通过_编制",      //工区_车间通过
		WRITE_WORKAREA_WORKSHOP_FAIL_NAME : "车间审核不通过_编制",      //工区_车间不通过
		WRITE_WORKAREA_SEGMENT_FAIL_NAME : "段科室审核不通过_编制",       //工区_段科室不通过
		WRITE_WORKAREA_SEGMENT_PASS_NAME : "段科室审核通过_编制",		 //工区_段科室通过
		
		
		//工区年月表执行流程的状态
		WORKAREA_EXECUTE_REPORT : "102",     		 //工区_上报
		WORKAREA_WORKSHOP_EXECUTE_PASS : "103",      //工区_车间通过
		WORKAREA_WORKSHOP_EXECUTE_FAIL : "104",      //工区_车间不通过
		WORKAREA_SEGMENT_EXECUTE_FAIL : "106",       //工区_段科室不通过
		WORKAREA_SEGMENT_EXECUTE_PASS : "107",		 //工区_段科室通过

		WORKAREA_EXECUTE_REPORT_NAME : "工区上报_执行",     		 //工区_上报
		WORKAREA_WORKSHOP_EXECUTE_PASS_NAME : "车间审核通过_执行",      //工区_车间通过
		WORKAREA_WORKSHOP_EXECUTE_FAIL_NAME : "车间审核不通过_执行",      //工区_车间不通过
		WORKAREA_SEGMENT_EXECUTE_FAIL_NAME : "段科室审核不通过_执行",       //工区_段科室不通过
		WORKAREA_SEGMENT_EXECUTE_PASS_NAME : "段科室审核通过_执行",		 //工区_段科室通过
		
		
		//车间年月表编制流程的状态
		WRITE_WORKSHOP_AUDIT : "32",				 //车间_车间待审核
		WRITE_WORKSHOP_FAIL : "33",				 //车间_车间不通过
		WRITE_WORKSHOP_PASS : "34",				 //车间_车间通过
		WRITE_WORKSHOP_REPORT : "35",			 //车间_上报
		WRITE_WORKSHOP_SEGMENT_FAIL : "36",      //车间_段不通过
		WRITE_WORKSHOP_SEGMENT_PASS : "37",		 //车间_段通过

		WRITE_WORKSHOP_AUDIT_NAME : "车间待审核_编制",				 //车间_车间待审核
		WRITE_WORKSHOP_FAIL_NAME : "车间审核不通过_编制",				 //车间_车间不通过
		WRITE_WORKSHOP_PASS_NAME : "车间审核通过_编制",				 //车间_车间通过
		WRITE_WORKSHOP_REPORT_NAME : "车间上报_编制",			 //车间_上报
		WRITE_WORKSHOP_SEGMENT_FAIL_NAME : "段科室审核不通过_编制",      //车间_段不通过
		WRITE_WORKSHOP_SEGMENT_PASS_NAME : "段科室审核通过_编制",		 //车间_段通过
		
		//车间年月表执行流程的状态
		WORKSHOP_EXECUTE_AUDIT : "302",				 //车间_车间待审核
		WORKSHOP_EXECUTE_FAIL : "303",				 //车间_车间不通过
		WORKSHOP_EXECUTE_PASS : "304",				 //车间_车间通过
		WORKSHOP_EXECUTE_REPORT : "305",			 //车间_上报
		WORKSHOP_SEGMENT_EXECUTE_FAIL : "306",      //车间_段不通过
		WORKSHOP_SEGMENT_EXECUTE_PASS : "307",		 //车间_段通过

		WORKSHOP_EXECUTE_AUDIT_NAME : "车间待审核_执行",				 //车间_车间待审核
		WORKSHOP_EXECUTE_FAIL_NAME : "车间审核不通过_执行",				 //车间_车间不通过
		WORKSHOP_EXECUTE_PASS_NAME : "车间审核通过_执行",				 //车间_车间通过
		WORKSHOP_EXECUTE_REPORT_NAME : "车间上报_执行",			 //车间_上报
		WORKSHOP_SEGMENT_EXECUTE_FAIL_NAME : "段科室审核不通过_执行",      //车间_段不通过
		WORKSHOP_SEGMENT_EXECUTE_PASS_NAME : "段科室审核通过_执行",		 //车间_段通过
		statusName:function(value){
			switch(value){
			case this.WRITE_WORKAREA_NOTWRITED:
				return this.WRITE_WORKAREA_NOTWRITED_NAME;
			case this.WRITE_WORKAREA_UNLOCK:
				return this.WRITE_WORKAREA_UNLOCK_NAME;
			case this.WRITE_WORKAREA_DRAFT:
				return this.WRITE_WORKAREA_DRAFT_NAME;
			case this.WRITE_WORKAREA_REPORT:
				return this.WRITE_WORKAREA_REPORT_NAME;
			case this.WRITE_WORKAREA_WORKSHOP_PASS:
				return this.WRITE_WORKAREA_WORKSHOP_PASS_NAME;
			case this.WRITE_WORKAREA_WORKSHOP_FAIL:
				return this.WRITE_WORKAREA_WORKSHOP_FAIL_NAME;
			case this.WRITE_WORKAREA_SEGMENT_PASS:
				return this.WRITE_WORKAREA_SEGMENT_PASS_NAME;
			case this.WRITE_WORKAREA_SEGMENT_FAIL:
				return this.WRITE_WORKAREA_SEGMENT_FAIL_NAME;
			case this.WRITE_WORKSHOP_AUDIT:
				return this.WRITE_WORKSHOP_AUDIT_NAME;
			case this.WRITE_WORKSHOP_FAIL:
				return this.WRITE_WORKSHOP_FAIL_NAME;
			case this.WRITE_WORKSHOP_PASS:
				return this.WRITE_WORKSHOP_PASS_NAME;
			case this.WRITE_WORKSHOP_REPORT:
				return this.WRITE_WORKSHOP_REPORT_NAME;
			case this.WRITE_WORKSHOP_SEGMENT_PASS:
				return this.WRITE_WORKSHOP_SEGMENT_PASS_NAME;
			case this.WRITE_WORKSHOP_SEGMENT_FAIL:
				return this.WRITE_WORKSHOP_SEGMENT_FAIL_NAME;
			
			
			case this.WORKAREA_EXECUTE_REPORT:
				return this.WORKAREA_EXECUTE_REPORT_NAME;
			case this.WORKAREA_WORKSHOP_EXECUTE_PASS:
				return this.WORKAREA_WORKSHOP_EXECUTE_PASS_NAME;
			case this.WORKAREA_WORKSHOP_EXECUTE_FAIL:
				return this.WORKAREA_WORKSHOP_EXECUTE_FAIL_NAME;
			case this.WORKAREA_SEGMENT_EXECUTE_PASS:
				return this.WORKAREA_SEGMENT_EXECUTE_PASS_NAME;
			case this.WORKAREA_SEGMENT_EXECUTE_FAIL:
				return this.WORKAREA_SEGMENT_EXECUTE_FAIL_NAME;
			case this.WORKSHOP_EXECUTE_AUDIT:
				return this.WORKSHOP_EXECUTE_AUDIT_NAME; 
			case this.WORKSHOP_EXECUTE_FAIL:
				return this.WORKSHOP_EXECUTE_FAIL_NAME;
			case this.WORKSHOP_EXECUTE_PASS:
				return this.WORKSHOP_EXECUTE_PASS_NAME;
			case this.WORKSHOP_EXECUTE_REPORT:
				return this.WORKSHOP_EXECUTE_REPORT_NAME;
			case this.WORKSHOP_SEGMENT_EXECUTE_PASS:
				return this.WORKSHOP_SEGMENT_EXECUTE_PASS_NAME;
			case this.WORKSHOP_SEGMENT_EXECUTE_FAIL:
				return this.WORKSHOP_SEGMENT_EXECUTE_FAIL_NAME;
			}
		}
};
////////////////////////////////////////年月报表命令结束//////////////////////////////////////
////////////////////////////////////////技术支持中心年月表命令开始/////////////////////////////////
var YearMonthTechStatus={
		//工区年月表编制流程的状态
		WRITE_WORKAREA_NOTWRITED : "-1",         //工区未填报
		WRITE_WORKAREA_UNLOCK : "0",             //段科室已解锁
		WRITE_WORKAREA_DRAFT : "1",              //工区_草稿
		WRITE_WORKAREA_REPORT : "2",     		 //工区_上报
		WRITE_WORKAREA_SEGMENT_PASS : "3",      //工区_段科室通过
		WRITE_WORKAREA_SEGMENT_FAIL : "4",      //工区_段科室不通过
		
		WRITE_WORKAREA_NOTWRITED_NAME : "工区未填报",             //工区未填报
		WRITE_WORKAREA_UNLOCK_NAME : "段科室已解锁_编制",             //段科室已解锁
		WRITE_WORKAREA_DRAFT_NAME : "草稿_编制",              //工区_草稿
		WRITE_WORKAREA_REPORT_NAME : "工区上报_编制",     		 //工区_上报
		WRITE_WORKAREA_SEGMENT_PASS_NAME : "段科室审核通过_编制",      //工区_段科室通过
		WRITE_WORKAREA_SEGMENT_FAIL_NAME : "段科室审核不通过_编制",      //工区_段科室不通过
		
		
		//工区年月表执行流程的状态
		WORKAREA_EXECUTE_REPORT : "102",     		 //工区_上报
		WORKAREA_SEGMENT_EXECUTE_PASS : "103",      //工区_段科室通过
		WORKAREA_SEGMENT_EXECUTE_FAIL : "104",      //工区_段科室不通过

		WORKAREA_EXECUTE_REPORT_NAME : "工区上报_执行",     		 //工区_上报
		WORKAREA_SEGMENT_EXECUTE_PASS_NAME : "段科室审核通过_执行",      //工区_段科室通过
		WORKAREA_SEGMENT_EXECUTE_FAIL_NAME : "段科室审核不通过_执行",      //工区_段科室不通过
		
		
		//车间年月表编制流程的状态
		WRITE_SEGMENT_AUDIT : "32",				 //段科室_段科室待审核
		WRITE_SEGMENT_FAIL : "33",				 //段科室_段科室不通过
		WRITE_SEGMENT_PASS : "34",				 //段科室_段科室通过

		WRITE_SEGMENT_AUDIT_NAME : "段科室待审核_编制",				 //段科室_段科室待审核
		WRITE_SEGMENT_FAIL_NAME : "段科室审核不通过_编制",				 //段科室_段科室不通过
		WRITE_SEGMENT_PASS_NAME : "段科室审核通过_编制",				 //段科室_段科室通过
		
		//车间年月表执行流程的状态
		SEGMENT_EXECUTE_AUDIT : "302",				 //段科室_段科室待审核
		SEGMENT_EXECUTE_FAIL : "303",				 //段科室_段科室不通过
		SEGMENT_EXECUTE_PASS : "304",				 //段科室_段科室通过

		SEGMENT_EXECUTE_AUDIT_NAME : "段科室待审核_执行",				 //段科室_段科室待审核
		SEGMENT_EXECUTE_FAIL_NAME : "段科室审核不通过_执行",				 //段科室_段科室不通过
		SEGMENT_EXECUTE_PASS_NAME : "段科室审核通过_执行",				 //段科室_段科室通过
		statusName:function(value){
			switch(value){
			case this.WRITE_WORKAREA_NOTWRITED:
				return this.WRITE_WORKAREA_NOTWRITED_NAME;
			case this.WRITE_WORKAREA_UNLOCK:
				return this.WRITE_WORKAREA_UNLOCK_NAME;
			case this.WRITE_WORKAREA_DRAFT:
				return this.WRITE_WORKAREA_DRAFT_NAME;
			case this.WRITE_WORKAREA_REPORT:
				return this.WRITE_WORKAREA_REPORT_NAME;
			case this.WRITE_WORKAREA_SEGMENT_PASS:
				return this.WRITE_WORKAREA_SEGMENT_PASS_NAME;
			case this.WRITE_WORKAREA_SEGMENT_FAIL:
				return this.WRITE_WORKAREA_SEGMENT_FAIL_NAME;
			case this.WRITE_SEGMENT_AUDIT:
				return this.WRITE_SEGMENT_AUDIT_NAME;
			case this.WRITE_SEGMENT_FAIL:
				return this.WRITE_SEGMENT_FAIL_NAME;
			case this.WRITE_SEGMENT_PASS:
				return this.WRITE_SEGMENT_PASS_NAME;
			case this.WORKAREA_EXECUTE_REPORT:
				return this.WORKAREA_EXECUTE_REPORT_NAME;
			case this.WORKAREA_SEGMENT_EXECUTE_PASS:
				return this.WORKAREA_SEGMENT_EXECUTE_PASS_NAME;
			case this.WORKAREA_SEGMENT_EXECUTE_FAIL:
				return this.WORKAREA_SEGMENT_EXECUTE_FAIL_NAME;
			case this.SEGMENT_EXECUTE_AUDIT:
				return this.SEGMENT_EXECUTE_AUDIT_NAME; 
			case this.SEGMENT_EXECUTE_FAIL:
				return this.SEGMENT_EXECUTE_FAIL_NAME;
			case this.SEGMENT_EXECUTE_PASS:
				return this.SEGMENT_EXECUTE_PASS_NAME;
			}
		}
};
///////////////////////////////////////技术支持中心年月表命令结束////////////////////////////////