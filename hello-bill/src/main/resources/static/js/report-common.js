/**
 * *-report.html js
 *
 * @author dukunbiao(null)  2021-01-27
 * https://github.com/dkbnull/HelloBill
 */
function callback(result) {
    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    pieChart(result.data.reportClass);
    lineChart(result.data.reportDate, result.data.date);
}

function pieChart(data) {
    let legendData = [];
    let seriesData = [];
    for (let i = 0; i < data.length; i++) {
        let dataItem = data[i];

        legendData.push(dataItem.secondClass);
        const seriesDataItem = {value: dataItem.amount, name: dataItem.secondClass};
        seriesData.push(seriesDataItem);
    }

    const pieChart = echarts.init(document.getElementById('report-pie-chart'));
    const option = {
        title: {
            text: '支出分类报表',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            top: 20,
            bottom: 20,
            data: legendData
        },
        series: [
            {
                type: 'pie',
                radius: '55%',
                center: ['40%', '50%'],
                data: seriesData,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    pieChart.setOption(option);
}

function lineChart(data, date) {
    let legendData = [];
    let seriesData = [];
    for (let i = 0; i < data.length; i++) {
        const dataItem = data[i];

        legendData.push(dataItem.secondClass);

        let seriesDataTemp = [];
        for (let j = 0; j < dataItem.report.length; j++) {
            const expendReportItem = dataItem.report[j];
            seriesDataTemp.push(expendReportItem)
        }

        const seriesDataItem = {
            name: dataItem.secondClass,
            type: 'line',
            stack: '总量',
            areaStyle: {},
            emphasis: {
                focus: 'series'
            },
            data: seriesDataTemp
        };
        seriesData.push(seriesDataItem)
    }

    const lineChart = echarts.init(document.getElementById("report-line-chart"));
    const option = {
        title: {
            text: "支出明细报表"
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data: legendData
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: date
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: seriesData
    };

    lineChart.setOption(option);
}
