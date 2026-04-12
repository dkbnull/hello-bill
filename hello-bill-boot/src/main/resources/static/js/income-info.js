/**
 * income-info.html js
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
const incomeListConfig = {
    defaultBeginDate: dateBeginTheYear(),
    queryUrl: "income/queryList",
    addPageUrl: 'income-info-add.html',
    addPageHeight: '560px',
    deleteUrl: "income/delete",
    editConfirmMsg: '是否修改当前收入明细？',
    deleteConfirmMsg: '是否删除当前收入明细？',
    columns: [
        {field: 'incomeDate', title: '日期', sort: true},
        {field: 'secondClass', title: '分类', sort: true},
        {field: 'detail', title: '明细'},
        {field: 'amount', title: '金额', totalRow: true},
        {field: 'remark', title: '备注'},
        {fixed: 'right', title: '操作', toolbar: '#info-table-bar', width: 120}
    ],
    buildQueryData: function (beginDate, endDate) {
        return {
            beginDate: beginDate,
            endDate: endDate,
            secondClass: $('#second-class-input').val(),
            detail: $('#detail-input').val(),
            order: $('input[name="order"]:checked').val()
        };
    }
};

initBillListPage(incomeListConfig);

function closeAll(message) {
    closeAllAndRefresh(message);
}
