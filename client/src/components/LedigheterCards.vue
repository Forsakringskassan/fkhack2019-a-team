<template>
    <div class="card  dash-card">
        <div class="card card-header">
            Mina ledigheter - {{userObj.info.namn}}
        </div>
        <div class="card card-body">
            <table class="table-style">
                <thead>
                <tr>

                    <th scope="col">typ</th>
                    <th scope="col">from</th>
                    <th scope="col">tom</th>
                    <th scope="col">Godkänd</th>
                </tr>
                </thead>
                <tbody>

                <tr class="dayoff-item" v-for="item in items.ledighet" :key="item.from">


                    <td scope="row">{{item.typ}}</td>
                    <td>{{item.from}}</td>
                    <td> {{item.tom}}</td>
                    <td> {{item.godkand}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    export default {
        name: "LedigheterCard",
        props: ['userObj'],
        data() {
            return {
                items: this.userObj
            }
        }
    }
</script>

<style scoped>

</style>