/**
 * home.html js
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['layer', 'element'], function () {
    if (!validate()) {
        return;
    }

    const $ = layui.jquery;
    $(".username").html(localStorage.getItem("username"));
});

function logout() {
    localStorage.clear();
    window.location.href = "index.html";
}