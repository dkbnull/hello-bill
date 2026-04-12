/**
 * bill-common.js
 *
 * @author null
 * @date 2026-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
let $;

function initBillListPage(config) {
    layui.use(['layer', 'table', 'laydate'], function () {
        if (!validate()) {
            return;
        }

        $ = layui.jquery;

        initListDatetime(config.defaultBeginDate);
        initListMethod(config);
        doPostListQuery(config.defaultBeginDate, dateCalc(0), config);
    });
}

function initListDatetime(defaultBeginDate) {
    const laydate = layui.laydate;

    laydate.render({
        elem: '#begin-date-input',
        theme: 'grid',
        value: defaultBeginDate,
        max: 0,
        done: function (value) {
            doPostListQuery(value, $('#end-date-input').val(), currentListConfig);
        }
    });
    laydate.render({
        elem: '#end-date-input',
        theme: 'grid',
        value: dateCalc(0),
        max: 0,
        done: function (value) {
            doPostListQuery($('#begin-date-input').val(), value, currentListConfig);
        }
    });
}

function initListMethod(config) {
    const layer = layui.layer;

    $('.reload-info .layui-btn').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        reloadInfo: function () {
            doPostListQuery($('#begin-date-input').val(), $('#end-date-input').val(), currentListConfig);
        },

        addInfo: function () {
            layer.open({
                type: 2,
                skin: 'layui-layer-custom',
                title: '记账',
                content: config.addPageUrl,
                area: ['350px', config.addPageHeight],
                shade: 0,
                btn: ['确认', '取消'],
                yes: function (index, layero) {
                    const childWindow = layero.find('iframe')[0].contentWindow;
                    childWindow.addInfo();
                },
                btn2: function () {
                    layer.closeAll();
                }
            });
        }
    };
}

let currentListConfig = null;

function doPostListQuery(beginDate, endDate, config) {
    currentListConfig = config;

    const data = config.buildQueryData(beginDate, endDate);
    doPost(config.queryUrl, data, function (result) {
        renderListTable(result, config);
    });
}

function renderListTable(result, config) {
    const table = layui.table;
    table.render({
        elem: '#info-table',
        data: result.data,
        cellMinWidth: 100,
        totalRow: true,
        cols: [config.columns],
        page: true,
        limit: 10
    });

    table.on('tool(infoTable)', function (obj) {
        if (obj.event === 'edit') {
            layer.confirm(config.editConfirmMsg, {icon: 3}, function (index) {
                layer.open({
                    type: 2,
                    title: '修改',
                    content: config.addPageUrl + '?id=' + obj.data.id,
                    area: ['350px', '500px'],
                    maxmin: true,
                    shade: 0,
                    btn: ['确认', '取消'],
                    yes: function (index, layero) {
                        const childWindow = layero.find('iframe')[0].contentWindow;
                        childWindow.updateInfo();
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });

                layer.close(index);
            });
        } else if (obj.event === 'del') {
            layer.confirm(config.deleteConfirmMsg, {icon: 2}, function (index) {
                const data = {id: obj.data.id};
                doPost(config.deleteUrl, data, function () {
                    doPostListQuery($('#begin-date-input').val(), $('#end-date-input').val(), currentListConfig);
                });
                layer.close(index);
            });
        }
    });
}

function closeAllAndRefresh(message) {
    layer.closeAll();
    layer.msg(message);
    doPostListQuery($('#begin-date-input').val(), $('#end-date-input').val(), currentListConfig);
}

function initBillAddPage(config) {
    let $, form;

    layui.use(['layer', 'form', 'laydate'], function () {
        $ = layui.jquery;
        form = layui.form;

        config.initDatetime($, form);
        initAddClass(config.classType);
        initAddData(config);
        initAddMethod(config);
    });

    return {$: $, form: form};
}

function initAddClass(classType) {
    doPost("class/secondClassQuery", {type: classType}, function (result) {
        for (let i = 0; i < result.data.length; i++) {
            $("#second-class-select").append('<option>' + result.data[i] + '</option>');
        }
        layui.form.render();
    });
}

function initAddData(config) {
    const search = window.location.search;
    if (search.startsWith("?id")) {
        const data = {id: search.substring(4, search.length)};
        doPost(config.queryUrl, data, function (result) {
            config.fillForm(result.data);
            layui.form.render();
            config.onDataLoaded(result.data);
        });
    }
}

function initAddMethod(config) {
    if (config.bindKeyEvents) {
        config.bindKeyEvents();
    }
}

function submitBillInfo(form, formFilter, config) {
    const data = form.val(formFilter);
    if (!validateBillData(data, config)) {
        return;
    }

    const search = window.location.search;
    if (search.startsWith("?id")) {
        data.id = search.substring(4, search.length);
        doPost(config.updateUrl, data, handleBillCallback);
    } else {
        doPost(config.addUrl, data, handleBillCallback);
    }
}

function validateBillData(data, config) {
    const error = $(".error");
    const fields = config.requiredFields;

    for (let i = 0; i < fields.length; i++) {
        if (!data[fields[i].name] || data[fields[i].name].length === 0) {
            error.text(fields[i].message);
            return false;
        }
    }

    error.text("");
    return true;
}

function handleBillCallback(result) {
    const error = $(".error");
    if (!isSuccess(result.code)) {
        error.text(result.message);
        return;
    }
    parent.closeAllAndRefresh(result.message);
}
