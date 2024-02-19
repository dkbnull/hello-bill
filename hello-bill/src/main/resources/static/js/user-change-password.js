/**
 * user-change-password.html js
 *
 * @author dukunbiao(null)  2024-02-19
 */
let $;

layui.use(['layer', 'form'], function () {
    $ = layui.jquery;

    initMethod();
    initInputCheck();
});

function initMethod() {
    $('.layui-form-item .layui-btn').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        confirm: function () {
            changePassword();
        },

        cancel: function () {
            closeContent();
        }
    };
}

function initInputCheck() {
    $("#old-password-input").on("input", function (e) {
        const error = $(".old-password-error");
        if (isEmpty(e.delegateTarget.value)) {
            error.text("原密码不能为空");
            return;
        }

        error.text("");
    });

    $("#new-password-input").on("input", function (e) {
        const error = $(".new-password-error");
        if (isEmpty(e.delegateTarget.value)) {
            error.text("新密码不能为空");
            return
        }

        const newPasswordConfirm = $("#new-password-confirm-input").val();
        if (!isEmpty(newPasswordConfirm) && e.delegateTarget.value !== newPasswordConfirm) {
            $(".new-password-confirm-error").text("两次输入密码不一致");
        } else {
            $(".new-password-confirm-error").text("");
        }

        if (e.delegateTarget.value.length < 6 || e.delegateTarget.value.length > 16) {
            error.text("密码长度为 6-16 位");
            return;
        }

        error.text("");
    });

    $("#new-password-confirm-input").on("input", function (e) {
        const error = $(".new-password-confirm-error");
        if (isEmpty(e.delegateTarget.value)) {
            error.text("确认密码不能为空");
            return;
        }

        if (e.delegateTarget.value !== $("#new-password-input").val()) {
            error.text("两次输入密码不一致");
            return;
        }

        error.text("");
    });
}

function changePassword() {
    const oldPasswordError = $(".old-password-error");
    const newPasswordError = $(".new-password-error");
    const newPasswordConfirmError = $(".new-password-confirm-error");

    const oldPassword = $('#old-password-input').val();
    const newPassword = $('#new-password-input').val();
    const newPasswordConfirm = $('#new-password-confirm-input').val();
    if (isEmpty(oldPassword)) {
        oldPasswordError.text("原密码不能为空");
    }
    if (isEmpty(newPassword)) {
        newPasswordError.text("新密码不能为空");
    }
    if (isEmpty(newPasswordConfirm)) {
        newPasswordConfirmError.text("确认密码不能为空");
    }

    if (!isEmpty(oldPasswordError.text()) || !isEmpty(newPasswordError.text()) || !isEmpty(newPasswordConfirmError.text())) {
        return;
    }

    const data = {
        username: getItem("username"),
        oldPassword: oldPassword,
        newPassword: newPassword
    };

    doPost("user/changePassword", data, callbackChangePassword);
}

function callbackChangePassword(result) {
    closeContent();
    parent.layer.msg(result.message);
}

function closeContent() {
    const content = parent.layer.getFrameIndex(window.name);
    parent.layer.close(content);
}
