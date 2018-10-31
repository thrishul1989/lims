var Namespace = new Object();
Namespace.register = function (path) {
    var arr = path.split(".");
    var ns = "";
    for (var i = 0; i < arr.length; i++) {
        if (i > 0) {
            ns += ".";
        }
        ns += arr[i];
        eval("if(typeof(" + ns + ") == 'undefined') " + ns + " = new Object();");
    }
};
jQuery.extend({
	checkAll: function (a, b) {
    $("."+a).prop("checked", b);
}, getChkValue: function (a) {
    var b = "";
    $('input[type="checkbox"][name=' + a + "]").each(function () {
        if ($(this).attr("checked")) {
            b += $(this).val() + ",";
        }
    });
    if (b != "") {
        b = b.substring(0, b.length - 1);
    }
    return b;
}, setValues:function(array,type){
    var attrs = "name";
    if(type != undefined && type !=""){
        attrs = type;
    }
    $.each(array,function (index,value) {
        if (attrs == "name"){
            $("[name='"+value+"']").val($("#"+value).val());
        }else if(attrs == "id"){
            $("#"+id).val($("#"+value).val());
        }else if(attrs == "class"){
            $("."+value).val($("#"+value).val());
        }
    })
},getSelectValue: function (a) {
    var b = "";
    $("select[name=" + a + "] option").each(function () {
        b += $(this).val() + ",";
    });
    if (b != "") {
        b = b.substring(0, b.length - 1);
    }
    return b;
}, copyToClipboard: function (a) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", a);
        return true;
    } else {
        if (navigator.userAgent.indexOf("Opera") != -1) {
            window.location = a;
            return false;
        } else {
            if (window.netscape) {
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                } catch (f) {
                    alert($lang.tip.msg, $lang_js.util.copyToClipboard.netscape);
                    return false;
                }
                var c = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard);
                if (!c) {
                    return false;
                }
                var b = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
                if (!b) {
                    return false;
                }
                b.addDataFlavor("text/unicode");
                var g = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
                var h = a;
                g.data = h;
                b.setTransferData("text/unicode", g, h.length * 2);
                var d = Components.interfaces.nsIClipboard;
                if (!c) {
                    return false;
                }
                c.setData(b, null, d.kGlobalClipboard);
                return true;
            } else {
                alert($lang.tip.msg, $lang_js.util.copyToClipboard.notCopy);
                return false;
            }
        }
    }
}, copy: function (a) {
    var c = $("#" + a).val();
    var b = jQuery.copyToClipboard(c);
    if (b) {
        alert($lang_js.util.copy.success);
    }
}, isIE: function () {
    var b = navigator.appName;
    var a = b.indexOf("Microsoft");
    return a == 0;
}, isIE6: function () {
    if (($.browser.msie && $.browser.version == "6.0") && !$.support.style) {
        return true;
    }
    return false;
}, getChildXml: function (c, j) {
    var b = c.childNodes;
    var f = b.length;
    for (var e = 0; e < f; e++) {
        var a = b[e];
        if (a.nodeType != 1) {
            continue;
        }
        var h = a.nodeName;
        j.append("<" + h + " ");
        var l = a.attributes;
        for (var d = 0; d < l.length; d++) {
            var g = l[d];
            j.append(" " + g.name + '="' + g.value + '" ');
        }
        j.append(">");
        $.getChildXml(a, j);
        j.append("</" + h + ">");
    }
}, getChildXmlByNode: function (a) {
    var b = new StringBuffer();
    jQuery.getChildXml(a, b);
    return b.toString();
}, getAttrXml: function (d, c) {
    var b = d.childNodes;
    var h = b.length;
    for (var g = 0; g < h; g++) {
        var a = b[g];
        if (a.nodeType != 1) {
            continue;
        }
        var l = a.attributes;
        var f = new Object();
        for (var e = 0; e < l.length; e++) {
            var j = l[e];
            f[j.name] = j.value;
        }
        c.push(f);
        $.getAttrXml(a, c);
    }
}, fixPNG: function (f) {
    var h = navigator.appVersion.split("MSIE");
    var c = parseFloat(h[1]);
    if ((c >= 5.5) && (c < 7) && (document.body.filters)) {
        var b = (f.id) ? "id='" + f.id + "' " : "";
        var e = (f.className) ? "class='" + f.className + "' " : "";
        var g = (f.title) ? "title='" + f.title + "' " : "title='" + f.alt + "' ";
        var d = "display:inline-block;" + f.style.cssText;
        var a = "<span " + b + e + g + ' style="' + "width:" + f.width + "px; height:" + f.height + "px;" + d + ";" + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" + "(src='" + f.src + "', sizingMethod='scale');\"></span>";
        f.outerHTML = a;
    }
}, getParameter: function (b) {
    var d = unescape(window.location.search.substr(1)).split("&");
    for (var a = 0; a < d.length; a++) {
        var c = d[a].split("=");
        if (c.length == 2 && c[0].toUpperCase() == b.toUpperCase()) {
            return c[1];
        }
    }
    return new String();
}, getMonthDays: function (a, c) {
    if (c < 0 || c > 11) {
        return 30;
    }
    var b = new Array(12);
    b[0] = 31;
    if (a % 4 == 0) {
        b[1] = 29;
    } else {
        b[1] = 28;
    }
    b[2] = 31;
    b[3] = 30;
    b[4] = 31;
    b[5] = 30;
    b[6] = 31;
    b[7] = 31;
    b[8] = 30;
    b[9] = 31;
    b[10] = 30;
    b[11] = 31;
    return b[c];
}, weekOfYear: function (e, h, b) {
    var g = new Date(e, 0, 1);
    var f = new Date(e, h - 1, b, 1);
    var d = 24 * 60 * 60 * 1000;
    var a = (7 - g.getDay()) * d;
    var c = 7 * d;
    g = g.getTime();
    f = f.getTime();
    return Math.ceil((f - g - a) / c) + 1;
}, addBookmark: function (b, a) {
    if (window.sidebar) {
        window.sidebar.addPanel(b, a, "");
    } else {
        if (document.all) {
            window.external.AddFavorite(a, b);
        } else {
            if (window.opera && window.print) {
                return true;
            }
        }
    }
}, setCookie: function (b, h) {
    var c = new Date();
    var g = arguments;
    var e = arguments.length;
    var d = (e > 2) ? g[2] : null;
    var i = (e > 3) ? g[3] : null;
    var f = (e > 4) ? g[4] : null;
    var a = (e > 5) ? g[5] : false;
    if (d != null) {
        c.setTime(c.getTime() + (d * 1000));
    }
    document.cookie = b + "=" + escape(h) + ((d == null) ? "" : (";  expires=" + c.toGMTString())) + ((i == null) ? "" : (";  path=" + i)) + ((f == null) ? "" : (";  domain=" + f)) + ((a == true) ? ";  secure" : "");
}, delCookie: function (a) {
    var c = new Date();
    c.setTime(c.getTime() - 1);
    var b = GetCookie(a);
    document.cookie = a + "=" + b + ";  expires=" + c.toGMTString();
}, getCookie: function (d) {
    var b = d + "=";
    var f = b.length;
    var a = document.cookie.length;
    var e = 0;
    while (e < a) {
        var c = e + f;
        if (document.cookie.substring(e, c) == b) {
            return $.getCookieVal(c);
        }
        e = document.cookie.indexOf("  ", e) + 1;
        if (e == 0) {
            break;
        }
    }
    return null;
}, getCookieVal: function (b) {
    var a = document.cookie.indexOf(";", b);
    if (a == -1) {
        a = document.cookie.length;
    }
    return unescape(document.cookie.substring(b, a));
}, setFormByJson: function (d) {
    var b = d;
    if (typeof(d) == "string") {
        b = jQuery.parseJSON(d);
    }
    for (var e in b) {
        var c = b[e];
        var a = $("input[name='" + e + "'],textarea[name='" + e + "']");
        if (a[0]) {
            a.val(c);
        }
    }
}, highlightTableRows: function () {
    $("tr.odd,tr.even").hover(function () {
        $(this).addClass("over");
    }, function () {
        $(this).removeClass("over");
    });
}, selectTr: function () {
    $("tr.odd,tr.even").each(function () {
        $(this).bind("mousedown", function (b) {
            if (b.target.tagName == "TD") {
                var a = 'input:checkbox[class="pk"],input:radio[class="pk"]';
            }
            var d = $(this).find(a);
            if (d.length == 1) {
                var c = d.attr("checked");
                d.attr("checked", !c);
            }
        });
    });
}, insert: function (a, e, c) {
    if (isNaN(c) || c < 0 || c > a.length) {
        a.push(e);
    } else {
        var b = a.slice(c);
        a[c] = e;
        for (var d = 0; d < b.length; d++) {
            a[c + 1 + d] = b[d];
        }
    }
}, getFirstLower: function (a) {
    var e = "";
    if (a.indexOf("_") != -1) {
        var d = a.split("_");
        for (var c = 0; c < d.length; c++) {
            var b = d[c];
            if (c == 0) {
                e += b.toLowerCase();
            } else {
                e += b.substring(0, 1).toUpperCase() + b.substring(1, b.length + 1).toLowerCase();
            }
        }
    } else {
        e = a.toLowerCase();
    }
    return e;
}, getFirstUpper: function (a) {
    var e = "";
    if (a.indexOf("_") != -1) {
        var d = a.split("_");
        for (var c = 0; c < d.length; c++) {
            var b = d[c];
            e += b.substring(0, 1).toUpperCase() + b.substring(1, b.length + 1).toLowerCase();
        }
    } else {
        e = a.substring(0, 1).toUpperCase() + a.substring(1, a.length + 1).toLowerCase();
    }
    return e;
}, openFullWindow: function (b) {
    var c = screen.availHeight - 35;
    var a = screen.availWidth - 5;
    var e = "top=0,left=0,height=" + c + ",width=" + a + ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
    var d = window.open(b, "", e, true);
    return d;
}, isEmpty: function (b, a) {
    return b === null || b === undefined || (!a ? b === "" : false);
}, convertCurrency: function (x) {
    var c = 99999999999.99;
    var B = "零";
    var F = "壹";
    var j = "贰";
    var k = "叁";
    var m = "肆";
    var H = "伍";
    var E = "陆";
    var A = "柒";
    var J = "捌";
    var C = "玖";
    var g = "拾";
    var q = "佰";
    var t = "仟";
    var f = "万";
    var h = "亿";
    var z = "";
    var w = "元";
    var e = "角";
    var u = "分";
    var y = "整";
    var b;
    var M;
    var v;
    var I;
    var K, o, s, r;
    var a;
    var G, D, L;
    var N, l;
    x = x.toString();
    if (x == "") {
        return"";
    }
    if (x.match(/[^,.\d]/) != null) {
        return"";
    }
    if ((x).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
        return"";
    }
    x = x.replace(/,/g, "");
    x = x.replace(/^0+/, "");
    if (Number(x) > c) {
        return"";
    }
    I = x.split(".");
    if (I.length > 1) {
        b = I[0];
        M = I[1];
        M = M.substr(0, 2);
    } else {
        b = I[0];
        M = "";
    }
    K = new Array(B, F, j, k, m, H, E, A, J, C);
    o = new Array("", g, q, t);
    s = new Array("", f, h);
    r = new Array(e, u);
    v = "";
    if (Number(b) > 0) {
        a = 0;
        for (G = 0; G < b.length; G++) {
            D = b.length - G - 1;
            L = b.substr(G, 1);
            N = D / 4;
            l = D % 4;
            if (L == "0") {
                a++;
            } else {
                if (a > 0) {
                    v += K[0];
                }
                a = 0;
                v += K[Number(L)] + o[l];
            }
            if (l == 0 && a < 4) {
                v += s[N];
            }
        }
        v += w;
    }
    if (M != "") {
        for (G = 0; G < M.length; G++) {
            L = M.substr(G, 1);
            if (L != "0") {
                v += K[Number(L)] + r[G];
            }
        }
    }
    if (v == "") {
        v = B + w;
    }
    if (M == "") {
        v += y;
    }
    v = z + v;
    return v;
}, tagName: function (b, a) {
    var d = b.attributes, f = document.createElement(a);
    for (var e = 0, g; g = d[e++];) {
        if (!g.value || g.value == "null") {
            continue;
        }
        $(f).attr(g.name, g.value);
    }
    $(b).before($(f));
    $(b).remove();
    return $(f);
}, insertText: function (g, a) {
    if (document.selection) {
        var e = document.selection.createRange().text;
        if (!e) {
            e = a;
        }
        g.focus();
        if (e.charAt(e.length - 1) == " ") {
            e = e.substring(0, e.length - 1);
            document.selection.createRange().text = e + " ";
        } else {
            document.selection.createRange().text = e;
        }
    } else {
        if (g.selectionStart || g.selectionStart == "0") {
            var c = g.selectionStart;
            var b = g.selectionEnd;
            var f = (g.value).substring(c, b);
            if (!f) {
                f = a;
            }
            if (f.charAt(f.length - 1) == " ") {
                subst = f.substring(0, (f.length - 1)) + " ";
            } else {
                subst = f;
            }
            g.value = g.value.substring(0, c) + subst + g.value.substring(b, g.value.length);
            g.focus();
            var d = c + (f.length);
            g.selectionStart = d;
            g.selectionEnd = d;
        } else {
            g.value += a;
            g.focus();
        }
    }
    if (g.createTextRange) {
        g.caretPos = document.selection.createRange().duplicate();
    }
}, confirm: function (a, b, c) {
    $(a).click(function () {
        if ($(this).hasClass("disabled")) {
            return false;
        }
        var d = this;
        $.ligerDialog.confirm(b, $lang.tip.msg, function (e) {
            if (e) {
                if ($.browser.msie) {
                    $.gotoDialogPage(d.href);
                } else {
                    location.href = d.href;
                }
            }
        });
        return false;
    });
}, gotoDialogPage: function (c) {
    if ($.browser.msie) {
        var b = document.createElement("a");
        b.href = c;
        document.body.appendChild(b);
        b.click();
    } else {
        location.href = c;
    }
}, cloneObject: function (b) {
    var c = b.constructor === Array ? [] : {};
    for (var a in b) {
        if (b.hasOwnProperty(a)) {
            c[a] = typeof b[a] === "object" ? cloneObject(b[a]) : b[a];
        }
    }
    return c;
}, clearQueryForm: function () {
    $("input[name^='Q_'],select[name^='Q_']").each(function () {
        $(this).val("");
    });
}, getFileExtName: function (b) {
    var a = b.lastIndexOf(".");
    if (a == -1) {
        return"";
    }
    return b.substring(a + 1);
}, comdify: function (a) {
    if (a && a != "") {
        n = a + "";
        var c = /\d{1,3}(?=(\d{3})+$)/g;
        var b = n.trim().replace(/^(\d+)((\.\d+)?)$/, function (f, e, d) {
            return e.replace(c, "$&,") + d;
        });
        return b;
    }
    return a;
}, toNumber: function (a) {
    if (a && a != "") {
        if (a.indexOf(",") == -1) {
            return a;
        }
        var b = a.split(",");
        var c = b.join("");
        return c;
    }
    return 0;
}, moveTr: function (e, d) {
    var c = $(e).parents("tr");
    if (d) {
        var b = $(c).prev();
        if (b) {
            c.insertBefore(b);
        }
    } else {
        var a = $(c).next();
        if (a) {
            c.insertAfter(a);
        }
    }
},getDate: function(str) { 
	str = str.split('-'); 
	var date = new Date(); 
	date.setUTCFullYear(str[0], str[1] - 1, str[2]); 
	date.setUTCHours(0, 0, 0, 0); 
	return date; 
}});
String.prototype.getNewUrl = function () {
    var c = new Date().getTime();
    var b = this;
    if (b.indexOf("#") != -1) {
        var a = b.lastIndexOf("#", b.length - 1);
        b = b.substring(0, a);
    }
    while (b.endWith("#")) {
        b = b.substring(0, b.length - 1);
    }
    b = b.replace(/(\?|&)rand=\d*/g, "");
    if (b.indexOf("?") == -1) {
        b += "?rand=" + c;
    } else {
        b += "&rand=" + c;
    }
    return b;
};
String.prototype.getSessionUrl = function () {
    var a = this;
    if (a.indexOf(";jsessionid=") != -1) {
        return a;
    }
    if (a.indexOf("?") == -1) {
        a += ";jsessionid=" + __jsessionid;
    } else {
        var b = a.split("?");
        a = b[0] + ";jsessionid=" + __jsessionId + "?" + b[1];
    }
    return a;
};
String.prototype.isEmpty = function () {
    var a = (this == null || this == undefined || this.trim() == "");
    return a;
};
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.lTrim = function () {
    return this.replace(/(^\s*)/g, "");
};
String.prototype.rTrim = function () {
    return this.replace(/(\s*$)/g, "");
};
String.prototype.endWith = function (c, b) {
    if (c == null || c == "" || this.length == 0 || c.length > this.length) {
        return false;
    }
    var a = this.substring(this.length - c.length);
    if (b == undefined || b) {
        return a == c;
    } else {
        return a.toLowerCase() == c.toLowerCase();
    }
};
String.prototype.startWith = function (c, b) {
    if (c == null || c == "" || this.length == 0 || c.length > this.length) {
        return false;
    }
    var a = this.substr(0, c.length);
    if (b == undefined || b) {
        return a == c;
    } else {
        return a.toLowerCase() == c.toLowerCase();
    }
};
String.prototype.leftPad = function (f, e) {
    if (!isNaN(e)) {
        var b = "";
        for (var d = this.length; d < e; d++) {
            b = b.concat(f);
        }
        b = b.concat(this);
        return b;
    }
    return null;
};
String.prototype.rightPad = function (f, e) {
    if (!isNaN(e)) {
        var b = this;
        for (var d = this.length; d < e; d++) {
            b = b.concat(f);
        }
        return b;
    }
    return null;
};
String.prototype.htmlEncode = function () {
    return this.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\"/g, "&#34;").replace(/\'/g, "&#39;");
};
String.prototype.htmlDecode = function () {
    return this.replace(/\&amp\;/g, "&").replace(/\&gt\;/g, ">").replace(/\&lt\;/g, "<").replace(/\&quot\;/g, "'").replace(/\&\#39\;/g, "'");
};
String.prototype.jsonEscape = function () {
    return this.replace(/\"/g, "&quot;").replace(/\n/g, "&nuot;");
};
String.prototype.jsonUnescape = function () {
    return this.replace(/&quot;/g, '"').replace(/&nuot;/g, "\n");
};
String.prototype.replaceAll = function (b, a) {
    return this.replace(new RegExp(b, "gm"), a);
};
String.prototype.getArgs = function () {
    var a = {};
    if (this.indexOf("?") > -1) {
        var j = this.split("?")[1], d = j.split("&");
        for (var b = 0, h; h = d[b++];) {
            var g = h.indexOf("=");
            if (g == -1) {
                continue;
            }
            var f = h.substring(0, g), e = h.substring(g + 1);
            e = decodeURIComponent(e);
            a[f] = e;
        }
    }
    return a;
};
String.format = function () {
    var b = arguments[0];
    var a = arguments;
    var c = b.replace(/\{(\d+)\}/g, function (d, f) {
        var e = parseInt(f) + 1;
        return a[e];
    });
    return c;
};
function StringBuffer() {
    this.content = new Array;
}
StringBuffer.prototype.append = function (a) {
    this.content.push(a);
};
StringBuffer.prototype.toString = function () {
    return this.content.join("");
};
Date.prototype.Format = function (b) {
    var c = b;
    var a = ["日", "一", "二", "三", "四", "五", "六"];
    c = c.replace(/yyyy|YYYY/, this.getFullYear());
    c = c.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : "0" + (this.getYear() % 100));
    c = c.replace(/MM/, (this.getMonth() + 1) > 9 ? (this.getMonth() + 1).toString() : "0" + (this.getMonth() + 1));
    c = c.replace(/M/g, (this.getMonth() + 1));
    c = c.replace(/w|W/g, a[this.getDay()]);
    c = c.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : "0" + this.getDate());
    c = c.replace(/d|D/g, this.getDate());
    c = c.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : "0" + this.getHours());
    c = c.replace(/h|H/g, this.getHours());
    c = c.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : "0" + this.getMinutes());
    c = c.replace(/m/g, this.getMinutes());
    c = c.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : "0" + this.getSeconds());
    c = c.replace(/s|S/g, this.getSeconds());
    return c;
};
function daysBetween(a, p) {
    var b = "";
    var q = "";
    var o = "";
    var g = "";
    if (a != null && a != "") {
        var d = a.split(" ");
        b = d[0];
        if (d.length > 1) {
            o = d[1];
        }
    }
    if (p != null && p != "") {
        var u = p.split(" ");
        q = u[0];
        if (u.length > 1) {
            g = u[1];
        }
    }
    var v = 0;
    var j = 0;
    var w = 0;
    if (b != null && b != "") {
        var f = b.split("-");
        w = parseInt(f[0], 10);
        v = parseInt(f[1], 10);
        j = parseInt(f[2], 10);
    }
    var l = 0;
    var e = 0;
    var x = 0;
    if (q != null && q != "") {
        var f = q.split("-");
        x = parseInt(f[0], 10);
        l = parseInt(f[1], 10);
        e = parseInt(f[2], 10);
    }
    var s = 0;
    var i = 0;
    var m = 0;
    if (o != null && o != "") {
        var t = o.split(":");
        s = parseInt(t[0]);
        i = parseInt(t[1]);
        m = parseInt(t[2]);
    }
    var r = 0;
    var c = 0;
    var h = 0;
    if (g != null && g != "") {
        var t = g.split(":");
        r = parseInt(t[0]);
        c = parseInt(t[1]);
        h = parseInt(t[2]);
    }
    var k = x > w ? true : false;
    if (!k) {
        k = l > v ? true : false;
        if (!k) {
            k = e > j ? true : false;
            if (!k) {
                if (j == e) {
                    k = r > s ? true : false;
                    if (!k) {
                        k = c > i ? true : false;
                        if (!k) {
                            k = h >= m ? true : false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }
    return k;
}
jQuery.getMutilScript = function (g, h) {
    var b = function (i, j) {
        $.ajax({url: i, dataType: "script", success: j, async: false}).done(function () {
            j && j();
        });
    };
    var f = g.length, d = function () {
        c++;
    }, e = [], c = 0, a = 0;
    for (; a < f; a++) {
        e.push(b(g[a], d));
    }
    jQuery.when(e).done(function () {
        h && h();
    });
};

jQuery.getWindowRect = function () {
    var b = 0, a = 0;
    if (typeof(window.innerWidth) == "number") {
        b = window.innerWidth;
        a = window.innerHeight;
    } else {
        if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
            b = document.documentElement.clientWidth;
            a = document.documentElement.clientHeight;
        } else {
            if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
                b = document.body.clientWidth;
                a = document.body.clientHeight;
            }
        }
    }
    return{height: a, width: b};
};
function forbidF5(b) {
    var a = window.navigator.userAgent;
    if (a.indexOf(b) >= 0) {
        document.onkeydown = function (f) {
            var d = window.event || f;
            var c = d.keyCode || d.which;
            if (c == 116) {
                d.keyCode ? d.keyCode = 0 : d.which = 0;
                cancelBubble = true;
                return false;
            }
        };
    }
}