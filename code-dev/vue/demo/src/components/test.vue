<template>
<div>

 <el-button type="primary" @click="save()">保存<i class="el-icon-upload el-icon--right"></i></el-button>
   <el-divider></el-divider>
<textarea
id="kind_editor"
v-model="content"
style="width: 100%; min-height: 600px"
></textarea>
  <div id="content2"></div>
</div>
</template>
<script>
import 'kindeditor/themes/default/default.css'
import 'kindeditor/kindeditor-all-min.js'
import 'kindeditor/lang/zh-CN.js'
  export default {
    name: 'test',
   data() {
    return {
        editor: null,
        content: 'null',
        Realview: '',
          article: {url: 'http://localhost:9000/images/android-chrome-192x192.png',
          username: '',
          type: '',
          message: '',
          name: '',
            nickname: ''}
    }
},
mounted() {
    this.initEditor()
},
methods: {
    initEditor() {
        this.editor = window.KindEditor.create('#kind_editor')
    },
    save() {
      var that = this
    this.article.message = this.editor.html()
    console.log(this.article.message)
      console.log(this.$route.query.nickname)
    var name = this.$route.query.name
      var username = this.$route.query.username
      var type = this.$route.query.type
      var nickname = this.$route.query.nickname
      this.article.name = name
       this.article.username = username
       this.article.type = type
        this.article.nickname = nickname
      console.log(this.nickname)
      console.log(this.article.url)
       this.$axios.post('http://localhost:8081/common/save', {
         userid: this.article.username,
          type: this.article.type,
          content: this.article.message,
          title: this.article.name,
          nickname: this.$route.query.nickname,
          url: this.article.url
      }).then(function (response) {
    if (response.data.code = 200) {
      console.log(response.data)
         that.$router.push({path: '/UserSpace'})
    }
  })
  .catch(function (error) {
    console.log(error)
    })
    }
}
  }
</script>
