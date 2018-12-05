function getUrlValue(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var result = location.search.substring(1).match(reg);
    return result == null ? null : decodeURIComponent(result[2]);
}

function getCookieValue(name) {
    var reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    var result = document.cookie.match(reg);
    return result == null ? null : result[2];
}

function setCookie(name, value, maxAge, path, domain, secure) {
    document.cookie = name + "=" + value + ((maxAge) ? "; max-age=" + maxAge : "") + ((path) ? "; path=" + path : "") + ((domain) ? ";domain=" + domain : "") + ((secure) ? ";secure=" + secure : "");
}