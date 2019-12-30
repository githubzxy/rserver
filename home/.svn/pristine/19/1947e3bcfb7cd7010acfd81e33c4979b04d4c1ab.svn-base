/**
 * WebSocket公用组件
 * @author Bili
 * @date 2018/1/15 0015
 */
define('common/websocket/WebSocketComponent',['bui/common','bui/overlay'],function (r) {
    var BUI = r('bui/common'), Overlay = r('bui/overlay');
    var WebSockeetComponent = BUI.Component.Controller.extend({
        initializer:function () {
            var _self = this;
            _self.initWS();
        },
        bindUI:function () {

        },
        destructor:function () {
            var _self = this,
                websocket = _self.get('websocket');
            websocket.close();
        },
        initWS:function () {
            console.log("init websocket");
            var _self = this,
                websocket = new WebSocket('ws://'+_self.get('wshost'));
            websocket.onopen = _self.get('onopen')||_self._onopen;
            websocket.onmessage = _self.get('onmessage')||_self._onmessage;
            websocket.onerror = _self.get('onerror')||_self._onerror.bind(_self);
            websocket.onclose = _self.get('onclose')||_self._onclose.bind(_self);
            _self.set('websocket',websocket);
        },
        _onopen:function (e) {
            console.log(e,"open");
        },
        _onmessage:function (e) {
            console.log(e,"message");
        },
        _onerror:function (e) {
            console.log(e,"error");
            BUI.Message.Alert('数据错误！','error');
        },
        _onclose:function (e) {
            console.log(e,"close");
            var _self =this;
            //断开连接时15秒重连
            setTimeout(function(){
                _self.initWS();
            },_self.get('retime'));
        },
        sendString:function (msg) {
            var _self = this,
                websocket = _self.get('websocket');
            websocket.send(msg);
        },
        sendJson:function (data) {
            var _self = this,
                websocket = _self.get('websocket'),
                json = JSON.stringify(data);
            websocket.send(json);
        }
    },{
        ATTRS:{
            wshost:{},//socket地址
            onmessage:{},
            onopen:{},
            onerror:{},
            onclose:{},
            retime:{value:15000}//断线重连时间
        }
    });
    return WebSockeetComponent;
});