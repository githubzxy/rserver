/**
 * 编辑
 * @author Bili
 * @date 18-11-21
 */
define('kmms/workAssortProtocolManagement/workAssortProtocol/workAssortProtocolEdit',['bui/common','common/form/FormContainer',
   'bui/form','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var WorkAssortProtocolEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._initOrganizationPicker();
			_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #orgSelectName').val(e.org.text);
    		    $('#formContainer #orgSelectId').val(e.org.id);
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

        /**
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId'),delData={};
            $.ajax({
                url:'/kmms/commonAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #name").val(data.name);
                        $("#formContainer #orgSelectName").val(data.orgName);
            		    $('#formContainer #orgSelectId').val(data.orgId);
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
                width:493,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
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
                    itemColspan : 2,
                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99%">'+
                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                },{
                    label : '所属部门：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="orgSelectName" id="orgSelectName" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<div id="uploadfile" style="height:75px;overflow-y :auto"></div>'
                }
            ];
            var form = new FormContainer({
                id : 'WorkAssortProtocolEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'workAssortProtocolEditDialog'},
            elAttrs : {value: {id:"workAssortProtocolEdit"}},
            title:{value:'编辑'},
            width:{value:610},
            height:{value:240},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('WorkAssortProtocolEditForm'),delData=_self.get('delData');
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
                    xhr.open("POST", "/zuul/kmms/commonAction/updateDoc");
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
    return WorkAssortProtocolEdit;
});