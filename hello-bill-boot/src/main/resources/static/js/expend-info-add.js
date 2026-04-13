/**
 * expend-info-add.html js
 *
 * @author null
 * @date 2020-12-31
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
const expendAddConfig = {
    classType: '0',
    formFilter: 'expendInfo',
    queryUrl: 'expend/query',
    addUrl: 'expend/add',
    updateUrl: 'expend/update',
    requiredFields: [
        {name: 'expendTime', message: '时间不能为空'},
        {name: 'secondClass', message: '分类不能为空'},
        {name: 'detail', message: '明细不能为空'},
        {name: 'amount', message: '金额不能为空'}
    ],
    initDatetime: function ($, form) {
        const laydate = layui.laydate;
        laydate.render({
            elem: '#expend-date-input',
            type: 'datetime',
            theme: 'grid',
            value: datetimeCalc(0),
            max: 1,
            done: function (value) {
                $('#expend-time-value-input').val(value);
            }
        });
    },
    fillForm: function (data) {
        $('#expend-time-value-input').val(data.expendTime);
        $('#second-class-select').val(data.secondClass);
        $('#detail-input').val(data.detail);
        $('#amount-input').val(data.amount);
        $('#remark-input').val(data.remark);
    },
    onDataLoaded: function (data) {
        const laydate = layui.laydate;
        laydate.render({
            elem: '#expend-date-input',
            type: 'datetime',
            theme: 'grid',
            value: data.expendTime,
            max: 1,
            done: function (value) {
                $('#expend-time-value-input').val(value);
            }
        });
    },
    bindKeyEvents: function () {
        $('#expend-time-value-input').on('input', function (e) {
            let value = e.delegateTarget.value.replaceAll('：', ':');
            if (value.indexOf('-') === -1) {
                value = formatDateYyyy(value);
            }
            const laydate = layui.laydate;
            laydate.render({
                elem: '#expend-date-input',
                type: 'datetime',
                theme: 'grid',
                value: value,
                max: 1,
                done: function (val) {
                    $('#expend-time-value-input').val(val);
                }
            });
        });

        $('#expend-time-value-input').focus();
        $('#expend-time-value-input').on('keydown', function (event) {
            if (event.keyCode === 13) {
                $('#second-class-select').focus();
                return false;
            }
        });
        $('#second-class-select').on('keydown', function (event) {
            if (event.keyCode === 13) {
                $('#detail-input').focus();
                return false;
            }
        });
        $('#detail-input').on('keydown', function (event) {
            if (event.keyCode === 13) {
                $('#amount-input').focus();
                return false;
            }
        });
        $('#amount-input').on('keydown', function (event) {
            if (event.keyCode === 13) {
                $('#remark-input').focus();
                return false;
            }
        });
        $('#remark-input').on('keydown', function (event) {
            if (event.keyCode === 13) {
                addInfo();
                return false;
            }
        });
    }
};

let form;

layui.use(['layer', 'form', 'laydate'], function () {
    $ = layui.jquery;
    form = layui.form;

    expendAddConfig.initDatetime($, form);
    initAddClass(expendAddConfig.classType);
    initAddData(expendAddConfig);
    initAddMethod(expendAddConfig);
});

function addInfo() {
    submitBillInfo(form, expendAddConfig.formFilter, expendAddConfig);
}

function updateInfo() {
    submitBillInfo(form, expendAddConfig.formFilter, expendAddConfig);
}