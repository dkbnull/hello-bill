/**
 * web js
 *
 * @author null
 * @date 2020-12-29
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
isEmpty = function (data) {
    return (data === null || data === undefined || data === '');
}

isNumber = function (value) {
    return parseFloat(value).toString() !== "NaN";
}

function validate() {
    if (isEmpty(getItem("username"))) {
        parent.window.location.href = "index.html";
        return false;
    }

    const url = parent.window.location.href
    if (url.lastIndexOf("home.html") < 0) {
        parent.window.location.href = "home.html";
        return false;
    }

    return true;
}

function dateFormat() {
    return new Date().format("yyyy-MM-dd");
}

function datetimeCalc(day) {
    const time = day * 24 * 60 * 60 * 1000;
    return new Date(new Date().getTime() + time).format("yyyy-MM-dd hh:mm:ss");
}

function dateCalc(day) {
    const time = day * 24 * 60 * 60 * 1000;
    return new Date(new Date().getTime() + time).format("yyyy-MM-dd");
}

function dateBeginTheMonth() {
    return new Date().format("yyyy-MM-01");
}

function dateFormatYear() {
    return new Date().format("yyyy");
}

function dateFormatMonth() {
    return new Date().format("yyyy-MM");
}

function dateBeginTheYear() {
    return new Date().format("yyyy-01-01");
}

function formatDateYyyy(value) {
    const pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
    return value.replace(pattern, '$1-$2-$3 $4:$5:$6');
}

Date.prototype.format = function (format) {
    const args = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let i in args) {
        const n = args[i];
        if (new RegExp("(" + i + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? n : ("00" + n).substr(("" + n).length));
    }

    return format;
};

function setItem(key, value) {
    localStorage.setItem(key, value);
}

function getItem(key) {
    return localStorage.getItem(key);
}