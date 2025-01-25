/**
 * report-income-class.html js
 *
 * @author null  2024-02-12
 * https://github.com/dkbnull/HelloBill
 */
let $, form;

layui.use(['element', 'layer', 'laydate', 'form'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;
    form = layui.form;

    initDatetime();
});

function initDatetime() {
    const laydate = layui.laydate;
    laydate.render({
        elem: '#report-date',
        type: 'year',
        theme: 'grid',
        value: dateFormatYear(),
        max: 0,
        change: function (value, date) {
            $('.laydate-btns-confirm').click();
        },
        done: function (value, date) {
            doPostReportClass(value);
            doPostReportDetail(value);
        }
    });
}

function doPostReportClass(reportDate) {
    const data = {
        type: '1',
        reportDate: reportDate,
        topClass: $('#top-class-select').val()
    };

    doPost("report/incomeClass", data, callbackReportClass)
}

function callbackReportClass(result) {
    barChartReportClass(result.data.secondClass, result.data.secondAmount);
    pieChartReportClass(result.data.secondClass, result.data.secondAmount);
}

function doPostReportDetail(reportDate) {
    const data = {
        type: $('input[name="type"]:checked').val(),
        reportDate: reportDate,
        topClass: $('#top-class-select').val(),
        secondClass: $('#second-class-select').val()
    };

    doPost("report/incomeDetail", data, callbackReportDetail)
}

function callbackReportDetail(result) {
    barChartReportDetail(result.data.secondDetail, result.data.secondAmount);
}
