/**
 * expend-report.html js
 *
 * @author dukunbiao(null)  2021-01-26
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['element', 'layer', 'laydate'], function () {
    if (!validate()) {
        return;
    }

    const laydate = layui.laydate;
    laydate.render({
        elem: '#report-date',
        type: 'year',
        theme: 'grid',
        value: dateFormatYear(),
        max: 0,
        done: function (value, date) {
            doPostReport(value);
        }
    });

    doPostReport(dateFormatYear());
});

function doPostReport(reportDate) {
    const request = {
        username: getItem("username"),
        reportDate: reportDate
    };

    doPost("expend/report", request, callback)
}