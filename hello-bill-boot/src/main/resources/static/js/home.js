/**
 * home.html js
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
let $, element;

const routeMap = {
    report: {id: '10', title: '收支报表', href: 'report.html'},
    'report-expend-class': {id: '11', title: '支出分类报表', href: 'report-expend-class.html'},
    'report-income-class': {id: '12', title: '收入分类报表', href: 'report-income-class.html'},
    expend: {id: '20', title: '支出明细', href: 'expend-info.html'},
    income: {id: '21', title: '收入明细', href: 'income-info.html'},
    import: {id: '22', title: '导入数据', href: 'import.html'},
    balance: {id: '30', title: '资产信息', href: 'balance-sheet.html'},
    class: {id: '90', title: '分类信息', href: 'class-info.html'}
};

const idToHash = {};
Object.keys(routeMap).forEach(function (hash) {
    idToHash[routeMap[hash].id] = hash;
});

layui.use(['layer', 'element'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;
    element = layui.element;
    $('.username').html(getItem('username'));

    initMethod();

    const hash = window.location.hash.replace('#', '');
    if (hash && routeMap[hash]) {
        let route = routeMap[hash];
        openTab(route.id, route.title, route.href, false);
    } else {
        openTab('10', '收支报表', 'report.html', true);
    }

    $(window).on('hashchange', function () {
        let newHash = window.location.hash.replace('#', '');
        if (newHash && routeMap[newHash]) {
            let route = routeMap[newHash];
            let node = $('li[lay-id="' + route.id + '"]');
            if (node.length === 0) {
                openTab(route.id, route.title, route.href, false);
            } else {
                element.tabChange('navTab', route.id);
            }
        }
    });
});

function initMethod() {
    $('.layui-header .layui-nav-child dd').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    $('.layui-nav-child a').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        changePassword: function () {
            layer.open({
                type: 2,
                skin: 'layui-layer-custom',
                title: '修改密码',
                content: 'user-change-password.html',
                shade: 0,
                area: ['400px', '400px']
            });
        },

        logout: function () {
            localStorage.clear();
            window.location.href = 'index.html';
        },

        openTab: function () {
            const id = $(this).data('id');
            const title = $(this).data('title');
            const href = $(this).data('href');
            openTab(id, title, href, true);
        },

        refreshTab: function (id) {
            const iframe = $('iframe[data-id="' + id + '"]');
            iframe.attr('src', iframe.attr('src'));
        },

        deleteTab: function (id) {
            element.tabDelete('navTab', id);
            updateHashAfterClose(id);
        },

        deleteAllTab: function (ids) {
            $.each(ids, function (i, item) {
                if (item) {
                    element.tabDelete('navTab', item);
                }
            });
            const defaultHash = idToHash['10'];
            if (defaultHash) {
                window.location.hash = defaultHash;
            }
        }
    };

    $('.tab-popup-menu li').click(function () {
        const type = $(this).attr('data-type');
        const id = $(this).attr('data-id');
        if (type === 'refreshThis') {
            active.refreshTab(id);
            return;
        }
        if (type === 'closeThis') {
            active.deleteTab(id);
            return;
        }
        if (type === 'closeOther') {
            const tabTitle = $('.layui-tab-title li');
            const ids = [];
            $.each(tabTitle, function (i) {
                const layId = $(this).attr('lay-id');
                if (layId !== id) {
                    ids.push(layId);
                }
            });
            active.deleteAllTab(ids);
            return;
        }
        if (type === 'closeAll') {
            const tabTitle = $('.layui-tab-title li');
            const ids = [];
            $.each(tabTitle, function (i) {
                const layId = $(this).attr('lay-id');
                if (layId) {
                    ids.push(layId);
                }
            });
            active.deleteAllTab(ids);
        }
    });

    $('.layui-layout-body').click(function () {
        $('.tab-popup-menu').hide();
    });

    element.on('tab(navTab)', function () {
        const id = $(this).attr('lay-id');
        customRightClick(id);
        if (id && idToHash[id]) {
            window.location.hash = idToHash[id];
        }
    });
}

function openTab(id, title, href, updateHash) {
    let node = $('li[lay-id="' + id + '"]');
    if (node.length === 0) {
        element.tabAdd('navTab', {
            id: id,
            title: title,
            content: '<iframe data-id="' + id + '" src="' + href + '" class="frame"></iframe>'
        });
    }

    customRightClick(id);
    element.tabChange('navTab', id);

    if (updateHash && idToHash[id]) {
        window.location.hash = idToHash[id];
    }
}

function updateHashAfterClose(closedId) {
    const remainingTabs = $('.layui-tab-title li');
    if (remainingTabs.length > 0) {
        const activeTab = remainingTabs.filter('.layui-this');
        const activeId = activeTab.attr('lay-id');
        if (activeId && idToHash[activeId]) {
            window.location.hash = idToHash[activeId];
        }
    } else {
        history.replaceState(null, '', window.location.pathname);
    }
}

function customRightClick(id) {
    $('.layui-tab-title li').on('contextmenu', function (e) {
        const popupMenu = $('.tab-popup-menu');
        popupMenu.find('li').attr('data-id', id);

        const l = ($(document).width() - e.clientX) < popupMenu.width() ?
            (e.clientX - popupMenu.width()) : e.clientX - 200;
        const t = ($(document).height() - e.clientY) < popupMenu.height() ?
            (e.clientY - popupMenu.height()) : e.clientY - 30;

        popupMenu.css({left: l, top: t}).show();
        return false;
    });
}
