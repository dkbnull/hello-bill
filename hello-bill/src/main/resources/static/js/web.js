/**
 * web js
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
doPost = function (url, request, callback) {
    const $ = layui.jquery;

    let loading = layer.load(1, {
        shade: false,
    });

    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: url,
        data: JSON.stringify(request),
        dataType: 'json',
        success: function (result) {
            callback(result);
            layer.close(loading);
        },
        error: function (XMLHttpRequest) {
            callback(XMLHttpRequest.responseJSON);
            layer.close(loading);
        }
    });
}

isEmpty = function (data) {
    return (data === null || data === undefined || data === '');
}

isSuccess = function (code) {
    return code === "2000";
}

function dateFormat() {
    return new Date().format("yyyy-MM-dd");
}

function dateCalc(day) {
    const time = day * 24 * 60 * 60 * 1000;
    return new Date(new Date().getTime() + time).format("yyyy-MM-dd");
}

Date.prototype.format = function (format) {
    const args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let i in args) {
        const n = args[i];
        if (new RegExp("(" + i + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? n : ("00" + n).substr(("" + n).length));
    }

    return format;
};