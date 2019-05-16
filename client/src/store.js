import Vue from 'vue';
import Vuex from 'vuex';
import router from './router';
import createPersistedState from 'vuex-persistedstate';
import * as Cookie from 'js-cookie';

Vue.use(Vuex);

export default new Vuex.Store(
    {
        state: {user: ''},
        getters: {
            getUser: function (state) {
                return state.user;
            }
        },
        mutations: {
            setLoggedInUser: function (state, user) {
                state.user = user;
                 router.push({name: 'minfritid', params: {user: state.user}});

            }
        },
        actions: {
            loginAction: function ({commit}, kortid) {
                let userObj = {
                    "kortid": kortid,
                    "name": "Testor Testersen"
                };

                commit('setLoggedInUser', userObj);
            }
        },
        plugins: [
            createPersistedState({
                getState: (key) => Cookie.getJSON(key),
                setState: (key, state) => Cookie.set(key, state, {expires: 3, seure: true})
            })
        ]
    });

