<template>
  <el-container style="width:100%;border:1px solid #eee; position: absolute;">
    <el-header style="text-align: center; font-size: 12px">
      <el-row>
        <el-col :span="24" >
          <div class="grid-content bg-purple-dark">

        </div>
          <el-menu
            class="el-menu-demo"
            mode="horizontal"
            style="float:left;margin-left:150px;"
          >
            <el-menu-item @click="home">
              <el-image
                style="width: 50px; height: 50px"
                :src="img"
                ></el-image>
            </el-menu-item>
            <el-menu-item index="1" style="margin-left:30px; height:60px;"
                          v-on:click="article()">文章</el-menu-item
            >
            <el-menu-item
              index="4"
              style="margin-left:10px ;height:60px;"
              v-on:click="login()"
              >个人中心</el-menu-item
            >
            <el-menu-item @click="clearcache">
              <el-image
                style="width: 50px; height: 50px"
                :src="quit"
              ></el-image>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="background-color: rgb(249,249,249);">
      <router-view />
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
      quit: 'http://localhost:9000/images/q.png',
      img: 'http://localhost:9000/images/android-chrome-192x192.png',
      url:
        'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      input: '',
      charAtcircleUrl:
        'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
    }
  },
  methods: {
    article() {
     this.$router.push({
        path: '/article'
      })
    },
    login() {
      var token = localStorage.token
      console.log(token)
      var that = this
      if (token != null) {
        this.$axios
          .post('http://localhost:8070/admin/token', {
            token: localStorage.token
          })

          .then(function(response) {
            console.log(response)
            if ((response.data.code = 200)) {
              that.$router.push({
                path: '/UserSpace',
                query: {
                  user: response.data.data
                }
              })
            }
          })
          .catch(function(error) {
            that.$router.push({ path: '/login' })
            console.log(error)
          })
      } else {
        that.$router.push({ path: '/login' })
      }
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
    },
clearcache() {
      var token = localStorage.token
      console.log(token)
      if (token != null) {
        this.$axios.post('http://localhost:8070/admin/cache', {
          token: token
        }).then(response => {
          localStorage.clear()
          this.$router.push({path: '/home'})
        })
      }
    },
    home() {
      this.$router.push({path: '/home'})
    }
  }
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
