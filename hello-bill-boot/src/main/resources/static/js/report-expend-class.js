/**
 * report-expend-class.html js
 *
 * @author null  2024-02-11
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
    initClass();
    initMethod();
});

function initMethod() {
    form.on('select(topClass)', function (data) {
        $("#report-class-bar-chart").removeClass("report-display-none");
        $("#report-class-pie-chart").removeClass("report-display-none");

        doPostSecond();
        doPostReportClass($('#report-date').val());
        doPostReportDetail($('#report-date').val());
    });
    form.on('select(secondClass)', function (data) {
        $("#report-class-bar-chart").addClass("report-display-none");
        $("#report-class-pie-chart").addClass("report-display-none");

        if (isEmpty(data.value)) {
            $("#report-class-bar-chart").removeClass("report-display-none");
            $("#report-class-pie-chart").removeClass("report-display-none");

            doPostReportClass($('#report-date').val());
        }

        doPostReportDetail($('#report-date').val());
    });
}

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
            $("#report-class-bar-chart").addClass("report-display-none");
            $("#report-class-pie-chart").addClass("report-display-none");

            if (isEmpty($('#second-class-select').val())) {
                $("#report-class-bar-chart").removeClass("report-display-none");
                $("#report-class-pie-chart").removeClass("report-display-none");

                doPostReportClass(value);
            }

            doPostReportDetail(value);
        }
    });
}

function initClass() {
    const data = {
        type: '0'
    };

    doPost("class/queryClass", data, callbackTop);
}

function callbackTop(result) {
    const topClassSelect = $("#top-class-select");
    topClassSelect.empty();
    for (let i = 0; i < result.data.length; i++) {
        topClassSelect.append('<option>' + result.data[i] + '</option>');
    }

    form.render();
    doPostSecond();

    $("#report-class-bar-chart").removeClass("report-display-none");
    $("#report-class-pie-chart").removeClass("report-display-none");

    doPostReportClass($('#report-date').val());
}

function doPostSecond() {
    const data = {
        type: '0',
        topClass: $('#top-class-select').val()
    };

    doPost("class/queryClass", data, callbackSecond);
}

function callbackSecond(result) {
    const secondClassSelect = $("#second-class-select");
    secondClassSelect.empty();
    secondClassSelect.append('<option></option>')
    for (let i = 0; i < result.data.length; i++) {
        secondClassSelect.append('<option>' + result.data[i] + '</option>');
    }

    form.render();
}

function doPostReportClass(reportDate) {
    const data = {
        reportDate: reportDate,
        topClass: $('#top-class-select').val()
    };

    doPost("report/expendClass", data, callbackReportClass)
}

function callbackReportClass(result) {
    barChartReportClass(result.data.secondClass, result.data.secondAmount);
    pieChartReportClass(result.data.secondClass, result.data.secondAmount);
}

function doPostReportDetail(reportDate) {
    const data = {
        reportDate: reportDate,
        topClass: $('#top-class-select').val(),
        secondClass: $('#second-class-select').val()
    };

    doPost("report/expendDetail", data, callbackReportDetail)
}

function callbackReportDetail(result) {
    barChartReportDetail(result.data.secondDetail, result.data.secondAmount);
}
