<template>
<section class="user">
  <div class="user_options-container">
    <div class="user_options-text">
      <div class="user_options-unregistered">
        <h2 class="user_unregistered-title">没有账号？</h2>
        <p class="user_unregistered-text">点击按钮注册成为会员.</p>
        <button class="user_unregistered-signup" id="signup-button" @click="change()">注册</button>
      </div>

      <div class="user_options-registered">
        <h2 class="user_registered-title">已有账号?</h2>
        <p class="user_registered-text">点击按钮会员登录.</p>
        <button class="user_registered-login" id="login-button" @click="change2()">登录</button>
      </div>
    </div>

    <div class="user_options-forms" id="user_options-forms">
      <div class="user_forms-login">
        <h2 class="forms_title">登录</h2>
        <div class="forms_form"   name="actionForm" id="actionForm">
          <fieldset class="forms_fieldset">
            <div class="forms_field">
              <input type="text" placeholder="账号" class="forms_field-input" name="userCode" v-model="Admin.username"  required autofocus />
            </div>
            <div class="forms_field">
              <input type="password" placeholder="密码" id="userPassword" name="userPassword"  v-model="Admin.password"  class="forms_field-input" required />
            </div>
          </fieldset>
          <div class="forms_buttons">
            <button   value="登录" @click="logind" class="forms_buttons-action">登录</button>
          </div>
        </div>
      </div>
      <div class="user_forms-signup">
        <h2 class="forms_title">注册</h2>
        <div class="forms_form">
          <fieldset class="forms_fieldset">
            <div class="forms_field">
              <input type="text" placeholder="账号" class="forms_field-input" v-model="create.username" required />
            </div>
            <div class="forms_field">
              <input type="password" placeholder="密码" class="forms_field-input" v-model="create.password" required />
            </div>
          </fieldset>
          <div class="forms_buttons">
            <button  value="注册" @click="creates" class="forms_buttons-action"></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

</template>
  <style>
    @import "../css/1.css";
   </style>

<script>
export default{
    name: 'login2',
    data() {
        return {
             create: {
               username: '',
               password: ''
             },
             Admin: {
               username: '',
               password: ''
        },
          code: 0,
          cache: false
        }
    },
mounted() {

    },
    methods: {
     change() {
        console.log('ok')
            var userForms = document.getElementById('user_options-forms')
            userForms.classList.remove('bounceRight')
            userForms.classList.add('bounceLeft')
     },
    change2() {
 var userForms = document.getElementById('user_options-forms')
             userForms.classList.remove('bounceLeft')
            userForms.classList.add('bounceRight')
    },
     logind() {
       console.log(this.Admin)
      this.$axios.post('http://localhost:8070/admin/login', {
         username: this.Admin.username,
      password: this.Admin.password
      }
    )
  .then(response => {
     this.code = response.data.code
    console.log(this.code === 103)
    if ((this.code === 103)) {
      this.$router.push({ path: '/manager' })
    } else if ((response.data.code === 105)) {
      // eslint-disable-next-line standard/object-curly-even-spacing
      this.$router.push({ path: '/404'})
    } else if (response.data.code === 100) {
         localStorage.token = response.data.data
      console.log(localStorage.token)
      this.$router.push({
        path: '/UserSpace',
        query: {
              user: {username: this.Admin.username,
                      password: this.Admin.password}
        }
      })
    }
})
  .catch(function (error) {
    console.log(error)
    })
    },
      show() {
        // eslint-disable-next-line no-unused-vars
        var that = this
        console.log(this.Admin)
        this.$axios.post('http://localhost:8070/admin/login', {
            username: this.Admin.username,
            password: this.Admin.password
          }
        )
          .then(function (response) {
        console.log(response)
            console.log(response.data.code === 103)
          })
          .catch(function (error) {
            console.log(error)
          })
      },
    creates() {
       console.log(this.create)
       this.$axios.post('http://localhost:8070/admin/create', {
         username: this.create.username,
         password: this.create.password
      }).then(response => {
         if (response.status === 200) {
           window.alert('创建成功')
         }
       })
    }

}
}

</script>
