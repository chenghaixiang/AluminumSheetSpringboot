import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import History from '../views/History.vue'
import Index from  '../views/Index.vue'
import File from  '../views/File.vue'
import echarts from  '../views/echarts.vue'
import pingtai from '../views/pintai.vue'
import table_type from "@/components/table_type";
import LogIn from '../views/LogIn.vue'
import Register from "@/views/Register";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    redirect:'/LogIn',

    children:[
      {
        path: 'History',
        name: 'History',
        component: History,
      },
      {
        path: 'Index',
        name: 'Index',
        component: Index,
      },
      {
        path: 'File',
        name: '文件管理',
        component: File,
      },
      {
        path: 'echarts',
        name: '图表展示',
        component: echarts,
      },
    ]
  },
  {
    path: '/pingtai',
    name: 'pingtai',
    component: pingtai,
  },
  {
    path: '/LogIn',
    name:'LogIn',
    component: LogIn,
  },
  {
    path: '/Register',
    nameL:'Register',
    component: Register
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
