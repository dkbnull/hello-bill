/**
 * bill-common.js - 账单列表与新增页面公共逻辑模块
 *
 * @author null
 * @date 2026-04-12
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
function initBillListPage(config) {
    layui.use(['layer', 'table', 'laydate', 'form'], function () {
        if (!validate()) {
            return;
        }

        $ = layui.jquery;

        initListDatetime(config.defaultBeginDate);
        initListMethod(config);
        initClassSelect(config.classType);
        doPostListQuery(config.defaultBeginDate, dateCalc(0), config, 1, 10);
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
            doPostListQuery(value, $('#end-date-input').val(), currentListConfig, 1, currentPageSize);
        }
    });
    laydate.render({
        elem: '#end-date-input',
        theme: 'grid',
        value: dateCalc(0),
        max: 0,
        done: function (value) {
            doPostListQuery($('#begin-date-input').val(), value, currentListConfig, 1, currentPageSize);
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
            doPostListQuery($('#begin-date-input').val(), $('#end-date-input').val(), currentListConfig, 1, currentPageSize);
        },

        addInfo: function () {
            layer.open({
                type: 2,
                skin: 'layui-layer-custom',
                title: '记账',
                content: config.addPageUrl,
                area: ['400px', config.addPageHeight],
                shade: 0,
                btn: ['确认', '取消'],
                yes: function (index, layero) {
                    const childWindow = layero.find('iframe')[0].contentWindow;
                    childWindow.addInfo();
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }
    };
}

function initClassSelect(classType, options) {
    const form = layui.form;
    const opts = options || {};

    doPost('class/queryClass', {type: classType}, function (result) {
        const topClassSelect = $('#top-class-select');
        topClassSelect.empty();
        topClassSelect.append('<option></option>');
        for (let i = 0; i < result.data.length; i++) {
            topClassSelect.append('<option>' + result.data[i] + '</option>');
        }
        if (opts.topClass) {
            topClassSelect.val(opts.topClass);
        }
        form.render();

        if (opts.topClass) {
            doPost('class/queryClass', {type: classType, topClass: opts.topClass}, function (result2) {
                const secondClassSelect = $('#second-class-select');
                secondClassSelect.empty();
                secondClassSelect.append('<option></option>');
                for (let i = 0; i < result2.data.length; i++) {
                    secondClassSelect.append('<option>' + result2.data[i] + '</option>');
                }
                if (opts.secondClass) {
                    secondClassSelect.val(opts.secondClass);
                }
                form.render();
            });
        } else {
            const secondClassSelect = $('#second-class-select');
            secondClassSelect.empty();
            secondClassSelect.append('<option></option>');
            form.render();
        }
    });

    form.on('select(topClass)', function (data) {
        const secondClassSelect = $('#second-class-select');
        secondClassSelect.empty();
        secondClassSelect.append('<option></option>');

        if (data.value) {
            doPost('class/queryClass', {type: classType, topClass: data.value}, function (result) {
                for (let i = 0; i < result.data.length; i++) {
                    secondClassSelect.append('<option>' + result.data[i] + '</option>');
                }
                form.render();
            });
        } else {
            form.render();
        }
    });
}

let currentListConfig = null;
let currentPageNum = 1;
let currentPageSize = 10;

function doPostListQuery(beginDate, endDate, config, pageNum, pageSize) {
    currentListConfig = config;
    currentPageNum = pageNum;
    currentPageSize = pageSize;

    const data = config.buildQueryData(beginDate, endDate);
    data.pageNum = pageNum;
    data.pageSize = pageSize;
    doPost(config.queryUrl, data, function (result) {
        renderListTable(result, config);
    });
}

function renderListTable(result, config) {
    const table = layui.table;
    const pageData = result.data;

    table.render({
        elem: '#info-table',
        data: pageData.records,
        limit: pageData.size,
        cellMinWidth: 100,
        cols: [config.columns]
    });

    renderPage(pageData, function (curr, limit) {
        doPostListQuery($('#begin-date-input').val(), $('#end-date-input').val(), currentListConfig, curr, limit);
    });

    table.on('tool(infoTable)', function (obj) {
        if (obj.event === 'edit') {
            layer.confirm(config.editConfirmMsg, {skin: 'layui-layer-confirm', icon: 3}, function (index) {
                layer.open({
                    type: 2,
                    skin: 'layui-layer-custom',
                    title: '修改',
                    content: config.addPageUrl + '?id=' + obj.data.id,
                    area: ['400px', '500px'],
                    maxmin: true,
                    shade: 0,
                    btn: ['确认', '取消'],
                    yes: function (index, layero) {
                        const childWindow = layero.find('iframe')[0].contentWindow;
                        childWindow.updateInfo();
                    },
                    btn2: function (index) {
                        layer.close(index);
                    }
                });

                layer.close(index);
            });
        } else if (obj.event === 'del') {
            layer.confirm(config.deleteConfirmMsg, {skin: 'layui-layer-confirm', icon: 2}, function (index) {
                const data = {id: obj.data.id};
                doPost(config.deleteUrl, data, function () {
                    doPostListQuery($('#begin-date-input').val(), $('#end-date-input').val(), currentListConfig, currentPageNum, currentPageSize);
                });
                layer.close(index);
            });
        }
    });
}

function closeAllAndRefresh(message) {
    layer.closeAll();
    layer.msg(message);
    doPostListQuery($('#begin-date-input').val(), $('#end-date-input').val(), currentListConfig, currentPageNum, currentPageSize);
}

function initBillAddPage(config) {
    let $, form;

    layui.use(['layer', 'form', 'laydate'], function () {
        $ = layui.jquery;
        form = layui.form;

        config.initDatetime($, form);
        initClassSelect(config.classType);
        initAddData(config);
        initAddMethod(config);
    });

    return {$: $, form: form};
}

function initAddData(config) {
    const search = window.location.search;
    if (search.startsWith('?id')) {
        const data = {id: search.substring(4, search.length)};
        doPost(config.queryUrl, data, function (result) {
            config.fillForm(result.data);
            initClassSelect(config.classType, {
                topClass: result.data.topClass,
                secondClass: result.data.secondClass
            });
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
    if (search.startsWith('?id')) {
        data.id = search.substring(4, search.length);
        doPost(config.updateUrl, data, handleBillCallback);
    } else {
        doPost(config.addUrl, data, handleBillCallback);
    }
}

function validateBillData(data, config) {
    const error = $('.error');
    const fields = config.requiredFields;

    for (let i = 0; i < fields.length; i++) {
        if (!data[fields[i].name] || data[fields[i].name].length === 0) {
            error.text(fields[i].message);
            return false;
        }
    }

    error.text('');
    return true;
}

function handleBillCallback(result) {
    const error = $('.error');
    if (!isSuccess(result.code)) {
        error.text(result.msg);
        return;
    }
    parent.closeAllAndRefresh(result.msg);
}
