define("kmms/productionManage/JobRecord/MonthPic",
    [
        "bui/common",
        "bui/calendar"
    ],
    function(r) {
        var BUI = r("bui/common"),
            Calendar = r("bui/calendar");
        var MonthPicker = BUI.Component.Controller.extend({
            initializer: function() {
                var _self = this;
                _self._initMonthPicker();
            },
            renderUI: function() {},
            bindUI: function() {

            },
            _initMonthPicker: function() {
                var _self = this;
                var inputEl = $(_self.get('renderName')),
                    monthpicker = new BUI.Calendar.MonthPicker({
                        trigger: inputEl,
                        // month:1, //月份从0开始，11结束
                        // render:'#dateExport',
                        autoHide: true,
                        align: {
                            points: ['bl', 'tl']
                        },
                        year: 2019,
                        success: function() {
                            var month = this.get('month'),
                                year = this.get('year');
                            alert(month);
                            inputEl.val(year + '-' + (month + 1)); //月份从0开始，11结束
                            this.hide();
                        }
                    });
                monthpicker.render();
                monthpicker.on('show', function(ev) {
                    var val = inputEl.val(),
                        arr, month, year;
                    if (val) {
                        arr = val.split('-'); //分割年月
                        year = parseInt(arr[0]);
                        month = parseInt(arr[1]);
                        monthpicker.set('year', year);
                        monthpicker.set('month', month - 1);
                    }
                });
            },
        }, {
            ATTRS: {
                renderName: {}
            }
        });
        return MonthPicker;
    });