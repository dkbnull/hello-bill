/**
 * balance-sheet.html js
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
let $;

layui.use(['layer', 'table', 'laydate'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;

    doPostQuery();
});

function doPostQuery() {
    doPost("balance/query", null, callback)
}

function callback(result) {
    const table = layui.table;
    table.render({
        elem: '#info-table',
        data: result.data,
        cellMinWidth: 100,
        totalRow: true,
        cols: [[
            {field: 'balanceDate', title: '日期'},
            {field: 'incomeAmount', title: '收入'},
            {field: 'expendAmount', title: '支出'},
            {field: 'balanceAmount', title: '余额'},
            {field: 'remark', title: '备注'},
        ]],
        page: true,
        limit: 10
    });
}
