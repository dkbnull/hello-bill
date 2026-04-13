/**
 * income-info-add.html js
 *
 * @author null
 * @date 2021-01-01
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
const incomeAddConfig = {
    classType: '1',
    formFilter: 'incomeInfo',
    queryUrl: 'income/query',
    addUrl: 'income/add',
    updateUrl: 'income/update',
    requiredFields: [
        {name: 'incomeDate', message: '时间不能为空'},
        {name: 'secondClass', message: '分类不能为空'},
        {name: 'detail', message: '明细不能为空'},
        {name: 'amount', message: '金额不能为空'}
    ],
    initDatetime: function ($, form) {
        const laydate = layui.laydate;
        laydate.render({
            elem: '#income-date-input',
            theme: 'grid',
            value: dateCalc(0),
            max: 0
        });
    },
    fillForm: function (data) {
        $('#second-class-select').val(data.secondClass);
        $('#detail-input').val(data.detail);
        $('#amount-input').val(data.amount);
        $('#remark-input').val(data.remark);
    },
    onDataLoaded: function (data) {
        const laydate = layui.laydate;
        laydate.render({
            elem: '#income-date-input',
            theme: 'grid',
            value: data.incomeDate,
            max: 0
        });
    },
    bindKeyEvents: function () {
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

    incomeAddConfig.initDatetime($, form);
    initAddClass(incomeAddConfig.classType);
    initAddData(incomeAddConfig);
    initAddMethod(incomeAddConfig);
});

function addInfo() {
    submitBillInfo(form, incomeAddConfig.formFilter, incomeAddConfig);
}

function updateInfo() {
    submitBillInfo(form, incomeAddConfig.formFilter, incomeAddConfig);
}