<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div class="h-100">


        <h2 class="month-head">{{month}}</h2>
        <v-data-table hide-actions :headers="headers" :items="Personer" class="elevation-1 lillen">
            <template v-slot:items="props">
                <td>{{ props.item.namn }}</td>
                <td v-for="(dag, index) in props.item.dagar" :key="index" :class="{ledig: dag != null}">{{box(dag)}}</td>
            </template>
        </v-data-table>
    </div>
</template>


<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                currMonth: '',
                headers: [],
                Personer: [],
                months: [
                    'Januari',
                    'Februari',
                    'Mars',
                    'April',
                    'Maj',
                    'Juni',
                    'Juli',
                    'Augusti',
                    'September',
                    'Oktober',
                    'November',
                    'December'

                ]
            }
        },
        methods: {
            box(ledighetstyp) {
                return ledighetstyp !== null ? ledighetstyp.id : null;
            },
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
                dagar.sortable = false;
                array.push(dagar);
                this.headers = array;

                for (var i = 1; i <= antal; i++) {
                    let dagar = new Object();
                    dagar.text = [i]
                    dagar.value = [i]
                    dagar.sortable = false
                    array.push(dagar)
                }
            }
        },
        computed: {
          month: function() {
              return this.months[this.currMonth];
          }
        },
        created() {
            this.getPersoner()
            this.currMonth = new Date().getMonth();
            this.daysInCurrentMonth(this.currMonth);
        }
    }
</script>
<style lang="scss">
    .ledig {
        background-color: lightgray;
    }

    .lillen {
        margin-top: 1em;
    }
    .lillen tbody tr td {
        font-size: 8pt;
    }

    .lillen td, .lillen th {
        text-align: center !important;
        padding: 0 4px !important;
        width: 2em;
        border-right: 1px solid rgba(0, 0, 0, 0.12);
        font-size: 8pt;

    }
    h2.month-head {

        text-align: center;
        margin-top: 10px;
    }
</style>