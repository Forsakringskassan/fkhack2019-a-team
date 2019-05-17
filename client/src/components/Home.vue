<template>

    <div class="h-100">

        <AnsokanCard></AnsokanCard>
        <LedigheterCards v-if="userObj.ledighet.length > 0"  :userObj="userObj"></LedigheterCards>
        <NoLedigheterCard v-else></NoLedigheterCard>

    </div>

</template>

<script>

    import axios from 'axios';
    import LedigheterCards from "./LedigheterCard";
    import AnsokanCard from "./AnsokanCard";
    import NoLedigheterCard from "./NoLedigheterCard";

    export default {
        name: "Home",
        components: {NoLedigheterCard, AnsokanCard, LedigheterCards},
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
            console.log(this.user.kortid);
            axios.post('http://localhost:8080/mft/user', {kortid: this.user.kortid}).then(function (response) {
               // console.log(JSON.stringify(response, null, 1));

                self.userObj.info = response.data;
            }).catch(function(error) {
                console.log(JSON.stringify(error, null, 1));
            } );

            axios.post('http://localhost:8080/mft/user/ledighet', {kortid: this.user.kortid}).then(function (response) {
             //   console.log(JSON.stringify(response, null, 1));
                self.userObj.ledighet = response.data;
            }).catch(function(error) {
                console.log(JSON.stringify(error, null, 1));
            });

        }
    }
</script>

<style scoped>

</style>