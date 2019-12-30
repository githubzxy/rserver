/**
 * 点外维修申请数据详情
 * @author yangsy
 * @date 19-1-21
 */
define('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyDetail',
		[
		 	'bui/common',
		 	'common/data/PostLoad',
		 	'common/form/FormContainer',
		 	'common/uploader/ViewUploader',
		 ],function(r){
	var BUI = r('bui/common'),
		ViewUploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var PointOuterMaintainApplyDetail = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
			//显示数据
			_self._getShowData();
			_self._getLines();//获取线别下拉选数据
			_self._initSelect();
		},
		bindUI : function(){
			var _self = this;
			//定义按键
			var buttons = [
                {
                  text:'关闭',
                  elCls : 'button',
                  handler : function(){
                	  if(this.onCancel() !== false){
				        	this.close();
				        }
                  }
                }
              ];
			_self.set('buttons',buttons);
		},
		
		_initSelect: function(){
			$("#formContainer #lineType").append("<option  value='0'>高铁</option>");
			$("#formContainer #lineType").append("<option  value='1'>普铁</option>");
        },
        
        /**
         * 获取线别
         */
        _getLines:function(){
        	var _self=this;
        	$.ajax({
        		url:'/kmms/networkManageInfoAction/getLines',
        		type:'post',
        		dataType:"json",
        		success:function(res){
        			$("#formContainer #lineName").append("<option  value=''>请选择</option>");
        			for(var i=0;i<res.length;i++){
        				$("#formContainer #lineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
        			}
        		}
        	});
        },
        
		/**
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/pointOuterMaintainApplyAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(result){
					var data = result.data;
					if(data){
						$("#formContainer #unit").val(data.unit);
                        $("#formContainer #serial").val(data.serial);
                        $('#formContainer #workTime').val(data.workTime);
            		    $('#formContainer #lineName').val(data.lineName);
            		    $('#formContainer #section').val(data.section);
            		    $('#formContainer #station').val(data.station);
            		    $('#formContainer #workPrincipal').val(data.workPrincipal);
            		    $('#formContainer #phone').val(data.phone);
            		    $('#formContainer #auditor').val(data.auditor);
            		    $('#formContainer #auditDate').val(data.auditDate);
            		    $('#formContainer #approver').val(data.approver);
            		    $('#formContainer #approveDate').val(data.approveDate);
//            		    $('#formContainer #attendPeople').val(data.attendPeople);
            		    if(data.lineType=='0'){
            		    	$('#formContainer #lineType').val("高铁");
            		    }else if(data.lineType=='1'){
            		    	$('#formContainer #lineType').val("普铁");
            		    }
            		    if(data.uploadFileArr) {
            				_self._initViewUploader(data.uploadFileArr);
            			}
            		    $('#formContainer #workContentRange').val(data.workContentRange);
            		    $('#formContainer #workOrgCondition').val(data.workOrgCondition);
            		    $('#formContainer #localeDefendMeasure').val(data.localeDefendMeasure);
            		    $('#formContainer #relevantDemand').val(data.relevantDemand);
//            		    $('#formContainer #workPrepareCarry').val(data.workPrepareCarry);
//            		    $('#formContainer #peopleWorkSiteBackWay').val(data.peopleWorkSiteBackWay);
//            		    $('#formContainer #workPeopleDivision').val(data.workPeopleDivision);
//            		    $('#formContainer #safeguardDivision').val(data.safeguardDivision);
//            		    $('#formContainer #dredgeSkillCondition').val(data.dredgeSkillCondition);
//            		    $('#formContainer #safetyAttentionMatter').val(data.safetyAttentionMatter);
            		    $('#formContainer #auditAdvice').val(data.auditAdvice);
            		    $('#formContainer #approveAdvice').val(data.approveAdvice);
            		    $('#formContainer #workareaFinish').val(data.workareaFinish);
                    }
          		}
			});
		},
		
		/**
		 * 初始化上传文件（仅用于查看）
		 */
		_initViewUploader:function(uploadFiles){
			var _self = this;
			var viewFiles = new ViewUploader({
				render: '#formContainer #viewUploadfile',
				alreadyItems : uploadFiles,
				previewOnline : true
			});
			viewFiles.render();
		},
		
//		//时间戳转时间
//		_timestampToTime : function(timestamp) {
//			if(timestamp){
//				var date = new Date(timestamp);
//				Y = date.getFullYear() + '-';
//				M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
//				D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
//				h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
//		        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
//		        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
//		        return Y+M+D+h+m+s;
//			}else{
//				return "";
//			}
//	    },
		
//		_renderUploadView(file){
//			var _self = this,html="";
//			file.forEach(function(f){
//                html+= '<div class="success"><label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label><span style="float: right;"><a href="' + _self.get('downloadUrl') + f.path+'&fileName='+f.name + '">下载</a>&nbsp;' +
//                    '<a href="' + _self.get('previewUrl') + f.path + '" target="_blank">预览</a></span></div>';
//            });
//			return html;
//		},
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
            var childs = [
				{
				    label : '单位：',
				    itemColspan : 1,
				    item : '<input type="text" name="unit" id="unit" style="width:99%" readonly/>'
				},
				{
					label : '编号：',
					itemColspan : 1,
					item : '<input type="text" name="serial" id="serial" style="width:99%" readonly/>'
				},
				{
				    label : '作业时间：',
				    itemColspan : 2,
				    item : '<input type="text" name="workTime" id="workTime" style="width:99.5%" readonly/>'
				},
				{
					label : '归属：',
					itemColspan : 1,
					item : '<input  type="text" name="lineType" id="lineType" style="width:99%" readonly/>'
				},
				{
					label : '线别：',
					itemColspan : 1,
					item : '<input  type="text" name="lineName" id="lineName" style="width:99%" readonly/>'
				},
				{
					label : '区间：',
					itemColspan : 1,
					item : '<input type="text" name="section" id="section" style="width:99%" readonly/>'
				},
				{
					label : '站点：',
					itemColspan : 1,
					item : '<input  type="text" name="station" id="station" style="width:99%" readonly/>'
				},
				{
				    label : '负责人：',
				    itemColspan : 1,
				    item : '<input type="text" name="workPrincipal" id="workPrincipal" style="width:99%" readonly/>'
				},
				{
					label : '联系电话：',
					itemColspan : 1,
					item : '<input type="text" name="phone" id="phone" style="width:99%" readonly/>'
				},
//				{
//				    label : '天窗作业：',
//				    itemColspan : 1,
//				    item : '<input type="text" name="louver" id="louver" style="width:99%" readonly/>'
//				},
//				{
//					label : '参加人员：',
//					itemColspan : 2,
//					item : '<input type="text" name="attendPeople" id="attendPeople" style="width:99.5%" readonly/>'
//				},
				{
				    label : '作业内容及范围：',
				    itemColspan : 2,
				    item : '<textarea name="workContentRange" id="workContentRange" style="width:99.5%;height:100px" readonly/></textarea>'
				},
				{
					label : '作业组织情况：',
					itemColspan : 2,
					item : '<textarea name="workOrgCondition" id="workOrgCondition" style="width:99.5%;height:100px" readonly/></textarea>'
				},
				{
					label : '现场防护措施：',
					itemColspan : 2,
					item : '<textarea name="localeDefendMeasure" id="localeDefendMeasure" style="width:99.5%;height:100px" readonly/></textarea>'
				},
				{
					label : '相关要求：',
					itemColspan : 2,
					item : '<textarea name="relevantDemand" id="relevantDemand" style="width:99.5%;height:100px" readonly/></textarea>'
				},
				 {
					label: '委托书：',
					itemColspan: 2,
					item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
				},
//				{
//					label : '作业机械料具准备及携带情况：',
//					itemColspan : 2,
//					item : '<textarea name="workPrepareCarry" id="workPrepareCarry" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
//				{
//					label : '人员到达作业地点及返回路线：',
//					itemColspan : 2,
//					item : '<textarea name="peopleWorkSiteBackWay" id="peopleWorkSiteBackWay" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
//				{
//					label : '作业人员分工：',
//					itemColspan : 2,
//					item : '<textarea name="workPeopleDivision" id="workPeopleDivision" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
//				{
//					label : '防护人员分工：',
//					itemColspan : 2,
//					item : '<textarea name="safeguardDivision" id="safeguardDivision" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
//				{
//					label : '开通技术条件：',
//					itemColspan : 2,
//					item : '<textarea name="dredgeSkillCondition" id="dredgeSkillCondition" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
//				{
//					label : '安全风险控制措施：',
//					itemColspan : 2,
//					item : '<textarea name="safetyAttentionMatter" id="safetyAttentionMatter" style="width:99.5%;height:50px" readonly/></textarea>'
//				},
				{
					label : '车间审核人：',
					itemColspan : 1,
					item : '<input type="text" name="auditor" id="auditor" style="width:99%" readonly/>'
				},
				{
					label : '审核日期：',
					itemColspan : 1,
					item : '<input type="text" name="auditDate" id="auditDate" style="width:99%" readonly/>'
				},
				{
					label : '车间审核意见：',
					itemColspan : 2,
					item : '<textarea name="auditAdvice" id="auditAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '段调度审批人：',
					itemColspan : 1,
					item : '<input type="text" name="approver" id="approver" style="width:99%" readonly/>'
				},
				{
					label : '审批日期：',
					itemColspan : 1,
					item : '<input type="text" name="approveDate" id="approveDate" style="width:99%" readonly/>'
				},
				{
					label : '段调度审批意见：',
					itemColspan : 2,
					item : '<textarea name="approveAdvice" id="approveAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
				},
				{
					label : '工区完成情况说明：',
					itemColspan : 2,
					item : '<textarea name="workareaFinish" id="workareaFinish" style="width:99.5%;height:50px" readonly/></textarea>'
				},
            ];
			var form = new FormContainer({
				id : 'pointOuterMaintainApplyDetailShow',
				colNum : colNum,
				formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'pointOuterMaintainApplyDetailDialog'},
			title:{value:'详情'},
            width:{value:650},
            height:{value:500},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            shiftId : {},
            userId : {},
			collectionName:{},
		}
	});
	return PointOuterMaintainApplyDetail;
});