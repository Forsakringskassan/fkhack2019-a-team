<template>
    <div class="dash-card">

        <div class="card">
            <div class="card card-body">
                <h1>ANSÖKAN</h1>
                <div class="card-deck">

                    <div class="card">
                        <div class="card-header">From</div>
                        <div class="card-body">
                            <datepicker :value="date.today"></datepicker>
                        </div>

                    </div>
                    <div class="card">
                        <div class="card-header">Tom</div>
                        <div class="card-body">
                            <datepicker :value="date.tomorrow"></datepicker>
                        </div>

                    </div>

                    <div class="card">
                        <div class="card-header">Typ</div>
                        <div class="card-body">
                            <div class="form-group">
                                <select class="form-control">
                                    <option>Semester</option>
                                    <option>Flex</option>
                                    <option>Föräldraledighet</option>
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

    export default {
        name: "AnsokanCard",
        components: {datepicker},
        data() {
            return {
                date: {
                    today: new Date(),
                    tomorrow: new Date()
                }

            }
        }
    }
</script>

<style scoped>

</style>