(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-42cdb725"],{"1cd7":function(e,t,a){e.exports=a.p+"img/8.3bacd4b6.png"},"29a1":function(e,t,a){e.exports=a.p+"img/9.04a31ab0.png"},4182:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"totalContent"},[i("el-breadcrumb",{attrs:{"separator-class":"el-icon-arrow-right"}},[i("el-breadcrumb-item",{attrs:{to:{path:"/home"}}},[e._v("首页")]),i("el-breadcrumb-item",[e._v("量子密钥统计")])],1),i("el-card",{staticStyle:{height:"calc(100% - 28px)"}},[i("div",{staticClass:"total-header"},[i("div",{staticClass:"header-wrap"},[i("img",{attrs:{src:a("b84a"),alt:""}}),i("ul",[i("li",[e._v(e._s(e.deviceNum))]),i("li",[e._v("设备总量")])])]),i("div",{staticClass:"header-wrap"},[i("img",{attrs:{src:a("29a1"),alt:""}}),i("ul",[i("li",[e._v(e._s(e.onlineNum))]),i("li",[e._v("在线数量")])])]),i("div",{staticClass:"header-wrap"},[i("img",{attrs:{src:a("1cd7"),alt:""}}),i("ul",[i("li",[e._v(e._s(e.offlineNum))]),i("li",[e._v("离线数量")])])])]),i("div",{staticClass:"total-center"},[i("div",{staticClass:"pie2"}),i("div",{staticClass:"pie"}),i("div",{staticClass:"barRight1"})])])],1)},n=[],r=(a("99af"),a("a15b"),a("d81d"),a("fb6a"),a("a9e3"),a("b680"),a("96cf"),a("1da1")),o=a("313e"),s={data:function(){return{timer:null,deviceNum:"",onlineNum:"",offlineNum:"",keyUseNum:"",total:0,userList:[],queryInfo:{pagenum:1,pagesize:4}}},beforeRouteEnter:function(e,t,a){var i=window.sessionStorage.getItem("accountTypeLogin");if("9"!==i)return a("/404");a()},created:function(){this.getTotalInfo()},methods:{getTotalInfo:function(){var e=this;return Object(r["a"])(regeneratorRuntime.mark((function t(){var a,i;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("status/getDeviceStatusInfo?ts=".concat((new Date).getTime()));case 2:if(a=t.sent,i=a.data,0===i.code){t.next=6;break}return t.abrupt("return",e.$message.error("获取数据信息失败！"));case 6:e.deviceNum=i.data.deviceStatusInfo.deviceNum,e.onlineNum=i.data.deviceStatusInfo.onlineNum,e.offlineNum=i.data.deviceStatusInfo.offlineNum,e.keyUseNum=i.data.deviceStatusInfo.keyUseNum,e.userList=i.data.logs,e.barRight1(i.data),e.chartNum(i.data),e.pieTotal(i.data);case 14:case"end":return t.stop()}}),t)})))()},jishi:function(){var e=this;this.timer=setInterval((function(){e.getTotalInfo()}),5e3)},barRight1:function(e){var t=o["a"](document.querySelector(".barRight1"));null==t&&(t=o["b"](document.querySelector(".barRight1"))),t.setOption({title:[{text:"加解密数据统计",left:"50%",top:"85%",textAlign:"center"}],color:["#2f89cf"],tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},grid:{left:"0%",top:"10px",right:"0%",bottom:"22%",containLabel:!0},xAxis:{type:"category",data:["加密数量","解密数量","加解密数据总量"],axisTick:{alignWithLabel:!0},axisLabel:{color:"#909399",fontSize:"12"},axisLine:{show:!0}},yAxis:{type:"value",axisLabel:{color:"#909399",fontSize:12,formatter:function(e,t){return e>=1e4&&e<1e7?e=e/1e4+"万":e>=1e7&&(e=e/1e7+"千万"),e}},axisLine:{show:!0,lineStyle:{color:"#909399",width:1}},splitLine:{lineStyle:{color:"rgba(255,255,255,.1)"}}},series:[{name:"合计",type:"bar",barWidth:"25%",data:[e.deviceStatusInfo.encDataNum,e.deviceStatusInfo.decDataNum,e.deviceStatusInfo.encDataNum+e.deviceStatusInfo.decDataNum],itemStyle:{borderRadius:5}}]}),window.addEventListener("resize",(function(){t.resize()}))},chartNum:function(e){var t=o["a"](document.querySelector(".pie"));null==t&&(t=o["b"](document.querySelector(".pie")));var a=e.deviceStatusInfo.keyGenNum,i=e.deviceStatusInfo.keyUseNum;t.setOption({title:{text:"密钥总量:".concat(a,"     密钥使用量:").concat(i),left:"45%",top:"85%",textAlign:"center",textStyle:{overflow:"break",width:290}},series:[{type:"gauge",radius:"50%",startAngle:90,endAngle:-270,center:["50%","50%"],itemStyle:{color:"#409EFF"},pointer:{show:!1},progress:{show:!0,overlap:!1,roundCap:!0,clip:!1,itemStyle:{borderWidth:1,borderColor:"#409EFF"}},axisLine:{lineStyle:{width:15}},splitLine:{show:!1,distance:0,length:10},axisTick:{show:!1},axisLabel:{show:!1,distance:50},data:[{value:(i/a*100).toFixed(2)}],detail:{valueAnimation:!0,fontSize:18,color:"auto",formatter:"{value}%",offsetCenter:["0%","0%"]}}]}),window.addEventListener("resize",(function(){t.resize()}))},pieTotal:function(e){var t=o["a"](document.querySelector(".pie2"));null==t&&(t=o["b"](document.querySelector(".pie2")));var a=e.deviceStatusInfo.keyGenNum,i=e.deviceStatusInfo.offlineKeyNum,n=e.deviceStatusInfo.onlineKeyNum;t.setOption({title:[{text:"密钥数据统计",left:"50%",top:"85%",textAlign:"center"}],tooltip:{trigger:"item"},legend:{orient:"vertical",left:"left",show:!1},series:[{type:"pie",radius:"50%",center:["50%","50%"],data:[{value:a,name:"密钥总量"},{value:i,name:"离线充注密钥"},{value:n,name:"在线密钥总量"}],emphasis:{itemStyle:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]}),window.addEventListener("resize",(function(){t.resize()}))},barLeft1:function(e){var t=o["a"](document.querySelector(".barLeft1"));null==t&&(t=o["b"](document.querySelector(".barLeft1")));var a=e.deviceStatusInfo.keyGenNum+"",i=a.length,n=a.slice(0,1),r=Number(n)+1+Array(i).join(0);r<100&&(r=100);var s=e.deviceStatusInfo.everyDayKeyNum,l=s.map((function(e){return e.timeInfo})),c=s.map((function(e){return e.keyNum}));t.setOption({title:{text:"近期密钥使用量",left:"center"},xAxis:{type:"category",nameLocation:"center",data:l},yAxis:{type:"value",min:0,max:r,minInterval:1,axisLine:{show:!0,lineStyle:{color:"#909399",width:1}}},series:[{data:c,type:"line",smooth:!0,itemStyle:{normal:{label:{show:!0}}}}]}),window.addEventListener("resize",(function(){t.resize()}))}},beforeDestroy:function(){clearInterval(this.timer)}},l=s,c=(a("491b"),a("2877")),u=Object(c["a"])(l,i,n,!1,null,"01d737a0",null);t["default"]=u.exports},"491b":function(e,t,a){"use strict";var i=a("94d0"),n=a.n(i);n.a},"94d0":function(e,t,a){},b84a:function(e,t,a){e.exports=a.p+"img/10.33073272.png"}}]);
//# sourceMappingURL=chunk-42cdb725.609727d5.js.map