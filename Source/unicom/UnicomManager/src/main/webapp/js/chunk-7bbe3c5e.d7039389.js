(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7bbe3c5e"],{"159b":function(e,t,r){var n=r("da84"),a=r("fdbc"),o=r("17c2"),u=r("9112");for(var s in a){var c=n[s],i=c&&c.prototype;if(i&&i.forEach!==o)try{u(i,"forEach",o)}catch(d){i.forEach=o}}},"16e9":function(e,t,r){},"1dde":function(e,t,r){var n=r("d039"),a=r("b622"),o=r("2d00"),u=a("species");e.exports=function(e){return o>=51||!n((function(){var t=[],r=t.constructor={};return r[u]=function(){return{foo:1}},1!==t[e](Boolean).foo}))}},"99af":function(e,t,r){"use strict";var n=r("23e7"),a=r("d039"),o=r("e8b5"),u=r("861d"),s=r("7b0b"),c=r("50c4"),i=r("8418"),d=r("65f0"),l=r("1dde"),p=r("b622"),g=r("2d00"),f=p("isConcatSpreadable"),m=9007199254740991,h="Maximum allowed index exceeded",b=g>=51||!a((function(){var e=[];return e[f]=!1,e.concat()[0]!==e})),v=l("concat"),w=function(e){if(!u(e))return!1;var t=e[f];return void 0!==t?!!t:o(e)},x=!b||!v;n({target:"Array",proto:!0,forced:x},{concat:function(e){var t,r,n,a,o,u=s(this),l=d(u,0),p=0;for(t=-1,n=arguments.length;t<n;t++)if(o=-1===t?u:arguments[t],w(o)){if(a=c(o.length),p+a>m)throw TypeError(h);for(r=0;r<a;r++,p++)r in o&&i(l,p,o[r])}else{if(p>=m)throw TypeError(h);i(l,p++,o)}return l.length=p,l}})},b129:function(e,t,r){"use strict";var n=r("16e9"),a=r.n(n);a.a},d506:function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"deviceAuth"},[r("el-breadcrumb",{attrs:{"separator-class":"el-icon-arrow-right"}},[r("el-breadcrumb-item",{attrs:{to:{path:"/home"}}},[e._v("首页")]),r("el-breadcrumb-item",[e._v("权限管理")]),r("el-breadcrumb-item",[e._v("分组权限")])],1),r("el-card",[r("el-table",{attrs:{data:e.userList,stripe:"","row-key":e.getRowKeys,"expand-row-keys":e.expands},on:{"expand-change":e.expandChange}},[r("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.ruleItemData.length>0?r("el-row",e._l(t.row.ruleItemData,(function(n){return r("span",{key:n.apiId,staticStyle:{display:"inline-block",margin:"5px"}},[r("el-tag",{attrs:{closable:""},on:{close:function(r){return e.removeRightById(t.row,n.groupAuthId)}}},[e._v(e._s(n.apiName))])],1)})),0):r("el-row",[e._v(" 暂无配置权限 ")])]}}])}),r("el-table-column",{attrs:{type:"index",label:"序号"}}),r("el-table-column",{attrs:{label:"分组名称",prop:"groupName","show-overflow-tooltip":!0}}),r("el-table-column",{attrs:{label:"分组唯一标志",prop:"groupCode","show-overflow-tooltip":!0}}),r("el-table-column",{attrs:{label:"分组描述","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(null==t.row.groupDescribe||""==t.row.groupDescribe?"-":t.row.groupDescribe)+" ")]}}])}),r("el-table-column",{attrs:{label:"时间",prop:"updateTime"}}),r("el-table-column",{attrs:{label:"操作",width:"300px"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(r){return e.openAdd(t.row)}}},[e._v("添加权限")]),r("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(r){return e.removeAllById(t.row.groupId)}}},[e._v("删除权限")])]}}])})],1),r("el-pagination",{attrs:{"current-page":e.queryInfo.pagenum,"page-sizes":[5,10,15,20],"page-size":e.queryInfo.pagesize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),r("el-dialog",{attrs:{title:"添加权限",visible:e.addDialogVisible,width:"600px"},on:{"update:visible":function(t){e.addDialogVisible=t},close:e.addDialogClosed}},[r("el-form",{ref:"addFormRef",attrs:{model:e.addForm,rules:e.addFormRules,"label-width":"90px"}},[r("el-form-item",{attrs:{label:"选择权限:",prop:"checkedAuth"}},[r("div",{staticClass:"block"},[r("el-cascader",{staticStyle:{width:"100%"},attrs:{options:e.options,props:e.prop,"show-all-levels":!1,clearable:""},model:{value:e.addForm.checkedAuth,callback:function(t){e.$set(e.addForm,"checkedAuth",t)},expression:"addForm.checkedAuth"}})],1)])],1),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.addDialogVisible=!1}}},[e._v("取 消")]),r("el-button",{directives:[{name:"preventReClick",rawName:"v-preventReClick",value:1e3,expression:"1000"}],attrs:{type:"primary"},on:{click:e.submitAdd}},[e._v("确 定")])],1)],1)],1)},a=[],o=(r("99af"),r("4160"),r("d81d"),r("159b"),r("96cf"),r("1da1")),u={data:function(){return{getRowKeys:function(e){return e.groupId},expands:[],userList:[],queryInfo:{pagenum:1,pagesize:10,query:""},total:0,addGroupId:"",addDialogVisible:!1,checkaddCode:[],addForm:{checkedAuth:[]},addFormRules:{},options:[],prop:{multiple:!0,emitPath:!1,value:"apiId",label:"apiName",children:"apiResources"}}},beforeRouteEnter:function(e,t,r){var n=window.sessionStorage.getItem("accountTypeLogin");if("9"!==n)return r("/404");r()},created:function(){this.getUserList()},methods:{getUserList:function(){var e=this;return Object(o["a"])(regeneratorRuntime.mark((function t(){var r,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("group/groupList/".concat(e.queryInfo.pagenum,"/").concat(e.queryInfo.pagesize,"?ts=").concat((new Date).getTime()));case 2:if(r=t.sent,n=r.data,0===n.code){t.next=6;break}return t.abrupt("return",e.$message.error("获取分组信息失败！"));case 6:n.data.list.map((function(e){e.ruleItemData=[]})),e.userList=n.data.list,e.total=n.data.total;case 9:case"end":return t.stop()}}),t)})))()},handleSizeChange:function(e){this.queryInfo.pagesize=e,this.getUserList()},handleCurrentChange:function(e){this.queryInfo.pagenum=e,this.getUserList()},expandChange:function(e,t){var r=this;return Object(o["a"])(regeneratorRuntime.mark((function n(){return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:r.expands=[],t.length>0&&(e&&r.expands.push(e.groupId),r.getAuthority(e.groupId));case 2:case"end":return n.stop()}}),n)})))()},getAuthority:function(e){var t=this;return Object(o["a"])(regeneratorRuntime.mark((function r(){var n,a;return regeneratorRuntime.wrap((function(r){while(1)switch(r.prev=r.next){case 0:return r.next=2,t.$http.get("groupAuth/get?groupId=".concat(e,"&ts=").concat((new Date).getTime()));case 2:if(n=r.sent,a=n.data,0===a.code){r.next=6;break}return r.abrupt("return",t.$message.error(""!==a.msg&&null!==a.msg?a.msg:"获取权限失败！"));case 6:t.userList.forEach((function(r,n){r.groupId===e&&(t.userList[n].ruleItemData=a.data)}));case 7:case"end":return r.stop()}}),r)})))()},openAdd:function(e){var t=this;return Object(o["a"])(regeneratorRuntime.mark((function r(){var n,a,o,u;return regeneratorRuntime.wrap((function(r){while(1)switch(r.prev=r.next){case 0:return t.addGroupId=e.groupId,r.next=3,t.$http.get("groupAuth/get?groupId=".concat(e.groupId,"&ts=").concat((new Date).getTime()));case 3:if(n=r.sent,a=n.data,0===a.code){r.next=7;break}return r.abrupt("return",t.$message.error(""!==a.msg&&null!==a.msg?a.msg:"获取权限失败！"));case 7:if(o=a.data,o.length>0){for(u=0;u<o.length;u++)t.checkaddCode.push(o[u].groupId);t.addForm.checkedAuth=[].concat(t.checkaddCode)}t.addDialogVisible=!0,t.getCategory();case 11:case"end":return r.stop()}}),r)})))()},addDialogClosed:function(){this.$refs.addFormRef.clearValidate(),this.addForm.checkedAuth=[],this.checkaddCode=[]},submitAdd:function(){var e=this;if(this.addForm.checkedAuth.length<1)return this.$message.error("请选择权限！");this.$refs.addFormRef.validate(function(){var t=Object(o["a"])(regeneratorRuntime.mark((function t(r){var n,a,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(r){t.next=2;break}return t.abrupt("return");case 2:return n={apiId:e.addForm.checkedAuth,groupId:e.addGroupId},t.next=5,e.$http.post("groupAuth/add",n);case 5:if(a=t.sent,o=a.data,0===o.code){t.next=9;break}return t.abrupt("return",e.$message.error(null!==o.msg&&""!==o.msg?o.msg:"添加权限失败！"));case 9:e.$message.success("添加权限成功！"),e.addDialogVisible=!1,e.getAuthority(e.addGroupId);case 12:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}())},removeRightById:function(e,t){var r=this;return Object(o["a"])(regeneratorRuntime.mark((function n(){var a,o,u;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.next=2,r.$confirm("此操作将删除该权限, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).catch((function(e){return e}));case 2:if(a=n.sent,"confirm"===a){n.next=5;break}return n.abrupt("return");case 5:return n.next=7,r.$http.get("groupAuth/deletePart?groupAuthId=".concat(t,"&ts=").concat((new Date).getTime()));case 7:if(o=n.sent,u=o.data,0===u.code){n.next=11;break}return n.abrupt("return",r.$message.error(null!==u.msg&&""!==u.msg?u.msg:"删除权限失败！"));case 11:r.$message.success("删除权限成功！"),r.getAuthority(e.groupId);case 13:case"end":return n.stop()}}),n)})))()},removeAllById:function(e){var t=this;return Object(o["a"])(regeneratorRuntime.mark((function r(){var n,a,o;return regeneratorRuntime.wrap((function(r){while(1)switch(r.prev=r.next){case 0:return r.next=2,t.$confirm("此操作将删除该分组所有权限, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).catch((function(e){return e}));case 2:if(n=r.sent,"confirm"===n){r.next=5;break}return r.abrupt("return");case 5:return r.next=7,t.$http.get("groupAuth/deleteAll?groupId=".concat(e,"&ts=").concat((new Date).getTime()));case 7:if(a=r.sent,o=a.data,0===o.code){r.next=11;break}return r.abrupt("return",t.$message.error(null!==o.msg&&""!==o.msg?o.msg:"删除权限失败！"));case 11:t.$message.success("删除所有权限成功！"),t.getAuthority(e);case 13:case"end":return r.stop()}}),r)})))()},getCategory:function(){var e=this;return Object(o["a"])(regeneratorRuntime.mark((function t(){var r,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("resource/getResource");case 2:if(r=t.sent,n=r.data,0===n.code){t.next=6;break}return t.abrupt("return",e.$message.error("获取权限信息失败！"));case 6:e.options=n.data;case 7:case"end":return t.stop()}}),t)})))()}}},s=u,c=(r("b129"),r("2877")),i=Object(c["a"])(s,n,a,!1,null,null,null);t["default"]=i.exports},d81d:function(e,t,r){"use strict";var n=r("23e7"),a=r("b727").map,o=r("1dde"),u=r("ae40"),s=o("map"),c=u("map");n({target:"Array",proto:!0,forced:!s||!c},{map:function(e){return a(this,e,arguments.length>1?arguments[1]:void 0)}})}}]);
//# sourceMappingURL=chunk-7bbe3c5e.d7039389.js.map