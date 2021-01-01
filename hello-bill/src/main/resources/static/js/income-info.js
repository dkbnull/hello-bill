/**
 * income-info.html js
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['layer', 'table', 'laydate'], function () {
    const url = parent.window.location.href;
    if (url.lastIndexOf("home.html") < 0) {
        window.location.href = "home.html";
        return;
    }

    initDatetime();
    initMethod();
    doPostQuery();
});

function initDatetime() {
    const laydate = layui.laydate;

    laydate.render({
        elem: '#begin-date-input',
        theme: 'grid',
        value: dateCalc(-31),
        max: 0,
        done: function (value, date) {
            doPostQuery();
        }
    });
    laydate.render({
        elem: '#end-date-input',
        value: dateCalc(0),
        max: 0,
        done: function (value, date) {
            doPostQuery();
        }
    });
}

function initMethod() {
    const $ = layui.jquery, layer = layui.layer;

    $('.reload-info .layui-btn').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        reloadInfo: function () {
            doPostQuery();
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

function doPostQuery() {
    const $ = layui.jquery;
    const request = {
        username: localStorage.getItem("username"),
        beginDate: $('#begin-date-input').val(),
        endDate: $('#end-date-input').val(),
        secondClass: $('#second-class-input').val(),
        detail: $('#detail-input').val(),
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
        cols: [[
            {field: 'incomeDate', title: '日期', sort: true},
            {field: 'secondClass', title: '分类', sort: true},
            {field: 'detail', title: '明细'},
            {field: 'amount', title: '金额'},
            {field: 'remark', title: '备注'}
        ]],
        page: true,
        limit: 20
    });
}

function closeAll() {
    layer.closeAll();

    doPostQuery();
}