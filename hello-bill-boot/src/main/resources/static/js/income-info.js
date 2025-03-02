/**
 * income-info.html js
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
let $;

layui.use(['layer', 'table', 'laydate'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;

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
        theme: 'grid',
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
                type: 2,
                skin: 'layui-layer-custom',
                title: '记账',
                content: 'income-info-add.html',
                area: ['350px', '560px'],
                shade: 0,
                btn: ['确认', '取消'],
                yes: function (index, layero) {
                    const childWindow = layero.find('iframe')[0].contentWindow;
                    childWindow.addInfo();
                },
                btn2: function () {
                    layer.closeAll();
                }
            });
        }
    };
}

function doPostQuery(beginDate, endDate) {
    const data = {
        beginDate: beginDate,
        endDate: endDate,
        secondClass: $('#second-class-input').val(),
        detail: $('#detail-input').val(),
        order: $('input[name="order"]:checked').val()
    };

    doPost("income/queryList", data, callback)
}

function callback(result) {
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
            {fixed: 'right', title: '操作', toolbar: '#info-table-bar', width: 120}
        ]],
        page: true,
        limit: 10
    });

    table.on('tool(infoTable)', function (obj) {
        if (obj.event === 'edit') {
            layer.confirm('是否修改当前收入明细？', {icon: 3}, function (index) {
                layer.open({
                    type: 2
                    , title: '修改'
                    , content: 'income-info-add.html?id=' + obj.data.id
                    , area: ['350px', '500px']
                    , maxmin: true
                    , shade: 0
                    , btn: ['确认', '取消']
                    , yes: function (index, layero) {
                        const childWindow = layero.find('iframe')[0].contentWindow;
                        childWindow.updateInfo();
                    }
                    , btn2: function () {
                        layer.closeAll();
                    }
                });

                layer.close(index);
            });
        } else if (obj.event === 'del') {
            layer.confirm('是否删除当前收入明细？', {icon: 2}, function (index) {
                const data = {
                    id: obj.data.id
                };

                doPost("income/delete", data, callbackDelete)
                layer.close(index);
            });
        }
    });
}

function callbackDelete(result) {
    doPostQuery($('#begin-date-input').val(), $('#end-date-input').val());
}

function closeAll(message) {
    layer.closeAll();
    layer.msg(message);

    doPostQuery($('#begin-date-input').val(), $('#end-date-input').val());
}