<template>
   <j-modal
    :width="600"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
<#assign form_date = false>
<#assign form_select = false>
<#assign form_select_multi = false>
<#assign form_select_search = false>
<#assign form_popup = false>
<#assign form_sel_depart = false>
<#assign form_sel_user = false>
<#assign form_file = false>
<#assign form_image = false>
<#assign form_editor = false>
<#assign form_cat_tree = false>
<#assign form_cat_back = "">
<#assign form_pca = false>
<#assign form_md = false>
<#assign form_switch=false>
<#assign form_span = 24>

    <#noparse>
<!--  <a-tabs v-model="activeKey" @change="handleChangeTabs">-->
      <!--主表区域 -->
<!--     <a-tab-pane tab="SysRole" key="SysRole" :forceRender="true">-->
    </#noparse>
     
            <a-form-model 
				ref="ruleForm"
              :model="model"
              :rules="rules"
              :label-col="labelCol"
              :wrapper-col="wrapperCol">
                <a-row>
            <#list col_List as po>
              <#if po.component?? > 
              		<#if po.component =='select'> 
              			<a-form-model-item :label="$t('${bean_name}.${po.filed}')" prop="${po.filed}" >
		                    
		                       <a-select v-model="model.${po.filed}" :disabled="isDetail" > 
		                       	 	<#list po.selectList as se>
		                       	 	<a-select-option :value=${se.value} >{{$t('${se.key}')}}</a-select-option>  
		                       	 	</#list>
		                      </a-select> 
		                </a-form-model-item>
                  </#if>
              <#else>
              	<#if po.filed =='parentId'> 
              	  <a-form-model-item label="parentId" v-show=false>
                  		<a-input :disabled="isDetail" v-model="model.parentId"  placeholder="parentId" ></a-input>
                  </a-form-model-item>
          		  <a-form-model-item :label="$t('parent.name')"  >
            		    <a-input :disabled=true v-model="model.parentName" placeholder="title" ></a-input>
          		  </a-form-model-item>
	              <#elseif po.filed != 'id'>
	              	  <a-form-model-item :label="$t('${bean_name}.${po.filed}')" prop="${po.filed}" v-if="showFiled('${po.filed}')">
	                  <#if po.bean_type =='Date'>
	                  <#assign form_date = true>
	                        <j-date :disabled="isDetail || isEdit" v-model="model.${po.filed}"  :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"  />	
	                  <#elseif po.bean_type=='Integer' || po.bean_type=='Long' || po.bean_type=='BigDecimal'>
	                        <a-input-number :disabled="isDetail" v-model="model.${po.filed}" :max=100 :min=0 :precision=0 placeholder="${po.filed}" style="width: 100%" />
	                  <#elseif po.bean_type=='boolean' || po.bean_type=='Boolean'>
	                  		<a-switch :disabled="isDetail" :checked="model.${po.filed}" @change="(check) => { model.${po.filed}=check}" :checked-children="$t('common.enable')" :un-checked-children="$t('common.disable')" />
	                  <#else>
	                  		<a-input :disabled="isDetail" :maxLength=30 v-model="model.${po.filed}" prop="${po.filed}" placeholder="${po.filed}" ></a-input>
	                  </#if>
	                  </a-form-model-item>
	              <#else>
	                  <a-form-model-item label="id"  v-show=false>
	                      <a-input v-model="model.id"></a-input>
	                  </a-form-model-item>
	              </#if>
              </#if>
            </#list>

                </a-row>
            </a-form-model>
             
        
    <#noparse>
       <!--        </a-tab-pane>-->
<!--      </a-tabs>-->
    </#noparse>
    </a-spin>
  </j-modal>
</template>

<script>
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import API   from '@/api/${entityName}'
  <#if form_date>
  import JDate from '@/components/jeecg/JDate'
  </#if>
  
  export default {
    name: '${entityName}Modal',
    mixins: [JEditableTableMixin],
    components: {
    <#if form_date>
      JDate,
    </#if>
    },
    data() {
      return {
        visible: false,
        disableSubmit:false,
        confirmLoading: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        type: 'add',
        rules:{
        <#list col_List as po>
          ${po.filed}:[
          
          <#if po.bean_type = "String">
				{
	              validator: (rule,value,callback) =>{  
	                  let reg = /^[^\s].{0,98}[^\s]$/ig
	                  let error=value==undefined||value==null||!reg.test(value)
	                  if(error){
	                      callback(new Error(this.$t('${entityName}.${po.filed}Error')));
	                  }else{
	                      callback();
	                  }
	              },
	              trigger: 'blur'
	            }
	         
	       </#if>
	        <#if po.bean_type = "Integer" || po.bean_type=='Long' || po.bean_type=='BigDecimal'>
				{
              
	              validator: (rule,value,callback) =>{  
	                  let min =0
	                  let max =100
	                  let error=value==undefined||value==null||value>max||value<min
	                  if(error){
	                      callback(new Error(this.$t('${entityName}.${po.filed}Error')));
	                  }else{
	                      callback();
	                  }
	              },
	              trigger: 'blur'
	            } 
	         
	       </#if>
	       
	       
	       <#if po.bean_type = "Boolean" >
				{
              
	              validator: (rule,value,callback) =>{  
	                  
	                  let error=value==undefined||value==null
	                  if(error||value<0){
	                      callback(new Error(this.$t('${entityName}.${po.filed}Error')));
	                  }else{
	                      callback();
	                  }
	              },
	              trigger: 'blur'
	            } 
	         
	       </#if>
			],
			
          </#list>
        }
      }
    },
    methods: {
        getAllTable() {
            
        },
        /** 调用完edit()方法之后会自动调用此方法 */
        editAfter() {
            
        
        },
        async show(record){
      if (this.$store.getters.userInfo.sysDept.config.modalLayoutType != undefined && this.$store.getters.userInfo.sysDept.config.modalLayoutType) {//读取配置   如果时左右显示的
        this.labelCol = {
          xs: { span: 24 },
          sm: { span: 6 },
        },
          this.wrapperCol = {
            xs: { span: 24 },
            sm: { span: 16 },
          }
      } else {//上下显示的

        this.labelCol = {
          xxl: 24, // ≥1600px 响应式栅格
          xl: 24, // ≥1200px 响应式栅格
          lg: 10, // ≥992px 响应式栅格
          md: 12, // ≥768px 响应式栅格
          style:localStorage.getItem('language') == 'ar_EG' ? 'text-align:right' : ''//针对阿拉伯的样式绑定，解决行列布局覆盖labelAlign:'right'的问题

          // xs: { span: 24 }, 
          // sm: { span: 6 }
        },
          this.wrapperCol = {
            xxl: 24, // ≥1600px 响应式栅格
            xl: 24, // ≥1200px 响应式栅格
            lg: 10, // ≥992px 响应式栅格
            md: 12 // ≥768px 响应式栅格
            // xs: { span: 24 },
            // sm: { span: 16 }
          }
      }        
        
          //清除form的验证
		  if(this.$refs.ruleForm){
			  this.$refs.ruleForm.resetFields()
		  }
          
          this.model = Object.assign({}, record)
          
          
          this.visible = true
        },
      
       /** 确定按钮点击事件 */
        handleOk(e) {
           e.preventDefault();
           this.$refs.ruleForm.validate(valid => {
              if (valid) {
		           let fn=this.getFn();//获取方法
		           
		           fn(this.model).then((res) => {
		             if (res.code === 0) {
		               this.$message.success(res.msg);
		             } else {
		               this.$message.warning(res.errors);
		             }
		             this.$emit('ok');
		             this.visible=false
		           })
		       } 
            });
        },
        getFn(){
           if(this.isAdd){ return API.add;}else if(this.isEdit){return API.update;}
        },
        validateError(msg){
            this.$message.error(msg)
        },
        showFiled(filedName){
           let config = this.$store.getters.userInfo.sysDept.config.${model_name}AddFiled
           config=config ==undefined ?0:config
           let indexOfFiled=API.filedList.indexOf(filedName)
           let flag=1<<indexOfFiled;
           return !(config&flag)
        }

    }
  }
</script>

<style scoped>
</style>