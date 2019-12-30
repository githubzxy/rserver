/**
 * 2018-07-26 -- yangsy
 * 使用说明
 * 选择列表中的数据仅有展示的展示数据，没有隐藏的value或者id，封装的数据格式为：['a','b','c','d',...]
 * 展示的数据在调用该组件的业务模块中封装好作为参数传递到该组件中展示
  	例1：
    var _self = this;
	var nameData = []; //封装的数据
	var postLoad = new PostLoad({
		url:'/transform/circuitQuery/getApplication',
	});
	postLoad.load({},function(result){
		if(result.data==null){
			return;
		}
		var data = result.data;
		for(var i=0;i<data.length;i++){
			nameData.push(data[i]);//这里后端的数据封装为List<String>,并且还封装到了ResultMsg中
		}
	});
	
	例2：
	var _self = this;
	var nameData = [];
	var postLoad = new PostLoad({
		url:'/transform/ems/queryAll',
	});
	postLoad.load({},function(result){
		console.log(result);
		if(result==null){
			return;
		}
		for(var i=0;i<result.length;i++){
			nameData.push(result[i].emsName);//这里后端的数据封装是一个List<EMSDTO>
		}
	});
	
	然后在业务模块中的调用方式：
	var suggest = new SelectSuggest({
		renderName : '#application4Serch',//该组件渲染的div的id
		inputName : 'application',
		renderData : nameData,
		width : 168
	});
 * 具体使用可以参考transform/circuitQuery/CircuitQuery
 */
define("kmms/technicalManagement/visitRoomRecords/SelectSuggest",
		[
			"bui/common",
			'bui/select',
		],function(r){
	var BUI = r("bui/common"),
		Select = r('bui/select');
	var SelectSuggest = BUI.Component.Controller.extend({
		initializer:function(){
			var _self = this;
			_self._initSelectSuggest();
		},
		renderUI:function(){
		},
		bindUI:function(){
		},
		_initSelectSuggest:function(){
			var _self = this;
			var suggest = new Select.Suggest({
				width : _self.get('width'),
				render : _self.get('renderName'),
				name : _self.get('inputName'),
				data : _self.get('renderData')
			});
			suggest.render();
			suggest.get('picker').set('elCls', 'suggestApplication');
			$(".suggestApplication").attr("style","height:300px;width:165px;overflow-y:auto;overflow-x:auto;");//border:1px solid #C3C3D6;border-radius:4px
		},
	},{ATTRS:{
		renderName : {},//'#application4Serch' 渲染id
		inputName : {},//'application' 输入框（与传入后端的字段名相同）
		renderData : {},//nameData[] 渲染的数据
		width : {value:168},//组件的宽度默认168
	}});
	return SelectSuggest;
});