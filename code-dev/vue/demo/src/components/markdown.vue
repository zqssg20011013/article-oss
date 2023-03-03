<template>
<div>
  <el-popover
    placement="top-start"
    title="提醒"
    width="200"
    trigger="hover"
    content="切换视图后如要保存请在主编辑器空格递进或输入一次。">
  <el-button type="primary" icon="el-icon-edit" @click=" openMarkdownEditor()" slot="reference">Markdown实时视图</el-button>
  </el-popover>

 <el-button type="primary" @click="save()">保存<i class="el-icon-upload el-icon--right"></i></el-button>
   <el-divider></el-divider>
  <textarea v-model="Testarea" id="kind_editor" style="width: 100%; min-height: 600px" disabled></textarea>
</div>
</template>
 <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>

<script>
import Stackedit from 'stackedit-js';
var Marked=require('marked')
  export default {
    name: 'markdown',
    data () {
      return {
         visible: false,
      Testarea:'',
      Realarea: '',
      article: {
          username: '',
          type:'',
          message:'',
          name:'',
         nickname:'',
         url:'http://localhost:9000/images/android-chrome-192x192.png'
      }
      }
    },
mounted(){
}
    ,methods: {
    // 打开 markdown 编辑器, 编辑详情
    openMarkdownEditor() {

    const el = document.querySelector('textarea');
  const stackedit = new Stackedit();

  stackedit.openFile({
    name: 'Filename', // with an optional filename
    content: {
      text: el.value // and the Markdown content.
    }
  });
  stackedit.on('fileChange', (file) => {
    el.value = file.content.text;
   this.Realarea=file.content.html;
  },true);
    },
  save(){
       const that=this
      var name= this.$route.query.name
      var username=this.$route.query.username
      var type= this.$route.query.type
    var nickname= this.$route.query.nickname
      this.article.name=name
       this.article.username= username
       this.article.type= type
       this.article.message=this.Realarea
    this.article.nickname=nickname
       console.log(this.article);
        this.$axios.post('http://localhost:8081/common/save',{
         userid: this.article.username,
          type: this.article.type,
          content:this.article.message,
          title:this.article.name,
          nickname:this.article.nickname,
          url: this.article.url
        }).then(function (response) {
    if(response.data.code=200){
      console.log(response.data)
      that.$router.push({path:"/UserSpace"})
    }
  })
  .catch(function (error) {

    console.log(error);
    });

    //
    }
  }
}

</script>
