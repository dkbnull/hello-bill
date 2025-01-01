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

    doPostQuery(dateFormatYear());
    doPostQueryNet(dateFormatYear());
});

function doPostQuery() {
    doPost("report/query", null, function (result) {
        barChartReport(result, '总收支报表', "report-query-bar-chart");
    })
}

function doPostQueryNet() {
    doPost("report/queryNet", null, function (result) {
        barChartReport(result, '净收支报表', "report-query-net-bar-chart");
    })
}

function barChartReport(result, title, id) {
    const barChart = echarts.init(document.getElementById(id));
    const option = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: result.data.date
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        series: [
            {
                name: '收入',
                type: 'bar',
                data: result.data.incomeData,
                label: {
                    show: true,
                    position: 'top'
                },
            },
            {
                name: '支出',
                type: 'bar',
                data: result.data.expendData,
                label: {
                    show: true,
                    position: 'top'
                },
            }
        ]
    };

    barChart.setOption(option);
}