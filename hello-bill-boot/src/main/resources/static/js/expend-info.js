/**
 * expend-info.html js
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
const expendListConfig = {
    defaultBeginDate: dateBeginTheMonth(),
    queryUrl: "expend/queryList",
    addPageUrl: 'expend-info-add.html',
    addPageHeight: '620px',
    deleteUrl: "expend/delete",
    editConfirmMsg: '是否修改当前支出明细？',
    deleteConfirmMsg: '是否删除当前支出明细？',
    columns: [
        {field: 'expendTime', title: '时间', width: 180, sort: true},
        {field: 'topClass', title: '顶级分类', width: 110, sort: true},
        {field: 'secondClass', title: '二级分类', width: 110, sort: true},
        {field: 'detail', title: '明细'},
        {field: 'amount', title: '金额', width: 120, totalRow: true},
        {field: 'remark', title: '备注'},
        {fixed: 'right', title: '操作', toolbar: '#info-table-bar', width: 120}
    ],
    buildQueryData: function (beginDate, endDate) {
        return {
            beginDate: beginDate,
            endDate: endDate,
            topClass: $('#top-class-input').val(),
            secondClass: $('#second-class-input').val(),
            detail: $('#detail-input').val(),
            order: $('input[name="order"]:checked').val()
        };
    }
};

initBillListPage(expendListConfig);

function closeAll(message) {
    closeAllAndRefresh(message);
}
