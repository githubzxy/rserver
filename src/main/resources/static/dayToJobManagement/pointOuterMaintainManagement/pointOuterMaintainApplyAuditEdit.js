/**
 * 点外维修申请数据修改（审核不通过的数据）
 * @author yangsy
 * @date 19-1-22
 */
define('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyAuditEdit',
	[
	 	'bui/common',
	 	'bui/form',
		'bui/calendar',
		'common/form/FormContainer',
		'common/uploader/UpdateUploader',
		'common/org/OrganizationPicker',
		'kmms/dayToJobManagement/pointOuterMaintainManagement/SelectSuggest'
	],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	UpdateUploader = r('common/uploader/UpdateUploader'),
        FormContainer= r('common/form/FormContainer'),
        SelectSuggest = r('kmms/dayToJobManagement/pointOuterMaintainManagement/SelectSuggest');
    var PointOuterMaintainApplyAuditEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
//			_self._initOrganizationPicker();
			_self._initSelect();
			_self._initDate();
//			_self._getLines();//获取线别下拉选数据
			_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
//            var orgPicker=_self.get('orgPicker');
//            /**
//             * 组织机构选择
//             */
//            orgPicker.on('orgSelected',function (e) {
//                $('#formContainer #backOrgName').val(e.org.text);
//    		    $('#formContainer #backOrgId').val(e.org.id);
//            });
            
            //定义按键
            var buttons = [
                 {
                    text:'申请',
                    elCls : 'button',
                    handler : function(){
                        var successApply = _self.get('successApply');
                        if(successApply){
                        	successApply.call(_self);
                        }
                    }
                },{
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
                        var success = _self.get('success');
                        if(success){
                            success.call(_self);
                        }
                    }
                },{
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
            
//            $("#formContainer #lineName").on('change',function(){
//	        	$("#formContainer #station").empty();
//	        	var lineName=$("#formContainer #lineName").val();
//	        	_self._getStation(lineName);
//	        });
	        
	        $("#formContainer #station").on('change',function(){
	        	$("#formContainer #section").val("");
	        });
	        $("#formContainer #section").on('change',function(){
//	        	$("#formContainer #station").val("");
	        	$("input[name='station']").val("");
	        });
	        
//	        $("#workTimeStart").on("change",function(e){
//	        	
//	        	$("#workTimeEnd").val("");
//	        	
//	            var workTimeEnd = new Calendar.DatePicker({//加载日历控件
//	                trigger: '#formContainer #workTimeEnd',
//	                showTime: true,
//	                autoRender: true,
//	                lockTime:{second:00},
//	                selectedDate: new Date($("#workTimeStart").val()),
//	                textField:'#formContainer #workTimeEnd'
//	            });
//	            _self.set('workTimeEnd', workTimeEnd);
//	            
//	            _self.get("workTimeEnd").set("minDate",$("#workTimeStart").val().substring(0,10));
//	            _self.get("workTimeEnd").set("maxDate",$("#workTimeStart").val().substring(0,10));
//	            
//	        });
	        
        },
        
        /**
         * 初始化时间
         */
        _initDate: function () {
        	var _self = this;
            var workTimeStart = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #workTimeStart',
                showTime: true,
                autoRender: true,
                lockTime:{second:00},
                textField:'#formContainer #workTimeStart'
            });
            _self.set('workTimeStart', workTimeStart);
            var workTimeEnd = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #workTimeEnd',
                showTime: true,
                autoRender: true,
                lockTime:{second:00},
                textField:'#formContainer #workTimeEnd'
            });
            _self.set('workTimeEnd', workTimeEnd);
        },
        
        _initSelect: function(){
			$("#formContainer #lineType").append("<option  value='0'>高铁</option>");
			$("#formContainer #lineType").append("<option  value='1'>普铁</option>");
        },
        
        /**
         * 获取线别
         */
//        _getLines:function(){
//        	var _self=this;
//        	$.ajax({
//        		url:'/kmms/networkManageInfoAction/getLines',
//        		type:'post',
//        		dataType:"json",
//        		success:function(res){
//        			$("#formContainer #lineName").append("<option  value=''>请选择</option>");
//        			for(var i=0;i<res.length;i++){
//        				$("#formContainer #lineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
//        			}
//        			_self._getShowData();
//        		}
//        	});
//        },
        _getLines:function(lineName){
        	var _self=this;
        	var nameData = [];
        	$.ajax({
        		url:'/kmms/networkManageInfoAction/getLines',
        		type:'post',
        		dataType:"json",
        		success:function(res){
        			for(var i=0;i<res.length;i++){
        				nameData.push(res[i]);
        			}
        		}
        	});
        	
        	var suggest = new SelectSuggest({
                renderName: '#formContainer #lineName',
                inputName: 'formContainerlineName',
                renderData: nameData,
                width: 213
            });
        	
        	$("input[name='formContainerlineName']").val(lineName);
        	
        },
        
//        _getStation:function(lineName,station){
//        	var _self=this;
//        	$.ajax({
//        		url:'/kmms/networkManageInfoAction/getStations',
//        		type:'post',
//        		dataType:"json",
//        		data:{lineName : lineName},
//        		success:function(res){
//        			$("#formContainer #station").append("<option  value=''>请选择</option>");
//        			for(var i=0;i<res.length;i++){
//        				$("#formContainer #station").append("<option  value="+res[i].staName+">"+res[i].staName+"</option>");
//        			}
//        			$("#formContainer #station option[value='"+station+"']").attr("selected","selected");
//        		}
//        	});
//        },
        _getStation:function(station){
        	var _self=this;
        	var nameData = [];
        	$.ajax({
        		url:'/kmms/networkManageInfoAction/getStationNoCondition',
        		type:'post',
        		dataType:"json",
        		success:function(res){
        			for(var i=0;i<res.length;i++){
        				nameData.push(res[i]);
        			}
        		}
        	});
        	
        	var suggest = new SelectSuggest({
                renderName: '#formContainer #station',
                inputName: 'station',
                renderData: nameData,
                width: 213
            });
        	
        	$("input[name='station']").val(station);
        	
        },

        /**
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/pointOuterMaintainApplyAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                    	
                    	_self._getLines(data.lineName);
                    	_self._getStation(data.station);
                    	
                        $("#formContainer #unit").val(data.unit);
                        $("#formContainer #serial").val(data.serial);
                        $('#formContainer #workTimeStart').val(data.workTimeStart);
                        $('#formContainer #workTimeEnd').val(data.workTimeEnd);
                        $('#formContainer #lineType').val(data.lineType);
//            		    $('#formContainer #lineName').val(data.lineName);
            		    $('#formContainer #section').val(data.section);
//            		    $('#formContainer #station').val(data.station);
            		    $('#formContainer #workPrincipal').val(data.workPrincipal);
            		    $('#formContainer #phone').val(data.phone);
            		    $('#formContainer #auditor').val(data.auditor);
            		    $('#formContainer #auditDate').val(data.auditDate);
//            		    $('#formContainer #approver').val(data.approver);
//            		    $('#formContainer #approveDate').val(data.approveDate);
//            		    $('#formContainer #attendPeople').val(data.attendPeople);
//            		    $('#formContainer #louver').val(data.louver);
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
            		    if(data.uploadFileArr) {
            				_self._initUploader(data.uploadFileArr);
            			}
                    }
                }
            })
        },
        
        /**
		 * 初始化上传文件
		 */
		_initUploader:function(uploadFiles){
			var _self = this;
			var uploader = new UpdateUploader({
				render : '#formContainer #uploadfile',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        alreadyItems : uploadFiles,
		        isSuccess : function(result){
					return true;
		        },
			});
			uploader.render();
			_self.set('uploader',uploader);
		},
		
		 /**
		 * 获取上传文件数据(上传的)
		 */
		_getUploadFileData : function(){
			var _self = this,uploader = _self.get('uploader');
			var arr = new Array(); //声明数组,存储数据发往后台
			//获取上传文件的队列
			var fileArray = uploader.getSucFiles();
			for(var i in fileArray){
		    	var dto = new _self._UploadFileDto(fileArray[i].name,fileArray[i].path); //声明对象
				arr[i] = dto; // 向集合添加对象
			};
			return arr;
		},
		
		/**
		  * 声明上传文件对象
		  * @param name 上传文件名
		  * @param path 上传文件路径
		  */
		_UploadFileDto : function(name,path){
			this.name = name;
			this.path = path;
		},
        
//        /**
//         * 初始化组织机构选择
//         * @private
//         */
//        _initOrganizationPicker:function(){
//            var _self=this;
//            var orgPicker = new OrganizationPicker({
//                trigger : '#formContainer #backOrgName',
//                rootOrgId:_self.get('rootOrgId'),//必填项
//                rootOrgText:_self.get('rootOrgText'),//必填项
//                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
//                autoHide: true,
//                align : {
//                    points:['bl','tl']
//                },
//                zIndex : '10000',
//                width:220,
//                height:200
//            });
//            orgPicker.render();
//            _self.set('orgPicker',orgPicker);
//        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
//                {
//                    label : '资料名称：',
//                    redStarFlag : true,
//                    itemColspan : 2,
//                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99%">'+
//                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
//                        'value="uploadfile">'
//                },
				{
				    label : '单位：',
				    redStarFlag : true,
				    itemColspan : 1,
				    item : '<input type="text" name="unit" id="unit" style="width:99%" data-rules="{required:true}"/>'
				},
				{
					label : '编号：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="serial" id="serial" style="width:99%" data-rules="{required:true}"/>'
				},
				{
				    label : '作业时间：',
				    redStarFlag : true,
				    itemColspan : 1,
				    item : '<input type="text" name="workTimeStart" id="workTimeStart" style="width:99%"  data-rules="{required:true}" readonly/>'
				},
				{
                	label : '至：',
                	redStarFlag : true,
                	itemColspan : 1,
                	item : '<input type="text" name="workTimeEnd" id="workTimeEnd" style="width:99%"  data-rules="{required:true}" readonly/>'
                },
                {
				    label : '归属：',
				    redStarFlag : true,
				    itemColspan : 1,
				    item : '<select name="lineType" id="lineType" style="width:99.5%"/></select>'
				},
				{
					label : '线别：',
//					redStarFlag : true,
					itemColspan : 1,
//					item : '<select name="lineName" id="lineName" style="width:99.5%" data-rules="{required:true}"/></select>'
					item : '<div id="lineName" name="lineName" style="width: 200px;" />'
				},
				{
					label : '区间：',
					itemColspan : 1,
					item : '<input type="text" name="section" id="section" style="width:99%"/>'
				},
				{
					label : '站点：',
					itemColspan : 1,
//					item : '<select name="station" id="station" style="width:99.5%"/></select>'
					item : '<div id="station" name="station" style="width: 200px;" />'
				},
				{
				    label : '负责人：',
				    itemColspan : 1,
				    item : '<input type="text" name="workPrincipal" id="workPrincipal" style="width:99%"/>'
				},
				{
					label : '联系电话：',
					itemColspan : 1,
					item : '<input type="text" name="phone" id="phone" style="width:99%"/>'
				},
//				{
//					label : '审批人：',
//					itemColspan : 1,
//					item : '<input type="text" name="approver" id="approver" style="width:99%" disabled="disabled"/>'
//				},
//				{
//					label : '审批日期：',
//					itemColspan : 1,
//					item : '<input type="text" name="approveDate" id="approveDate" style="width:99%" disabled="disabled"/>'
//				},
//				{
//				    label : '天窗作业：',
//				    itemColspan : 1,
//				    item : '<select name="louver" id="louver" style="width:99.5%"/></select>'
//				},
//				{
//					label : '参加人员：',
//					itemColspan : 2,
//					item : '<input type="text" name="attendPeople" id="attendPeople" style="width:99.5%"/>'
//				},
				{
				    label : '作业内容及范围：',
				    itemColspan : 2,
				    item : '<textarea name="workContentRange" id="workContentRange" style="width:99.5%;height:100px"/></textarea>'
				},
				{
					label : '作业组织情况：',
					itemColspan : 2,
					item : '<textarea name="workOrgCondition" id="workOrgCondition" style="width:99.5%;height:100px"/></textarea>'
				},
				{
					label : '现场防护措施：',
					itemColspan : 2,
					item : '<textarea name="localeDefendMeasure" id="localeDefendMeasure" style="width:99.5%;height:100px" placeholder="填写驻站防护，工地防护、拦车防护的设置情况"/></textarea>'
				},
				{
					label : '相关要求：',
					itemColspan : 2,
					item : '<textarea name="relevantDemand" id="relevantDemand" style="width:99.5%;height:100px" placeholder="结合作业特点，填写相关安全风险、卡控措施和质量技术要求"/></textarea>'
				},
				{
                    label : '委托书：',
                    itemColspan : 2,
                    item : '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
                },
//				{
//					label : '作业机械料具准备及携带情况：',
//					itemColspan : 2,
//					item : '<textarea name="workPrepareCarry" id="workPrepareCarry" style="width:99.5%;height:50px"/></textarea>'
//				},
//				{
//					label : '人员到达作业地点及返回路线：',
//					itemColspan : 2,
//					item : '<textarea name="peopleWorkSiteBackWay" id="peopleWorkSiteBackWay" style="width:99.5%;height:50px"/></textarea>'
//				},
//				{
//					label : '作业人员分工：',
//					itemColspan : 2,
//					item : '<textarea name="workPeopleDivision" id="workPeopleDivision" style="width:99.5%;height:50px"/></textarea>'
//				},
//				{
//					label : '防护人员分工：',
//					itemColspan : 2,
//					item : '<textarea name="safeguardDivision" id="safeguardDivision" style="width:99.5%;height:50px"/></textarea>'
//				},
//				{
//					label : '开通技术条件：',
//					itemColspan : 2,
//					item : '<textarea name="dredgeSkillCondition" id="dredgeSkillCondition" style="width:99.5%;height:50px"/></textarea>'
//				},
//				{
//					label : '安全风险控制措施：',
//					itemColspan : 2,
//					item : '<textarea name="safetyAttentionMatter" id="safetyAttentionMatter" style="width:99.5%;height:50px" placeholder="请按指定格式填写：列出相应风险，明确“三防”措施，例如：风险一（人防：物防：技防：）风险二（人防：物防：技防：）......"/></textarea>'
//				},
				{
					label : '车间审核人：',
					itemColspan : 1,
					item : '<input type="text"  id="auditor" style="width:99%" readonly/>'
				},
				{
					label : '审核日期：',
					itemColspan : 1,
					item : '<input type="text"  id="auditDate" style="width:99%" readonly/>'
				},
				{
					label : '车间审核意见：',
					itemColspan : 2,
					item : '<textarea name="auditAdvice" id="auditAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
				},
//                {
//                    label : '附件：',
//                    itemColspan : 2,
//                    item : '<div id="uploadfile" style="height:75px;overflow-y :auto"></div>'
//                }
            ];
            var form = new FormContainer({
                id : 'pointOuterMaintainApplyAuditEditForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'pointOuterMaintainApplyAuditEditDialog'},
            elAttrs : {value: {id:"pointOuterMaintainApplyAuditEdit"}},
            title:{value:'修改'},
            width:{value:650},
            height:{value:500},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            shiftId:{},
            userId:{},
            successApply:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('pointOuterMaintainApplyAuditEditForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('uploadFileArr',JSON.stringify(uploadFileArr));
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('flowState',"1");//流程状态（待审核）
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/pointOuterMaintainApplyAction/updateDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSave",{
                                result : result
                            });
                        }
                    }
                }
            },
            success:{
            	value : function(){
            		var _self = this;
            		var formAdd = _self.getChild('pointOuterMaintainApplyAuditEditForm');
            		//获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
            		//验证不通过
            		if(!formAdd.isValid()){
            			return;
            		}
            		var formData = new FormData(formAdd.get('el')[0]);
            		formData.append('uploadFileArr',JSON.stringify(uploadFileArr));
            		formData.append('collectionName',_self.get('collectionName'));
            		formData.append('flowState',"0");//流程状态（草稿）
            		formData.append('id',_self.get('shiftId'));
            		var xhr = new XMLHttpRequest();
            		xhr.open("POST", "/zuul/kmms/pointOuterMaintainApplyAction/updateDoc");
            		xhr.send(formData);
            		xhr.onload = function (e) {
            			if (e.target.response) {
            				var result = JSON.parse(e.target.response);
            				_self.fire("completeAddSave",{
            					result : result
            				});
            			}
            		}
            	}
            },
            events : {
                value : {'completeAddSave' : true,}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return PointOuterMaintainApplyAuditEdit;
});