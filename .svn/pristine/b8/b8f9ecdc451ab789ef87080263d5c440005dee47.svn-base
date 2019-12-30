/**
 * 修改
 * @author yangli
 * @date 19-1-14
 */
define('kmms/dayToJobManagement/dayToJob/DayToJobEdit',
		[
		 	'bui/common',
		 	'common/form/FormContainer',
		 	'bui/form',
		 	'bui/calendar',
		 	'common/org/OrganizationPicker',
		 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer');
    var DayToJobEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._initDate();
			_self._getShowData();
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
            var _self = this,id = _self.get('dataId');
            $.ajax({
                url:'/kmms/dayToJobAction/findById',
                data:{id : id,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #date").val(data.date);
                        $("#formContainer #dispatch").val(data.dispatch);
                        $("#formContainer #leader").val(data.leader);
                        $("#formContainer #cadre").val(data.cadre);
                    }
                }
            })
        },
        
        _initDate: function () {
            var _self = this;
            var date = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #date',
//                showTime: true,
                autoRender: true,
                textField:'#formContainer #date'
            });
            _self.set('date', date);
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
                    itemColspan : 1,
                    item : '<input type="text"  name="date" id="date" class="calendar"  style="width:99.5%" readonly />'
                },{
                    label : '值班调度：',
                    itemColspan : 1,
                    item : '<input type="text" name="dispatch" id="dispatch" style="width:99%"/>'
                },{
                    label : '值班领导：',
                    itemColspan : 1,
                    item : '<input type="text" name="leader" id="leader"  style="width:99%"/>'
                },{
                    label : '值班干部：',
                    itemColspan : 1,
                    item : '<input type="text" name="cadre" id="cadre" style="width:99%"/>'
                }
            ];
            var form = new FormContainer({
                id : 'dayToJobEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'dayToJobEditDialog'},
            elAttrs : {value: {id:"dayToJobEdit"}},
            title:{value:'修改'},
            width:{value:600},
            height:{value:165},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            dataId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('dayToJobEditForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('id',_self.get('dataId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/dayToJobAction/updateDoc");
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
    return DayToJobEdit;
});