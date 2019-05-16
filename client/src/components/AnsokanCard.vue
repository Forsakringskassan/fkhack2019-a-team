<template>
    <div class="dash-card">

        <div class="card">
            <div class="card card-body">
                <h1>Ledighet</h1>
                <div class="card-deck">

                    <div class="card">
                        <div class="card-header">From</div>
                        <div class="card-body">
                            <datepicker v-model="date.from"></datepicker>
                        </div>

                    </div>
                    <div class="card">
                        <div class="card-header">Tom</div>
                        <div class="card-body">
                            <datepicker v-model="date.tom"></datepicker>
                        </div>

                    </div>

                    <div class="card">
                        <div class="card-header">Typ {{selected}}</div>
                        <div class="card-body">
                            <div class="form-group">
                                <select class="form-control" v-model="selected">
                                    <option v-for="typ in ledighetstyper" :key="typ">{{typ}}</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <button class="btn btn-primary" @click="ansok" type="button">Skicka</button>
            </div>
        </div>

    </div>
</template>

<script>
    import datepicker from 'vuejs-datepicker'
    import moment from 'moment';
    import axios from 'axios';

    export default {
        name: "AnsokanCard",
        components: {datepicker},
        data() {
            return {
                date: {
                    from: moment().toDate(),
                    tom: moment(moment()).add(1, 'days').toDate()
                },
                selected: 'Semester',
                ledighetstyper: []


            }
        },
        computed: {
            dates: function () {
                let from = moment().toDate();
                let tom = moment(from).add(1, 'days').toDate();

                return {from: from, tom: tom};
            }
        },
        created() {
          this.getLedighetstyper();
        },
        methods: {
            ansok: function () {
                let url = 'http://localhost:8080/mft/user/ledighet/create';

                let body = {
                    kortid: this.$store.getters.getUser.kortid,
                    from: this.formatDate(this.date.from),
                    tom: this.formatDate(this.date.tom),
                    ledighetstyp: this.selected
                };

                console.log(JSON.stringify(body, null, 1));
                axios.post(url, body).then(function (response) {

                    console.log(response);
                })
            },
            getLedighetstyper: function() {

                let self = this;

                axios.post('http://localhost:8080/mft/ledighetstyper', {headers: {'charset': 'utf8'}}).then(function (response) {
                    self.ledighetstyper = response.data;
                })
            },
            formatDate: function (date) {


                let month = date.getMonth() <= 9 ? "0"+(date.getMonth()+1) : (date.getMonth() +1);

                return date.getFullYear() + '-'+ month + '-' + date.getDate();

            }
        }
    }
</script>

<style scoped>

</style>