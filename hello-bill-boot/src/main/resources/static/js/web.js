/**
 * web js
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/HelloBill">GitHub</a>
 */
doPost = function (url, data, callback) {
    const $ = layui.jquery;

    let loading = layer.load(1, {
        shade: false,
    });

    const request = getRequest(data);

    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: url,
        data: JSON.stringify(request),
        headers: {"Authorization": 'Bearer ' + getItem("token")},
        dataType: "json",
        success: function (result) {
            callbackSuccess(loading, result, callback);
        },
        error: function (XMLHttpRequest) {
            callbackFail(loading, XMLHttpRequest, callback);
        }
    });
}

doUpload = function (elem, exts, url, data, callback) {
    const upload = layui.upload;
    const element = layui.element;

    let loading = layer.load(1, {
        shade: false,
    });

    const request = getRequest(data);

    upload.render({
        elem: elem,
        url: url,
        accept: 'file',
        exts: exts,
        data: request,
        headers: {"token": getItem("token")},
        progress: function (n, elem, res, index) {
            element.progress('uploadProgress', n + '%');
        },
        done: function (res, index, upload) {
            callbackSuccess(loading, res, callback);
        },
        error: function (index, upload) {
            callback();
            layer.close(loading);
        }
    });
}

function getRequest(data) {
    if (data == null) {
        data = {};
    }

    const timestamp = Math.round(new Date().getTime());
    const nonce = Math.round(Math.random() * 10000000000);

    return {
        username: isEmpty(getItem("username")) ? data.username : getItem("username"),
        timestamp: timestamp,
        nonce: nonce,
        sign: "sign",
        data: data
    };
}

function callbackSuccess(loading, result, callback) {
    if (isEmpty(result)) {
        layer.alert("返回参数为空");
        layer.close(loading);
        return;
    }

    if (isTokenExpired(result.code)) {
        layer.confirm(result.message + '，是否重新登录？', function (index) {
            localStorage.clear();
            parent.window.location.href = 'index.html';
            layer.close(index);
        });

        layer.close(loading);

        return;
    }

    if (!isSuccess(result.code)) {
        layer.msg(result.message);
        layer.close(loading);
        return;
    }

    callback(result);
    layer.close(loading);
}

function callbackFail(loading, XMLHttpRequest, callback) {
    if (isEmpty(XMLHttpRequest.responseJSON)) {
        layer.alert("未知异常");
        layer.close(loading);
        return;
    }

    layer.alert(XMLHttpRequest.responseJSON);
    callback(XMLHttpRequest.responseJSON);
    layer.close(loading);
}

isSuccess = function (code) {
    return code === "1000";
}

isTokenExpired = function (code) {
    return code.length === 4 && code.startsWith("30");
}