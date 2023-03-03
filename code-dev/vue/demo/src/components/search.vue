<template>
  <div>
 <div v-if="page.articleList.length>0">
   <el-collapse accordion>
     <el-collapse-item v-for="value in page.articleList">
       <template slot="title">
         {{value.title}}<i class="header-icon el-icon-info"></i>
       {{value.type}}  <i class="el-icon-edit"></i>
       </template>
       <el-divider></el-divider>
       <div class="content" v-html="value.content">
       </div>
     </el-collapse-item>
   </el-collapse>
   <el-pagination
     background
     layout="prev, pager, next"
     :current-page=page.currentpage
     :page-count="page.totalpage"
     @current-change="currentchange"
     :next-click="currentchange"
     :size-change="currentchange">
   </el-pagination>
 </div>
    <div v-else><el-empty description="暂无数据"></el-empty></div>
  </div>
</template>

  <script>
    import article from './article.vue'

    export default {
        name: 'search',
      computed: {
        article() {
          return article
        }
      },
      data() {
        return {
          searchname: '',
          activeNames: ['1'],
          currentpage: 1,
           page: {
             articleList: [{
               title: '',
               type: '',
               content: '',
               username: ''
             }],
             currentpage: 1,
             totalpage: 1},
          search: [{
          }]
        }
      },
mounted() {
          console.log(this.$route.params.searchname)
         this.page = this.$route.params.list
         this.searchname = this.$route.params.searchname
  console.log(this.page)
},
      methods: {
        // eslint-disable-next-line vue/no-dupe-keys
       getcontent() {
         console.log(this.searchname)
         var that = this
         that.$axios.post('http://localhost:8081/common/search', {
            title: this.searchname,
            type: this.searchname,
            currentpage: this.currentpage
         }).then(response => {
           console.log(response.data)
           if (this.response != null) {
              this.page = response.data
           }
         }).catch(error => {
           console.log(error)
         })
       },
        currentchange(val) {
          this.$axios.post('http://localhost:8081/common/search', {
            title: this.searchname,
            type: this.searchname,
            currentpage: val
          }).then(response => {
          this.page = response.data
          }).catch(error => {
            console.log(error)
          })
        }
      }
    }
  </script>
<style>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
 .content{
   width:100% ;
   height:100%;
   float:left;
 }
</style>
