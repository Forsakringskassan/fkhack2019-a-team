<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-data-table :headers="headers" :items="Personer" class="elevation-1">
        <template v-slot:items="props">
            <td>{{ props.item.namn }}</td>
            <td v-for="(dag, index) in props.item.dagar" :key="index">{{dag}}</td>
        </template>
    </v-data-table>
</template>


<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                headers: [],
                Personer: [],
            }
        },
        methods: {
            getPersoner() {
                let self = this;
                let date = new Date()
                let month = date.getMonth() <= 9 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
                let firstDay = date.getFullYear() + '-' + month + '-01';

                axios.post("http://localhost:8080/mft/enhetsvy", {
                    kortid: this.$store.getters.getUser.kortid,
                    date: firstDay
                }).then(function (response) {
                    self.Personer = response.data
                });
            },

            daysInCurrentMonth(month) {
                let datum = new Date();
                let antal = new Date(datum.getFullYear(), month + 1, 0).getDate();
                let dagar = new Object();
                let array = []
                dagar.text = "Namn";
                dagar.value = "name";
                array.push(dagar);
                this.headers = array;

                for (var i = 1; i <= antal; i++) {
                    let dagar = new Object();
                    dagar.text = [i]
                    dagar.value = [i]
                    array.push(dagar)
                }
            }
        },
        created() {
            this.getPersoner()
            this.daysInCurrentMonth(new Date().getMonth())
        }
    }
</script>