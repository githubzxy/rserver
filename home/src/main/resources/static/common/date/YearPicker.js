/**
 * 仅选择年份的公用模块，使用时需要引入yearPicker.css文件
 */
seajs.use('common/date/yearPicker.css');
define('common/date/YearPicker',['bui/common','bui/overlay','bui/list','bui/toolbar'],function(require){
	var BUI = require('bui/common'),Component = BUI.Component,Toolbar = require('bui/toolbar'),
	Overlay = require('bui/overlay').Overlay,List = require('bui/list').SimpleList,
	PREFIX = BUI.prefix, DATA_YEAR = 'data-year',
	CLS_YEAR = 'x-monthpicker-year',CLS_YEAR_NAV = 'x-monthpicker-yearnav',
	CLS_SELECTED = 'x-monthpicker-selected',CLS_ITEM = 'x-monthpicker-item';
	var YearPanel = List.extend({
		  bindUI : function(){
		    var _self = this,
		      el = _self.get('el');
		    el.delegate('a','click',function(ev){
		      ev.preventDefault();
		    });

		    el.delegate('.' + CLS_YEAR,'dblclick',function(){
		      _self.fire('yeardblclick');
		    });

		    el.delegate('.x-icon','click',function(ev){
		      var sender = $(ev.currentTarget);

		      if(sender.hasClass(CLS_YEAR_NAV + '-prev')){
		        _self._prevPage();
		      }else if(sender.hasClass(CLS_YEAR_NAV + '-next')){
		        _self._nextPage();
		      }
		    });
		    _self.on('itemselected',function(ev){
		      if(ev.item){
		        _self.setInternal('year',ev.item.value);
		      }
		      
		    });
		  },
		  _prevPage : function(){
		    var _self = this,
		      start = _self.get('start'),
		      yearCount = _self.get('yearCount');
		    _self.set('start',start - yearCount);
		  },
		  _nextPage : function(){
		    var _self = this,
		      start = _self.get('start'),
		      yearCount = _self.get('yearCount');
		    _self.set('start',start + yearCount);
		  },
		  _uiSetStart : function(){
		    var _self = this;
		    _self._setYearsContent();
		  },
		  _uiSetYear : function(v){
		    var _self = this,
		      item = _self.findItemByField('value',v);
		    if(item){
		      _self.setSelectedByField(v);
		    }else{
		      _self.set('start',v);
		    }
		  },
		  _setYearsContent : function(){
		    var _self = this,
		      year = _self.get('year'),
		      start = _self.get('start'),
		      yearCount = _self.get('yearCount'),
		      items = [];

		    for(var i = start;i< start + yearCount;i++){
		      var text = i.toString();

		      items.push({text:text,value:i});
		    }
		    _self.set('items',items);
		    _self.setSelectedByField(year);
		  }

		},{
		  ATTRS:{
		    items:{
		      view:true,
		      value:[]
		    },
		    elCls : {
		      view:true,
		      value:'x-monthpicker-years'
		    },
		    itemCls : {
		      value : CLS_ITEM
		    },
		    year:{},
		    /**
		     * 起始年
		     * @private
		     * @ignore
		     * @type {Number}
		     */
		    start:{
		      value: new Date().getFullYear()
		    },
		    /**
		     * 年数
		     * @private
		     * @ignore
		     * @type {Number}
		     */
		    yearCount:{
		      value:12
		    },
		    itemTpl : {
		      view:true,
		      value : '<li class="'+CLS_ITEM+' '+CLS_YEAR+'"><a href="#" hidefocus="on">{text}</a></li>'
		    },
		    tpl : {
		      view:true,
		      value:'<div class="'+CLS_YEAR_NAV+'">'+
		            '<span class="'+CLS_YEAR_NAV+'-prev x-icon x-icon-normal x-icon-small"><span class="icon icon-caret icon-caret-left"></span></span>'+
		            '<span class="'+CLS_YEAR_NAV+'-next x-icon x-icon-normal x-icon-small"><span class="icon icon-caret icon-caret-right"></span></span>'+
		            '</div>'+
		            '<ul></ul>'
		    }
		  }
		});

	var YearPicker = Overlay.extend({
		initializer : function(){
		    var _self = this,
		      children = _self.get('children'),
		      yearPanel = new YearPanel(),
		      footer = _self._createFooter();
		    children.push(yearPanel);
		    children.push(footer);

		    _self.set('yearPanel',yearPanel);
		  },
		  bindUI : function(){
		    var _self = this;

		    _self.get('yearPanel').on('itemselected',function(ev){
		      if(ev.item){
		        _self.setInternal('year',ev.item.value);
		      }
		    }).on('yeardblclick',function(){
		      _self._successCall();
		    });

		  },
		  _successCall : function(){
		    var _self = this,
		      callback = _self.get('success');

		    if(callback){
		      callback.call(_self);
		    }
		  },
		  _createFooter : function(){
		    var _self = this;
		    return new Toolbar.Bar({
		        elCls : PREFIX + 'clear x-monthpicker-footer',
		        children:[
		          {
		            xclass:'bar-item-button',
		            text:'确定',
		            btnCls: 'button button-small button-primary',
		            handler:function(){
		              _self._successCall();
		            }
		          },{
		            xclass:'bar-item-button',
		            text:'取消',
		            btnCls:'button button-small last',
		            handler:function(){
		              var callback = _self.get('cancel');
		              if(callback){
		                callback.call(_self);
		              }
		            }
		          }
		        ]
		      });
		  },
		  _uiSetYear : function(v){
		    this.get('yearPanel').set('year',v);
		  }
	},{
		ATTRS : {
			 /**
		     * 下部工具栏
		     * @private
		     * @type {Object}
		     */
		    footer : {},
		    align : {
		      value : {}
		    },
		    elCls : {value:'common-yearpicker'},
		    /**
		     * 选中的年
		     * @type {Number}
		     */
		    year : {},
		    /**
		     * 成功的回调函数
		     * @type {Function}
		     */
		    success:{
		      value : function(){}
		    },
		    /**
		     * 取消的回调函数
		     * @type {Function}
		     */
		    cancel :{
		    	value : function(){} 
		    },
		    width:{
		      value:180
		    },
		    /**
		     * 选择年的控件
		     * @private
		     * @type {Object}
		     */
		    yearPanel : {}
		}
	});
	return YearPicker;
})