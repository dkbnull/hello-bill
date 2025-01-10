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

    doPostIncome();
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

function doPostIncome() {
    doPost("report/income", null, function (result) {
        barChartClass(result, '净收入', "report-income-bar-chart");
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

function barChartClass(result, title, id) {
    let seriesData = [];
    const amountData = new Map(Object.entries(result.data.amountData));
    for (let i = 0; i < result.data.date.length; i++) {
        const date = result.data.date[i];
        const seriesDataItem = {
            value: amountData.get(date),
            groupId: date
        };

        seriesData.push(seriesDataItem)
    }

    const option = {
        title: {
            text: title
        },
        xAxis: {
            data: result.data.date,
            axisLabel: {
                interval: 0,
                rotate: 30
            }
        },
        yAxis: {},
        dataGroupId: '',
        animationDurationUpdate: 500,
        series: {
            type: 'bar',
            id: 'sales',
            data: seriesData,
            label: {
                show: true,
                position: 'top'
            },
            universalTransition: {
                enabled: true,
                divideShape: 'clone'
            }
        }
    };

    let drilldownData = [];
    for (let i = 0; i < result.data.date.length; i++) {
        const date = result.data.date[i];
        const classAmountData = result.data.classAmountData[date];
        const classAmountDataMap = new Map(Object.entries(isEmpty(classAmountData) ? {} : classAmountData));

        const data = [];
        classAmountDataMap.forEach((value, key) => {
            const dataItem = [];
            dataItem.push(key);
            dataItem.push(value);
            data.push(dataItem);
        })

        const drilldownDataItem = {
            data: data,
            dataGroupId: date
        };

        drilldownData.push(drilldownDataItem)
    }

    const dom = document.getElementById(id);
    var barChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    barChart.on('click', function (event) {
        if (event.data) {
            const subData = drilldownData.find(function (data) {
                return data.dataGroupId === event.data.groupId;
            });
            if (!subData) {
                return;
            }
            barChart.setOption({
                xAxis: {
                    data: subData.data.map(function (item) {
                        return item[0];
                    })
                },
                series: {
                    type: 'bar',
                    id: 'sales',
                    dataGroupId: subData.dataGroupId,
                    data: subData.data.map(function (item) {
                        return item[1];
                    }),
                    universalTransition: {
                        enabled: true,
                        divideShape: 'clone'
                    }
                },
                graphic: [
                    {
                        type: 'text',
                        left: 50,
                        top: 20,
                        style: {
                            text: 'Back',
                            fontSize: 18
                        },
                        onclick: function () {
                            barChart.setOption(option);
                        }
                    }
                ]
            });
        }
    });

    barChart.setOption(option);
}