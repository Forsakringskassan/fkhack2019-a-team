<template>

    <div class="h-100">

        <div class="card  day-off-card">
            <div class="card card-header">
                Mina ledigheter - {{userObj}}
            </div>
            <div class="card card-body">
                <table class="table-style">
                    <thead>
                    <tr>

                        <th scope="col">from</th>
                        <th scope="col">tom</th>
                        <th scope="col">Godkänd</th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr class="dayoff-item" v-for="item in items" :key="item.fromdat">


                        <td scope="row"> {{item.fromdat}}</td>
                        <td> {{item.tomdat}}</td>
                        <td> {{item.godkand}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>


    </div>

</template>

<script>

    import axios from 'axios';
    export default {
        name: "Home",
        //props: ['user'],
        data() {
            return {
                userObj: ''
            }
        },
        computed: {
            user: function(){
              return this.$store.getters.getUser;
            },
            items: function () {
            /*
                let item = {
                    fromdat: '2019-03-02',
                    tomdat: '2020-11-12',
                    godkand: 'OK'
                };
                let ar = [];
                ar.push(item);
                return ar;
             */
              return this.userObj
            }
        },
        created() {
            axios.post('http://localhost:8080/mft/user', {kortid: this.user.kortid}).then(function (response) {
                console.log(JSON.stringify(response, null,1));
                this.userObj = response.data;
            })


        }
    }
</script>

<style scoped>

</style>