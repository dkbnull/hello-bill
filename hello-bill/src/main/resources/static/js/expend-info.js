/**
 * expend-info.html js
 *
 * @author dukunbiao(null)  2020-12-31
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
    doPostInfo();
});

function initDatetime() {
    const laydate = layui.laydate;

    laydate.render({
        elem: '#begin-time-input',
        theme: 'grid',
        value: dateCalc(-7),
        max: 0,
        done: function (value, date) {
            doPostInfo();
        }
    });
    laydate.render({
        elem: '#end-time-input',
        value: dateCalc(0),
        max: 0,
        done: function (value, date) {
            doPostInfo();
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
            doPostInfo();
        },

        addInfo: function () {
            layer.open({
                type: 2
                , title: '记账'
                , content: 'expend-info-add.html'
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

function doPostInfo() {
    const $ = layui.jquery;
    const request = {
        username: localStorage.getItem("username"),
        beginTime: $('#begin-time-input').val(),
        endTime: $('#end-time-input').val(),
        topClass: $('#top-class-input').val(),
        secondClass: $('#second-class-input').val(),
        detail: $('#detail-input').val(),
    };

    doPost("expend/info", request, callback)
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
            {field: 'expendTime', title: '时间', sort: true},
            {field: 'topClass', title: '顶级分类', sort: true},
            {field: 'secondClass', title: '二级分类', sort: true},
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

    doPostInfo();
}