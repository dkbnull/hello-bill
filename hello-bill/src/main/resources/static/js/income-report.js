/**
 * income-report.html js
 *
 * @author dukunbiao(null)  2021-01-27
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
    doPostReport(dateFormatYear());
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
            doPostReport(value);
        }
    });
}

function doPostReport(reportDate) {
    const request = {
        username: getItem("username"),
        reportDate: reportDate
    };

    doPost("income/report", request, callback)
}