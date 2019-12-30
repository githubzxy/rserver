/**
 * 审核状态模块
 */
define('kmms/pointInnerMaintainPlanMangement/pointInnerMaintainPlan/PointInnerMaintainPlanAuditStatus',[
	'bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		PostLoad = r('common/data/PostLoad'),
		FormContainer= r('common/form/FormContainer');
	var PointInnerMaintainPlanAuditStatus = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
			//显示数据
			_self._getShowData();
		},
		bindUI : function(){
			var _self = this;
			//定义按键
			var buttons = [
				{
                    text:'审核',
                    elCls : 'button button-primary audit',
                    handler : function(){
                    	var success = _self.get('success');
                        if(success){
                            success.call(_self);
                        }
                    }
                },
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
		
		/**
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/commonAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						$("#formContainer #name").val(data.name);
                        $("#formContainer #orgName").val(data.orgName);
                        $("#formContainer #createDate").val(_self._timestampToTime(data.createDate));
                        $("#formContainer #createUserName").val(data.createUserName);
                        if(data.auditStatus==1){
                        	$(".audit").hide();
                        }
                        $("#auditStatus #name").val(data.name);
                        var fileCols = $("#fileCols").val();
                        fileCols.split(",").forEach(function(col){
                        	$("#"+col).html(_self._renderUploadView(data[col]));
						});
                    }
          		}
			});
		},
		_renderUploadView(file){
			var _self = this,html="";
			file.forEach(function(f){
                html+= '<div class="success"><label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label><span style="float: right;"><a href="' + _self.get('downloadUrl') + f.path+'&fileName='+f.name + '">下载</a>&nbsp;' +
                    '<a href="' + _self.get('previewUrl') + f.path + '" target="_blank">预览</a></span></div>';
            });
			return html;
		},
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
			var childs = [
                {
                    label : '施工项目：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="name" id="name" style="width:99%" readonly>'+
                        '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                },{
                    label : '提交部门：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="orgName" id="orgName" style="width:99%" readonly>'
                },{
                    label : '创建时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="createDate" id="createDate" style="width:99%" readonly>'
                },
                {
                    label : '创建人：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="createUserName" id="createUserName" style="width:99%" readonly>'
                },
                {
                    label : '附件：',
                    itemColspan : 2,
                    item : '<div name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto"></div>'
                }
            ];
			var form = new FormContainer({
				id : 'workPlanInfoShow',
				colNum : colNum,
				formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
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
	},{
		ATTRS : {
			id : {value : 'pointInnerMaintainPlanAuditStatusDialog'},
            previewUrl:{value:'/pageoffice/openPage?filePath='},
            downloadUrl:{value:'/kmms/commonAction/download?path='},
			title:{value:'审核'},
            width:{value:610},
            height:{value:240},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
			success:{
                value : function(){
        			var _self = this;
        			//提交到数据库
            		var postLoad = new PostLoad({
        				url : '/zuul/kmms/workPlanAction/auditDoc.cn',
        			}); 
        			postLoad.load({id : _self.get('shiftId'),collectionName:_self.get('collectionName')}, function(result){
        				if(result != null){
        					_self.fire("completeAddSave",{
        						result : result
        					});
        				}
        			});
        		}
            },
            events : {
                value : {
                    /**
                     * 绑定审核按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
		}
	});
	return PointInnerMaintainPlanAuditStatus;
});