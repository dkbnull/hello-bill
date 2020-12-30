/**
 * index.html js
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['layer', 'form'], function () {
    const form = layui.form;

    form.verify({
        username: function (value) {
            if (value.length === 0) {
                return '用户名不能为空';
            }
        },
        password: function (value) {
            if (value.length === 0) {
                return '密码不能为空';
            }
        }
    });

    form.on('submit(login)', function (data) {
        doPost("user/login", data.field, callback)
        return false;
    });
});

function callback(result) {
    if (isEmpty(result)) {
        layer.alert("返回参数为空");
        return;
    }

    if (!isSuccess(result.code)) {
        layer.alert(result.message);
        return;
    }

    localStorage.setItem("username", result.data.username);

    window.location.href = "home.html";
}