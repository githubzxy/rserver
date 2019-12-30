/**
 * 修改
 * @author yangli
 * @date 19-1-14
 */
define('kmms/dayToJobManagement/constructRepair/ConstructRepairEdit',['bui/common','common/form/FormContainer',
    'bui/form','bui/calendar','bui/data','bui/select','common/org/OrganizationPicker','common/uploader/UpdateUploader','common/data/PostLoad'],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	Select = r('bui/select'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	Data = r('bui/data'),
        FormContainer= r('common/form/FormContainer'),
    	UpdateUploader = r('common/uploader/UpdateUploader'),
    	PostLoad = r('common/data/PostLoad');
    var ConstructRepairEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._initDate();
//			_self._getShowData();
			_self._getWorkShop();
        	_self._getLines();
        	_self._initPlanDate();
			
        },
        bindUI : function(){
            var _self = this;
            
            //定义按键
            var buttons = [
                {
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
            
          //工区下拉选选项根据车间而变化
            $("#workShop").on('change',function(){
            	$("#workArea").empty();
            	var orgName=$("#workShop").val();
            	_self._getDepart(orgName);
            	
            });
            /**
             * 等级下拉选选项和维修类型根据项目而变化
             */
            $("#formContainer #project").on('change',function(){
            	var project=$("#formContainer #project").val();
            	_self._initGrade(project);
            	_self._initRepairType(project);
            	
            });
            /**
             * 申请时间的计算(开始)
             */
            $("#formContainer #planAgreeTimeStart").on('change',function(){
            	_self._applyTime();
            	_self._timeCash();
            });
            /**
             * 申请时间的计算（截止）
             */
            $("#formContainer #planAgreeTimeEnd").on('change',function(){
            	_self._applyTime();
            	_self._timeCash();
            });
            /**
             * 实际给点时间的计算
             */
            $("#formContainer #actAgreeTime").on('change',function(){
            	_self._actrTime();
            	_self._timeCash();
            });
            /**
             * 实际完成时间的计算
             */
            $("#formContainer #actOverTime").on('change',function(){
            	_self._actrTime();
            	_self._timeCash();
            });
            
            
        },
        /**
         * 时间兑现率计算
         */
        _timeCash:function(){
        	var applyMinute=$("#formContainer #applyMinute").val();
        	var actMinute=$("#formContainer #actMinute").val();
        	if(applyMinute==0) return;
        	if(applyMinute!='' && actMinute!=''){
        		var v=Math.pow(10,2);
        		var minutes=Math.round(actMinute/applyMinute*100*v)/v;
        		var minutesStr=minutes+'%';
        		$("#timeCash").val(minutesStr);
        		
        	} 
        },
        /**
         * 申请时间计算
         */
        _applyTime:function(){
        	var planAgreeTimeStart=$("#formContainer #planAgreeTimeStart").val();
        	var planAgreeTimeEnd=$("#formContainer #planAgreeTimeEnd").val();
        	if(planAgreeTimeStart!='' && planAgreeTimeEnd!=''){
        		startTime=Date.parse(new Date(planAgreeTimeStart));
        		endTime=Date.parse(new Date(planAgreeTimeEnd));
        		var tamp=endTime-startTime;
        		var minutes=tamp/60000;
        		$("#applyMinute").val(minutes);
        		
        	}
        },
        /**
         * 实际批准时间
         */
        _actrTime:function(){
        	var actAgreeTime=$("#formContainer #actAgreeTime").val();
        	var actOverTime=$("#formContainer #actOverTime").val();
        	if(actAgreeTime!='' && actOverTime!=''){
        		startTime=Date.parse(new Date(actAgreeTime));
        		endTime=Date.parse(new Date(actOverTime));
        		var tamp=endTime-startTime;
        		var minutes=tamp/60000;
        		$("#actMinute").val(minutes);
        		
        	}
        },
        /**
         * 计划给点时间
         */
        _initPlanDate: function () {
            var _self = this;
            var planAgreeTimeStart = new Calendar.DatePicker({//加载日历控件
                trigger: '#planAgreeTimeStart',
                showTime: true,
                autoRender: true,
                textField:'#planAgreeTimeStart'
            });
            var planAgreeTimeEnd = new Calendar.DatePicker({//加载日历控件
                trigger: '#planAgreeTimeEnd',
                showTime: true,
                autoRender: true,
                textField:'#planAgreeTimeEnd'
            });
            var actAgreeTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#actAgreeTime',
                showTime: true,
                autoRender: true,
                textField:'#actAgreeTime'
            });
            var actOverTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#actOverTime',
                showTime: true,
                autoRender: true,
                textField:'#actOverTime'
            });
            _self.set('planAgreeTimeStart', planAgreeTimeStart);
            _self.set('planAgreeTimeEnd', planAgreeTimeEnd);
            _self.set('actAgreeTime', planAgreeTimeEnd);
            _self.set('actOverTime', planAgreeTimeEnd);
        },
        /**
         * 获取车间下拉选数据
         */
        _getWorkShop:function(){
        	 var _self = this;
             $.ajax({
                 url:'/kmms/constructCooperateAction/getWorkShop',
//                 data:{id : shiftId,collectionName:_self.get('collectionName')},
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 for(var i=0;i<res.length;i++){
                		 $("#workShop").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                	 $("#depart option[value='"+depart+"']").attr("selected","selected");
                	 var orgName=$("#workShop").val();
                 	_self._getDepart(orgName);
                 }
             })
        },
        /**
         * 获取线别
         */
        _getLines:function(){
        	var _self=this;
       	 $.ajax({
                url:'/kmms/constructCooperateAction/getLines',
//                data:{workShopName : orgName},
                type:'post',
                dataType:"json",
                success:function(res){
               	 for(var i=0;i<res.length;i++){
               		 $("#line").append("<option  value="+res[i]+">"+res[i]+"</option>");
               	 }
               	 _self._getShowData();
                }
            })
        },
        /**
         * 获取工区下拉选数据
         */
        _getDepart:function(orgName,depart){
        	var _self=this;
        	 $.ajax({
                 url:'/kmms/constructCooperateAction/getDepart',
                 data:{workShopName : orgName},
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 for(var i=0;i<res.length;i++){
                		 $("#workArea").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                	 if(depart!=null)
                	 $("#workArea option[value='"+depart+"']").attr("selected","selected");
                 }
             })
        },
        /**
         * 初始化等级下拉选
         */
        _initGrade:function(project){
        	$("#formContainer #grade").empty();
        	$("#formContainer #grade").append("<option value='Ⅰ'>Ⅰ</option>");
        	$("#formContainer #grade").append("<option value='Ⅱ'>Ⅱ</option>");
        	if(project=='施工'){
        		$("#formContainer #grade").append("<option value='Ⅲ'>Ⅲ</option>");
        		$("#formContainer #grade").append("<option value='ⅢA'>ⅢA</option>");
        		$("#formContainer #grade").append("<option value='ⅢB'>ⅢB</option>");
        		$("#formContainer #grade").append("<option value='ⅢC'>ⅢC</option>");
        	}
        },
        /**
         * 初始化维修类型下拉选
         */
        _initRepairType:function(project){
        	$("#formContainer #repairType").empty();
        	if(project=='施工'){
        		$("#formContainer #repairType").attr("disabled","disabled");
        	}else{
        		$("#formContainer #repairType").removeAttr("disabled");
        		$("#formContainer #repairType").append("<option value='垂直'>垂直</option>");
        		$("#formContainer #repairType").append("<option value='V型'>V型</option>");
        		$("#formContainer #repairType").append("<option value='协议修'>协议修</option>");
        	}
        },
        /**
         * 获取车间下拉选数据
         */
        _getWorkShop:function(workShop){
        	 var _self = this;
             $.ajax({
                 url:'/kmms/constructCooperateAction/getWorkShop',
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 $("#workShop").append("<option  value="+""+">"+"请选择"+"</option>");
                	 for(var i=0;i<res.length;i++){
                		 $("#workShop").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                		$("#workShop option[value='"+workShop+"']").attr("selected","selected");
                 }
             })
        },
        //时间戳转时间
		_timestampToTime : function(timestamp) {
			if(timestamp){
				var date = new Date(timestamp);
				Y = date.getFullYear() + '-';
				M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
				D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
				h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
		        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
		        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		        return Y+M+D+h+m+s;
			}else{
				return "";
			}
	    },
	    _renderUploadView(file,col){
            var _self = this,html="";
            html+='<input name="'+col+'" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>';
            file.forEach(function(f){
                html += '<div class="success">' 
                + '<label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label>' 
                + '<span style="float: right;" >' 
                + '<a class="delFileBtn" data-col="'+col+'" data-id="'+f.id+'">删除   </a>' 
                + '<a href="' + _self.get('downloadUrl') + f.path+'&fileName='+f.name + '">下载</a> '
                + '</span>' 
                + '</div>';
            });
            return html;
        },
        _delFile:function(e,self){
            var delData=self.get('delData'),tdata=e.target.dataset;
            delData[tdata.col].push(tdata.id);
            $(e.target).parents('.success').remove();
        },
        
	    /**
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer"),delData={};
			$.ajax({
				url:'/kmms/constructRepairAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(data){
					if(data){
						_self._initRepairType(data.project);
						_self._getDepart(data.workShop,data.workArea);
						 $("#formContainer #date").val(data.date);
						 $("#formContainer #project").val(data.project);
						 $("#formContainer #grade").val(data.grade);
						 $("#formContainer #repairType").val(data.repairType);
						 $("#formContainer #line").val(data.line);
						 $("#formContainer #planNum").val(data.planNum);
						 $("#formContainer #workShop").val(data.workShop);
						 $("#formContainer #workArea").val(data.workArea);
						 $("#formContainer #planAgreeTimeStart").val(data.planAgreeTimeStart);
						 $("#formContainer #planAgreeTimeEnd").val(data.planAgreeTimeEnd);
						 $("#formContainer #applyMinute").val(data.applyMinute);
						 $("#formContainer #actAgreeTime").val(data.actAgreeTime);
						 $("#formContainer #actOverTime").val(data.actOverTime);
						 $("#formContainer #actMinute").val(data.actMinute);
						 $("#formContainer #timeCash").val(data.timeCash);
						 $("#formContainer #totalMan").val(data.totalMan);
						 $("#formContainer #checkLeader").val(data.checkLeader);
						 $("#formContainer #constructContent").val(data.constructContent);
						 $("#formContainer #remark").val(data.remark);
						 if(data.uploadfile) {
	            				_self._initUploader(data.uploadfile);
	            			}
						 	/*var fileCols = $("#fileCols").val();
	                        fileCols.split(",").forEach(function(col){
	                            delData[col]=[];
	                            $("#formContainer #"+col).html(_self._renderUploadView(data[col],col));
	                        });
	                        $(".delFileBtn").on('click',function (e) {
	                            _self._delFile(e,_self);
	                        });*/
                    }
          		}
			});
			_self.set('delData',delData);
		},
        
        _initDate: function () {
            var _self = this;
            var date = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #date',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #date'
            });
            _self.set('date', date);
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
        
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                  {
                    label : '日期：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="date" id="date" class="calendar"   readonly />'+
                    	 '<input type="hidden"  name="fileCols" id="fileCols" ' +
                         'value="uploadfile">'
                },{
                    label : '项目：',
                    itemColspan : 1,
                    item : '<select  name="project" id="project" >'+
                    '<option value="施工">施工</option>'+
                    '<option value="维修">维修</option>'+
                    '<option value="骨干网计划">骨干网计划</option>'+
                    '<option value="临时修">临时修</option>'+
                    '<option value="紧急修">紧急修</option>'+
                    '<option value="一体化">一体化</option>'+
                    '</select>'
                },{
                    label : '等级：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select name="grade" id="grade"  >'+
                    '<option value="Ⅰ">Ⅰ</option>'+
                    '<option value="Ⅱ">Ⅱ</option>'+
                    '<option value="Ⅲ">Ⅲ</option>'+
                    '<option value="ⅢA">ⅢA</option>'+
                    '<option value="ⅢB">ⅢB</option>'+
                    '<option value="ⅢC">ⅢC</option>'+
                    	'</select>'
                },{
                    label : '维修类型：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select  name="repairType" id="repairType"></select>'
                },{
                    label : '线别：',
//                  redStarFlag : true,
                  itemColspan : 1,
                  item : '<select  name="line" id="line" ></select>'
              },{
                  label : '计划号：',
//                redStarFlag : true,
                itemColspan : 1,
                item : '<input type="text" name="planNum" id="planNum" />'
            },{
                label : '车间：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<select  name="workShop" id="workShop"></select>'
            },{
                label : '工区：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<select name="workArea" id="workArea" ></select>'
            },{
                label : '计划批准时间（开始）：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="planAgreeTimeStart" id="planAgreeTimeStart" class="calendar" readonly/>'
            },{
                label : '计划批准时间（结束）：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="planAgreeTimeEnd" id="planAgreeTimeEnd" class="calendar" readonly/>'
            },{
                label : '申请时间（分钟）：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="applyMinute" id="applyMinute" readonly/>'
            },{
                label : '实际给点时间：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="actAgreeTime" id="actAgreeTime" class="calendar"   readonly/>'
            },{
                label : '实际完成时间：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="actOverTime" id="actOverTime" class="calendar"   readonly/>'
            },{
                label : '实际批准时间（分钟）：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="actMinute" id="actMinute" readonly/>'
            },{
                label : '时间兑现率：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="timeCash" id="timeCash" readonly/>'
            },{
                label : '作业人数：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="totalMan" id="totalMan" />'
            },{
                label : '机关把关干部：',
//              redStarFlag : true,
              itemColspan : 2,
              item : '<input type="text" name="checkLeader" id="checkLeader" />'
            },{
                label : '施工内容：',
//              redStarFlag : true,
              itemColspan : 2,
              item : '<textarea type="text" name="constructContent" id="constructContent" />'
            },{
                label : '备注：',
//              redStarFlag : true,
              itemColspan : 2,
              item : '<textarea type="text" name="remark" id="remark" />'
          },{
              label : '上传：',
//            redStarFlag : true,
            itemColspan : 2,
            item : '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
            /*item : '<div id="uploadfile" style="height:75px;overflow-y :auto"></div>'*/
        }
            ];
            var form = new FormContainer({
                id : 'constructRepairEditEditForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'constructRepairEditEditDialog'},
            elAttrs : {value: {id:"constructRepairEditEdit"}},
            title:{value:'修改'},
            downloadUrl:{value:'/kmms/commonAction/download?path='},
            width:{value:640},
            height:{value:500},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            shiftId:{},
//            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('constructRepairEditEditForm'),delData=_self.get('delData');
                    //获取上传文件
            		var uploadfile = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		
                    /*var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('id',_self.get('shiftId'));
                    for (var key in delData) {
                        formData.append('del-'+key,delData[key].join(","));
                    }
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/constructRepairAction/updateDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSave",{
                                result : result
                            });
                        }
                    }*/
	        		
	        		
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.uploadfile=JSON.stringify(uploadfile);
	        		data.collectionName=_self.get('collectionName');
	        		data.id=_self.get('shiftId');
	        		/*for (var key in delData) {
	        			data.'del-'+key=delData[key].join(",");
                        //formData.append('del-'+key,delData[key].join(","));
                    }*/
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/constructRepairAction/updateDoc',
        				el : _self.get('el'),
        				loadMsg : '上传中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        			});

                }
            },
            events : {
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
//            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
//            rootOrgText:{value:'昆明通信段'},
        }
    });
    return ConstructRepairEdit;
});