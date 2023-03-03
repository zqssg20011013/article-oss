<template>
  <div>
  <el-row>
    <el-col :span="24" >
      <el-card class="box-card" v-for="value in table_audit.jsonarray" :key="table_audit.jsonarray.id"   >
        <div slot="header" class="clearfix" >
          <span>{{value.title}} </span>
          <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more" circle @click="show(value.content)"></el-button>
        </div>
        <div>
          <i class="el-icon-user-solid" style="padding: 3px">{{value.nickname}}</i>
        </div>
        <div class="demo-image">
            <el-image
              style="height:200px"
              :src="value.url"
              ></el-image>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <el-dialog
    title="提示"
    :visible.sync="dialogVisible"
    width="70%"
  >
    <div v-html="content"></div>
    <span slot="footer" class="dialog-footer">
  </span>
  </el-dialog>
    <el-pagination
      background
      layout="prev, pager, next"
      :current-page=table_audit.currentpage
      :page-count="table_audit.totalpage"
      @current-change="currentchange"
    >
    </el-pagination>
 </div>

</template>

<script>
export default {
  name: 'article',
  data() {
    return {
      dialogVisible: false,
      content: '',
      table_audit: {
        jsonarray: [{
          id: '',
          title: '',
          type: '',
          userid: '',
          content: ''
        }
        ],
        currentpage: 1,
        totalpage: 1
      }
    }
  },
  mounted() {
    this.List()
  },
  methods: {
    show(val) {
      this.content = val
      this.dialogVisible = true
    },
    List() {
      this.$axios.post('http://localhost:8081/common/ListAudit', {
        currentpage: this.table_audit.currentpage
      }).then(response => {
      this.table_audit = response.data
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

        this.table_audit = response.data
        console.log(this.table_audit)
      }).catch(error => {
        console.log(error)
      })
    }

  }
}
</script>

<style>
.box-card {
  width: 15%;
  float:left;
  padding: 10px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
