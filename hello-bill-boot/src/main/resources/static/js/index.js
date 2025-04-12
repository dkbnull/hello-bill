/**
 * index.html js
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
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
    setItem("token", result.data.token);

    window.location.href = "home.html";
}