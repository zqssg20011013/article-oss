<template>
  <el-row class="tac">
    <el-col :span="4">
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose">
        <el-menu-item index="2" @click="show='user'">
          <i class="el-icon-menu"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="3" @click="show='recommend'">
          <i class="el-icon-document"></i>
          <span slot="title">推荐管理</span>
        </el-menu-item>
        <el-menu-item index="4" @click="show='audit'">
          <i class="el-icon-setting"></i>
          <span slot="title">审核列表</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="20" class="content">
      <template class="user" v-if="show==='user'">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item><a >用户管理</a></el-breadcrumb-item>
        </el-breadcrumb>
        <el-divider></el-divider>
        <el-dialog
          title="Search"
          :visible.sync="dialogVisible_search"
          width="70%"
         >
          <template>
            <el-table
              ref="multipleTable"
              :data="this.table"
              tooltip-effect="dark"
              style="width: 100%"
            >
              <el-table-column
                prop="username"
                label="用户名"
                width="120">
                <template slot-scope="scope">{{ scope.row.username }}</template>
              </el-table-column>
              <el-table-column
                prop="type"
                label="用户权限"
                width="120">
                <template slot-scope="scope"> <div v-if="scope.row.type">
                  <span>管理员</span>
                </div>
                  <div v-else>
                    <span>用户</span>
                  </div></template>
              </el-table-column>
              <el-table-column
                width="300"
                prop="locktype"
                label="用户状态"
                show-overflow-tooltip>
                <template slot-scope="scope">
                   <div v-if="scope.row.locktype">
                     <span>封禁</span>
                   </div>
                  <div v-else>
                    <span>未封禁</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="ban(scope.row.username)">封禁</el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="clock(scope.row.username)">解封</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-dialog>
        <i class="el-icon-edit"></i><el-input v-model="name" style="width: 100px"></el-input>
            <el-button  type="primary" icon="el-icon-search" @click="searchUser"></el-button>
        <el-divider></el-divider>
        <template>
          <el-table
            ref="multipleTable"
            :data="table_user.typeList"
            tooltip-effect="dark"
            style="width: 100%"
           >
            <el-table-column
              prop="username"
              label="用户名"
              width="120">
              <template slot-scope="scope">{{ scope.row.username }}</template>
            </el-table-column>
            <el-table-column
              prop="type"
              label="用户权限"
              width="120">
              <template slot-scope="scope"><div v-if="scope.row.type">
                <span>管理员</span>
              </div>
                <div v-else>
                  <span>用户</span>
                </div></template>
            </el-table-column>
            <el-table-column
              width="300"
              prop="locktype"
              label="用户状态"
              show-overflow-tooltip>
              <template slot-scope="scope"><div v-if="scope.row.locktype">
                <span>封禁</span>
              </div>
                <div v-else>
                  <span>未封禁</span>
                </div></template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="ban(scope.row.username)">封禁</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="clock(scope.row.username)">解封</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <el-pagination
          background
          layout="prev, pager, next"
          :current-page=table_user.currentpage
          :page-count="table_user.totalpage"
          @current-change="currentchange"
        :next-click="currentchange"
        :size-change="currentchange">
        </el-pagination>
        <el-dialog
          title=""
          :visible.sync="dialogVisible"
          width="30%"
          >
          <h2>你确定要封禁？</h2>
          <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible=false">取 消</el-button>
    <el-button type="primary" @click="banneduser">确 定</el-button>
  </span>
        </el-dialog>
        <el-dialog
          title=""
          :visible.sync="dialogVisible2"
          width="30%"
        >
          <h2>你确定要接禁？</h2>
          <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2=false">取 消</el-button>
    <el-button type="primary" @click="unclock">确 定</el-button>
  </span>
        </el-dialog>
      </template>
      <template class="article" v-if="show=='recommend'">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item><a >推荐管理</a></el-breadcrumb-item>
        </el-breadcrumb>
        <el-divider></el-divider>
        <el-divider></el-divider>
        <template>
          <el-table
            ref="multipleTable"
            :data="table_recommend.jsonarray"
            tooltip-effect="dark"
            style="width: 100%"
          >
            <el-table-column
              prop="username"
              label="用户名"
              width="120">
              <template slot-scope="scope">{{ scope.row.userid }}</template>
            </el-table-column>
            <el-table-column
              prop="type"
              label="标题"
              width="120">
              <template slot-scope="scope">{{scope.row.title}}</template>
            </el-table-column>
            <el-table-column
              width="300"
              prop="locktype"
              label="类型"
              show-overflow-tooltip>
              <template slot-scope="scope">{{
                scope.row.type}}</template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  icon="el-icon-view"
                  circle
               @click="audit_before(scope.row)" ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <el-pagination
          background
          layout="prev, pager, next"
          :current-page=table_recommend.currentpage
          :page-count="table_recommend.totalpage"
          @current-change="currentchange2"
          >
        </el-pagination>
        <el-dialog
          title=""
          :visible.sync="dialogVisible_audit"
          width="50%"
        >
          <el-descriptions class="margin-top"  title="信息" >
            <el-descriptions-item label="用户名">{{show_audit.userid}}</el-descriptions-item>
            <el-descriptions-item label="标题">{{show_audit.title}}</el-descriptions-item>
            <el-descriptions-item label="类型">{{show_audit.type}}</el-descriptions-item>
            <el-descriptions-item label="昵称">{{show_audit.nickname}}</el-descriptions-item>
          </el-descriptions>
          <el-divider></el-divider>
          <div id="content" v-html="show_audit.content"></div>
          <span slot="footer" class="dialog-footer">
    <el-button @click="remove">失败</el-button>
    <el-button type="primary" @click="audit">通过</el-button>
  </span>
        </el-dialog>

      </template>
      <template class="audit" v-if="show=='audit'">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item><a >审核通过列表</a></el-breadcrumb-item>
        </el-breadcrumb>
        <el-divider></el-divider>
        <el-divider></el-divider>
        <template>
          <el-table
            ref="multipleTable"
            :data="table_audit.jsonarray"
            tooltip-effect="dark"
            style="width: 100%"
          >
            <el-table-column
              prop="username"
              label="用户名"
              width="120">
              <template slot-scope="scope">{{ scope.row.userid }}</template>
            </el-table-column>
            <el-table-column
              prop="type"
              label="标题"
              width="120">
              <template slot-scope="scope">{{scope.row.title}}</template>
            </el-table-column>
            <el-table-column
              width="300"
              prop="locktype"
              label="类型"
              show-overflow-tooltip>
              <template slot-scope="scope">{{
                  scope.row.type}}</template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  icon="el-icon-view"
                  circle
                  @click="AuditList_before(scope.row)" ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <el-pagination
          background
          layout="prev, pager, next"
          :current-page=table_audit.currentpage
          :page-count="table_audit.totalpage"
          @current-change="currentchange3"
        >
        </el-pagination>
        <el-dialog
          title=""
          :visible.sync="dialogVisible_Audit"
          width="50%"
        >
          <el-descriptions class="margin-top"  title="信息" >
            <el-descriptions-item label="用户名">{{audits.userid}}</el-descriptions-item>
            <el-descriptions-item label="标题">{{audits.title}}</el-descriptions-item>
            <el-descriptions-item label="类型">{{audits.type}}</el-descriptions-item>
            <el-descriptions-item label="昵称">{{audits.nickname}}</el-descriptions-item>
          </el-descriptions>
          <el-divider></el-divider>
          <div id="content" v-html="audits.content"></div>
          <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="cancelaudit">移除</el-button>
  </span>
        </el-dialog>

      </template>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'manage',
  data() {
    return {
     name: '',
      Banned_state: false,
      Banned_time: 0,
      page_user: 1,
      page_recommend: '1',
      show: 'user',
      banned_user: '',
      dialogVisible2: false,
      dialogVisible: false,
      dialogVisible_search: false,
      dialogVisible_audit: false,
      dialogVisible_Audit:false,
      table_user: {
        currentpage: 1,
        totalpage: 1,
        typeList: [{
          username: '',
          type: false,
          locktype: false
        }]
      },
      audits:{},
      show_audit: {},
      table: {},
      table_recommend: {
        jsonarray: [{
          id: '',
          title: '',
          type: '',
          userid: '',
          content: '',
          nickname: '',
          url: ''
        }
        ],
        currentpage: 1,
        totalpage: 1
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
        ],
        currentpage: 1,
        totalpage: 1
      },
    multipleSelection: []
    }
  },
mounted() {
  this.$axios.post('http://localhost:8070/admin/manager').then(response => {
    if ((response.status = 200)) {
       this.Listtable_user()
      this.Listtable_recommend()
      this.Listtable_audit()
}
  }).catch(error => {
    console.log(error)
  })
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
  },
    searchUser() {
      this.table = []
      this.$axios.post('http://localhost:8070/admin/search', {
        username: this.name
      }).then(response => {
        this.table = response.data
        this.dialogVisible_search = true
      }).catch(error => {
        console.log(error)
      })
    },
    Listtable_user() {
      this.$axios.post('http://localhost:8070/admin/List_User', {
         currentpage: this.table_user.currentpage
      }).then(response => {
          this.table_user.typeList = []
          this.table_user.typeList = JSON.parse(JSON.stringify(response.data.typeList))
          this.table_user.totalpage = response.data.totalpage
          this.table_user.currentpage = response.data.currentpage
      }).catch(error => {
        console.log(error)
      })
    },
    Listtable_recommend() {
      this.$axios.post('http://localhost:8081/common/ListAll', {
        currentpage: this.table_recommend.currentpage
      }).then(response => {
        this.table_recommend = response.data
        console.log(response.data)
      }).catch(error => {
        console.log(error)
      })
    },
    Listtable_audit() {
      this.$axios.post('http://localhost:8081/common/ListAudit', {
        currentpage: this.table_audit.currentpage
      }).then(response => {
        this.table_audit = response.data
        console.log(this.table_recommend)
      }).catch(error => {
        console.log(error)
      })
    },
    common(val) {
      this.$axios.post('http://localhost:8070/admin/common', {
         key: val.name
      }).then(response => {
        this.table_recommend = response.data
    }).catch(error => {
      console.log(error)
      })
},
ban(val) {
  // eslint-disable-next-line no-unused-expressions
         this.banned_user = val
        console.log(this.banned_user)
      this.dialogVisible = true
    },
    clock(val) {
      // eslint-disable-next-line no-unused-expressions
      this.banned_user = val
      console.log(this.banned_user)
      this.dialogVisible2 = true
    },
// eslint-disable-next-line vue/no-dupe-keys
banneduser() {
       this.$axios.post('http://localhost:8070/admin/Bannedorunclock', {
          username: this.banned_user,
          locktype: true
       }).then(response => {
            this.Listtable_user()
       }).catch(error => {
         console.log(error)
       })
    },
    unclock() {
      this.$axios.post('http://localhost:8070/admin/Bannedorunclock', {
        username: this.banned_user,
        locktype: false
      }).then(response => {
        this.Listtable_user()
      }).catch(error => {
        console.log(error)
      })
    },
currentchange(val) {
  this.$axios.post('http://localhost:8070/admin/List_User', {
    currentpage: val
  }).then(response => {
    this.table_user.typeList = []
    this.table_user.typeList = JSON.parse(JSON.stringify(response.data.typeList))
    this.table_user.totalpage = response.data.totalpage
    this.table_user.currentpage = response.data.currentpage
  }).catch(error => {
    console.log(error)
  })
    },
    currentchange2(val) {
      this.$axios.post('http://localhost:8081/common/ListAll', {
        currentpage: val
      }).then(response => {
      this.table_recommend.jsonarray = []
        this.table_recommend = response.data
      }).catch(error => {
        console.log(error)
      })
    },
    currentchange3(val) {
      this.$axios.post('http://localhost:8081/common/ListAudit', {
        currentpage: val
      }).then(response => {
        this.table_audit.jsonarray = []
        this.table_audit = response.data
      }).catch(error => {
        console.log(error)
      })
    },
     AuditList_before(val) {
       this.audits = val
       this.dialogVisible_Audit = true
     },
    audit_before(val) {
      this.show_audit = val
      console.log(this.show_audit)
      this.dialogVisible_audit = true
    },
    cancelaudit() {
      this.$axios.post('http://localhost:8081/common/removeaudit', {
        id: this.audits.id
      }).then(response => {
        this.Listtable_audit()
        this.dialogVisible_audit = false
      }).catch(error => {
        console.log(error)
      })
    },
    remove() {
      this.$axios.post('http://localhost:8081/common/concelrecommend', {
        id: this.show_audit.id
      }).then(response => {
        console.log('thisiis remove ')
       this.Listtable_recommend()
        this.Listtable_audit()
        this.dialogVisible_audit = false
      }).catch(error => {
        console.log(error)
      })
    },
    audit() {
      this.$axios.post('http://localhost:8081/common/audit', {
         id: this.show_audit.id}).then(response => {
        this.Listtable_recommend()
        this.Listtable_audit()
        this.dialogVisible_audit = false
      }).catch(error => {
        console.log(error)
      })
    }
}}
</script>

<style >
.content{
  border-radius:4px;
  padding-left:10px;
}
.el-col{
  border-radius: 4px;
}
</style>
