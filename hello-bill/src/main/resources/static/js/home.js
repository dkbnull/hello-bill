/**
 * home.html js
 *
 * @author dukunbiao(null)  2020-12-29
 * https://github.com/dkbnull/HelloBill
 */
let $, element;

layui.use(['layer', 'element'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;
    element = layui.element;
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
        },

        refreshTab: function (id) {
            const iframe = $('iframe[data-id="' + id + '"]');
            iframe.attr("src", iframe.attr("src"))
        },

        deleteTab: function (id) {
            element.tabDelete("navTab", id);
        },

        deleteAllTab: function (ids) {
            $.each(ids, function (i, item) {
                element.tabDelete("navTab", item);
            })
        }
    };

    $(".tab-popup-menu li").click(function () {
        const type = $(this).attr("data-type");
        const id = $(this).attr("data-id");
        if (type === "refreshThis") {
            active.refreshTab(id);
            return;
        }

        if (type === "closeThis") {
            active.deleteTab(id);
            return;
        }

        if (type === "closeOther") {
            const tabTitle = $(".layui-tab-title li");
            const ids = [];
            $.each(tabTitle, function (i) {
                const layId = $(this).attr("lay-id");
                if (layId !== id) {
                    ids[i] = layId;
                }
            })

            active.deleteAllTab(ids);
            return;
        }

        if (type === "closeAll") {
            const tabTitle = $(".layui-tab-title li");
            const ids = [];
            $.each(tabTitle, function (i) {
                ids[i] = $(this).attr("lay-id");
            })

            active.deleteAllTab(ids);
        }
    })

    $('.layui-layout-body').click(function () {
        $('.tab-popup-menu').hide();
    });

    element.on('tab(navTab)', function () {
        const id = $(this).attr("lay-id");
        customRightClick(id);
    });
}

function openTab(id, title, href) {
    let node = $('li[lay-id="' + id + '"]');
    if (node.length === 0) {
        element.tabAdd('navTab', {
            id: id,
            title: title,
            content: '<iframe data-id="' + id + '" src="' + href + '" id="frame" class="frame"></iframe>'
        });
    }

    customRightClick(id);
    element.tabChange('navTab', id);
}

function customRightClick(id) {
    $('.layui-tab-title li').on('contextmenu', function (e) {
        const popupMenu = $(".tab-popup-menu");
        popupMenu.find("li").attr("data-id", id);

        const l = ($(document).width() - e.clientX) < popupMenu.width() ?
            (e.clientX - popupMenu.width()) : e.clientX - 200;
        const t = ($(document).height() - e.clientY) < popupMenu.height() ?
            (e.clientY - popupMenu.height()) : e.clientY - 30;

        popupMenu.css({left: l, top: t}).show();
        return false;
    });
}

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}