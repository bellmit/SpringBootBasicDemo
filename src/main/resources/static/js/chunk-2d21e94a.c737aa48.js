(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d21e94a"],{d5be:function(e,r,t){"use strict";t.r(r);var n=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",[t("a-timeline",{attrs:{mode:"alternate"}},e._l(e.records,(function(r,n){return t("a-timeline-item",{key:n},[t("p",[e._v("日期: "+e._s(r.date))]),t("p",[e._v("操作者: "+e._s(r.username))]),t("p",[e._v("备注："+e._s(r.remark))])])})),1)],1)},o=[],a=(t("b0c0"),t("b65a")),s=t("c0f4"),i={name:"order-detail_model",mixins:[a["a"]],props:{operateDetail:{type:Object,required:!0}},mounted:function(){this.getUserName()},data:function(){return{records:[]}},created:function(){},watch:{operateDetail:function(e,r){}},methods:{getUserName:function(){if(this.operateDetail&&this.operateDetail.processData){var e=this.operateDetail.processData.operation_records;console.log(),this.records=[];for(var r=0;r<e.length;r++)this.getNameFromServer(e[r])}},getUserRecords:function(e){var r=this;Object(s["j"])(e).then((function(e){r.records=e.result})).catch((function(e){r.showErrorMsg(e.errmsg)}))},getNameFromServer:function(e){var r=this;Object(s["k"])(e.userid).then((function(t){t?(e.username=t.result.name,r.records.push(e),r.records.sort(r.compare("date","inverted"))):r.showErrorMsg("获取数据失败!")})).catch((function(e){r.showErrorMsg(e.errmsg,e.errcode)}))},showErrorMsg:function(e,r){var t;t=r&&null!=r?"errorCode："+r+" , errorMsg："+e:e,this.$message.error(t)},compare:function(e,r){return function(t,n){var o=t[e],a=n[e];return"positive"==r?new Date(o)-new Date(a):"inverted"==r?new Date(a)-new Date(o):void 0}},fun:function(e,r,t,n){}}},c=i,u=t("2877"),d=Object(u["a"])(c,n,o,!1,null,null,null);r["default"]=d.exports}}]);
//# sourceMappingURL=chunk-2d21e94a.c737aa48.js.map