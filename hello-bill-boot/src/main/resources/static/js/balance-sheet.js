/**
 * balance-sheet.html js
 *
 * @author null
 * @date 2025-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
let $;
let currentPageNum = 1;
let currentPageSize = 10;

layui.use(['layer', 'table', 'laydate'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;

    doPostQuery(1, 10);
});

function doPostQuery(pageNum, pageSize) {
    currentPageNum = pageNum;
    currentPageSize = pageSize;
    doPost('balance/list', {pageNum: pageNum, pageSize: pageSize}, callback);
}

function callback(result) {
    const table = layui.table;
    const pageData = result.data;

    table.render({
        elem: '#info-table',
        data: pageData.records,
        limit: pageData.size,
        cellMinWidth: 100,
        totalRow: true,
        cols: [[
            {field: 'balanceDate', title: '日期'},
            {field: 'incomeAmount', title: '收入'},
            {field: 'expendAmount', title: '支出'},
            {field: 'balanceAmount', title: '余额'},
            {field: 'remark', title: '备注'},
        ]]
    });

    renderPage(pageData, function (curr, limit) {
        doPostQuery(curr, limit);
    });
}
