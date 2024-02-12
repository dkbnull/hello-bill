/**
 * index.html js
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['layer', 'form'], function () {
    if (!isEmpty(getItem("username"))) {
        window.location.href = "home.html";
        return;
    }

    const $ = layui.jquery;
    const form = layui.form;
    form.on('submit(login)', function (obj) {
        if (document.activeElement.id === 'username') {
            $('#password').focus();
            return false;
        }

        const data = obj.field
        if (isEmpty(data.username)) {
            layer.msg('用户名不能为空');
            $('#username').focus();
            return false;
        }
        if (isEmpty(data.password)) {
            layer.msg('密码不能为空');
            $('#password').focus();
            return false;
        }

        doPost("user/login", data, callback)
        return false;
    });
});

function callback(result) {
    setItem("username", result.data.username);

    window.location.href = "home.html";
}