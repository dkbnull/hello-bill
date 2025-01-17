/**
 * report.html js
 *
 * @author dukunbiao(null)  2023-01-28
 * https://github.com/dkbnull/HelloBill
 */
let $;

layui.use(['element', 'layer', 'laydate', 'form'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;

    doPostQuery();
    doPostQueryNet();

    doPostExpendDaily();
    doPostExpendLiving();
    doPostExpendChild();

    doPostIncome();
});

function doPostQuery() {
    doPost("report/query", null, function (result) {
        barChartQuery(result, '总收支报表', "report-query-bar-chart");
    })
}

function doPostQueryNet() {
    doPost("report/queryNet", null, function (result) {
        barChartQuery(result, '净收支报表', "report-query-net-bar-chart");
    })
}

function doPostExpendDaily() {
    const data = {
        reportClass: 1
    }

    doPost("report/expend", data, function (result) {
        barChartClass(result, '日常支出', "report-expend-daily-bar-chart");
    })
}

function doPostExpendLiving() {
    const data = {
        reportClass: 2
    }

    doPost("report/expend", data, function (result) {
        barChartClass(result, '生活支出', "report-expend-living-bar-chart");
    })
}

function doPostExpendChild() {
    const data = {
        reportClass: 3
    }

    doPost("report/expend", data, function (result) {
        barChartClass(result, '子女支出', "report-expend-child-bar-chart");
    })
}

function doPostIncome() {
    doPost("report/income", null, function (result) {
        barChartClass(result, '净收入', "report-income-bar-chart", 1);
    })
}
