<template>

    <div class="h-100">

        <AnsokanCard></AnsokanCard>
        <LedigheterCards :userObj="userObj"></LedigheterCards>

    </div>

</template>

<script>

    import axios from 'axios';
    import LedigheterCards from "./LedigheterCards";
    import AnsokanCard from "./AnsokanCard";

    export default {
        name: "Home",
        components: {AnsokanCard, LedigheterCards},
        //props: ['user'],
        data() {
            return {
                userObj: {
                    info: '',
                    ledighet: ''
                }
            }
        },
        computed: {
            user: function () {
                return this.$store.getters.getUser;
            },
            items: function () {
                return this.userObj
            }
        },
        created() {
            let self = this;
            axios.post('http://localhost:8080/mft/user', {kortid: this.user.kortid}).then(function (response) {
              //  console.log(JSON.stringify(response, null, 1));
                self.userObj.info = response.data;
            });

            axios.post('http://localhost:8080/mft/user/ledighet', {kortid: this.user.kortid}).then(function (response) {
                console.log(JSON.stringify(response, null, 1));
                self.userObj.ledighet = response.data;
            });

        }
    }
</script>

<style scoped>

</style>