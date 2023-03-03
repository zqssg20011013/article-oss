
<template>
<el-container >
             <el-header  :style="{backgroundImage:'url('+url+')'}" style="height:200px;">
       <el-col>
         <div id="img" style="height:50%;margin-top:120px">
           <el-upload
             class="avatar-uploader"
             action=""
             :show-file-list="false"
             :on-success="handleAvatarSuccess"
             :before-upload="beforeAvatarUpload"
             :http-request="uploadFile">
             <img v-if="imageUrl" :src="imageUrl" class="avatar">
             <i v-else class="el-icon-plus avatar-uploader-icon"></i>
           </el-upload>

         </div>
         <el-button icon="el-icon-turn-off"  style="float:right" @click="clearcache">

         </el-button>
       </el-col>

  </el-header>
  <el-main>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="个人信息" name="first">
      <el-form ref="form" :model="form" label-width="80px">
      <el-form-item style="display:none" v-model="form.username"></el-form-item>
  <el-form-item label="昵称">
      <el-col :span="6"><el-input v-model="form.name"></el-input></el-col>
  </el-form-item>
  <el-form-item label="出生时间">
    <el-col :span="11">
      <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 100%;"></el-date-picker>
    </el-col>
  </el-form-item>
        <el-form-item label="性别">
    <el-radio-group v-model="form.sex">
      <el-radio label="男"></el-radio>
      <el-radio label="女"></el-radio>
      <el-radio label="保密"></el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="个性签名">
    <el-input type="textarea" v-model="form.article"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">提交</el-button>
    <el-button>取消</el-button>
  </el-form-item>
</el-form>
      </el-tab-pane>
    <el-tab-pane label="网盘" name="second">

      <el-container id="网盘">
        <el-header>
          <el-row>
            <el-col :span="22"><div class="grid-content bg-purple-light">
              <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a >{{form.username}}</a></el-breadcrumb-item>
                <el-col :span="24"><div class="grid-content bg-purple-dark">
                  <div >
                    <svg xmlns="http://www.w3.org/2000/svg" class="min-icon" fill="currentcolor" viewBox="0 0 256 256" width="40"><defs><clipPath id="prefix__a"><path d="M0 0h256v256H0z"></path></clipPath></defs><g clip-path="url(#prefix__a)">
                      <path fill="none" d="M0 0h256v256H0z"></path>
                      <g data-name="folder-icn{">
                        <path d="M101.729 42.952c6.612 0 13.7 18.758 20.78 18.758h88.049a9.441 9.441 0 0 1 9.444 9.379v4.689H40.349V52.33h-.236a9.441 9.441 0 0 1 9.448-9.378h52.168m124.4 44.255a9.778 9.778 0 0 1 9.774 9.725L225.885 204.09a9.788 9.788 0 0 1-9.794 9.716H39.679a9.781 9.781 0 0 1-9.786-9.716l-9.79-107.158a9.781 9.781 0 0 1 9.79-9.725h196.236M101.729 23H49.561a29.469 29.469 0 0 0-29.544 29.33 20.109 20.109 0 0 0 .236 3.063v13.438A29.758 29.758 0 0 0 0 96.931c0 .6.031 1.2.09 1.8l9.723 106.5a29.827 29.827 0 0 0 29.862 28.532h176.412a29.833 29.833 0 0 0 29.862-28.5l9.959-106.484a17.091 17.091 0 0 0 .091-1.847 29.673 29.673 0 0 0-15.911-26.229 29.477 29.477 0 0 0-29.532-28.949h-81.5c-.4-.529-.787-1.05-1.117-1.5-5.1-6.87-12.815-17.248-26.208-17.248Z"></path>
                        <path data-name="Rect\xE1ngulo 874" fill="none" d="M0 0h256v256H0z"></path>
                      </g></g></svg>
                  </div>
                </div></el-col>
              </el-breadcrumb>
            </div></el-col>
            <el-col :span="2"><div class="grid-content bg-purple" style="text-align: center">
              <el-tooltip class="item" effect="dark" content="创建文件夹" placement="top-start">
                <el-button type="primary" icon="el-icon-folder-add" @click="dialogVisible=true"></el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="上传文件" placement="top" style="float:right">
                <el-upload
                  class="upload-article"
                  :http-request="uploadFile_disk">
                  <el-button type="primary" icon="el-icon-upload"></el-button>
                </el-upload>
              </el-tooltip>
            </div></el-col>
            <el-dialog
              title="创建文件夹"
              :visible.sync="dialogVisible"
              width="50%"
              :before-close="handleClose">
              <el-row><h2>Choose or create a new path</h2></el-row>
               <el-row><i>localpath:</i>{{localpath}}</el-row>
              <el-row><i>New Folder Path*</i>
               <el-form>
                 <el-input v-model="filename"></el-input>
               </el-form>
              </el-row>
              <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="uploadpath">确 定</el-button>
  </span>
            </el-dialog>

          </el-row>

        </el-header>
        <el-main>
          <div id="table">
            <template>
              <el-table
                :data="table"
                style="width: 100%"
              >
                <el-table-column
                  prop="objectname"
                  label="名字"
                  width="280"
                  selectable="selectable">
                  <template v-slot="scope">
                   <div v-if="scope.row.isdir" @click="show(scope.row.objectname)">
                 <a class="el-icon-folder"  @click="show(scope.row.objectname)" >{{scope.row.objectname}}</a>

                   </div>
                   <div v-else>
                     <i  class="el-icon-picture"></i>{{scope.row.objectname}}
                   </div>
                  </template>
                </el-table-column>

                <el-table-column
                  prop="size"
                  label="文件大小"
                  >
                  <tempate slot-scope="scope">
                    <div v-if="scope.row.isdir"></div>
                    <div v-else><i>{{scope.row.size}}</i></div>

                  </tempate>
                </el-table-column>
                <el-table-column label="操作" width="180">
                  <template slot-scope="scope">
                    <div v-if="scope.row.isdir">
                      <el-button size="mini" circle icon="el-icon-view" @click="getprofix(scope.row.objectname)"></el-button>
                    </div>
                    <div v-else>
                      <el-button size="mini" circle icon="el-icon-delete-solid" @click="deletefile(scope.row.objectname)"></el-button>
                      <el-button size="mini" circle icon="el-icon-download" @click="download(scope.row.objectname)"></el-button>
                    </div>
                  </template>
                </el-table-column>

              </el-table>
            </template>
          </div>
        </el-main>
        <el-footer></el-footer>
      </el-container>
      <el-divider></el-divider>
    </el-tab-pane>
    <el-tab-pane label="文章" name="third">
    <el-col :span="24" style="padding:1px">
     <el-button @click="dialogVisible = true">添加文章</el-button>

      <el-dialog
  title="提示"
  :visible.sync="dialogVisible"
  width="37%"
  :before-close="handleClose">
<el-form ref="article" :model="article" label-width="40px">
  <el-form-item label="文章名称">
    <el-input v-model="article.name"></el-input>
  </el-form-item>
  <el-form-item label="类型">
    <el-input v-model="article.type"></el-input>
  </el-form-item>
  <el-form-item label="编辑器">
    <el-radio-group v-model="article.editor">
      <el-radio label="Markdown"></el-radio>
      <el-radio label="富文本编辑器"></el-radio>
    </el-radio-group>
  </el-form-item>
</el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="editor()">确 定</el-button>
  </span>
</el-dialog>
       <el-divider></el-divider>
    </el-col>
      <div class="List" v-for="(value) in content" style="text-align: center">
      <el-card class="box-card" style=" float:left;margin-left:10px;margin-top:10px" >
  <div slot="header" class="clearfix">
    <span >{{value.title}} </span>
       <el-dialog  id="contentcard"
  :visible.sync="dialogVisiblecard"
  width="70%"
  :before-close="handleClose">
  <div v-html="message.content"></div>
  <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="dialogVisiblecard = false">确 定</el-button>
  </span>
</el-dialog>
  </div>
  <div class="picture" >
    <el-image
       :src= value.url
      style="width: 100px; height: 100px"
      ></el-image>
    <el-divider></el-divider>
  </div>
  <div  class="text item">
  <el-button type="primary" @click="  dialogVisible_click(value.title,value.content)" icon="el-icon-s-platform" circle></el-button>
    <el-button  type="danger" @click="Delete(value.id)" icon="el-icon-delete" circle ></el-button>
    <div v-if="value.recomend">
       <el-button icon="el-icon-success" circle disabled></el-button>
    </div>
    <div v-else>
      <el-button  type="success" @click="recommend(value.id)" icon="el-icon-check" circle ></el-button>
    </div>

        <el-upload
  class="upload-demo"
  action=""
  :on-preview="handlePreview"
  multiple
  :limit="3"
  :http-request="uploadFileArticle">
  <el-button   icon=" el-icon-picture" @click="id(value.id)"  circle></el-button>
</el-upload>
   </div>
</el-card>
    </div>
    </el-tab-pane>

  </el-tabs>
  </el-main>
  <el-footer>
    <el-dialog
      title="创建文件夹"
      :visible.sync="dialogVisible2"
      width="50%"
      :before-close="handleClose">
      <el-row><h2>Choose or create a new path</h2></el-row>
      <el-row><i>path:</i>{{profix}}</el-row>
      <el-row><i>New Folder Path*</i>
        <el-form>
          <el-input v-model="filename"></el-input>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="uploadpath_in">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="创建文件夹"
      :visible.sync="dialogVisible_inner"
      width="50%"
      :before-close="handleClose">
      <el-row><h2>Choose or create a new path</h2></el-row>
      <el-row><i>path:</i>{{profix_inner}}</el-row>
      <el-row><i>New Folder Path*</i>
        <el-form>
          <el-input v-model="filename"></el-input>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="uploadpath_inner">确 定</el-button>
  </span>
    </el-dialog>
    <el-drawer
      title=""
      :visible.sync="drawer"
      :direction="direction"
      ref="navDrawer"
    :size="1000">

      <el-row :span="2">
        <el-tooltip class="item" effect="dark" content="创建文件夹" placement="top" style="text-align: right">
        <el-button type="primary" icon="el-icon-folder-add" @click="dialogVisible2=true"></el-button>
      </el-tooltip>
          <el-upload
            class="upload-article"
            :http-request="uploadFile_diskin">
            <el-button type="primary" icon="el-icon-upload"></el-button>
          </el-upload>
      </el-row>

      <div id="table">
        <template>
          <el-table
            :data="table_in"
            style="width: 100%"
          >
            <el-table-column
              prop="objectname"
              label="名字"
              width="280"
              selectable="selectable">
              <template v-slot="scope">
                <div v-if="scope.row.isdir" @click="show(scope.row.objectname)">
                  <a class="el-icon-folder"  @click="show(scope.row.objectname)" >{{scope.row.objectname}}</a>

                </div>
                <div v-else>
                  <i  class="el-icon-picture"></i>{{scope.row.objectname}}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="size"
              label="文件大小"
            >
              <tempate slot-scope="scope">
                <div v-if="scope.row.isdir"></div>
                <div v-else><i>{{scope.row.size}}</i></div>

              </tempate>
            </el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <div v-if="scope.row.isdir">
                  <el-button size="mini" circle icon="el-icon-view" @click="getprofix_inner(scope.row.objectname)"></el-button>
                </div>
                <div v-else>
                  <el-button size="mini" circle icon="el-icon-download" @click="download(scope.row.objectname)"></el-button>
                  <el-button size="mini" circle icon="el-icon-delete-solid" @click="deletefile_in(scope.row.objectname)"></el-button>
                </div>
              </template>
            </el-table-column>

          </el-table>
        </template>
      </div>
      <el-drawer
        title="我是里面的"
        :append-to-body="true"
        :before-close="handleClose"
        :visible.sync="innerDrawer"
        :size="700" >
        <p>_(:зゝ∠)_</p>
        <el-tooltip class="item" effect="dark" content="创建文件夹" placement="top">
          <el-button type="primary" icon="el-icon-folder-add" @click="dialogVisible_inner=true"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="上传文件" placement="top">
          <el-upload
            class="upload-article"
            :http-request="uploadFile_diskinner">
            <el-button type="primary" icon="el-icon-upload"></el-button>
          </el-upload>
        </el-tooltip>
        <div id="table">
          <template>
            <el-table
              :data="table_inner"
              style="width: 100%"

            >
              <el-table-column
                prop="objectname"
                label="名字"
                width="280"
                selectable="selectable">
                <template v-slot="scope">
                  <div v-if="scope.row.isdir" @click="show(scope.row.objectname)">
                    <a class="el-icon-folder"  @click="show(scope.row.objectname)" >{{scope.row.objectname}}</a>

                  </div>
                  <div v-else>
                    <i  class="el-icon-picture"></i>{{scope.row.objectname}}
                  </div>
                </template>
              </el-table-column>

              <el-table-column
                prop="size"
                label="文件大小"
              >
                <tempate slot-scope="scope">
                  <div v-if="scope.row.isdir"></div>
                  <div v-else><i>{{scope.row.size}}</i></div>

                </tempate>
              </el-table-column>
              <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                  <div v-if="scope.row.isdir">
                    <el-button size="mini" circle icon="el-icon-view" @click="getprofix_inner2(scope.row.objectname)"></el-button>
                  </div>
                  <div v-else>
                    <el-button size="mini" circle icon="el-icon-delete-solid" @click="deletefile_inner(scope.row.objectname)"></el-button>
                    <el-button size="mini" circle icon="el-icon-download" @click="download(scope.row.objectname)"></el-button>
                  </div>
                </template>
              </el-table-column>

            </el-table>
          </template>
        </div>
        <el-drawer
          title="我是inner2"
          :append-to-body="true"
          :before-close="handleClose"
          :visible.sync="innerDrawer2"
          :size="500">
          <el-tooltip class="item" effect="dark" content="上传文件" placement="top">
            <el-upload
              class="upload-article"
              :http-request="uploadFile_diskinner2">
              <el-button type="primary" icon="el-icon-upload"></el-button>
            </el-upload>
          </el-tooltip>
          <div id="table">
            <template>
              <el-table
                :data="table_inner2"
                style="width: 100%"
              >
                <el-table-column
                  prop="objectname"
                  label="名字"
                  width="280"
                  selectable="selectable">
                  <template v-slot="scope">
                    <div v-if="scope.row.isdir" @click="show(scope.row.objectname)">
                      <a class="el-icon-folder"  @click="show(scope.row.objectname)" >{{scope.row.objectname}}</a>

                    </div>
                    <div v-else>
                      <i  class="el-icon-picture"></i>{{scope.row.objectname}}
                    </div>
                  </template>
                </el-table-column>

                <el-table-column
                  prop="size"
                  label="文件大小"
                >
                  <tempate slot-scope="scope">
                    <div v-if="scope.row.isdir"></div>
                    <div v-else><i>{{scope.row.size}}</i></div>

                  </tempate>
                </el-table-column>
                <el-table-column label="操作" width="180">
                  <template slot-scope="scope">
                    <div v-if="scope.row.isdir">
                      <el-button size="mini" circle icon="el-icon-view" @click="getprofix_inner2(scope.row.objectname)"></el-button>
                    </div>
                    <div v-else>
                      <el-button size="mini" circle icon="el-icon-delete-solid" @click="deletefile_inner2(scope.row.objectname)"></el-button>
                      <el-button size="mini" circle icon="el-icon-download" @click="download(scope.row.objectname)"></el-button>
                    </div>
                  </template>
                </el-table-column>

              </el-table>
            </template>
          </div>
        </el-drawer>
      </el-drawer>
      </el-drawer>
  </el-footer>
</el-container>
</template>
<script>

export default {
    name: 'Userspace',
    data() {
      return {
        quit: 'http://localhost:9000/images/q.png',
        profix_inner: '',
        profix_inner2: '',
        profix: '',
        drawer: false,
        innerDrawer: false,
        innerDrawer2: false,
        direction: 'rtl',
        error: '错误报告',
        fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
        url: require('../assets/name.png'),
        circleUrl: '',
        activeName: 'second',
         dialogVisible: false,
        dialogVisible2: false,
        dialogVisible_inner: false,
        dialogVisible_inner2: false,
         dialogVisiblecard: false,
          imageUrl: '',
          imageid: '',
        form: {
          username: '',
          name: '',
          date: '',
          sex: '',
          article: '',
          imageUrl: ''},
        article: {
          name: '',
          username: '',
          type: '',
          editor: ''

         },
        content: {
          totalpage: 1,
          currentpage: 1,
          array: [{
          id: '',
          userid: '',
          type: '',
          title: '',
          content: '',
          recomend: false,
          url: ''
        }]},
        message: {
          title: '',
          content: '',
               status: true

        },
       image: [{
           url: ''
      }],
        all: [],
        localpath: '/',
        filename: '',
        table: [{
          objectname: '',
          isdir: false,
          size: 0
        }],
        table_in: [{
          objectname: '',
          isdir: false,
          size: 0
        }],
        table_inner: [{
          objectname: '',
          isdir: false,
          size: 0
        }],
        table_inner2: [{
        objectname: '',
        isdir: false,
        size: 0
      }]
    }
},
    mounted() {
       console.log(this.$route.query.user)
     var that = this
      this.$axios.post('http://localhost:8070/admin/getbytoken', {
        token: localStorage.token
      }
    )
  .then(function (response) {
            console.log(response.data)
            that.form = response.data
            that.article.username = response.data.username
            that.imageUrl = response.data.imgeUrl
            that.circleUrl = response.data.imgeUrl
      // article信息
            that.Listbyid()
      // image信息
        //    that.ListImage()
      //
        //    that.listall()
        //   console.log(that.all)
            that.Listtable()
  })
  .catch(error => {
       this.$axios.post('htto://localhost:8070/admin/getbypassword', {
          username: this.$route.query.username,
          password: this.$route.query.password
       }).then(response => {
         this.form = response.data
         this.article.username = response.data.username
         this.imageUrl = response.data.imgeUrl
         this.circleUrl = response.data.imgeUrl
         this.Listbyid()
         this.Listtable()
       }).catch(error => {
         this.$router.push({ path: '/login' })
       })
    })
    },
     methods: {

      handleClick(tab, event) {
        console.log(tab, event)
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done()
          })
          .catch(_ => {})
      },

      onSubmit() {
      // 修改个人信息
        var that = this
       console.log(this.form)
       this.$axios.post('http://localhost:8070/admin/UpdateUseer', {
        username: this.form.username,
        name: this.form.name,
        date: this.form.date,
        sex: this.form.sex,
        article: this.form.article

      }
    )
  .then(function (response) {
    if (response.data.code = 200) {
      console.log(response.data)
      that.form = response.data
        window.alert('修改成功')
    }
  })
  .catch(function (error) {
    console.log(error)
    })
      },
      editor() {
         var that = this
        // eslint-disable-next-line eqeqeq
         if (this.article.editor == 'Markdown') {
           that.$router.push({path: '/markdown',
query: { nickname: this.form.name,
               name: this.article.name,
               username: this.article.username,
               type: this.article.type
           }
    })
    } else {
       that.$router.push({path: '/test',
query: { nickname: this.form.name,
              name: this.article.name,
               username: this.article.username,
               type: this.article.type
           }
    })
    };
      },
    // List
     Listbyid() {
      var that = this
         // article对象获取
       this.$axios.post('http://localhost:8081/common/ListById', {
       id: this.form.username
      }
    )
  .then(function (response) {
    that.content.array = []
     that.content = response.data
  })
  .catch(function (error) {
    console.log(error)
    })
     },
     dialogVisible_click(title, content) {
      var that = this
      that.dialogVisiblecard = true
      that.message.content = content
      that.messasge.title = title
       console.log(that.message)
    },
     recommend(id) {
       // eslint-disable-next-line no-unused-vars
           var that = this
           this.$axios.post('http://localhost:8081/common/recommend', {
           id: id
      }
    )
  .then(function (response) {
      this.Listbyid()
  })
  .catch(function (error) {
    console.log(error)
    })
     },
     Delete(id) {
        var that = this
           console.log(id)
           this.$axios.post('http://localhost:8070/image/delete', {
           id: id
      }
    )
  .then(function (response) {
       that.Listbyid()
      console.log(response.data)
  })
  .catch(function (error) {
    console.log(error)
    })
     },
     handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw)
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
     //   var File = file
        console.log(file)
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      handlePreview(title, id) {
        console.log(id)
        console.log(title)
        var that = this
      that.imageid = id
  },
      // 更新图片
      uploadFile(param) {
        var that = this
      console.log(param.file)
       var data = new FormData()
         data.append('file', param.file)
         data.append('username', this.form.username)
        this.$axios.post('http://localhost:8070/admin/UpdateImage', data, { 'Content-Type': 'multipart/form-data'}).then(function (response) {
          that.imageUrl = response.data
      console.log(response.data)
          that.Listbyid()
  })
  .catch(function (error) {
    console.log(error)
    })
      },
       uploadFile_disk(param) {
         var that = this
         console.log(param.file)
         var data = new FormData()
         data.append('file', param.file)
         data.append('username', this.form.username)
         data.append('prefix', '')
         this.$axios.post('http://localhost:8081/upload', data, { 'Content-Type': 'multipart/form-data'}).then(function (response) {
           that.imageUrl = response.data
           console.log(response.data)
           that.Listtable()
         })
           .catch(function (error) {
             console.log(error)
           })
       },
       uploadFile_diskin(param) {
         var that = this
         console.log(param.file)
         var data = new FormData()
         data.append('file', param.file)
         data.append('username', this.form.username)
         data.append('prefix', this.profix)
         this.$axios.post('http://localhost:8081/upload', data, { 'Content-Type': 'multipart/form-data'}).then(function (response) {
           that.imageUrl = response.data
           console.log(response.data)
           that.Listtable_in()
         })
           .catch(function (error) {
             console.log(error)
           })
       },
       uploadFile_diskinner(param) {
         var that = this
         console.log(param.file)
         var data = new FormData()
         data.append('file', param.file)
         data.append('username', this.form.username)
         data.append('prefix', this.profix_inner)
         this.$axios.post('http://localhost:8081/upload', data, { 'Content-Type': 'multipart/form-data'}).then(function (response) {
           that.imageUrl = response.data
           console.log(response.data)
           that.Listtable_inner()
         })
           .catch(function (error) {
             console.log(error)
           })
       },
       uploadFile_diskinner2(param) {
         var that = this
         console.log(param.file)
         var data = new FormData()
         data.append('file', param.file)
         data.append('username', this.form.username)
         data.append('prefix', this.profix_inner2)
         this.$axios.post('http://localhost:8081/upload', data, { 'Content-Type': 'multipart/form-data'}).then(function (response) {
           that.imageUrl = response.data
           console.log(response.data)
           that.Listtable_inner2()
         })
           .catch(function (error) {
             console.log(error)
           })
       },
      // 文章更新
       uploadFileArticle(param) {
        const that = this
        var data = new FormData()
         data.append('file', param.file)
         data.append('username', this.form.username)
         console.log(this.imageid)
         data.append('articleid', this.imageid)
        // eslint-disable-next-line standard/object-curly-even-spacing
        this.$axios.post('http://localhost:8070/image/add2', data, { 'Content-Type': 'multipart/form-data'}).then(function (response) {
 that.Listbyid()
      })
},
        id(id) {
  console.log(id)
  this.imageid = id
}, // 获取图片
       ListImage() {
  console.log('ok')
  var that = this
   this.image = []
  this.$axios.post('http://localhost:8070/image/List', {
    username: this.form.username
  }).then(function (response) {
     that.image = response.data
    console.log(that.image)
      that.listall()
    })
},
       uploadpath() {
        var that = this
  this.$axios.post('http://localhost:8081/mkdir', {
           username: this.form.username,
           objectname: this.filename,
           localpath: this.localpath
         }).then(function (response) {
            that.Listtable()
           this.dialogVisible = false
         }).catch(function (error) {
           console.log(error)
         })
       },
       uploadpath_in() {
         var that = this
         this.$axios.post('http://localhost:8081/mkdir', {
           username: this.form.username,
           objectname: this.filename,
           localpath: this.profix
         }).then(function (response) {
           that.Listtable_in()
           this.dialogVisible = false
         }).catch(function (error) {
           console.log(error)
         })
       },
       uploadpath_inner() {
         var that = this
         this.$axios.post('http://localhost:8081/mkdir', {
           username: this.form.username,
           objectname: this.filename,
           localpath: this.profix_inner
         }).then(function (response) {
           that.Listtable_inner()
           this.dialogVisible = false
         }).catch(function (error) {
           console.log(error)
         })
       },
       Listtable() {
         const that = this
         this.$axios.post('http://localhost:8081/list', {
          objectname: this.form.username,
          profix: this.localpath
        }).then(function (response) {
           that.table = []
         that.table = response.data
        }).catch(function (error) {
          console.log(error)
        })
       },
       Listtable_in() {
         const that = this
         console.log('list table')
         console.log(this.profix)
         this.$axios.post('http://localhost:8081/list', {
           objectname: this.form.username,
           prefix: this.profix
         }).then(function (response) {
           that.table_in = []
           console.log(response.data)
           that.table_in = response.data
           console.log(that.table)
         }).catch(function (error) {
           console.log(error)
         })
       },
       Listtable_inner() {
         const that = this
         console.log('list table')
         this.$axios.post('http://localhost:8081/list', {
           objectname: this.form.username,
           prefix: this.profix_inner
         }).then(function (response) {
           that.table_inner = []
           console.log(response.data)
           that.table_inner = response.data
           console.log(that.table)
         }).catch(function (error) {
           console.log(error)
         })
       },
       Listtable_inner2() {
         const that = this
         console.log('list table')
         this.$axios.post('http://localhost:8081/list', {
           objectname: this.form.username,
           prefix: this.profix_inner2
         }).then(function (response) {
           that.table_inner2 = []
           console.log(response.data)
           that.table_inner2 = response.data
         }).catch(function (error) {
           console.log(error)
         })
       },
       change() {
       this.$refs.navDrawer.closeDrawer()
          window.alert(this.drawer)
        // this.drawer2 = true
           this.drawer2 = true
       },
       download(objectname) {
        console.log(objectname)
         this.$axios.post('http://localhost:8081/url', {
           username: this.form.username,
           objectname: objectname
         }).then(function(response) {
           console.log(response.data)
           window.open(response.data, '_blank')
         }).catch(function(error) {
           console.log(error)
         })
       },
       getprofix(val) {
         var that = this
         this.profix = val
         this.$axios.post('http://localhost:8081/list', {
           objectname: this.form.username,
           prefix: val
         }).then(function (response) {
           console.log(response.data)
           that.table_in = response.data
           console.log(that.table_in)
           that.drawer = true
         }).catch(function (error) {
           console.log(error)
         })
       },
       getprofix_inner(val) {
    var that = this
    this.profix_inner = val
    this.$axios.post('http://localhost:8081/list', {
      objectname: this.form.username,
      prefix: this.profix_inner
    }).then(function (response) {
      console.log(response.data)
      that.table_inner = response.data
      console.log(that.table_inner)
      that.innerDrawer = true
    }).catch(function (error) {
      console.log(error)
    })
  },
       getprofix_inner2(val) {
         var that = this
        this.profix_inner2 = val
         this.$axios.post('http://localhost:8081/list', {
           objectname: this.form.username,
           prefix: val
         }).then(function (response) {
           console.log(response.data)
           that.table_inner2 = response.data
           console.log(that.table_in)
           that.innerDrawer2 = true
         }).catch(function (error) {
           console.log(error)
         })
       },
       deletefile(val) {
        console.log(val)
        this.$axios.post('http://localhost:8081/delete', {
            username: this.form.username,
            objectname: val
        }).then(response => {
            this.Listtable()
        })
       },
       deletefile_in(val) {
         console.log(val)
         this.$axios.post('http://localhost:8081/delete', {
           username: this.form.username,
           objectname: val
         }).then(response => {
           this.Listtable_in()
         })
       },
       deletefile_inner(val) {
          console.log(val)
       this.$axios.post('http://localhost:8081/delete', {
          username: this.form.username,
          objectname: val
    }).then(response => {
      this.Listtable_inner()
    })
  },
       deletefile_inner2(val) {
         console.log(val)
           console.log('delete inner2')
         this.$axios.post('http://localhost:8081/delete', {
           username: this.form.username,
           objectname: val
         }).then(response => {
           this.Listtable_inner2()
         })
       },
       clearcache() {
         var token = localStorage.token
         console.log(token)
         if (token != null) {
          localStorage.removeItem(token)
           this.$axios.post('http://localhost:8081/common/cache', {
             token: token
           }).then(response => {
             this.$router.push({path: '/home'})
           })
         }
       }
}
}
</script>

<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 25%;
    maring:10px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 40px;
    height: 40px;
    line-height: 78px;
    text-align: center;
  }
  .avatar {
    width: 78px;
    height: 78px;
    display: block;
  }
</style>
