(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e9c30fac"],{"25eb4":function(e,t,a){"use strict";var r=a("7685"),n=a.n(r);n.a},4339:function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"appMuser"},[a("el-breadcrumb",{attrs:{"separator-class":"el-icon-arrow-right"}},[a("el-breadcrumb-item",{attrs:{to:{path:"/home"}}},[e._v("首页")]),a("el-breadcrumb-item",[e._v("应用管理")])],1),a("el-card",[a("el-table",{attrs:{data:e.userlist,stripe:"","row-key":e.getRowKeys,"expand-row-keys":e.expands},on:{"expand-change":e.expandChange}},[a("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left",inline:""}},[a("el-form-item",{attrs:{label:"appKey:"}},[a("span",[e._v(e._s(""===t.row.appKey||null==t.row.appKey?"-":t.row.appKey))])])],1),a("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left",inline:""}},[a("el-form-item",{attrs:{label:"appSecret:"}},[a("span",[e._v(e._s(""===t.row.appSecret||null==t.row.appSecret?"-":t.row.appSecret))])])],1)]}}])}),a("el-table-column",{attrs:{type:"index",label:"序号"}}),a("el-table-column",{attrs:{label:"应用名称",prop:"appName","show-overflow-tooltip":!0}}),a("el-table-column",{attrs:{label:"应用类型",prop:"appType"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.appType?a("span",[e._v("专用应用")]):2===t.row.appType?a("span",[e._v("通用应用")]):e._e()]}}])}),a("el-table-column",{attrs:{label:"备注",prop:"comments","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(""===t.row.comments||null==t.row.comments?"-":t.row.comments))])]}}])}),a("el-table-column",{attrs:{label:"创建时间",prop:"createTime","show-overflow-tooltip":!0}}),a("el-table-column",{attrs:{label:"操作",width:"330px"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"success",size:"mini"},on:{click:function(a){return e.goDeviceDetail(t.row.appId,t.row.appType)}}},[e._v("终端列表 ")]),a("el-button",{attrs:{type:"warning",size:"mini"},on:{click:function(a){return e.goDeviceTotal(t.row.appId)}}},[e._v("量子密钥统计")]),1===t.row.appType?a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(a){return e.goDeviceConfig(t.row.appId)}}},[e._v("配置 ")]):e._e()]}}])})],1),a("el-pagination",{attrs:{"current-page":e.queryInfo.pagenum,"page-sizes":[5,10,15,20],"page-size":e.queryInfo.pagesize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)},n=[],o=(a("99af"),a("96cf"),a("1da1")),s={data:function(){return{userId:null,isShow:"",userlist:[],queryInfo:{pagenum:1,pagesize:10},total:0,expands:[],getRowKeys:function(e){return e.appId},addDialogVisible:!1,addForm:{loginName:"",appType:null,comments:""},addFormRules:{loginName:[{required:!0,message:"请输入应用名",trigger:"blur"},{min:2,max:16,message:"长度为2-16位",trigger:"blur"}],appType:[{required:!0,message:"请选择应用类型",trigger:"change"}],comments:[{min:0,max:200,message:"长度在200个字符以内",trigger:"blur"}]}}},beforeRouteEnter:function(e,t,a){var r=window.sessionStorage.getItem("accountTypeLogin");if("9"!==r&&"1"!==r)return a("/404");a()},created:function(){this.userId=window.sessionStorage.getItem("userId"),this.getUserList()},methods:{getUserList:function(){var e=this;return Object(o["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("userApp/getManagerApps/".concat(e.queryInfo.pagenum,"/").concat(e.queryInfo.pagesize,"?ts=").concat((new Date).getTime()),{params:{userId:e.userId}});case 2:if(a=t.sent,r=a.data,0===r.code){t.next=6;break}return t.abrupt("return",e.$message.error("获取用户信息失败！"));case 6:e.userlist=r.data.list,e.total=r.data.total;case 8:case"end":return t.stop()}}),t)})))()},handleSizeChange:function(e){this.queryInfo.pagesize=e,this.getUserList()},handleCurrentChange:function(e){this.queryInfo.pagenum=e,this.getUserList()},expandChange:function(e,t){t.length?(this.expands=[],e&&this.expands.push(e.appId)):this.expands=[]},goDeviceDetail:function(e,t){this.$router.push({path:"/pwsp",query:{id:e,type:t,urlType:2}})},goDeviceConfig:function(e){this.$router.push({path:"/qems",query:{id:e,urlType:2}})},goDeviceTotal:function(e){this.$router.push({path:"/total",query:{id:e,urlType:2}})}}},i=s,p=(a("25eb4"),a("2877")),l=Object(p["a"])(i,r,n,!1,null,"4070f373",null);t["default"]=l.exports},7685:function(e,t,a){}}]);
//# sourceMappingURL=chunk-e9c30fac.35409501.js.map