/**
 * *-report.html js
 *
 * @author dukunbiao(null)  2021-01-27
 * https://github.com/dkbnull/HelloBill
 */
function callback(result) {
    barChart(result.data.reportDate, result.data.date, result.data.total);
    classPieChart(result.data.reportClass);
    secondClassPieChart(result.data.reportSecondClass);
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
    const secondClassPieChart = $("#report-second-class-pie-chart");
    secondClassPieChart.empty();

    if (data === null) {
        return;
    }

    for (let i = 0; i < data.length; i++) {
        let dataItem = data[i];

        const value = '<div class="layui-col-xs3 report-second-class-pie-chart-item" id="report-second-class-pie-chart-' + i + '">' +
            '</div>';
        secondClassPieChart.append(value);
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

function barChart(data, date, total) {
    let seriesData = [];
    for (let i = 0; i < data.length; i++) {
        const dataItem = data[i];
        const seriesDataItem = {
            name: dataItem.topClass,
            type: 'bar',
            stack: '金额',
            label: {
                show: true
            },
            emphasis: {
                focus: 'series'
            },
            data: dataItem.report
        };

        seriesData.push(seriesDataItem)
    }

    seriesData.push({
        name: '总金额',
        type: 'line',
        data: total
    })

    const barChart = echarts.init(document.getElementById("report-class-bar-chart"));
    const option = {
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
        yAxis: [{
            type: 'value'
        }, {
            type: 'value'
        }],
        xAxis: {
            type: 'category',
            data: date
        },
        series: seriesData
    };

    barChart.setOption(option);
}
