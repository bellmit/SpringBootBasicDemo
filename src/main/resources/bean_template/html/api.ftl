import { axios } from '@/utils/request'

export default {
	defaultObject:{
		<#list col_List as col>
			<#if col.bean_type=='boolean' || col.bean_type=='Boolean'>
		 ${col.filed}:false,//  ${col.comment} 类型${col.bean_type}
			</#if>
		</#list>
	},
	${model_name} :	{
		<#list col_List as col>
		
		 ${col.filed}:'',//  ${col.comment} 类型${col.bean_type}
		
		</#list>
	},
	
	findByAll:function findAll(pageInfo) {
	  return axios({
	    url: '/${model_name}/findAll',
	    method: 'post',
	    data: pageInfo.condition,
	    headers: {
	      'Content-Type': 'application/json;charset=UTF-8'
	    }
	  })
	},
	
	findByPage:function findByPage(pageInfo) {
	  return axios({
	    url: '/${model_name}/findPage/'+pageInfo.current+'/'+pageInfo.pageSize,
	    method: 'post',
	    data: pageInfo.condition,
	    headers: {
	      'Content-Type': 'application/json;charset=UTF-8'
	    }
	  })
	},
	<#if isTree>
	getChildListBatch:function getChildListBatch(pids) {
	  return axios({
	    url: '/${model_name}/getChildListBatch',
	    method: 'post',
	    data: pids,
	    headers: {
	      'Content-Type': 'application/json;charset=UTF-8'
	    }
	  })
	},
	selectNodeFromList: function selectNodeFromList(list,id){

		for (let i in list) {
            if(list[i].id==id){
            	return list[i]
        	}
        if(list[i].children && list[i].children.length >0){
            let node=this.selectNodeFromList(list[i].children,id);
            if(node!=null){
                return node
            }
          }
        }  
		
		return null

	},

	selectNodeFromNode: function selectNodeFromNode(treeNode,id){
		if(treeNode.id === id){
			return treeNode;
		}
		if(treeNode.children && treeNode.children.length >0){
            return this.selectNodeFromList(treeNode.children,id); 
        } 
		return null 
	},
	</#if>
	add:function add(${model_name}) {
	  return axios({
	    url: '/${model_name}/add',
	    method: 'post',
	    data: ${model_name},
	    headers: {
	      'Content-Type': 'application/json;charset=UTF-8'
	    }
	  })
	},
	
	update:function update(${model_name}) {
	  return axios({
	    url: '/${model_name}/update',
	    method: 'post',
	    data: ${model_name},
	    headers: {
	      'Content-Type': 'application/json;charset=UTF-8'
	    }
	  })
	},
	deleteById:function deleteById(id) {
	  return axios({
	    url: '/${model_name}/deleteById/'+id,
	    method: 'delete'
	 
	  })
	},
	
	realDeleteById:function realDeleteById(id) {
	  return axios({
	    url: '/${model_name}/realDeleteById/'+id,
	    method: 'delete'
	 
	  })
	},
	
	findById:function findById(id) {
	  return axios({
	    url: '/${model_name}/findById/'+id,
	    method: 'get'
	 
	  })
	},
	
	deleteByIds:function deleteByIds(ids) {
	  return axios({
	    url: '/${model_name}/deleteByIds',
	    method: 'delete',
	 	data: ids,
	    headers: {
	      'Content-Type': 'application/json;charset=UTF-8'
	    }
	  })
	},
	filedList:[
		<#list col_List as col>
			<#if col_has_next>
		        "${col.filed}",
			<#else >
		        "${col.filed}"
		    </#if>
		</#list>
	]
}