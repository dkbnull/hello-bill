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
        elem: '#expend-date-input',
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
    const data = {
        type: '0'
    };

    doPost("class/secondClassQuery", data, callback)
}

function initData() {
    const search = window.location.search;
    if (search.startsWith("?id")) {
        const data = {
            id: search.substring(4, search.length)
        };

        doPost("expend/query", data, callbackQuery)
    }
}

function initMethod() {
    $("#expend-time-value-input").on("input", function (e) {
        let value = e.delegateTarget.value.replaceAll("：", ":");
        if (value.indexOf("-") === -1) {
            value = formatDateYyyy(value);
        }

        initDatetime(value);
    });

    $('#expend-time-value-input').focus();
    $('#expend-time-value-input').on('keydown', function (event) {
        if (event.keyCode === 13) {
            $('#second-class-select').focus();
            return false
        }
    });
    $('#second-class-select').on('keydown', function (event) {
        if (event.keyCode === 13) {
            $('#detail-input').focus();
            return false
        }
    });
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
    const data = form.val('expendInfo');
    if (!checkData(data)) {
        return
    }

    doPost("expend/add", data, callbackAdd);
}

function updateInfo() {
    const data = form.val('expendInfo');
    if (!checkData(data)) {
        return
    }

    const search = window.location.search;
    data.id = search.substring(4, search.length);
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
    for (let i = 0; i < result.data.length; i++) {
        $("#second-class-select").append('<option>' + result.data[i] + '</option>');
    }

    form.render();
}

function callbackQuery(result) {
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
