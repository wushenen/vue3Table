(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-015a995e"],{"775e":function(e,t,n){"use strict";var r=n("ceae"),a=n.n(r);a.a},9989:function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-breadcrumb",{attrs:{"separator-class":"el-icon-arrow-right"}},[n("el-breadcrumb-item",{attrs:{to:{path:"/home"}}},[e._v("首页")]),n("el-breadcrumb-item",[e._v("关于")])],1),n("el-card",[n("div",[n("p",[n("span",[e._v("系统状态:")]),n("span",[e._v(e._s(e.info.systemStatus))])]),n("p",[n("span",[e._v("系统版本:")]),n("span",[e._v(e._s(e.info.systemVersion))])]),n("p",[n("span",[e._v("数据库:")]),n("span",[e._v(e._s(e.info.mysqlVersion))])])])])],1)},a=[],s=(n("96cf"),n("1da1")),o={data:function(){return{info:"",version:{}}},beforeRouteEnter:function(e,t,n){var r=window.sessionStorage.getItem("accountTypeLogin");if("1"!==r&&"9"!==r&&"2"!==r)return n("/404");n()},methods:{getInfo:function(){var e=this;return Object(s["a"])(regeneratorRuntime.mark((function t(){var n,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("system/getVersion?ts=".concat((new Date).getTime()));case 2:if(n=t.sent,r=n.data,0!==r.code){t.next=8;break}e.info=r.data,t.next=9;break;case 8:return t.abrupt("return",e.$message.error("获取状态信息失败！"));case 9:case"end":return t.stop()}}),t)})))()}},created:function(){this.getInfo()}},i=o,c=(n("775e"),n("2877")),u=Object(c["a"])(i,r,a,!1,null,"5d7e0e04",null);t["default"]=u.exports},ceae:function(e,t,n){}}]);
//# sourceMappingURL=chunk-015a995e.ac38d320.js.map