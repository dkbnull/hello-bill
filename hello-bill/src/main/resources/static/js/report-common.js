/**
 * *-report.html js
 *
 * @author dukunbiao(null)  2021-01-27
 * https://github.com/dkbnull/HelloBill
 */
function callback(result) {
    classPieChart(result.data.reportClass);
    secondClassPieChart(result.data.reportSecondClass);
    // lineChart(result.data.reportDate, result.data.date);
}

function classPieChart(data) {
    let legendData = [];
    let seriesData = [];
    for (let i = 0; i < data.length; i++) {
        let dataItem = data[i];

        legendData.push(dataItem.topClass);
        const seriesDataItem = {value: dataItem.amount, name: dataItem.topClass};
        seriesData.push(seriesDataItem);
    }

    pieChart(legendData, seriesData, 'report-class-pie-chart', "");
}

function secondClassPieChart(data) {
    if (data === null) {
        return;
    }

    const secondClassPie = $("#report-second-class-pie-chart");
    for (let i = 0; i < data.length; i++) {
        let dataItem = data[i];

        const value = '<div class="layui-col-xs3 report-second-class-pie-chart-item" id="report-second-class-pie-chart-' + i + '">' +
            '</div>';
        secondClassPie.append(value);
        form.render();

        let legendData = [];
        let seriesData = [];
        for (let i = 0; i < dataItem.reportClass.length; i++) {
            let dataClassItem = dataItem.reportClass[i];

            legendData.push(dataClassItem.secondClass);
            const seriesDataItem = {value: dataClassItem.amount, name: dataClassItem.secondClass};
            seriesData.push(seriesDataItem);
        }

        pieChart(legendData, seriesData, 'report-second-class-pie-chart-' + i, dataItem.topClass);
    }
}

function pieChart(legendData, seriesData, id, title) {
    const pieChart = echarts.init(document.getElementById(id));
    const option = {
        title: {
            text: title,
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
                name: '金额',
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
