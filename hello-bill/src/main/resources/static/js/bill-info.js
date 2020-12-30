/**
 * bill-info.html js
 *
 * @author dukunbiao(null)  2020-12-31
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['layer', 'table'], function () {
    const url = parent.window.location.href;
    if (url.lastIndexOf("home.html") < 0) {
        window.location.href = "home.html";
        return;
    }

    doPostInfo();
});

function doPostInfo() {
    const request = {
        username: localStorage.getItem("username"),
        beginTime: "",
        endTime: "",
        topClass: "",
        secondClass: "",
        detail: "",
    };

    doPost("bill/info", request, callback)
}

function callback(result) {
    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    const table = layui.table;
    table.render({
        elem: '#info-table'
        , data: result.data
        , cellMinWidth: 100
        , cols: [[
            {field: 'billTime', title: '时间', sort: true}
            , {field: 'topClass', title: '顶级分类', sort: true}
            , {field: 'secondClass', title: '二级分类', sort: true}
            , {field: 'detail', title: '明细'}
            , {field: 'amount', title: '金额'}
            , {field: 'remark', title: '备注'}
        ]]
        , page: true,
        limit: 20
    });
}