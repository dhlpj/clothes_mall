layui.define(["jquery", "layer", "form"], function (e) {
    var f = layui.$, b = layui.layer, m = layui.form, y = "dtree-nav-ul-sid", v = "dtree-nav-item", k = "dtree-nav-div",
        u = "dtreefont", g = "dtreefont-special", i = "dtree-icon-move-down", o = "dtree-icon-move-up",
        n = "dtree-icon-refresh", s = "dtree-icon-delete1", r = "dtree-icon-search_list_light", t = "dtree-toolbar",
        C = "dtree-toolbar-tool", j = "dtree-icon-dian", I = "dtree-nav-checkbox-div",
        N = "dtree-icon-fuxuankuangxuanzhong", T = "dtree-icon-fuxuankuang", x = "dtree-icon-fuxuankuang-banxuan",
        P = "d-click-checkbar", c = "dtree", a = "dtree-", d = "-item-this", l = "-item", h = "-dtreefont",
        p = "-ficon", q = "-icon", D = "-checkbox", S = "-choose", A = "dtree-nav-this", L = "dtree-nav-show",
        O = "dtree-icon-hide", _ = f("body"), F = "dtree", w = {}, R = {
            "-1": {open: "dtree-icon-null-open", close: "dtree-icon-null-close"},
            0: {open: "dtree-icon-jian", close: "dtree-icon-jia"},
            1: {open: "dtree-icon-xiangxia1", close: "dtree-icon-xiangyou"}
        }, B = {
            "-1": {open: "dtree-icon-null-open", close: "dtree-icon-null-close"},
            0: {open: "dtree-icon-wenjianjiazhankai", close: "dtree-icon-weibiaoti5"}
        }, M = {
            "-1": "dtree-icon-null",
            0: "dtree-icon-weibiaoti5",
            1: "dtree-icon-yonghu",
            2: "dtree-icon-fenzhijigou",
            3: "dtree-icon-fenguangbaobiao",
            4: "dtree-icon-xinxipilu",
            5: "dtree-icon-shuye1",
            6: "dtree-icon-caidan_xunzhang",
            7: "dtree-icon-normal-file"
        }, E = "checkNodeClick", U = "itemNodeClick", J = "addToolbar", z = "editToolbar", G = "delToolbar", $ = "moveDown",
        H = "moveUp", X = "refresh", K = "remove", Q = "searchNode", V = {
            getElemId: function (e) {
                var t = e.elem || "", a = e.obj || f(t);
                return 0 == a.length ? "" : f(a)[0].id
            }, escape: function (e) {
                return "string" != typeof e ? "" : e.replace(Z.escape, function (e) {
                    return Y.escape[e]
                })
            }, unescape: function (e) {
                return "string" != typeof e ? "" : e.replace(Z.unescape, function (e) {
                    return Y.unescape[e]
                })
            }, cloneObj: function (e, t) {
                var a = {};
                e instanceof Array && (a = []);
                var i = "";
                for (var o in void 0 !== t && (i = t.join(",")), e) if (-1 == i.indexOf(o)) {
                    var n = e[o];
                    a[o] = "object" == typeof n ? V.cloneObj(n, void 0 !== typeof t ? t : []) : n
                }
                return a
            }
        }, W = Object.keys || function (e) {
            e = Object(e);
            var t = [];
            for (var a in e) t.push(a);
            return t
        }, Y = {escape: {"&": "&amp;", "<": "&lt;", ">": "&gt;", "'": "&quo;"}};
    Y.unescape = function (e) {
        e = Object(e);
        var t = {};
        for (var a in e) t[e[a]] = a;
        return t
    }(Y.escape);
    var Z = {
        escape: RegExp("[" + W(Y.escape).join("") + "]", "g"),
        unescape: RegExp("(" + W(Y.unescape).join("|") + ")", "g")
    }, ee = function (i) {
        var e = i.data ? i.data : {}, t = "boolean" != typeof i.async || i.async;
        f.ajax({
            type: i.type ? i.type : "POST",
            headers: i.headers,
            url: i.url,
            dataType: i.dataType ? i.dataType : "json",
            data: e,
            async: t,
            success: i.success,
            error: function (e, t, a) {
                "function" == typeof i.error ? i.error() : b.msg("系统异常导致操作失败, 请联系管理员。", {icon: 5, shift: 6})
            },
            statusCode: {
                404: function () {
                    b.msg("未找到指定请求，请检查访问路径！", {icon: 5, shift: 6})
                }, 500: function () {
                    b.msg("系统错误，请联系管理员。", {icon: 5, shift: 6})
                }
            },
            complete: function (e, t) {
                "function" == typeof i.complete && i.complete(e, t)
            }
        })
    }, te = function (e) {
        var t = "?";
        for (var a in e) t += a + "=" + e[a] + "&";
        return t = t.substring(0, t.length - 1)
    }, ae = function (e) {
        this.response = {
            statusName: "code",
            statusCode: 200,
            message: "message",
            rootName: "data",
            treeId: "id",
            parentId: "parentId",
            title: "title",
            iconClass: "iconClass",
            childName: "children",
            isLast: "isLast",
            spread: "spread",
            disabled: "disabled",
            checkArr: "checkArr",
            isChecked: "isChecked",
            type: "type",
            basicData: "basicData"
        }, this.defaultRequest = {
            nodeId: "nodeId",
            parentId: "parentId",
            context: "context",
            isLeaf: "isLeaf",
            level: "level",
            spread: "spread",
            dataType: "dataType",
            ischecked: "ischecked",
            initchecked: "initchecked",
            basicData: "basicData",
            recordData: "recordData"
        }, this.toolbarFun = {
            addTreeNode: function (e, t) {
            }, editTreeNode: function (e, t) {
            }, editTreeLoad: function (e) {
            }, delTreeNode: function (e, t) {
            }, loadToolbarBefore: function (e, t, a) {
                return e
            }
        }, this.toolbarStyle = {title: "节点", area: ["60%", "80%"]}, this.menubarFun = {
            remove: function (e) {
                return !0
            }
        }, this.menubarTips = {
            toolbar: [],
            group: [$, H, X, K, Q],
            freedom: []
        }, this.checkbarFun = {
            chooseBefore: function (e, t) {
                return !0
            }, chooseDone: function (e) {
            }
        }, this.iframe = {
            iframeElem: "",
            iframeUrl: "",
            iframeLoad: "leaf",
            iframeDefaultRequest: {
                nodeId: "nodeId",
                parentId: "parentId",
                context: "context",
                isLeaf: "isLeaf",
                level: "level",
                spread: "spread",
                dataType: "dataType",
                ischecked: "ischecked",
                initchecked: "initchecked",
                basicData: "basicData",
                recordData: "recordData"
            },
            iframeRequest: {}
        }, this.iframeFun = {
            iframeDone: function (e) {
            }
        }, this.style = {item: "", itemThis: "", dfont: "", icon: "", cbox: "", chs: ""}, this.node = {
            nodeId: "",
            parentId: "",
            context: "",
            isLeaf: "",
            level: "",
            spread: "",
            dataType: "",
            ischecked: "",
            initchecked: "",
            basicData: "",
            recordData: ""
        }, this.toolbarMenu = {}, this.checkbarNode = [], this.checkArrLen = 0, this.temp = [], this.setting(e)
    };
    ae.prototype.setting = function (e) {
        this.options = e || {}, this.elem = this.options.elem || "", this.obj = this.options.obj || f(this.elem), this.initLevel = this.options.initLevel || 2, this.type = this.options.type || "load", this.cache = "boolean" != typeof this.options.cache || this.options.cache, this.record = "boolean" == typeof this.options.record && this.options.record, this.load = "boolean" != typeof this.options.load || this.options.load, this.firstIconArray = f.extend(R, this.options.firstIconArray) || R, this.nodeIconArray = f.extend(B, this.options.nodeIconArray) || B, this.leafIconArray = f.extend(M, this.options.leafIconArray) || M, this.skin = this.options.skin || "theme", "layui" == this.skin ? (this.ficon = this.options.ficon || "1", this.dot = "boolean" == typeof this.options.dot && this.options.dot, this.icon = this.options.icon || "7", this.nodeIcon = "string" == typeof this.icon || "number" == typeof this.icon ? (this.icon, "-1") : this.icon[0]) : (this.ficon = this.options.ficon || "0", this.dot = "boolean" != typeof this.options.dot || this.options.dot, this.icon = this.options.icon || "5", this.nodeIcon = "string" == typeof this.icon || "number" == typeof this.icon ? "-1" == this.icon ? "-1" : "0" : this.icon[0]), this.ficonOpen = this.firstIconArray[this.ficon].open, this.ficonClose = this.firstIconArray[this.ficon].close, this.nodeIconOpen = this.nodeIconArray[this.nodeIcon].open, this.nodeIconClose = this.nodeIconArray[this.nodeIcon].close, this.leafIcon = "string" == typeof this.icon || "number" == typeof this.icon ? this.icon : this.icon[1], this.leafIconShow = this.leafIconArray[this.leafIcon], this.style.item = a + this.skin + l, this.style.itemThis = a + this.skin + d, this.style.dfont = a + this.skin + h, this.style.ficon = a + this.skin + p, this.style.icon = a + this.skin + q, this.style.cbox = a + this.skin + D, this.style.chs = a + this.skin + S, this.url = this.options.url || "", this.async = "boolean" != typeof this.options.async || this.options.async, this.headers = this.options.headers || {}, this.method = this.options.method || "post", this.dataType = this.options.dataType || "json", this.defaultRequest = f.extend(this.defaultRequest, this.options.defaultRequest) || this.defaultRequest, this.filterRequest = this.options.filterRequest || [], this.request = this.options.request || {}, this.response = f.extend(this.response, this.options.response) || this.response, this.data = this.options.data || null, this.dataFormat = this.options.dataFormat || "levelRelationship", this.dataStyle = this.options.dataStyle || "defaultStyle", this.success = this.options.success || function (e, t) {
        }, this.done = this.options.done || function (e, t) {
        }, this.toolbar = this.options.toolbar || !1, this.toolbarStyle = f.extend(this.toolbarStyle, this.options.toolbarStyle) || this.toolbarStyle, this.toolbarScroll = this.options.toolbarScroll || this.elem, this.toolbarLoad = this.options.toolbarLoad || "node", this.toolbarShow = this.options.toolbarShow || ["add", "edit", "delete"], this.toolbarBtn = this.options.toolbarBtn || null, this.toolbarExt = this.options.toolbarExt || [], this.toolbarFun = f.extend(this.toolbarFun, this.options.toolbarFun) || this.toolbarFun, this.menubar = this.options.menubar || !1, this.menubarTips = f.extend(this.menubarTips, this.options.menubarTips) || this.menubarTips, this.menubarFun = f.extend(this.menubarFun, this.options.menubarFun) || this.menubarFun, this.checkbar = this.options.checkbar || !1, this.checkbarLoad = this.options.checkbarLoad || "node", this.checkbarType = this.options.checkbarType || "all", this.checkbarData = this.options.checkbarData || "choose", this.checkbarFun = f.extend(this.checkbarFun, this.options.checkbarFun) || this.checkbarFun, this.useIframe = this.options.useIframe || !1, this.iframe = f.extend(this.iframe, this.options.iframe) || this.iframe, this.iframeFun = f.extend(this.iframeFun, this.options.iframeFun) || this.iframeFun
    }, ae.prototype.reloadSetting = function (e) {
        this.options = f.extend(this.options, e) || this.options, this.elem = this.options.elem || this.elem, void 0 === this.options.obj ? this.elem && 0 < f(this.elem).length && (this.obj = f(this.elem)) : this.obj = this.options.obj || this.obj, this.initLevel = this.options.initLevel || this.initLevel, this.type = this.options.type || this.type, this.cache = "boolean" == typeof this.options.cache ? this.options.cache : this.cache, this.record = "boolean" == typeof this.options.record ? this.options.record : this.record, this.load = "boolean" == typeof this.options.load ? this.options.load : this.load, this.firstIconArray = f.extend(R, this.options.firstIconArray) || this.firstIconArray, this.nodeIconArray = f.extend(B, this.options.nodeIconArray) || this.nodeIconArray, this.leafIconArray = f.extend(M, this.options.leafIconArray) || this.leafIconArray, this.skin = this.options.skin || this.skin, "layui" == this.skin ? (this.ficon = this.options.ficon || this.ficon, this.dot = "boolean" == typeof this.options.dot && this.options.dot, this.icon = this.options.icon || this.icon, this.nodeIcon = "string" == typeof this.icon || "number" == typeof this.icon ? (this.icon, "-1") : this.icon[0]) : (this.ficon = this.options.ficon || this.ficon, this.dot = "boolean" != typeof this.options.dot || this.options.dot, this.icon = this.options.icon || this.icon, this.nodeIcon = "string" == typeof this.icon || "number" == typeof this.icon ? "-1" == this.icon ? "-1" : "0" : this.icon[0]), this.ficonOpen = this.firstIconArray[this.ficon].open, this.ficonClose = this.firstIconArray[this.ficon].close, this.nodeIconOpen = this.nodeIconArray[this.nodeIcon].open, this.nodeIconClose = this.nodeIconArray[this.nodeIcon].close, this.leafIcon = "string" == typeof this.icon || "number" == typeof this.icon ? this.icon : this.icon[1], this.leafIconShow = this.leafIconArray[this.leafIcon], this.style.item = a + this.skin + l, this.style.itemThis = a + this.skin + d, this.style.dfont = a + this.skin + h, this.style.ficon = a + this.skin + p, this.style.icon = a + this.skin + q, this.style.cbox = a + this.skin + D, this.style.chs = a + this.skin + S, this.url = this.options.url || this.url, this.async = "boolean" == typeof this.options.async ? this.options.async : this.async, this.headers = this.options.headers || this.headers, this.method = this.options.method || this.method, this.dataType = this.options.dataType || this.dataType, this.defaultRequest = f.extend(this.defaultRequest, this.options.defaultRequest) || this.defaultRequest, this.filterRequest = this.options.filterRequest || this.filterRequest, this.request = this.options.request || this.request, this.response = f.extend(this.response, this.options.response) || this.response, this.data = this.options.data || this.data, this.dataFormat = this.options.dataFormat || this.dataFormat, this.dataStyle = this.options.dataStyle || this.dataStyle, this.success = this.options.success || this.success, this.done = this.options.done || this.done, this.toolbar = this.options.toolbar || this.toolbar, this.toolbarStyle = f.extend(this.toolbarStyle, this.options.toolbarStyle) || this.toolbarStyle, this.toolbarScroll = this.options.toolbarScroll || this.toolbarScroll, this.toolbarLoad = this.options.toolbarLoad || this.toolbarLoad, this.toolbarShow = this.options.toolbarShow || this.toolbarShow, this.toolbarBtn = this.options.toolbarBtn || this.toolbarBtn, this.toolbarExt = this.options.toolbarExt || this.toolbarExt, this.toolbarFun = f.extend(this.toolbarFun, this.options.toolbarFun) || this.toolbarFun, this.menubar = this.options.menubar || this.menubar, this.menubarTips = f.extend(this.menubarTips, this.options.menubarTips) || this.menubarTips, this.menubarFun = f.extend(this.menubarFun, this.options.menubarFun) || this.menubarFun, this.checkbar = this.options.checkbar || this.checkbar, this.checkbarLoad = this.options.checkbarLoad || this.checkbarLoad, this.checkbarType = this.options.checkbarType || this.checkbarType, this.checkbarData = this.options.checkbarData || this.checkbarData, this.checkbarFun = f.extend(this.checkbarFun, this.options.checkbarFun) || this.checkbarFun, this.useIframe = this.options.useIframe || this.useIframe, this.iframe = f.extend(this.iframe, this.options.iframe) || this.iframe, this.iframeFun = f.extend(this.iframeFun, this.options.iframeFun) || this.iframeFun
    }, ae.prototype.reload = function (e) {
        this.reloadSetting(e), this.init()
    }, ae.prototype.init = function () {
        var i = this;
        if ("object" == typeof i) if (i.data) {
            if (void 0 === i.data.length) return void b.msg("数据解析异常，data数据格式不正确", {icon: 5});
            if (i.obj.html(""), i.success(i.data, i.obj), "list" == i.dataFormat) {
                var e = i.obj.attr("data-id"), t = i.queryListTreeByPid(e, i.data);
                i.loadListTree(t, i.data, 1)
            } else i.loadTree(i.data, 1);
            i.done(i.data, i.obj)
        } else {
            if (!i.url) return void b.msg("数据请求异常，url参数未指定", {icon: 5});
            i.obj.html("");
            var a = i.load ? b.load(1) : "";
            ee({
                async: i.async,
                headers: i.headers,
                type: i.method,
                url: i.url,
                dataType: i.dataType,
                data: i.getFilterRequestParam(i.getRequestParam()),
                success: function (e) {
                    "string" == typeof e && (e = f.parseJSON(e));
                    if (("layuiStyle" == i.dataStyle ? e[i.response.statusName] : e.status[i.response.statusName]) == i.response.statusCode) {
                        if (i.success(e, i.obj), "list" == i.dataFormat) {
                            var t = i.obj.attr("data-id"), a = i.queryListTreeByPid(t, e[i.response.rootName]);
                            i.loadListTree(a, e[i.response.rootName], 1)
                        } else i.loadTree(e[i.response.rootName], 1);
                        i.done(e, i.obj)
                    } else "layuiStyle" == i.dataStyle ? b.msg(e[i.response.message], {icon: 2}) : b.msg(e.status[i.response.message], {icon: 2})
                },
                complete: function () {
                    i.load && b.close(a)
                }
            })
        } else b.msg("树组件未成功加载，请检查配置", {icon: 5})
    }, ae.prototype.getChild = function (e, t) {
        var o = this, n = e.next("ul");
        if (o.setNodeParam(e), void 0 !== t) {
            if (void 0 === t.length) return void b.msg("数据解析异常，data数据格式不正确", {icon: 5});
            if (n.html(""), "list" == o.dataFormat) {
                var a = o.node.nodeId, i = parseInt(o.node.level) + 1, s = o.queryListTreeByPid(a, t);
                o.loadListTree(s, o.data, i)
            } else o.loadTree(t, i)
        } else {
            if (!o.url) return void b.msg("数据请求异常，url参数未指定", {icon: 5});
            n.html("");
            var r = o.load ? b.load(1) : "";
            ee({
                async: o.async,
                headers: o.headers,
                type: o.method,
                url: o.url,
                dataType: o.dataType,
                data: o.getFilterRequestParam(o.getRequestParam()),
                success: function (e) {
                    "string" == typeof e && (e = f.parseJSON(e));
                    if (("layuiStyle" == o.dataStyle ? e[o.response.statusName] : e.status[o.response.statusName]) == o.response.statusCode) {
                        var t = o.node.nodeId, a = parseInt(o.node.level) + 1;
                        if ("list" == o.dataFormat) {
                            var i = o.queryListTreeByPid(t, e[o.response.rootName]);
                            o.loadListTree(i, e[o.response.rootName], a, n)
                        } else o.loadTree(e[o.response.rootName], a, n);
                        n.addClass(L)
                    } else "layuiStyle" == o.dataStyle ? b.msg(e[o.response.message], {icon: 2}) : b.msg(e.status[o.response.message], {icon: 2})
                },
                complete: function () {
                    o.load && b.close(r)
                }
            })
        }
    }, ae.prototype.loadListTree = function (e, t, a, i) {
        var o = this;
        if (i = i || o.getNowNodeUl(), 0 < e.length) for (var n = 0; n < e.length; n++) {
            var s = e[n];
            if ("object" == typeof s) {
                var r = o.parseData(s), d = o.queryListTreeByPid(r.treeId(), t);
                if (i.append(o.getLiItemDom(r.treeId(), r.parentId(), r.title(), r.isLast(d.length), r.iconClass(), r.checkArr(), a, r.spread(a), r.disabled(), r.basicData(), r.recordData(), i.hasClass(c) ? "root" : "item")), 0 < d.length) {
                    var l = parseInt(a) + 1;
                    o.loadListTree(d, t, l, o.obj.find("ul[data-id='" + r.treeId() + "']"))
                }
            }
        }
    }, ae.prototype.queryListTreeByPid = function (e, t) {
        var a = [];
        if (t) for (var i = 0; i < t.length; i++) {
            var o = t[i];
            "object" == typeof o && ("null" == e || null == e ? null == o[this.response.parentId] && a.push(o) : o[this.response.parentId] == e && a.push(o))
        }
        return a
    }, ae.prototype.loadTree = function (e, t, a) {
        var i = this;
        if (e) {
            a = a || i.getNowNodeUl();
            for (var o = 0; o < e.length; o++) {
                var n = e[o];
                if ("object" == typeof n) {
                    var s = i.parseData(n), r = s.children();
                    if (a.append(i.getLiItemDom(s.treeId(), s.parentId(), s.title(), s.isLast(r.length), s.iconClass(), s.checkArr(), t, s.spread(t), s.disabled(), s.basicData(), s.recordData(), a.hasClass(c) ? "root" : "item")), 0 != r.length) {
                        var d = parseInt(t) + 1;
                        i.loadTree(r, d, i.obj.find("ul[data-id='" + s.treeId() + "']"))
                    }
                }
            }
        }
    }, ae.prototype.parseData = function (a) {
        var i = this;
        return {
            treeId: function () {
                return a[i.response.treeId]
            }, parentId: function () {
                return a[i.response.parentId]
            }, title: function () {
                return a[i.response.title] || ""
            }, level: function () {
                return a[i.response.level] || ""
            }, iconClass: function () {
                return a[i.response.iconClass] || ""
            }, isLast: function (e) {
                return 0 == e ? "boolean" != typeof a[i.response.isLast] || a[i.response.isLast] : "boolean" == typeof a[i.response.isLast] && a[i.response.isLast]
            }, spread: function (e) {
                return e < i.initLevel ? "boolean" != typeof a[i.response.spread] || a[i.response.spread] : "boolean" == typeof a[i.response.spread] && a[i.response.spread]
            }, disabled: function () {
                return "boolean" == typeof a[i.response.disabled] && a[i.response.disabled]
            }, checkArr: function () {
                var e = [], t = a[i.response.checkArr];
                return "string" == typeof t && (t = -1 < t.indexOf("{") && -1 < t.indexOf("}") ? JSON.parse(t) : {
                    type: "0",
                    isChecked: t
                }), "object" == typeof t && (void 0 === t.length ? e.push(t) : e = t), 0 < e.length && e.length > i.checkArrLen && (i.checkArrLen = e.length), e
            }, children: function () {
                return a[i.response.childName] || []
            }, basicData: function () {
                return V.escape(JSON.stringify(a[i.response.basicData])) || JSON.stringify({})
            }, recordData: function () {
                var e = i.record ? V.cloneObj(a, [i.response.basicData, i.response.childName]) : {};
                return V.escape(JSON.stringify(e))
            }, data: function () {
                return V.escape(JSON.stringify(a))
            }
        }
    }, ae.prototype.getDom = function (s, e, t, r, n, d, a, l, i) {
        var c = this, h = c.obj[0].id, p = (c.toolbar, c.checkbar);
        return {
            fnode: function () {
                var e = c.ficon, t = c.ficonOpen, a = c.ficonClose, i = c.dot;
                return "-1" != e && i ? r ? "<i class='" + u + " " + j + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "'></i>" : l ? "<i class='" + u + " " + t + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + a + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>" : "-1" == e || i ? "-1" == e && i ? r ? "<i class='" + u + " " + j + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "'></i>" : l ? "<i class='" + u + " " + t + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + a + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>" : "-1" != e || i ? void 0 : r ? "<i class='" + u + " " + j + " " + O + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "' style='display:none;'></i>" : l ? "<i class='" + u + " " + t + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + a + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>" : r ? "<i class='" + u + " " + j + " " + O + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "'></i>" : l ? "<i class='" + u + " " + t + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + a + " " + c.style.dfont + " " + c.style.ficon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>"
            }, node: function () {
                var e = c.nodeIcon, t = c.leafIcon, a = c.leafIconShow, i = c.nodeIconOpen, o = c.nodeIconClose;
                return n && (o = i = a = n), "-1" != e && "-1" != t ? r ? "<i class='" + u + " " + a + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "'></i>" : l ? "<i class='" + u + " " + i + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + o + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>" : "-1" != e && "-1" == t ? r ? "<i class='" + u + " " + a + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "'></i>" : l ? "<i class='" + u + " " + i + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + o + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>" : "-1" == e && "-1" != t ? r ? "<i class='" + u + " " + a + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "'></i>" : l ? "<i class='" + u + " " + i + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + o + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>" : "-1" == e && "-1" == t ? r ? "<i class='" + u + " " + a + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='last' data-id='" + s + "' dtree-id='" + h + "'></i>" : l ? "<i class='" + u + " " + i + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='open' data-id='" + s + "' dtree-id='" + h + "'></i>" : "<i class='" + u + " " + o + " " + g + " " + c.style.dfont + " " + c.style.icon + "' data-spread='close' data-id='" + s + "' dtree-id='" + h + "'></i>" : void 0
            }, checkbox: function () {
                var e = !1;
                if ("node" == c.checkbarLoad ? p && (e = !0) : r && p && (e = !0), e) {
                    var t = "<div class='" + I + "' data-id='" + s + "' dtree-id='" + h + "'>";
                    if (d && 0 < d.length) for (var a = 0; a < d.length; a++) {
                        var i = d[a], o = i.isChecked, n = T;
                        n = "2" == o ? x + " " + c.style.chs : "1" == o ? N + " " + c.style.chs : T, t += "<i class='" + u + " " + c.style.dfont + " " + c.style.cbox + " " + n + "' data-id='" + s + "' dtree-id='" + h + "' data-checked='" + i.isChecked + "' data-initchecked='" + i.isChecked + "' data-type='" + i.type + "' dtree-click='" + E + "' data-par='." + P + "'></i>"
                    }
                    return t += "</div>"
                }
                return ""
            }, text: function () {
                return "<cite class='t-click' data-id='" + s + "' data-leaf='" + (r ? "leaf" : "node") + "'>" + t + "</cite>"
            }, ul: function () {
                return r ? "<ul class='" + y + "' data-id='" + s + "' dtree-id='" + h + "'></ul>" : l ? "<ul class='" + y + " " + L + "' data-id='" + s + "' dtree-id='" + h + "'></ul>" : "<ul class='" + y + "' data-id='" + s + "' dtree-id='" + h + "'></ul>"
            }
        }
    }, ae.prototype.getLiItemDom = function (e, t, a, i, o, n, s, r, d, l, c, h) {
        var p = this, u = p.obj[0].id, f = p.getDom(e, t, a, i, o, n, s, r, d);
        l = "{}" == l ? "" : l, c = "{}" == c ? "" : c;
        var b = "<div class='" + k + " " + p.style.item + "' data-id='" + e + "' dtree-id='" + u + "' dtree-click='" + U + "' data-basic='" + l + "' data-record='" + c + "' ";
        return p.toolbar ? ("node" == p.toolbarLoad && (b += " d-contextmenu='true'>"), "noleaf" == p.toolbarLoad && (b += i ? " d-contextmenu='false'>" : " d-contextmenu='true'>"), "leaf" == p.toolbarLoad && (b += i ? " d-contextmenu='true'>" : " d-contextmenu='false'>")) : b += " d-contextmenu='false'>", ["<li class='" + P + " " + v + "'data-id='" + e + "'data-pid='" + ("root" == h ? t || "-1" : t) + "'dtree-id='" + u + "'data-index='" + s + "'>" + b, f.fnode(), f.node(), f.checkbox(), f.text(), "</div>", f.ul(), "</li>"].join("")
    }, ae.prototype.dataInit = function (e) {
        var t = this, a = t.obj.find("div[data-id='" + e + "']");
        a.parent().find("." + A).removeClass(A), a.parent().find("." + t.style.itemThis).removeClass(t.style.itemThis), a.addClass(A), a.addClass(t.style.itemThis), t.setNodeParam(a);
        var i = a.parents("." + v);
        return i.children("ul").addClass(L), i.children("." + k).children("i[data-spread]." + t.ficonClose).addClass(t.ficonOpen), i.children("." + k).children("i[data-spread]." + t.ficonClose).removeClass(t.ficonClose), i.children("." + k).children("i[data-spread]." + t.nodeIconClose).addClass(t.nodeIconOpen), i.children("." + k).children("i[data-spread]." + t.nodeIconClose).removeClass(t.nodeIconClose), t.getNowParam()
    }, ae.prototype.clickSpread = function (e) {
        var t = e.find("i[data-spread]").eq(0), a = e.find("i[data-spread]").eq(1), i = a.attr("class"),
            o = (e.find("cite[data-leaf]").eq(0), t.attr("data-spread")), n = e.next("ul"), s = this;
        if (0 < n.length) if ("close" == o) {
            "load" == s.type ? s.cache ? n.html() ? n.addClass(L) : s.getChild(e) : (n.html(""), s.getChild(e)) : n.addClass(L), e.find("i[data-spread]").attr("data-spread", "open"), t.removeClass(s.ficonClose), t.addClass(s.ficonOpen);
            var r = s.nodeIconClose;
            0 < i.indexOf(r) && (a.removeClass(s.nodeIconClose), a.addClass(s.nodeIconOpen))
        } else if ("open" == o) {
            n.removeClass(L), e.find("i[data-spread]").attr("data-spread", "close"), t.removeClass(s.ficonOpen), t.addClass(s.ficonClose);
            r = s.nodeIconOpen;
            0 < i.indexOf(r) && (a.removeClass(s.nodeIconOpen), a.addClass(s.nodeIconClose))
        }
    }, ae.prototype.escape = function (e) {
        return V.escape(e)
    }, ae.prototype.unescape = function (e) {
        return V.unescape(e)
    }, ae.prototype.initTreePlus = function () {
        var e = this;
        e.obj.prevAll("div#dtree_menubar_" + e.obj[0].id).remove(), e.obj.prevAll("div#dtree_toolbar_" + e.obj[0].id).remove(), e.toolbarMenu = {}, e.menubar && e.menubarTips.group && 0 < e.menubarTips.group.length && e.obj.before("<div class='dtree-menubar' id='dtree_menubar_" + e.obj[0].id + "'><div class='layui-btn-group'></div></div>"), e.toolbar && e.obj.before("<div class='" + t + " layui-nav' id='dtree_toolbar_" + e.obj[0].id + "'><div class='layui-nav-item'><dl class='layui-nav-child layui-anim'></dl></div></div>")
    }, ae.prototype.openTreePlus = function () {
        var e = this, t = [];
        if (e.toolbar && e.getToolbarDom(), e.menubar) {
            var a = e.menubarTips, i = a.toolbar, o = a.group;
            a.freedom;
            if (i && 0 < i.length) for (var n = 0; n < i.length; n++) {
                var s = i[n];
                "string" == typeof s && e.getMenubarToolDom(s), "object" == typeof s && e.getExtMenubarToolDom(s)
            }
            if (o && 0 < o.length) {
                for (n = 0; n < o.length; n++) {
                    var r = o[n];
                    "string" == typeof r && t.push(e.getMenubarDom(r)), "object" == typeof r && t.push(e.getExtMenubarDom(r))
                }
                e.obj.prevAll("div#dtree_menubar_" + e.obj[0].id).children("div.layui-btn-group").append(t.join(""))
            }
        }
    }, ae.prototype.getMenubarDom = function (e) {
        var t = this.obj[0].id, a = "";
        switch (e) {
            case $:
                a = "<button class='layui-btn layui-btn-sm layui-btn-primary' dtree-id='" + t + "' d-menu='" + $ + "' title='展开节点'><i class='" + u + " " + i + "'></i></button>";
                break;
            case H:
                a = "<button class='layui-btn layui-btn-sm layui-btn-primary' dtree-id='" + t + "' d-menu='" + H + "' title='收缩节点'><i class='" + u + " " + o + "'></i></button>";
                break;
            case X:
                a = "<button class='layui-btn layui-btn-sm layui-btn-primary' dtree-id='" + t + "' d-menu='" + X + "' title='刷新'><i class='" + u + " " + n + "'></i></button>";
                break;
            case K:
                a = this.checkbar ? "<button class='layui-btn layui-btn-sm layui-btn-primary' dtree-id='" + t + "' d-menu='" + K + "' title='删除选中节点'><i class='" + u + " " + s + "'></i></button>" : "";
                break;
            case Q:
                a = "<button class='layui-btn layui-btn-sm layui-btn-primary' dtree-id='" + t + "' d-menu='" + Q + "' title='查询节点'><i class='" + u + " " + r + "'></i></button>"
        }
        return a
    }, ae.prototype.getExtMenubarDom = function (e) {
        return "<button class='layui-btn layui-btn-sm layui-btn-primary' dtree-id='" + this.obj[0].id + "' d-menu='" + e.menubarId + "' title='" + e.title + "'><i class='" + u + " " + e.icon + "'></i></button>"
    }, ae.prototype.getMenubarToolDom = function (e) {
        var t = this, a = t.obj[0].id;
        switch (e) {
            case $:
                t.toolbarMenu[$] = "<dd><a dtree-id='" + a + "' d-menu='" + $ + "'><i class='" + u + " " + i + "'></i>&nbsp;展开" + t.toolbarStyle.title + "</a></dd>";
                break;
            case H:
                t.toolbarMenu[H] = "<dd><a dtree-id='" + a + "' d-menu='" + H + "'><i class='" + u + " " + o + "'></i>&nbsp;收缩" + t.toolbarStyle.title + "</a></dd>";
                break;
            case X:
                t.toolbarMenu[X] = "<dd><a dtree-id='" + a + "' d-menu='" + X + "'><i class='" + u + " " + n + "'></i>&nbsp;刷新</a></dd>";
                break;
            case K:
                t.checkbar && (t.toolbarMenu[K] = "<dd><a dtree-id='" + a + "' d-menu='" + K + "'><i class='" + u + " " + s + "'></i>&nbsp;删除选中" + t.toolbarStyle.title + "</a></dd>");
                break;
            case Q:
                t.toolbarMenu[Q] = "<dd><a dtree-id='" + a + "' d-menu='" + Q + "'><i class='" + u + " " + r + "'></i>&nbsp;查询" + t.toolbarStyle.title + "</a></dd>"
        }
    }, ae.prototype.getExtMenubarToolDom = function (e) {
        this.toolbarMenu[e.menubarId] = "<dd><a dtree-id='" + this.obj[0].id + "' d-menu='" + e.menubarId + "'><i class='" + u + " " + e.icon + "'></i>&nbsp;" + e.title + "</a></dd>"
    }, ae.prototype.menubarMethod = function () {
        var p = this;
        return {
            openAllNode: function (e) {
                for (var t = e || p.obj.children("li").children("ul"), a = 0; a < t.length; a++) {
                    var i = f(t[a]), o = i.prev("div"), n = o.find("i[data-spread]").eq(0),
                        s = o.find("i[data-spread]").eq(1), r = s.attr("class"), d = o.find("cite[data-leaf]").eq(0),
                        l = n.attr("data-spread");
                    if ("leaf" != d.attr("data-leaf")) {
                        if ("open" == l) ; else {
                            "load" == p.type ? p.cache ? i.html() ? i.addClass(L) : p.getChild(o) : (i.html(""), p.getChild(o)) : i.addClass(L), o.find("i[data-spread]").attr("data-spread", "open"), n.removeClass(p.ficonClose), n.addClass(p.ficonOpen);
                            var c = p.nodeIconClose;
                            0 < r.indexOf(c) && (s.removeClass(p.nodeIconClose), s.addClass(p.nodeIconOpen))
                        }
                        var h = i.children("li").children("ul");
                        p.menubarMethod().openAllNode(h)
                    }
                }
            }, closeAllNode: function () {
                p.obj.find("." + y).each(function () {
                    var e = f(this), t = e.prev("div"), a = t.find("i[data-spread]").eq(0),
                        i = t.find("i[data-spread]").eq(1), o = i.attr("class"), n = t.find("cite[data-leaf]").eq(0);
                    a.attr("data-spread"), n.attr("data-leaf");
                    e.removeClass(L), t.find("i[data-spread]").attr("data-spread", "close"), a.removeClass(p.ficonOpen), a.addClass(p.ficonClose);
                    var s = p.nodeIconOpen;
                    0 < o.indexOf(s) && (i.removeClass(p.nodeIconOpen), i.addClass(p.nodeIconClose))
                })
            }, refreshTree: function () {
                p.obj.html(""), p.initNodeParam(), p.init()
            }, remove: function () {
                if (0 == p.obj.find("i[data-par][data-checked='1']").length) b.msg("请至少选中一个节点", {icon: 2}); else {
                    p.checkbarNode = [];
                    p.obj.find("i[data-par][data-checked='1']").each(function () {
                        var e = f(this), t = e.closest("." + k);
                        p.checkbarNode.push(p.getRequestParam(p.getCheckbarNodeParam(t, e)))
                    }), b.confirm("确定要删除选中节点？", {icon: 3, title: "删除选中节点"}, function (e) {
                        p.menubarFun.remove(p.checkbarNode) && (p.obj.find("i[data-par][data-checked='1']").closest("." + k).next("ul").remove(), p.obj.find("i[data-par][data-checked='1']").closest("." + k).remove(), p.checkbarNode = []), b.close(e)
                    })
                }
            }, searchNode: function () {
                b.prompt({formType: 0, value: "", title: "查询节点"}, function (e, t, a) {
                    e ? p.searchNode(e) || b.msg("该名称节点不存在！", {icon: 5}) : b.msg("未指定查询节点名称", {icon: 5});
                    b.close(t)
                })
            }, extMethod: function (e, t, a) {
                if (p.menubar && p.menubarTips.group && 0 < p.menubarTips.group.length && "group" == a) for (var i = 0; i < p.menubarTips.group.length; i++) {
                    if (e == (o = p.menubarTips.group[i]).menubarId) {
                        o.handler(p.getRequestParam(p.getNodeParam(t), t));
                        break
                    }
                }
                if (p.menubar && p.menubarTips.toolbar && 0 < p.menubarTips.toolbar.length && "toolbar" == a) for (i = 0; i < p.menubarTips.toolbar.length; i++) {
                    if (e == (o = p.menubarTips.toolbar[i]).menubarId) {
                        o.handler(p.getRequestParam(p.getNodeParam(t), t));
                        break
                    }
                }
                if (p.menubar && p.menubarTips.freedom && 0 < p.menubarTips.freedom.length && "freedom" == a) for (i = 0; i < p.menubarTips.freedom.length; i++) {
                    var o;
                    if (e == (o = p.menubarTips.freedom[i]).menubarId) {
                        o.handler(p.getRequestParam(p.getNodeParam(t), t));
                        break
                    }
                }
            }
        }
    }, ae.prototype.menubarListener = function (e, t) {
        var a = this, i = a.getNowNode();
        switch (e) {
            case $:
                a.menubarMethod().openAllNode();
                break;
            case H:
                a.menubarMethod().closeAllNode();
                break;
            case X:
                a.menubarMethod().refreshTree();
                break;
            case K:
                a.menubarMethod().remove();
                break;
            case Q:
                a.menubarMethod().searchNode();
                break;
            default:
                a.menubarMethod().extMethod(e, i, t)
        }
    }, ae.prototype.searchNode = function (i) {
        var e = !1, o = [];
        if (this.obj.find("cite[data-leaf]").each(function () {
                var e = f(this);
                if (-1 < e.html().indexOf(i)) {
                    if ("leaf" == e.attr("data-leaf")) {
                        var t = "";
                        e.parents("li").each(function () {
                            t = "-" + f(this).find("cite[data-leaf]").html() + t
                        }), t = t.substring(1, t.length), e.attr("title", t)
                    }
                    var a = 0;
                    e.parents("li").each(function () {
                        if (-1 < f(this).find("cite[data-leaf]").html().indexOf(i) && a++, 2 <= a) return !0
                    }), a < 2 && o.push(e.closest("li").prop("outerHTML"))
                }
            }), 0 < o.length) {
            e = !0, this.obj.html("");
            for (var t = 0; t < o.length; t++) this.obj.append(o[t])
        }
        return e
    }, ae.prototype.getToolbarDom = function () {
        var e = this, t = e.toolbarShow, a = e.toolbarExt;
        if (0 < t.length) for (var i = 0; i < t.length; i++) {
            var o = t[i];
            "add" == o && (e.toolbarMenu[J] = "<dd><a dtree-tool='" + J + "'><i class='" + u + " dtree-icon-roundadd'></i>&nbsp;新增" + e.toolbarStyle.title + "</a></dd>"), "edit" == o && (e.toolbarMenu[z] = "<dd><a dtree-tool='" + z + "'><i class='" + u + " dtree-icon-bianji'></i>&nbsp;编辑" + e.toolbarStyle.title + "</a></dd>"), "delete" == o && (e.toolbarMenu[G] = "<dd><a dtree-tool='" + G + "'><i class='" + u + " dtree-icon-roundclose'></i>&nbsp;删除" + e.toolbarStyle.title + "</a></dd>")
        }
        if (0 < a.length) for (i = 0; i < a.length; i++) {
            var n = a[i];
            e.toolbarMenu[n.toolbarId] = "<dd><a dtree-tool='" + n.toolbarId + "'><i class='" + u + " " + n.icon + "'></i>&nbsp;" + n.title + "</a></dd>"
        }
    }, ae.prototype.setToolbarDom = function (e) {
        if (e) for (var t in this.obj.prevAll("div#dtree_toolbar_" + this.obj[0].id).find("div.layui-nav-item>dl.layui-nav-child").html(""), e) this.obj.prevAll("div#dtree_toolbar_" + this.obj[0].id).find("div.layui-nav-item>dl.layui-nav-child").append(e[t])
    }, ae.prototype.loadToolBar = function (e, t) {
        var a = this, i = (a.toolbarShow, a.toolbarBtn), o = "";
        switch (t) {
            case J:
                var n = ['<div class="layui-form-item">', '<label class="layui-form-label">当前选中：</label>', '<div class="layui-input-block f-input-par">', '<input type="text" name="nodeTitle" class="layui-input f-input" value="' + e + '" readonly/>', "</div>", "</div>"].join(""),
                    s = ['<div class="layui-form-item">', '<label class="layui-form-label">新增' + a.toolbarStyle.title + "：</label>", '<div class="layui-input-block f-input-par">', '<input type="text" name="addNodeName" class="layui-input f-input" value="" lay-verify="required"/>', "</div>", "</div>"].join(""),
                    r = ['<div class="layui-form-item">', '<div class="layui-input-block" style="margin-left:0px;text-align:center;">', '<button type="button" class="layui-btn layui-btn-normal btn-w100" lay-submit lay-filter="dtree_addNode_form">确认添加</button>', "</div>", "</div>"].join(""),
                    d = ['<div class="' + C + '"><form class="layui-form layui-form-pane" lay-filter="dtree_addNode_form">', n, s];
                if (null != i && 0 < i.length && null != i[0] && null != i[0] && 0 < i[0].length) for (var l = i[0], c = 0; c < l.length; c++) {
                    switch ((b = l[c].type) || (b = "text"), b) {
                        case"text":
                            d.push(a.loadToolBarDetail().text(l[c]));
                            break;
                        case"textarea":
                            d.push(a.loadToolBarDetail().textarea(l[c]));
                            break;
                        case"select":
                            d.push(a.loadToolBarDetail().select(l[c]));
                            break;
                        case"hidden":
                            d.push(a.loadToolBarDetail().hidden(l[c]))
                    }
                }
                d.push(r), d.push("</form></div>"), o = d.join("");
                break;
            case z:
                n = ['<div class="layui-form-item">', '<label class="layui-form-label">当前选中：</label>', '<div class="layui-input-block f-input-par">', '<input type="text" name="nodeTitle" class="layui-input f-input" value="' + e + '" readonly/>', "</div>", "</div>"].join("");
                var h = ['<div class="layui-form-item">', '<label class="layui-form-label">编辑' + a.toolbarStyle.title + "：</label>", '<div class="layui-input-block f-input-par">', '<input type="text" name="editNodeName" class="layui-input f-input" value="' + e + '" lay-verify="required"/>', "</div>", "</div>"].join(""),
                    p = ['<div class="layui-form-item">', '<div class="layui-input-block" style="margin-left:0px;text-align:center;">', '<button type="button" class="layui-btn layui-btn-normal btn-w100" lay-submit lay-filter="dtree_editNode_form">确认编辑</button>', "</div>", "</div>"].join(""),
                    u = ['<div class="' + C + '"><form class="layui-form layui-form-pane" lay-filter="dtree_editNode_form">', n, h];
                if (null != i && 0 < i.length && null != i[1] && null != i[1] && 0 < i[1].length) {
                    var f = i[1];
                    for (c = 0; c < f.length; c++) {
                        var b;
                        switch ((b = f[c].type) || (b = "text"), b) {
                            case"text":
                                u.push(a.loadToolBarDetail().text(f[c]));
                                break;
                            case"textarea":
                                u.push(a.loadToolBarDetail().textarea(f[c]));
                                break;
                            case"select":
                                u.push(a.loadToolBarDetail().select(f[c]));
                                break;
                            case"hidden":
                                u.push(a.loadToolBarDetail().hidden(f[c]))
                        }
                    }
                }
                u.push(p), u.push("</form></div>"), o = u.join("")
        }
        return o
    }, ae.prototype.loadToolBarDetail = function () {
        return {
            text: function (e) {
                return ['<div class="layui-form-item">', '<label class="layui-form-label" title="' + e.label + '">' + e.label + "：</label>", '<div class="layui-input-block f-input-par">', '<input type="text" name="' + e.name + '" class="layui-input f-input" value="' + (e.value ? e.value : "") + '"/>', "</div>", "</div>"].join("")
            }, textarea: function (e) {
                return ['<div class="layui-form-item layui-form-text">', '<label class="layui-form-label">' + e.label + "：</label>", '<div class="layui-input-block f-input-par">', '<textarea name="' + e.name + '" class="layui-textarea f-input">' + (e.value ? e.value : "") + "</textarea>", "</div>", "</div>"].join("")
            }, hidden: function (e) {
                return ['<input type="hidden" name="' + e.name + '" class="layui-input f-input" value="' + (e.value ? e.value : "") + '"/>'].join("")
            }, select: function (e) {
                var t = e.optionsData, a = "", i = e.value ? e.value : "";
                for (var o in t) i == t[o] ? a += "<option value='" + o + "' selected>" + t[o] + "</option>" : a += "<option value='" + o + "'>" + t[o] + "</option>";
                return ['<div class="layui-form-item">', '<label class="layui-form-label" title="' + e.label + '">' + e.label + "：</label>", '<div class="layui-input-block f-input-par">', '<select name="' + e.name + '">', a, "</select>", "</div>", "</div>"].join("")
            }
        }
    }, ae.prototype.changeTreeNodeAdd = function (e) {
        var t = this, a = t.temp, i = a[0], o = a[1], n = a[2], s = a[3];
        if (e) {
            var r = t.obj.find("[data-id='" + i + "']");
            if ("object" == typeof e) {
                r.remove();
                var d = t.parseData(e);
                if (!d.treeId()) return b.msg("添加失败,节点ID为undefined！", {icon: 5}), o.find("li[data-id='" + i + "']").remove(), t.setNodeParam(n), void(t.temp = []);
                o.append(t.getLiItemDom(d.treeId(), d.parentId(), d.title(), d.isLast(0), d.iconClass(), d.checkArr(), s, d.spread(), d.disabled(), d.basicData(), d.recordData(), "item"));
                var l = o.find("div[data-id='" + e.id + "']");
                t.setNodeParam(l)
            } else if ("string" == typeof e || "number" == typeof this.icon) {
                r.attr("data-id", e), o.find("li[data-id='" + e + "']").show();
                l = o.find("div[data-id='" + e + "']");
                t.setNodeParam(l)
            }
            var c = n.find("i[data-spread]");
            "last" == c.eq(0).attr("data-spread") ? (c.attr("data-spread", "open"), c.eq(0).removeClass(j), c.eq(0).removeClass(O), c.eq(0).addClass(t.ficonOpen), c.eq(1).removeClass(M[t.leafIcon])) : (c.attr("data-spread", "open"), c.eq(0).removeClass(t.ficonClose), c.eq(0).addClass(t.ficonOpen), c.eq(1).removeClass(t.nodeIconClose)), c.eq(1).addClass(t.nodeIconOpen), o.addClass(L)
        } else o.find("li[data-id='" + i + "']").remove(), t.setNodeParam(n);
        t.temp = []
    }, ae.prototype.changeTreeNodeEdit = function (e) {
        var t = this.temp, a = t[0], i = t[1];
        e || (a.html(title), node = this.getNodeParam(i)), this.temp = []
    }, ae.prototype.changeTreeNodeDone = function (e) {
        m.val("dtree_editNode_form", e), m.render()
    }, ae.prototype.changeTreeNodeDel = function (e) {
        var t = this, a = t.temp, i = a[0], o = i.parent("ul"), n = a[1];
        if (e) {
            if (i.remove(), 0 == o.children("li").length) {
                var s = n.find("i[data-spread]");
                s.attr("data-spread", "last"), s.eq(0).removeClass(t.ficonOpen), s.eq(0).removeClass(t.ficonClose), t.dot || s.eq(0).addClass(O), s.eq(0).addClass(j), s.eq(1).removeClass(t.nodeIconOpen), s.eq(1).removeClass(t.nodeIconClose), s.eq(1).addClass(M[t.leafIcon])
            }
            t.initNodeParam()
        }
        t.temp = []
    }, ae.prototype.chooseDataInit = function (e) {
        for (var t = this, a = e.split(","), i = 0; i < a.length; i++) t.obj.find("i[dtree-click='" + E + "']").each(function () {
            f(this).attr("data-id") == a[i] && t.checkStatus(f(this)).check()
        });
        var o = t.obj.find("i[dtree-click='" + E + "'][data-checked='1']").parents("." + v);
        return o.children("ul").addClass(L), o.children("." + k).children("i[data-spread]." + t.ficonClose).addClass(t.ficonOpen), o.children("." + k).children("i[data-spread]." + t.ficonClose).removeClass(t.ficonClose), o.children("." + k).children("i[data-spread]." + t.nodeIconClose).addClass(t.nodeIconOpen), o.children("." + k).children("i[data-spread]." + t.nodeIconClose).removeClass(t.nodeIconClose), t.getCheckbarNodesParam()
    }, ae.prototype.checkAllOrNot = function (e) {
        var t = this, a = e.attr("data-par"), i = e.attr("data-type"), o = e.closest(a), n = e.parents(a),
            s = o.find(a);
        if ("1" == e.attr("data-checked")) {
            t.checkStatus(e).noCheck();
            var r = s.find(">." + k + ">." + I + ">i[data-type='" + i + "']");
            t.checkStatus(r).noCheck();
            for (var d = 1, l = n; d < l.length; d++) {
                if (0 == l.eq(d).find(">." + y + " ." + I + ">i[data-type='" + i + "'][data-checked='1']").length) {
                    var c = l.eq(d).find(">." + k + ">." + I + ">i[data-type='" + i + "']");
                    t.checkStatus(c).noCheck()
                }
            }
        } else {
            t.checkStatus(e).check();
            r = s.find(">." + k + ">." + I + ">i[data-type='" + i + "']");
            t.checkStatus(r).check();
            for (d = 1, l = n; d < l.length; d++) {
                c = l.eq(d).find(">." + k + ">." + I + ">i[data-type='" + i + "']");
                t.checkStatus(c).check()
            }
        }
    }, ae.prototype.checkAllOrNoallOrNot = function (e) {
        var t = this, a = (e.closest("." + k), e.attr("data-par")), i = e.attr("data-type"), o = e.closest(a),
            n = e.parents(a), s = o.find(a);
        if ("1" == e.attr("data-checked")) {
            t.checkStatus(e).noCheck();
            var r = s.find(">." + k + ">." + I + ">i[data-type='" + i + "']");
            t.checkStatus(r).noCheck();
            for (var d = 1, l = n; d < l.length; d++) {
                var c = l.eq(d).find(">." + y + " ." + I + ">i[data-type='" + i + "'][data-checked='1']").length,
                    h = l.eq(d).find(">." + k + ">." + I + ">i[data-type='" + i + "']");
                0 == c ? t.checkStatus(h).noCheck() : t.checkStatus(h).noallCheck()
            }
        } else {
            t.checkStatus(e).check();
            r = s.find(">." + k + ">." + I + ">i[data-type='" + i + "']");
            t.checkStatus(r).check();
            for (d = 1, l = n; d < l.length; d++) {
                var p = l.eq(d).find(">." + y + " ." + I + ">i[data-type='" + i + "'][data-checked='1']").length,
                    u = l.eq(d).find(">." + y + " ." + I + ">i[data-type='" + i + "']").length;
                h = l.eq(d).find(">." + k + ">." + I + ">i[data-type='" + i + "']");
                p != u ? t.checkStatus(h).noallCheck() : t.checkStatus(h).check()
            }
        }
    }, ae.prototype.checkAllOrPcascOrNot = function (e) {
        e.closest("." + k);
        var t = e.attr("data-par"), a = e.attr("data-type"), i = e.closest(t), o = (e.parents(t), i.find(t));
        if ("1" == e.attr("data-checked")) {
            this.checkStatus(e).noCheck();
            var n = o.find(">." + k + ">." + I + ">i[data-type='" + a + "']");
            this.checkStatus(n).noCheck()
        } else {
            this.checkStatus(e).check();
            n = o.find(">." + k + ">." + I + ">i[data-type='" + a + "']");
            this.checkStatus(n).check()
        }
    }, ae.prototype.checkOrNot = function (e) {
        e.closest("." + k);
        var t = e.attr("data-par"), a = (e.attr("data-type"), e.closest(t));
        e.parents(t), a.find(t);
        "1" == e.attr("data-checked") ? this.checkStatus(e).noCheck() : this.checkStatus(e).check()
    }, ae.prototype.checkOnly = function (e) {
        e.closest("." + k);
        var t = e.attr("data-par"), a = (e.attr("data-type"), e.closest(t)),
            i = (e.parents(t), a.find(t), e.attr("data-checked")), o = this.obj.find("i[data-checked]");
        this.checkStatus(o).noCheck(), "1" != i && this.checkStatus(e).check()
    }, ae.prototype.changeCheck = function () {
        var e = this, t = e.temp[0];
        "all" == e.checkbarType ? e.checkAllOrNot(t) : "no-all" == e.checkbarType ? e.checkAllOrNoallOrNot(t) : "p-casc" == e.checkbarType ? e.checkAllOrPcascOrNot(t) : "self" == e.checkbarType ? e.checkOrNot(t) : "only" == e.checkbarType ? e.checkOnly(t) : e.checkAllOrNot(t);
        var a = e.setAndGetCheckbarNodesParam();
        e.checkbarFun.chooseDone(a), layui.event.call(this, F, "chooseDone(" + f(e.obj)[0].id + ")", {checkbarParams: a}), e.temp = []
    }, ae.prototype.initNoAllCheck = function () {
        var e = this.obj.find("i[data-checked='1']");
        if (0 < e.length) for (var t = 0; t < e.length; t++) for (var a = f(e[t]), i = a.attr("data-par"), o = a.attr("data-type"), n = a.closest(i), s = a.parents(i), r = (n.find(i), 1), d = s; r < d.length; r++) {
            var l = d.eq(r).find(">." + y + " ." + I + ">i[data-type='" + o + "'][data-checked='1']").length,
                c = d.eq(r).find(">." + y + " ." + I + ">i[data-type='" + o + "']").length,
                h = d.eq(r).find(">." + k + ">." + I + ">i[data-type='" + o + "']");
            l != c ? this.checkStatus(h).noallCheck() : this.checkStatus(h).check()
        }
    }, ae.prototype.initAllCheck = function () {
        var e = this.obj.find("i[data-checked='1']");
        if (0 < e.length) for (var t = 0; t < e.length; t++) for (var a = f(e[t]), i = a.attr("data-par"), o = a.attr("data-type"), n = a.closest(i), s = a.parents(i), r = (n.find(i), 1), d = s; r < d.length; r++) {
            var l = d.eq(r).find(">." + k + ">." + I + ">i[data-type='" + o + "']");
            this.checkStatus(l).check()
        }
    }, ae.prototype.checkStatus = function (e) {
        var t = this;
        return {
            check: function () {
                e.removeClass(T), e.removeClass(x), e.addClass(N), e.addClass(t.style.chs), e.attr("data-checked", "1")
            }, noCheck: function () {
                e.removeClass(x), e.removeClass(N), e.removeClass(t.style.chs), e.addClass(T), e.attr("data-checked", "0")
            }, noallCheck: function () {
                e.removeClass(T), e.removeClass(N), e.addClass(x), e.addClass(t.style.chs), e.attr("data-checked", "2")
            }
        }
    }, ae.prototype.setAndGetCheckbarNodesParam = function () {
        var a = this;
        return a.checkbarNode = [], "change" == a.checkbarData ? a.obj.find("i[data-par]").each(function () {
            var e = f(this), t = e.closest("." + k);
            e.attr("data-checked") != e.attr("data-initchecked") && a.checkbarNode.push(a.getRequestParam(a.getCheckbarNodeParam(t, e)))
        }) : "all" == a.checkbarData ? a.obj.find("i[data-par][data-checked]").each(function () {
            var e = f(this), t = e.closest("." + k);
            a.checkbarNode.push(a.getRequestParam(a.getCheckbarNodeParam(t, e)))
        }) : a.obj.find("i[data-par][data-checked='1']").each(function () {
            var e = f(this), t = e.closest("." + k);
            a.checkbarNode.push(a.getRequestParam(a.getCheckbarNodeParam(t, e)))
        }), a.checkbarNode
    }, ae.prototype.getCheckbarNodesParam = function () {
        return this.setAndGetCheckbarNodesParam()
    }, ae.prototype.getCheckbarNodeParam = function (e, t) {
        var a = {};
        return a.nodeId = e.attr("data-id"), a.parentId = e.parent().attr("data-pid"), a.context = e.find("cite[data-leaf]").eq(0).text(), a.isLeaf = "leaf" == e.find("cite[data-leaf]").eq(0).attr("data-leaf"), a.level = e.parent().attr("data-index"), a.spread = "open" == e.find("i[data-spread]").eq(0).attr("data-spread"), a.basicData = e.attr("data-basic"), a.recordData = e.attr("data-record"), a.dataType = t.attr("data-type"), a.ischecked = t.attr("data-checked"), a.initchecked = t.attr("data-initchecked"), a
    }, ae.prototype.changeCheckbarNodes = function () {
        var t = !1;
        return this.obj.find("i[data-par]").each(function () {
            var e = f(this);
            if ($div = e.closest("." + k), e.attr("data-checked") != e.attr("data-initchecked")) return t = !0
        }), t
    }, ae.prototype.loadIframe = function (e, t) {
        var a = e.find("cite[data-leaf]").eq(0);
        if (!this.useIframe) return !1;
        var i = this.iframe.iframeElem, o = this.iframe.iframeUrl,
            n = "leaf" != this.iframe.iframeLoad || "leaf" == a.attr("data-leaf");
        if (n) {
            if (!(0 < f(i).length)) return b.msg("iframe绑定异常，请确认页面中是否有iframe页对应的容器", {icon: 5}), !1;
            if (!o) return b.msg("数据请求异常，iframeUrl参数未指定", {icon: 5}), !1;
            var s = te(t);
            -1 < o.indexOf("?") && (s = "&" + s.substring(1, s.length));
            var r = o + s;
            f(i).attr("src", r)
        }
        return n
    }, ae.prototype.getIframeRequestParam = function (e) {
        var t = this.iframe.iframeRequest, a = this.iframe.iframeDefaultRequest, i = e || this.node, o = {};
        for (var n in t) o[n] = t[n];
        for (var n in a) {
            var s = a[n], r = i[n];
            "boolean" == typeof r ? o[s] = r : r && (o[s] = r)
        }
        var d = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
        for (var n in o) if (d.test(o[n])) {
            var l = o[n];
            o[n] = encodeURI(encodeURI(l))
        }
        return o
    }, ae.prototype.getNowNodeUl = function () {
        return 0 == this.obj.find("div[data-id]").parent().find("." + A).length ? this.obj : this.obj.find("div[data-id]").parent().find("." + A).next("ul")
    }, ae.prototype.getNowNode = function () {
        return 0 == this.obj.find("div[data-id]").parent().find("." + A).length ? this.obj.children("li").eq(0).children("div").eq(0) : this.obj.find("div[data-id]").parent().find("." + A)
    }, ae.prototype.setNodeParam = function (e) {
        var t = this;
        if (t.node.nodeId = e.attr("data-id"), t.node.parentId = e.parent().attr("data-pid"), t.node.context = e.find("cite[data-leaf]").eq(0).text(), t.node.isLeaf = "leaf" == e.find("cite[data-leaf]").eq(0).attr("data-leaf"), t.node.level = e.parent().attr("data-index"), t.node.spread = "open" == e.find("i[data-spread]").eq(0).attr("data-spread"), t.node.basicData = e.attr("data-basic"), t.node.recordData = e.attr("data-record"), e.find("i[data-par]")) {
            var a = "", i = "", o = "";
            e.find("i[data-par]").each(function () {
                a += f(this).attr("data-type") + ",", i += f(this).attr("data-checked") + ",", o += f(this).attr("data-initchecked") + ","
            }), a = a.substring(0, a.length - 1), i = i.substring(0, i.length - 1), o = o.substring(0, o.length - 1), t.node.dataType = a, t.node.ischecked = i, t.node.initchecked = o
        }
    }, ae.prototype.getNodeParam = function (e) {
        return e ? this.setNodeParam(e) : 0 == this.obj.find("div[data-id]").parent().find("." + A).length && this.initNodeParam(), this.node
    }, ae.prototype.getTempNodeParam = function (e) {
        var t = {};
        if (t.nodeId = e.attr("data-id"), t.parentId = e.parent().attr("data-pid"), t.context = e.find("cite[data-leaf]").eq(0).text(), t.isLeaf = "leaf" == e.find("cite[data-leaf]").eq(0).attr("data-leaf"), t.level = e.parent().attr("data-index"), t.spread = "open" == e.find("i[data-spread]").eq(0).attr("data-spread"), t.basicData = e.attr("data-basic"), t.recordData = e.attr("data-record"), e.find("i[data-par]")) {
            var a = "", i = "", o = "";
            e.find("i[data-par]").each(function () {
                a += f(this).attr("data-type") + ",", i += f(this).attr("data-checked") + ",", o += f(this).attr("data-initchecked") + ","
            }), a = a.substring(0, a.length - 1), i = i.substring(0, i.length - 1), o = o.substring(0, o.length - 1), t.dataType = a, t.ischecked = i, t.initchecked = o
        }
        return t
    }, ae.prototype.initNodeParam = function () {
        var e = this;
        e.node.nodeId = "", e.node.parentId = "", e.node.context = "", e.node.isLeaf = "", e.node.level = "", e.node.spread = "", e.node.dataType = "", e.node.ischecked = "", e.node.initchecked = "", e.node.basicData = ""
    }, ae.prototype.getRequestParam = function (e) {
        var t = this.request, a = this.defaultRequest, i = e || this.node, o = {};
        for (var n in t) o[n] = t[n];
        for (var n in a) {
            var s = a[n], r = i[n];
            "boolean" == typeof r ? o[s] = r : r && (o[s] = r)
        }
        return o
    }, ae.prototype.getFilterRequestParam = function (e) {
        var t = this.filterRequest;
        return V.cloneObj(e, t)
    }, ae.prototype.getNowParam = function () {
        return this.getRequestParam(this.getNodeParam())
    }, ae.prototype.getParentParam = function (e) {
        var t = this.obj.find("div[data-id='" + e + "']");
        return 0 < t.length ? this.callbackData().parentNode(t) : {}
    }, ae.prototype.getChildParam = function (e) {
        var t = this.obj.find("div[data-id='" + e + "']");
        return 0 < t.length ? this.callbackData().childNode(t) : []
    }, ae.prototype.callbackData = function () {
        var i = this;
        return {
            dom: function (e) {
                return e
            }, node: function (e) {
                return i.getRequestParam(e)
            }, childNode: function (e) {
                var t = e.next("ul").find("li." + v + " div." + k), a = [];
                return t && 0 < t.length && t.each(function () {
                    var e = f(this);
                    a.push(i.getRequestParam(i.getTempNodeParam(e)))
                }), a
            }, parentNode: function (e) {
                var t = e.parent().attr("data-pid"), a = i.obj.find("div[data-id='" + t + "']");
                return 0 < a.length ? i.getRequestParam(i.getTempNodeParam(a)) : {}
            }
        }
    }, ae.prototype.bindBrowserEvent = function () {
        var u = this;
        u.obj.on("click", "i[data-spread]", function (e) {
            e.stopPropagation();
            var t = f(this), a = t.parent("div"), i = (a.find("cite"), u.getNodeParam(a));
            a.next("ul"), a.parent("li[data-index]").parent("ul");
            u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), u.obj.find("div[data-id]").parent().find("." + A).removeClass(A), u.obj.find("div[data-id]").parent().find("." + u.style.itemThis).removeClass(u.style.itemThis), a.addClass(A), a.addClass(u.style.itemThis), u.clickSpread(a), layui.event.call(this, F, "changeTree(" + f(u.obj)[0].id + ")", {
                param: u.callbackData().node(i),
                dom: u.callbackData().dom(t),
                show: "open" == u.callbackData().dom(t).attr("data-spread")
            })
        }), u.obj.on("click", "div[dtree-click='" + U + "']", function (e) {
            e.stopPropagation();
            var t = f(this), a = (t.find("cite"), u.getNodeParam(t));
            t.next("ul"), t.parent("li[data-index]").parent("ul");
            if (u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), u.obj.find("div[data-id]").parent().find("." + A).removeClass(A), u.obj.find("div[data-id]").parent().find("." + u.style.itemThis).removeClass(u.style.itemThis), t.addClass(A), t.addClass(u.style.itemThis), u.useIframe) {
                var i = u.getFilterRequestParam(u.getIframeRequestParam(a));
                u.loadIframe(t, i) && (u.iframeFun.iframeDone(i), layui.event.call(this, F, "iframeDone(" + f(u.obj)[0].id + ")", {
                    iframeParam: i,
                    dom: u.callbackData().dom(t)
                }))
            } else layui.event.call(this, F, "node(" + f(u.obj)[0].id + ")", {
                param: u.callbackData().node(a),
                childParams: u.callbackData().childNode(t),
                parentParam: u.callbackData().parentNode(t),
                dom: u.callbackData().dom(t)
            })
        }), u.obj.on("dblclick", "div[dtree-click='" + U + "']", function (e) {
            e.stopPropagation();
            var t = f(this), a = (t.find("cite"), u.getNodeParam(t));
            t.next("ul"), t.parent("li[data-index]").parent("ul");
            u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), u.obj.find("div[data-id]").parent().find("." + A).removeClass(A), u.obj.find("div[data-id]").parent().find("." + u.style.itemThis).removeClass(u.style.itemThis), t.addClass(A), t.addClass(u.style.itemThis), layui.event.call(this, F, "nodedblclick(" + f(u.obj)[0].id + ")", {
                param: u.callbackData().node(a),
                childParams: u.callbackData().childNode(t),
                parentParam: u.callbackData().parentNode(t),
                dom: u.callbackData().dom(t)
            })
        }), u.obj.on("contextmenu", "div[dtree-click='" + U + "'][d-contextmenu]", function (e) {
            var t = f(this), a = u.getNodeParam(t), i = t.attr("d-contextmenu");
            if (u.toolbar) {
                var o = u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id);
                o.find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), u.setToolbarDom(u.toolbarFun.loadToolbarBefore(V.cloneObj(u.toolbarMenu), u.getRequestParam(a), t));
                var n = (e = e || window.event).pageX - t.offset().left + 45,
                    s = t.offset().top - u.obj.closest(u.toolbarScroll).offset().top + 15;
                "true" == i && (u.obj.find("div[data-id]").parent().find("." + A).removeClass(A), u.obj.find("div[data-id]").parent().find("." + u.style.itemThis).removeClass(u.style.itemThis), t.addClass(A), t.addClass(u.style.itemThis), o.find(".layui-nav-child").addClass("layui-anim-fadein layui-show"), o.css({
                    left: n + "px",
                    top: s + "px"
                }))
            }
            return e.stopPropagation(), !1
        }), u.obj.closest(u.toolbarScroll).scroll(function () {
            u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show")
        }), u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).on("click", "a[dtree-tool]", function (e) {
            e.stopPropagation();
            var l = u.getNowNode(), c = u.getNodeParam(l), h = l.next("ul"), p = l.parent("li[data-index]"),
                a = p.parent("ul").prev("div"), i = l.children("cite"), t = i.html();
            switch (u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), f(this).attr("dtree-tool")) {
                case J:
                    var o = u.loadToolBar(t, J);
                    b.open({
                        title: "新增" + u.toolbarStyle.title,
                        type: 1,
                        area: u.toolbarStyle.area,
                        content: o,
                        success: function (e, d) {
                            m.render(), m.on("submit(dtree_addNode_form)", function (e) {
                                e = e.field;
                                var t = l.attr("data-id"), a = l.attr("data-id") + "_node_" + h[0].childNodes.length,
                                    i = parseInt(p.attr("data-index")) + 1, o = [];
                                if (0 < u.checkArrLen) for (var n = 0; n < u.checkArrLen; n++) o.push({
                                    type: n,
                                    isChecked: "0"
                                });
                                h.append(u.getLiItemDom(a, t, e.addNodeName, !0, "", o, i, !1, !1, "", "", "item")), h.find("li[data-id='" + a + "']").hide();
                                var s = h.find("div[data-id='" + a + "']");
                                c = u.getNodeParam(s);
                                var r = u.getRequestParam(c);
                                return r = f.extend(r, e), u.temp = [a, h, l, i], u.toolbarFun.addTreeNode(r, l), b.close(d), !1
                            })
                        }
                    });
                    break;
                case z:
                    o = u.loadToolBar(t, z);
                    b.open({
                        title: "编辑" + u.toolbarStyle.title,
                        type: 1,
                        area: u.toolbarStyle.area,
                        content: o,
                        success: function (e, a) {
                            u.toolbarFun.editTreeLoad(u.getRequestParam(c)), m.render(), m.on("submit(dtree_editNode_form)", function (e) {
                                e = e.field;
                                i.html(e.editNodeName), c = u.getNodeParam(l);
                                var t = u.getRequestParam(c);
                                t = f.extend(t, e), u.temp = [i, l], u.toolbarFun.editTreeNode(t, l), b.close(a)
                            })
                        }
                    });
                    break;
                case G:
                    b.confirm("确定要删除该" + u.toolbarStyle.title + "？", {
                        icon: 3,
                        title: "删除" + u.toolbarStyle.title
                    }, function (e) {
                        var t = u.getNodeParam(l);
                        u.temp = [p, a], u.toolbarFun.delTreeNode(u.getRequestParam(t), l), b.close(e)
                    });
                    break;
                default:
                    var n = f(this).attr("dtree-tool");
                    if (0 < u.toolbarExt.length) for (var s = 0; s < u.toolbarExt.length; s++) {
                        var r = u.toolbarExt[s];
                        if (n == r.toolbarId) {
                            r.handler(u.getRequestParam(u.getNodeParam(l), l));
                            break
                        }
                    }
            }
        }), u.obj.prevAll("div#dtree_menubar_" + u.obj[0].id).on("click", "button[d-menu]", function (e) {
            e.stopPropagation(), u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), u.menubarListener(f(this).attr("d-menu"), "group")
        }), u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).on("click", "a[d-menu]", function (e) {
            e.stopPropagation(), u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), u.menubarListener(f(this).attr("d-menu"), "toolbar")
        }), u.obj.closest("body").find("*[dtree-id='" + u.obj[0].id + "'][dtree-menu]").on("click", function (e) {
            e.stopPropagation(), u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show"), u.menubarListener(f(this).attr("dtree-menu"), "freedom")
        }), u.obj.on("click", "i[dtree-click='" + E + "']", function (e) {
            u.obj.prevAll("div#dtree_toolbar_" + u.obj[0].id).find(".layui-nav-child").removeClass("layui-anim-fadein layui-show");
            var t = f(this), a = t.closest("div[dtree-click='" + U + "']"), i = u.getNodeParam(a),
                o = u.checkbarFun.chooseBefore(t, u.getRequestParam(i));
            u.temp = [t], o && u.changeCheck(), e.stopPropagation()
        })
    }, _.on("click", function (e) {
        f("div." + t).find(".layui-show").removeClass("layui-anim-fadein layui-show")
    }), ae.prototype.unbindBrowserEvent = function () {
        var e = this;
        e.obj.unbind(), e.menubar && (e.obj.prevAll("div#dtree_menubar_" + e.obj[0].id).unbind(), 0 < e.obj.closest("body").find("*[dtree-id='" + e.obj[0].id + "'][dtree-menu]").length && e.obj.closest("body").find("*[dtree-id='" + e.obj[0].id + "'][dtree-menu]").unbind()), e.toolbar && (e.obj.prevAll("div#dtree_toolbar_" + e.obj[0].id).unbind(), 0 < e.obj.closest(e.toolbarScroll).length && e.obj.closest(e.toolbarScroll).unbind())
    }, e("dtree", {
        render: function (e) {
            var t = null, a = V.getElemId(e);
            return "" == a ? b.msg("页面中未找到绑定id", {icon: 5}) : ("object" == typeof(t = w[a]) ? (t.reloadSetting(e), t.initTreePlus(), t.openTreePlus(), t.init(), t.unbindBrowserEvent()) : (t = new ae(e), (w[a] = t).initTreePlus(), t.openTreePlus(), t.init()), t.bindBrowserEvent()), t
        }, reload: function (e, t) {
            "string" == typeof e && (e = w[e]), void 0 !== e ? (e.reloadSetting(t), e.initTreePlus(), e.openTreePlus(), e.init(), e.unbindBrowserEvent(), e.bindBrowserEvent()) : b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, on: function (e, t) {
            return 0 < e.indexOf("'") && (e = e.replace(/'/g, "")), 0 < e.indexOf('"') && (e = e.replace(/"/g, "")), layui.onevent.call(this, F, e, t)
        }, getNowParam: function (e) {
            if ("string" == typeof e && (e = w[e]), void 0 !== e) return e.getNowParam();
            b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, getParentParam: function (e, t) {
            if ("string" == typeof e && (e = w[e]), void 0 !== e) return e.getParentParam(t);
            b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, getChildParam: function (e, t) {
            if ("string" == typeof e && (e = w[e]), void 0 !== e) return e.getChildParam(t);
            b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, getCheckbarNodesParam: function (e) {
            return "string" == typeof e && (e = w[e]), void 0 === e ? (b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2}), {}) : e.getCheckbarNodesParam()
        }, dataInit: function (e, t) {
            if ("string" == typeof e && (e = w[e]), void 0 !== e) return t ? e.dataInit(t) : void 0;
            b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, chooseDataInit: function (e, t) {
            if ("string" == typeof e && (e = w[e]), void 0 !== e) return t ? e.chooseDataInit(t) : void 0;
            b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, changeCheckbarNodes: function (e) {
            if ("string" == typeof e && (e = w[e]), void 0 !== e) return e.changeCheckbarNodes();
            b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, refreshTree: function (e) {
            "string" == typeof e && (e = w[e]), void 0 !== e || b.msg("方法获取失败，请检查ID或对象传递是否正确", {icon: 2})
        }, escape: function (e) {
            return V.escape(e)
        }, unescape: function (e) {
            return V.unescape(e)
        }, version: function () {
            return "v2.4.5_finally_beta"
        }
    })
});