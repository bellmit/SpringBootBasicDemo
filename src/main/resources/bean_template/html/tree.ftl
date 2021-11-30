<template>
  <a-card :bordered="false">
    
     <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
            <#list col_List as po>
                <#if po.isQuery == 'yes'> <#-- 如果这一列需要搜索 -->
                    <#if po.bean_type == 'String'> <#-- 如果这一列是字符串 -->
                        <a-col :md="6" :sm="8">
                            <a-form-item :label="$t('${bean_name}.${po.filed}')">
                                <a-input v-model="pageInfo.condition.${po.filed}"></a-input>
                            </a-form-item>
                        </a-col>
                    <#elseif po.bean_type == 'Date'>
                        <a-col :md="6" :sm="8">
	                        <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="$t('please.enter.startTime')" class="query-group-cust" v-model="pageInfo.condition.startTime"></j-date>
	                        <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="$t('please.enter.endTime')" class="query-group-cust" v-model="pageInfo.condition.endTime"></j-date>
                    	</a-col>
                    </#if>
                    
                </#if>
            </#list>
            <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="loadData" icon="search">$t('query')</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">$t('reset')</a-button>

            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button v-if= "showAdd()" @click="handleAdd" type="primary" icon="plus">$t('add')</a-button>
      <a-button v-if= "showExport()" type="primary" icon="download" @click="handleExportXls">$t('export.excel')</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> $t('already.choose') <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>$t(item)
        <a style="margin-left: 24px" @click="onClearSelected">$t('clear.all')</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pageInfo"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        @change="handleTableChange"
        @expand="handleExpand"
        v-bind="tableProps">
         <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-if= "showEdit(record)" >$t('edit')</a>
          <a-divider type="vertical" v-if= "showChildAdd(record)" />
          <a @click="handleAddChild(record)" v-if= "showChildAdd(record)" >$t('add.child')</a>
          <a-divider type="vertical" v-if= "showDelete(record)"/>
          <a-popconfirm v-if= "showDelete(record)" :title="$t('confirm.delete')" @confirm="() => handleDeleteNode(record.id)" placement="topLeft">
                  <a>$t(delete)</a>
          </a-popconfirm>


        </span>

      </a-table>
    </div>

    <${mid_score_name}-modal ref="modalForm" @ok="modalFormOk"></${mid_score_name}-modal>
  </a-card>
</template>

<script>

  import ${bean_name}Modal from './modules/${bean_name}Modal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JEllipsis from "@/components/jeecg/JEllipsis";
  import API   from '@/api/${bean_name}'
 

  export default {
    name: "${bean_name}List",
    mixins:[JeecgListMixin],
    components: {
      JEllipsis,
      ${bean_name}Modal
    },
    data () {
      return {
        description: '${bean_name}管理页面',
        orderByClause:'${orderByClause}',
        // 表头
        columns: [
            <#list col_List as po>
            {
                title: this.$t('${bean_name}.${po.filed}'),
                align: "center",
                dataIndex: '${po.filed}'
            },
            </#list>
            {
              title: this.$t('common.operation'),
              dataIndex: 'action',
              fixed: 'right',
              scopedSlots: {customRender: 'action'},
            }
        ],
        expandedRowKeys:[],//哪些被展开了
        hasChildrenField:"hasChild",
        pidField:"parentId",
        dictOptions: {},
        loadParent: false,
        superFieldList:[],
      }
    },
    created() {
      this.pageInfo.condition.orderByClause=this.orderByClause
      this.pageInfo.condition.level=1
      if(this.$store.getters.userInfo.id !="1"){
      	this.pageInfo.condition.delFlag=0
      }
      
      let config = this.$store.getters.userInfo.sysDept.config.${model_name}ListFiled
     config=config ==undefined ?0:config
     for(let i=0 ;i<30;i++){
          let j=(1<<i);
          if((config & j)!=0){  //为真说明这个字段需要删除
            let filedname= API.filedList[i];   
            this.columns.forEach((v,index) => {
              if(v.dataIndex==filedname){
                this.columns.splice(index,1) 
              }
            });
          }
      }
      
      this.loadData();
    },
    computed: {
      tableProps() {
        let _this = this
        return {
          // 列表项是否可选择
          rowSelection: {
            selectedRowKeys: _this.selectedRowKeys,
            onChange: (selectedRowKeys) => _this.selectedRowKeys = selectedRowKeys
          }
        }
      }
    },
    methods: {
      loadData(){
        this.loading = true;
        this.pageInfo.condition.level=1
        //判断条件
        let conditionCount=0;
        Object.keys(this.pageInfo.condition).forEach( key => {
          if(this.pageInfo.condition[key] && this.pageInfo.condition[key] != ''){
            conditionCount++
          }
        })
        if(conditionCount>2){//大于2个条件
          //this.pageInfo.condition.level=null
          delete this.pageInfo.condition.level;
        }
        API.findByPage(this.pageInfo).then(
          (res) => {
            this.dataSource = res.list;
            this.pageInfo.total=res.total;
            this.loading = false;
            this.loadDataByExpandedRows(this.dataSource)//加载子级
          }
        );
      },
       handleExpand(expanded, record) {
        if (expanded) {
          this.expandedRowKeys.push(record.id)
          let a=[];
          a.push(record)
          this.loadDataByExpandedRows(a)
        } else {
          let keyIndex = this.expandedRowKeys.indexOf(record.id)
          if (keyIndex >= 0) {
            this.expandedRowKeys.splice(keyIndex, 1);
          }
        }
      },
      fillChild(dataList,childrenMap){//注意这里是递归 递归的时候判断条件
        if(dataList && dataList.length>0){
                    dataList.forEach(data => {
                    if (this.expandedRowKeys.includes(data.id)) {
                      data.children = childrenMap.get(data.id)
                      this.fillChild(data.children,childrenMap)
                    }
          })
        }
      },
      // 根据已展开的行查询数据（用于保存后刷新时异步加载子级的数据）
      loadDataByExpandedRows(dataList) {
        if (this.expandedRowKeys.length > 0) {//如果有被展开的节点

          API.getChildListBatch(this.expandedRowKeys).then(
            res=>{
              let records = res.list
              let listMap = new Map();
              for (let item of records) {
                let pid = item[this.pidField];
                if (this.expandedRowKeys.includes(pid)) {
                  let mapList = listMap.get(pid);
                  if (mapList == null) {
                    mapList = [];
                  }
                  mapList.push(item);
                  listMap.set(pid, mapList);
                }
              }
              this.fillChild(dataList,listMap);
            }
          )
        } 
      },
      getDataByResult(result){
        if(result){
          return result.map(item=>{
            //判断是否标记了带有子节点
            if(item[this.hasChildrenField]=='1'){
              let loadChild = { id: item.id+'_loadChild', name: 'loading...', isLoading: true }
              item.children = [loadChild]
            }
            return item
          })
        }
      },
      handleAdd(){
        this.loadParent = true
        let obj = Object.assign({}, API.defaultObject)
        obj.parentId = '0'
        obj.parentName='root'
        this.$refs.modalForm.add(obj);
      },
      handleAddChild(record){
        this.loadParent = true
        let obj = {}
        obj.parentId  = record.id
        obj.parentName=record.${name_filed}
        this.$refs.modalForm.add(obj);
      },
      handleEdit: async function (record) { //
        if(record.parentId == '0'){
          record.parentName='root'
        }else{
          //前端自己找 一般都能找到因为要选择子菜单必须先打开父菜单
          let parent =API.selectNodeFromList(this.dataSource,record.parentId) 
          //让后端找 方法前面加个 async省的后面加then
          //let parent = await API.findById(record.parentId)
          //console.log(parent,"dept")
          if(parent!=null){
              record.parentName=parent.${name_filed}
          }
        }
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "$t(form.edit)";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleDeleteNode(id) {
        API.deleteById(id).then(
          (res) => {
            if (res.code === 0) {
              this.$message.success(res.msg);
            } else {
              this.$message.warning(res.errors);
            }
            this.loadData()
          }
        );
      },
      batchDel(){
       
      },
      getSuperFieldList(){
        
      },
      showAdd(item){
         return  this.$store.getters.userInfo.permissions.includes('${model_name}:add')
      },
    showAddChild(item){
        return  this.$store.getters.userInfo.permissions.includes('${model_name}:add')
    },
      showExport(){
         return  this.$store.getters.userInfo.permissions.includes('meterType:export')
      },
      showEdit(item){
         return  this.$store.getters.userInfo.permissions.includes('${model_name}:update')
      },
      showDelete(item){
         return  !item.hasChild&&this.$store.getters.userInfo.permissions.includes('${model_name}:delete')
      },
      hasPermission(permission){
        return  this.$store.getters.userInfo.permissions.includes(permission)
      },
      showRealDel(){
        return  this.$store.getters.userInfo.id == '1'
      },
      getConfig(){
        return  this.$store.getters.userInfo.sysDept.config
      },
    showFiled(filedName){
        let config = this.$store.getters.userInfo.sysDept.config.meterTypeAddFiled
        config=config ==undefined ?0:config
        let indexOfFiled=API.filedList.indexOf(filedName)
        let flag=1<<indexOfFiled;
        return !(config&flag)
    }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>