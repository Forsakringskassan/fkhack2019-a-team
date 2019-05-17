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
                    <button disabled class="top-nav__link">  {{kortid || 'Guest'}}</button>
                </div>
                <div class="top-nav__user">
                    <button class="top-nav__link" @click="logout">Logga Ut</button>
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
                this.$store.dispatch('logoutAction');
            }
        }
    }
</script>

<style scoped>
    .mft-white-text button {
        color: white;
    }
</style>