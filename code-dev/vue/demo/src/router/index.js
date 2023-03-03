import Vue from 'vue'
import Router from 'vue-router'
import home from '@/components/home'
import UserSpace from '@/components/UserSpace'
import markdown from '@/components/markdown.vue'
import test from '@/components/test.vue'
import N404 from '@/components/404'
import search from '@/components/search'
import manager from '@/components/manage'
import article from '@/components/article'
import login from '@/components/login'
Vue.use(Router)
export default new Router({

    routes: [{
      path: '/login',
      component: login
    },
        {
            path: '/UserSpace',
            name: '/UserSpace',
            component: UserSpace
        },
        {
            path: '/markdown',
            name: 'markdown',
            component: markdown
        }, {
            path: '/test',
            name: 'test',
            component: test
        },
        {
            path: '/home',
            name: 'home',
            component: home
        },
        {
            path: '/',
            redirect: '/home'
        }, {
            path: '/404',
            component: N404
        }, {
         path: '/search/:list/:searchname',
        name: 'search',
         component: search,
         props: true
      },
       {
        path: '/manager',
        component: manager
      },
      {
        path: '/article',
        component: article
      }
    ]
})
