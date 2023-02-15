/**
 * home.html js
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
let $;

layui.use(['layer', 'element'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;
    $('.username').html(getItem('username'));

    initMethod();
});

function initMethod() {
    $('.layui-nav-child a').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        openTab: function () {
            const id = $(this).data('id');
            const title = $(this).data('title');
            const href = $(this).data('href');

            openTab(id, title, href)
        }
    };
}

function openTab(id, title, href) {
    const element = layui.element
    let node = $('li[lay-id="' + id + '"]');
    if (node.length === 0) {
        element.tabAdd('tab', {
            id: id,
            title: title,
            content: '<iframe src="' + href + '" id="frame" class="frame"></iframe>'
        })
    }

    element.tabChange('tab', id);
}

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}