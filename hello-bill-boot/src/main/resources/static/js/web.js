/**
 * web js - 网络请求与回调处理模块
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
const RESPONSE_CODE = {
    SUCCESS: '1000',
    TOKEN_EXPIRED_PREFIX: '30'
};

const doPost = function (url, data, callback) {
    const $ = layui.jquery;

    const loading = layer.load(1, {shade: false});

    const request = getRequest(data);

    $.ajax({
        type: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: url,
        data: JSON.stringify(request),
        headers: {'Authorization': 'Bearer ' + getItem('token')},
        dataType: 'json',
        success: function (result) {
            callbackSuccess(loading, result, callback);
        },
        error: function (XMLHttpRequest) {
            callbackFail(loading, XMLHttpRequest, callback);
        }
    });
};

const doUpload = function (elem, exts, url, data, callback) {
    const upload = layui.upload;
    const element = layui.element;

    const loading = layer.load(1, {shade: false});

    const request = getRequest(data);

    upload.render({
        elem: elem,
        url: url,
        accept: 'file',
        exts: exts,
        data: request,
        headers: {'Authorization': 'Bearer ' + getItem('token')},
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
};

function getRequest(data) {
    if (data === null || data === undefined) {
        data = {};
    }

    const timestamp = Math.round(new Date().getTime());
    const nonce = Math.round(Math.random() * 10000000000);

    return {
        username: isEmpty(getItem('username')) ? data.username : getItem('username'),
        timestamp: timestamp,
        nonce: nonce,
        sign: 'sign',
        data: data
    };
}

function callbackSuccess(loading, result, callback) {
    if (isEmpty(result)) {
        layer.msg('返回参数为空');
        layer.close(loading);
        return;
    }

    if (isTokenExpired(result.code)) {
        layer.confirm(result.msg + '，是否重新登录？', {skin: 'layui-layer-confirm'}, function (index) {
            localStorage.clear();
            parent.window.location.href = 'index.html';
            layer.close(index);
        });
        layer.close(loading);
        return;
    }

    if (!isSuccess(result.code)) {
        layer.msg(result.msg);
        layer.close(loading);
        return;
    }

    callback(result);
    layer.close(loading);
}

function callbackFail(loading, XMLHttpRequest, callback) {
    if (isEmpty(XMLHttpRequest.responseJSON)) {
        layer.msg('未知异常');
        layer.close(loading);
        return;
    }

    layer.msg(XMLHttpRequest.responseJSON.message || '请求异常');
    callback(XMLHttpRequest.responseJSON);
    layer.close(loading);
}

const isSuccess = function (code) {
    return code === RESPONSE_CODE.SUCCESS;
};

const isTokenExpired = function (code) {
    return typeof code === 'string' && code.length === 4 && code.startsWith(RESPONSE_CODE.TOKEN_EXPIRED_PREFIX);
};
