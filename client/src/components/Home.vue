<template>

    <div class="h-100">

        <div class="card  day-off-card">
            <div class="card card-header">
                Mina ledigheter
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
    export default {
        name: "Home",
        props: ['user'],
        data() {
            return {}
        },
        computed: {
            items: function () {
                let item = {
                    fromdat: '2019-03-02',
                    tomdat: '2020-11-12',
                    godkand: 'OK'
                };
                let ar = [];
                ar.push(item);
                return ar;
            }
        },
        created() {
            console.log(this.user);
        }
    }
</script>

<style scoped>

</style>