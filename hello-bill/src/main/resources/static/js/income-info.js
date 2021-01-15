/**
 * income-info.html js
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
let $;

layui.use(['layer', 'table', 'laydate'], function () {
    $ = layui.jquery;
    const url = parent.window.location.href;
    if (url.lastIndexOf("home.html") < 0) {
        window.location.href = "home.html";
        return;
    }

    initDatetime();
    initMethod();
    doPostQuery(dateBeginTheYear(), dateCalc(0));
});

function initDatetime() {
    const laydate = layui.laydate;

    laydate.render({
        elem: '#begin-date-input',
        theme: 'grid',
        value: dateBeginTheYear(),
        max: 0,
        done: function (value, date) {
            doPostQuery(value, $('#end-date-input').val());
        }
    });
    laydate.render({
        elem: '#end-date-input',
        value: dateCalc(0),
        max: 0,
        done: function (value, date) {
            doPostQuery($('#begin-date-input').val(), value);
        }
    });
}

function initMethod() {
    const layer = layui.layer;

    $('.reload-info .layui-btn').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        reloadInfo: function () {
            doPostQuery($('#begin-date-input').val(), $('#end-date-input').val());
        },

        addInfo: function () {
            layer.open({
                type: 2
                , title: '记账'
                , content: 'income-info-add.html'
                , area: ['350px', '500px']
                , maxmin: true
                , shade: 0
                , btn: ['确认', '取消']
                , yes: function (index, layero) {
                    const childWindow = layero.find('iframe')[0].contentWindow;
                    childWindow.addInfo();
                }
                , btn2: function () {
                    layer.closeAll();
                }
            });
        }
    };
}

function doPostQuery(beginDate, endDate) {
    const request = {
        username: localStorage.getItem("username"),
        beginDate: beginDate,
        endDate: endDate,
        secondClass: $('#second-class-input').val(),
        detail: $('#detail-input').val(),
        order: $('input[name="order"]:checked').val()
    };

    doPost("income/query", request, callback)
}

function callback(result) {
    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    const table = layui.table;
    table.render({
        elem: '#info-table',
        data: result.data,
        cellMinWidth: 100,
        totalRow: true,
        cols: [[
            {field: 'incomeDate', title: '日期', sort: true},
            {field: 'secondClass', title: '分类', sort: true},
            {field: 'detail', title: '明细'},
            {field: 'amount', title: '金额', totalRow: true},
            {field: 'remark', title: '备注'},
            {fixed: 'right', title: '操作', toolbar: '#info-table-bar', width: 80}
        ]],
        page: true,
        limit: 10
    });

    table.on('tool(infoTable)', function (obj) {
        if (obj.event === 'del') {
            layer.confirm('是否删除当前收入明细？', function (index) {
                const request = {
                    username: localStorage.getItem("username"),
                    uuid: obj.data.uuid
                };

                doPost("income/delete", request, callbackDelete)
                layer.close(index);
            });
        }
    });
}

function callbackDelete(result) {
    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    doPostQuery($('#begin-date-input').val(), $('#end-date-input').val());
}

function closeAll() {
    layer.closeAll();

    doPostQuery($('#begin-date-input').val(), $('#end-date-input').val());
}