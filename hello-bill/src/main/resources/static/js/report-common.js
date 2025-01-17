/**
 * *-report.html js
 *
 * @author dukunbiao(null)  2021-01-27
 * https://github.com/dkbnull/HelloBill
 */
function barChartQuery(result, title, id) {
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
                color: '#91cc75',
                data: result.data.incomeData,
                label: {
                    show: true,
                    position: 'top'
                },
            },
            {
                name: '支出',
                type: 'bar',
                color: '#5470c6',
                data: result.data.expendData,
                label: {
                    show: true,
                    position: 'top'
                },
            }
        ]
    };

    const barChart = echarts.init(document.getElementById(id));
    barChart.setOption(option);
}

function barChartClass(result, title, id, tag) {
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
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
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
            color: tag === 1 ? '#91cc75' : '#5470c6',
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

function barChartReportClass(data, seriesData) {
    let option = {
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: data
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: seriesData,
                type: 'bar',
                label: {
                    show: true,
                    position: 'top'
                },
            }
        ]
    };

    const barChart = echarts.init(document.getElementById("report-class-bar-chart"));
    barChart.setOption(option);
}

function pieChartReportClass(data, seriesData) {
    let seriesDataNew = [];
    for (let i = 0; i < data.length; i++) {
        seriesDataNew.push({
            value: seriesData[i],
            name: data[i]
        })
    }

    const option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'right'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        series: [
            {
                type: 'pie',
                radius: '50%',
                data: seriesDataNew,
                label: {
                    formatter: '{name|{b}}\n{per|{d}%}',
                    // formatter: '{b}{abg}\n  {c}  {d}%  ',
                    rich: {
                        per: {
                            fontSize: 10,
                            color: '#999',
                            fontWeight: 'bold',
                            padding: [3, 4],
                            marginTop: 10
                        }
                    }
                },
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

    const pieChart = echarts.init(document.getElementById("report-class-pie-chart"));
    pieChart.setOption(option);
}

function barChartReportDetail(data, seriesData) {
    let option = {
        grid: {
            left: '3%',
            bottom: '3%',
            containLabel: true
        },
        yAxis: {
            type: 'category',
            data: data
        },
        xAxis: {
            type: 'value'
        },
        series: [
            {
                data: seriesData,
                type: 'bar',
                label: {
                    show: true,
                    position: 'right'
                },
            }
        ]
    };

    const barChart = echarts.init(document.getElementById("report-detail-bar-chart"));
    barChart.setOption(option);
}
