<template>
    <div class="page">
        <TopNavbar :kortid="getLoggedInUser.kortid"></TopNavbar>
        <div class="page__wrapper page__wrapper--overflow">
                <router-view></router-view>
        </div>

    </div>
</template>

<script>
    import TopNavbar from "./components/TopNavbar";

    export default {
        name: 'App',
        components: {
            TopNavbar,
        },
        computed: {
            getLoggedInUser: function () {
                return this.$store.getters.getUser;
            }
        },
        data() {
            return {
                kortid: ''
            }
        }
    }
</script>

<style lang="scss">
    @import 'assets/scss/main';
</style>
