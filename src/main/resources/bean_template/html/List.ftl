<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        <#list col_List as po>
            <#if po.isQuery == 'yes'> <#-- 如果这一列需要搜索 -->
                <#if po.bean_type == 'String'> <#-- 如果这一列是字符串 -->
            <a-col :md="6" :sm="8">
                <a-form-item :label="$t('${bean_name}.${po.filed}')" >   
                	<a-input v-model="pageInfo.condition.${po.filed}"></a-input>
                </a-form-item>
            </a-col>
                
                </#if>
           
            </#if>    	
        </#list>   
         	<a-col :md="6" :sm="6">
                <a-form-item :label="$t('${bean_name}.queryTime')" >   
                 <a-range-picker :default-value="queryTimeDefaultValue"  @change="queryTimeChange"  />
                </a-form-item>
            </a-col>
          <a-col :md="6" :sm="6">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click='searchQuery' icon="search">{{$t('common.query')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('common.reset')}}</a-button>
              <download-excel  v-if="showExport()"
                class="export-excel-wrapper"
                :data="exportDatas"
                :fields ="exportJsonFiled"
                :before-generate ="startDownload"
                type="xls"
                name ="${bean_name}List.xls">
                <a-button type="primary" icon="download" >  {{$t('common.export')}}   </a-button>
              </download-excel>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button v-if= "showAdd()" @click="handleAdd" v-show="show" type="primary" icon="plus">{{$t('common.add')}}</a-button>
      <a-button  v-if= "showExport()" type="primary" v-show="show" icon="download" @click="handleExportXls">{{$t('common.export')}}</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            {{$t('common.delete')}}
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> {{$t('common.deleteAll')}}
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('common.alreadyChoose')}} <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a> {{$t('common.item')}}
        <a style="margin-left: 24px" @click="onClearSelected"> {{$t('common.clearAll')}} </a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:1500}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pageInfo"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        

        <span slot="action" slot-scope="text, record">
          <a href="javascript:;" @click="handleDetail(record)">{{$t('common.detail')}}</a>
          <a-divider type="vertical" v-if= "showEdit(record)" />
          <a  @click="handleEdit(record)" v-if= "showEdit(record)"  >{{$t('common.edit')}}</a>
          <a-divider type="vertical" v-if= "showDelete(record)" />
          <a-popconfirm v-if= "showDelete(record)" :title="$t('common.delete')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('common.delete')}}</a>
          </a-popconfirm>
          <a-divider type="vertical" v-if= "showRealDel()" />
          <a-popconfirm v-if= "showRealDel()" :title="$t('common.realDelete')" @confirm="() => handleRealDelete(record.id)">
                  <a>{{$t('common.realDelete')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <${mid_score_name}-modal ref="modalForm" @ok="modalFormOk"></${mid_score_name}-modal>
  </a-card>
</template>

<script>
  import ${bean_name}Modal from './modules/${bean_name}Modal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import JEllipsis from "@/components/jeecg/JEllipsis";
  import API   from '@/api/${bean_name}'
  import moment from 'moment';
  export default {
    name: "${bean_name}List",
    mixins: [JeecgListMixin],
    components: {
      JEllipsis,
      ${bean_name}Modal
    },
    data() {
      return {
        description: '${bean_name}管理',
        // 新增修改按钮是否显示
        orderByClause:'${orderByClause}',
        show: true,
        // 表头
        columns: [
            {
                title: '#',
                dataIndex: '',
                key: 'rowIndex',
                width: 60,
                align: "center",
                customRender: function (t, r, index) {
                return parseInt(index) + 1;
                }
            },
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
        ]
      }
    },
    created() {
      this.pageInfo.condition.orderByClause=this.orderByClause
      if(this.$store.getters.userInfo.id !="1"){
      	this.pageInfo.condition.delFlag=0
      }
      
      //初始化查询条件的时间范围
      this.pageInfo.condition.startTime=this.moment().locale('en_US').add(-3, 'M').startOf('day').format('YYYY-MM-DD HH:mm:ss')//开始时间是当前时间减去3个月
      this.pageInfo.condition.endTime=this.moment().locale('en_US').endOf('day').format('YYYY-MM-DD HH:mm:ss')//结束时间是当天
      this.queryTimeDefaultValue[0]=this.moment(this.pageInfo.condition.startTime, 'YYYY-MM-DD')
      this.queryTimeDefaultValue[1]=this.moment(this.pageInfo.condition.endTime, 'YYYY-MM-DD')
     let config = this.$store.getters.userInfo.sysDept.config.${model_name}ListFiled
     config=config ==undefined ?0:config
     this.columns.forEach((v,index) => {//生成导出字段
         if(v.dataIndex&& v.dataIndex!='action'){
            this.exportJsonFiled[v.title]=v.dataIndex
         }
     });
     for(let i=0 ;i<30;i++){
          let j=(1<<i);
          if((config & j)!=0){  //为真说明这个字段需要删除
            let filedname= API.filedList[i];   
            this.columns.forEach((v,index) => {
              if(v.dataIndex==filedname){
                this.columns.splice(index,1) 
                delete this.exportJsonFiled[v.title] //去掉不需要的导出字段
              }
            });
          }
      }
      
      
      
      
      this.loadData();
    },
    computed: {
     
    },
    methods: {
      moment,
      loadData() {
		 	
          this.loading = true;
          API.findByPage(this.pageInfo).then(
            (res) => {
              this.dataSource = res.list;
              this.pageInfo.total=res.total;
              this.loading = false;
            }
          );
      },
      async startDownload(){
        this.loading = true;
        this.exportDatas= await API.findByAll(this.pageInfo)
        this.loading = false;
           
      },
      handleAdd: function () {
          let obj = Object.assign({}, API.defaultObject)
          this.$refs.modalForm.add(obj);
          this.$refs.modalForm.title = "$t('form.add)'";
          this.$refs.modalForm.disableSubmit = false;
      },
      handleDelete(id){
      	    <#noparse>
		//要真删除用 API.realDeleteById(id)
		    </#noparse>
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
       handleRealDelete(id){
        API.realDeleteById(id).then(
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
      showAdd(item){
         return  this.$store.getters.userInfo.permissions.includes('${model_name}:add')
      },
      showExport(){
         return  this.$store.getters.userInfo.permissions.includes('${model_name}:export')
      },
      showEdit(item){
         return  this.$store.getters.userInfo.permissions.includes('${model_name}:update')
      },
      showDelete(item){
         return  this.$store.getters.userInfo.permissions.includes('${model_name}:delete')
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
<style lang="less" scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }

  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
  .export-excel-wrapper {
    display: inline-block;
    margin-left: 8px;
    
  }
</style>