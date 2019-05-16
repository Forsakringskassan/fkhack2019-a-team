<template>
    <div class="h-100">
        <nav class="navbar fixed-top top-nav">
            <div class="top-nav__branding">

                <a class="top-nav__logo" href="/"></a>
            </div>
            <!--div class="container-fluid top-nav__container"-->

            <div class="top-nav__user">
                {{kortid || 'Guest'}}
            </div>
        </nav>
    </div>
</template>

<script>
    export default {
        name: "TopNavbar",
        props: {
            kortid: String
        }
    }
</script>

<style scoped>

</style>