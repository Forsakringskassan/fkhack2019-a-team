import Vue from 'vue';
import Router from 'vue-router';

import Home from './components/Home';
import Login from './components/Login';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '',
            redirect: '/login'
        },
        {
            path: '/minfritid',
            name: 'minfritid',
            component: Home,
            props: true
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        }
    ]
});