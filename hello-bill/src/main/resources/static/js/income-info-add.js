/**
 * income-info-add.html js
 *
 * @author dukunbiao(null)  2021-01-01
 * https://github.com/dkbnull/HelloBill
 */
let $, form;

layui.use(['layer', 'form', 'laydate'], function () {
    $ = layui.jquery;
    form = layui.form;

    initDatetime(dateCalc(0));
    initClass();
    initData();
    initMethod();
})

function initDatetime(date) {
    const laydate = layui.laydate;

    laydate.render({
        elem: '#income-date-input',
        theme: 'grid',
        value: date,
        max: 0
    });
}

function initClass() {
    doPost("income/classQuery", null, callback)
}

function initData() {
    const search = window.location.search;
    if (search.startsWith("?uuid")) {
        const data = {
            uuid: search.substring(6, search.length)
        };

        doPost("income/query", data, callbackQuery)
    }
}

function initMethod() {
    $('#detail-input').on('keydown', function (event) {
        if (event.keyCode === 13) {
            $('#amount-input').focus();
            return false
        }
    });
    $('#amount-input').on('keydown', function (event) {
        if (event.keyCode === 13) {
            $('#remark-input').focus();
            return false
        }
    });
    $('#remark-input').on('keydown', function (event) {
        if (event.keyCode === 13) {
            addInfo();
            return false
        }
    });
}

function addInfo() {
    const data = form.val('incomeInfo');
    if (!checkData(data)) {
        return
    }

    doPost("income/add", data, callbackAdd);
}

function updateInfo() {
    const data = form.val('incomeInfo');
    if (!checkData(data)) {
        return
    }

    const search = window.location.search;
    data.uuid = search.substring(6, search.length);
    doPost("income/update", data, callbackAdd);
}

function checkData(data) {
    const error = $(".error");
    if (data.incomeDate.length === 0) {
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
    for (let i = 0; i < result.data.length; i++) {
        $("#second-class-select").append('<option>' + result.data[i] + '</option>');
    }

    form.render();
}

function callbackQuery(result) {
    const data = result.data;
    initDatetime(data.incomeDate);
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
