<template>
    <div class="h-100">


        <div class="month-head">
            <div>
                <button class="month-head__month__previous" @click="changeMonth(-1)"></button>
            </div>
            <div class="month-head__month"> {{month}}</div>
            <div>
                <button class="month-head__month__next" @click="changeMonth(1)"></button>
            </div>
        </div>


        <table class="mft-table mft-table__enhet">
            <thead>
            <tr>

                <th scope="col">Namn</th>
                <th v-for="dag in daysInMonth" :key="dag">{{dag}}</th>
            </tr>
            </thead>
            <tbody>

            <tr class="dayoff-item" v-for="person in Personer" :key="person.namn">
                <td class="mft-table__enhet__namn">{{person.namn}}</td>
                <td v-if="index < daysInMonth" class="mft-table__enhet__dag" :style="cellWidth(dag)"
                    v-for="(dag, index) in person.dagar" :key="index">
                    {{dag !== null ? dag.id : ""}}
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</template>


<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                currMonth: '',
                numberOfDays: '',
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
                let month = this.currMonth <= 9 ? "0" + (this.currMonth + 1) : (this.currMonth +1);
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
                this.numberOfDays = antal;

                for (var i = 1; i <= antal; i++) {
                    let dagar = new Object();
                    dagar.text = [i]
                    dagar.value = [i]
                    dagar.sortable = false
                    array.push(dagar)
                }
            },
            cellWidth: function (dag) {
                if (dag === null) {
                    return {};
                }
                return {
                    "width": (88 / this.daysInMonth) + "%",
                    "background-color": this.getColor(dag.id)
                }
            },
            getColor: function (id) {
                switch (id) {
                    case "TJ":
                        return "red";
                    case "S":
                        return "green";
                    case "FL":
                        return "orange";
                    case "FÖ":
                        return "yellow";
                }
            },
            changeMonth: function (dir) {

                if ((this.currMonth + dir > 11) || (this.currMonth + dir < 0)) {
                    return;
                }
                this.currMonth += dir;

                this.updateTable();

                this.$emit('changeMonth', dir);
            },
            updateTable: function () {
                console.log("update");
                this.getPersoner();
                this.daysInCurrentMonth(this.currMonth);
            }
        },
        computed: {
            month: function () {
                return this.months[this.currMonth];
            },
            daysInMonth: function () {
                return this.numberOfDays;
            },

        },
        created() {
            this.currMonth = new Date().getMonth();
            this.updateTable();
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