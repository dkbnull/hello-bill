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

    doPostReport(dateFormatYear());
});

function doPostReport() {
    const request = {
        username: getItem("username"),
    };

    doPost("report/query", request, callback)
}

function callback(result) {
    const barChart = echarts.init(document.getElementById("report-class-bar-chart"));
    const option = {
        title: {
            text: '收支报表'
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
                data: result.data.incomeData
            },
            {
                name: '支出',
                type: 'bar',
                data: result.data.expendData
            }
        ]
    };

    barChart.setOption(option);
}