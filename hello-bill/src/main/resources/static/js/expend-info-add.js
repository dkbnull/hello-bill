/**
 * expend-info-add.html js
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
let $, form;

layui.use(['layer', 'form', 'laydate'], function () {
    $ = layui.jquery;
    form = layui.form;

    initDatetime(datetimeCalc(0));
    initClass();
    initData();
    initMethod();
})

function initDatetime(dateTime) {
    const laydate = layui.laydate;

    laydate.render({
        elem: '#expend-time-input',
        type: 'datetime',
        theme: 'grid',
        value: dateTime,
        max: 1,
        done: function (value, date) {
            $("#expend-time-value-input").val(value);
        }
    });
}

function initClass() {
    const request = {
        username: getItem("username")
    };

    doPost("expend/classQuery", request, callback)
}

function initData() {
    const search = window.location.search;
    if (search.startsWith("?uuid")) {
        const request = {
            username: getItem("username"),
            uuid: search.substring(6, search.length)
        };

        doPost("expend/query", request, callbackQuery)
    }
}

function initMethod() {
    $("#expend-time-value-input").on("input", function (e) {
        initDatetime(e.delegateTarget.value.replaceAll("：", ":"));
    });
}

function addInfo() {
    const data = form.val('expendInfo');
    if (!checkData(data)) {
        return
    }

    data.username = getItem("username");
    doPost("expend/add", data, callbackAdd);
}

function updateInfo() {
    const data = form.val('expendInfo');
    if (!checkData(data)) {
        return
    }

    const search = window.location.search;
    data.username = getItem("username");
    data.uuid = search.substring(6, search.length);
    doPost("expend/update", data, callbackAdd);
}

function checkData(data) {
    const error = $(".error");
    if (data.expendTime.length === 0) {
        error.text("时间不能为空");
        return false;
    }
    if (data.secondClass.length === 0) {
        error.text("分类不能为空");
        return false;
    }
    if (data.detail.length === 0) {
        error.text("明细不能为空");
        return false;
    }
    if (data.amount.length === 0) {
        error.text("金额不能为空");
        return false;
    }

    error.text("");
    return true;
}

function callback(result) {
    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    for (let i = 0; i < result.data.length; i++) {
        $("#second-class-select").append('<option>' + result.data[i] + '</option>');
    }

    form.render();
}

function callbackQuery(result) {
    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    const data = result.data;
    initDatetime(data.expendTime);
    $("#expend-time-value-input").val(data.expendTime);
    $("#second-class-select").val(data.secondClass);
    $("#detail-input").val(data.detail);
    $("#amount-input").val(data.amount);
    $("#remark-input").val(data.remark);

    form.render();
}

function callbackAdd(result) {
    const error = $(".error");

    if (!isSuccess(result.code)) {
        error.text(result.message);
        return;
    }

    parent.closeAll(result.message);
}
