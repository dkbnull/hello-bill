/**
 * home.html js
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
layui.use(['layer'], function () {
    const $ = layui.jquery;

    if (isEmpty(localStorage.getItem("username"))) {
        window.location.href = "index.html";
        return
    }

    $(".username").html(localStorage.getItem("username"));
});