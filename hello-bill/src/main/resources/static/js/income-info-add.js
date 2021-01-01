/**
 * income-info-add.html js
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['layer', 'form', 'laydate'], function () {
    if (isEmpty(localStorage.getItem("username"))) {
        window.location.href = "index.html";
        return
    }

    initDatetime();

    const request = {
        username: localStorage.getItem("username")
    };

    doPost("income/classQuery", request, callback)
})

function initDatetime() {
    const laydate = layui.laydate;

    laydate.render({
        elem: '#income-date-input',
        theme: 'grid',
        value: dateCalc(0)
    });
}

function addInfo() {
    const $ = layui.jquery, form = layui.form;
    const data = form.val('incomeInfo');
    const error = $(".error");
    if (data.incomeDate.length === 0) {
        error.text("时间不能为空");
        return;
    }
    if (data.secondClass.length === 0) {
        error.text("分类不能为空");
        return;
    }
    if (data.detail.length === 0) {
        error.text("明细不能为空");
        return;
    }
    if (data.amount.length === 0) {
        error.text("金额不能为空");
        return;
    }

    error.text("");
    data.username = localStorage.getItem("username");
    doPost("income/add", data, callbackAdd);
}

function callback(result) {
    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    const $ = layui.jquery, form = layui.form;
    for (let i = 0; i < result.data.length; i++) {
        $("#second-class-select").append('<option>' + result.data[i] + '</option>');
    }

    form.render();
}

function callbackAdd(result) {
    const $ = layui.jquery, error = $(".error");

    if (!isSuccess(result.code)) {
        error.text(result.message);
        return;
    }

    parent.closeAll();
}
