<template>
    <div class="h-100">
        <nav class="navbar fixed-top top-nav ">
            <div class="top-nav__links">

                <a class="top-nav__logo" href="/"></a>

                <button class="top-nav__link">
                    <router-link :to="{name: 'minfritid'}">Min Fritid</router-link>
                </button>
                <button class="top-nav__link">
                    <router-link :to="{name: 'enhetsvy'}">Enhet</router-link>
                </button>
            </div>

            <div class="top-nav__links mft-white-text">

                <div class="top-nav__user">
                    <button disabled class="top-nav__link">
                        <div v-if="kortid">Inloggad som: &nbsp;&nbsp;&nbsp;&nbsp; {{kortid }}</div>
                        <div v-else>Ej Inloggad</div>
                    </button>
                </div>
                <div class="top-nav__user">
                    <button class="top-nav__link" @click="logout">
                        <div v-if="kortid">Logga Ut</div>
                    </button>
                </div>
            </div>
        </nav>
    </div>
</template>

<script>
    export default {
        name: "TopNavbar",
        props: {
            kortid: String
        },
        methods: {
            logout: function () {
                let self = this;
                self.$store.dispatch('logoutAction').then(function () {
                    //self.$router.push({name: 'login'});
                    window.location.href = '/login';
                }).catch(function (error) {
                    console.log(error);
                    window.location.href = '/login';
                });
            }
        }
    }
</script>

<style scoped>
    .mft-white-text button {
        color: white;
    }
</style>