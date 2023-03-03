// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css' // 样式表
import axios from 'axios'
import VueAxios from 'vue-axios'
import qs from 'qs'
import Stackedit from 'stackedit-js';
import Marked from "marked";

Vue.prototype.$qs = qs
Vue.use(VueAxios, axios)
Vue.use(ElementUI)
Vue.prototype.$axios = axios
Vue.config.productionTip = false
 new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App/>'
})
