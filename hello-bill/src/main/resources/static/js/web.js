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

isSuccess = function (code) {
    return code === "2000";
}