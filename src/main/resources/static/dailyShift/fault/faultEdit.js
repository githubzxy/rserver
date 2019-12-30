/**
 * 编辑
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dailyShift/fault/faultEdit',['bui/common','common/form/FormContainer',
    'bui/form','bui/calendar','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var FaultEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._initDate();
        	_self._initFaultType();
			_self._initOrganizationPicker();
			_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            var departmentPicker=_self.get('departmentPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #orgSelectName').val(e.org.text);
    		    $('#formContainer #orgSelectId').val(e.org.id);
            });
            departmentPicker.on('orgSelected',function (e) {
            	$('#formContainer #departmentName').val(e.org.text);
            	$('#formContainer #departmentId').val(e.org.id);
            });
            
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
        
        /**
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId'),delData={};
            $.ajax({
                url:'/kmms/faultManagementAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #location").val(data.location);
                        $("#formContainer #faultType").val(data.faultType);
                        $("#formContainer #date").val(_self._timestampToTime(data.date));
                        $("#formContainer #occurrenceTime").val(_self._timestampToTime(data.occurrenceTime));
                        $("#formContainer #recoveryTime").val(_self._timestampToTime(data.recoveryTime));
                        $("#formContainer #departmentName").val(data.departmentName);
                        $('#formContainer #departmentId').val(data.departmentId);
                        $("#formContainer #orgSelectName").val(data.orgName);
            		    $('#formContainer #orgSelectId').val(data.orgId);
            		    $('#formContainer #mainContent').val(data.mainContent);
                        var fileCols = $("#fileCols").val();
                        fileCols.split(",").forEach(function(col){
                            delData[col]=[];
                            $("#formContainer #"+col).html(_self._renderUploadView(data[col],col));
                        });
                        $(".delFileBtn").on('click',function (e) {
                            _self._delFile(e,_self);
                        });
                    }
                }
            })
            _self.set('delData',delData);
        },
        _renderUploadView(file,col){
            var _self = this,html="";
            html+='<input name="'+col+'" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>';
            file.forEach(function(f){
                html += '<div class="success">' 
                + '<label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label>' 
                + '<span style="float: right;" >' 
                + '<a class="editBtn" target="_blank" href="/pageoffice/?filePath='+ f.path +'" data-col="'+col+'" data-id="'+f.id+'">编辑	</a>' 
                + '<a class="delFileBtn" data-col="'+col+'" data-id="'+f.id+'">删除</a>' 
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
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #orgSelectName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:230,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
            
            var departmentPicker = new OrganizationPicker({
                trigger : '#formContainer #departmentName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:230,
                height:200
            });
            departmentPicker.render();
            _self.set('departmentPicker',departmentPicker);
        },
        
        _initDate: function () {
            var _self = this;
            var date = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #date',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #date'
            });
            var occurrenceTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #occurrenceTime',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #occurrenceTime'
            });
            var endTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #recoveryTime',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #recoveryTime'
            });
            _self.set('date', date);
            _self.set('occurrenceTime', occurrenceTime);
            _self.set('recoveryTime', recoveryTime);
        },
        
        /**
         * 初始化类别
         */
        _initFaultType : function(){
        	$("#formContainer #faultType").append("<option value='1'>事故</option>");
			$("#formContainer #faultType").append("<option value='2'>故障</option>");
			$("#formContainer #faultType").append("<option value='3'>障碍</option>");
        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '处所：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text"  name="location" id="location" data-rules="{required:true}" style="width:99.5%">'+
                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                },{
                    label : '日期：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="date" id="date" data-rules="{required:true}" class="calendar" style="width:99%" readonly>'
                },{
                    label : '类别：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select name="faultType" id="faultType" style="width:99.5%;" data-rules="{required:true}"><option value="">请选择</option></select>'
                },{
                    label : '发生时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="occurrenceTime" id="occurrenceTime" data-rules="{required:true}" class="calendar" style="width:99%" readonly>'
                },{
                    label : '恢复时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="recoveryTime" id="recoveryTime" data-rules="{required:true}" class="calendar" style="width:99%" readonly>'
                },{
                    label : '责任部门：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="departmentName" id="departmentName" style="width:99%" readonly data-rules="{required:true}"/><input type="hidden" name="departmentId" id="departmentId" readonly/>'
                },{
                    label : '所属部门：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="orgSelectName" id="orgSelectName" style="width:99%" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
                },{
                    label : '主要内容：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<textarea  name="mainContent" id="mainContent" data-rules="{required:true}" style="height:20px;width:99.5%"></textarea>'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<div id="uploadfile" style="height:75px;overflow-y :auto"></div>'
                }
            ];
            var form = new FormContainer({
                id : 'faultEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'faultEditDialog'},
            elAttrs : {value: {id:"faultEdit"}},
            title:{value:'编辑'},
            width:{value:610},
            height:{value:365},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('faultEditForm'),delData=_self.get('delData');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('id',_self.get('shiftId'));
                    for (var key in delData) {
                        formData.append('del-'+key,delData[key].join(","));
                    }
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/faultManagementAction/updateDoc");
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
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return FaultEdit;
});