<template>
  <div name="card">

    <div class="container">
      <el-divider></el-divider>
      <el-row>
        <el-col :span="24">
          <span style="font-size: 28px;"><Strong>今天看什么</Strong></span>
          <a  style="float:right">
            <el-button icon="el-icon-more" circle @click="dialogVisible=true"></el-button>
          </a>
          <el-dialog
            title="搜索"
            :visible.sync="dialogVisible"
            width="30%"
            >
            <el-input
              v-model="input"
              placeholder="请输入内容" v-on:click="Search()"
              suffix-icon="el-icon-search"
            >
            </el-input>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="Search">确 定</el-button>
  </span>
          </el-dialog>
          <div >
            <el-dialog
              title="提示"
              :visible.sync="dialogVisible_card"
              width="70%"
            >
              <div v-html="content"></div>
              <span slot="footer" class="dialog-footer">
  </span>
            </el-dialog>
            <el-divider></el-divider>

          </div>
        </el-col>
      </el-row>
          <el-card class="box-card" v-for="value in table_audit.jsonarray"  >
            <div slot="header" class="clearfix">
              <span>{{value.title}}</span>
              <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more" circle @click="show(value.content)"></el-button>
            </div>
            <div>
              <i class="el-icon-user-solid">{{value.nickname}}</i>

                <el-image
                  style="width: 150px; height: 100px"
                  :src="value.url"
                  ></el-image>
            </div>
          </el-card>
    </div>

  </div>
</template>
<script>
export default {
  name: 'home',
  data() {
    return { input: '',
      dialogVisible: false,
      dialogVisible_card: false,
      msg: '',
       message: {
          title: '',
          content: '',
               status: true

        },
      table_audit: {
        jsonarray: [{
          id: '',
          title: '',
          type: '',
          userid: '',
          content: '',
          nickname: '',
          url: ''
        }
        ]
      },
      Content: [],
      // image: [{ articleid: "", id: "", url: "", username: "" }]
      image: [{
        url: ''
      }]
    }
  },
  mounted() {
    this.List()
  },

  methods: {
    show(val) {
      this.content = val
      this.dialogVisible_card = true
    },
    List() {
      this.$axios.post('http://localhost:8081/common/random').then(response => {
        this.table_audit.jsonarray = response.data
        console.log(this.table_audit)
      }).catch(error => {
        console.log(error)
      })
    },
    currentchange(val) {
      this.$axios.post('http://localhost:8081/common/ListAudit', {
        currentpage: val
      }).then(response => {
        this.table_audit.jsonarray = []
        this.table_audit.jsonarray = response.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRecommend() {
      var that = this
      this.$axios
        .post('http://localhost:8081/common/ListAll')
        .then(function(response) {
          console.log('content__', response.data)

          that.Content = response.data
          console.log(that.Content)
        })
        .catch(function(error) {
          console.log(error)
        })
    },
    getAllimage() {
      var that = this
      this.$axios
        .post('http://localhost:8081/common/ListAllimage')
        .then(function(response) {
          that.image = response.data
          console.log(that.image)
        })
        .catch(function(error) {
          console.log(error)
        })
    },
    dialogVisible_click(title, content) {
      var that = this
      console.log(title)
      that.dialogVisiblecard = true
      that.message.content = content
      that.messasge.title = title
       console.log(that.message)
    },
Search() {
  var test = this.input
  console.log(test)
  var that = this
  this.$axios.post('http://localhost:8081/common/search',
    {
      title: this.input,
      type: this.input,
      currentpage: 1
    }).then(function(response) {
    console.log(response)
    that.$router.push({ name: 'search',
      params: {
        list: response.data,
        searchname: test
      }})
  })
}
  }
}
</script>
<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.container {
  position: relative;
  margin: auto;
  overflow: hidden;
  width: 920px;
}
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
  clear: both;
}

.box-card {
  width: 20%;
  float: left;
  margin: 1%;
}
<style>
 .el-carousel__item h3 {
   color: #475669;
   font-size: 14px;
   opacity: 0.75;
   line-height: 200px;
   margin: 0;
 }

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
