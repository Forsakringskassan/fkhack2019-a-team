<template>
    <div class="dash-card">
        <div class="card">

            <div class="card-header">
                Mina ledigheter &nbsp;&nbsp;&nbsp;&nbsp; <span class="username">{{userObj.info.namn}}</span>
            </div>
            <div class="card-body">
                <table class="mft-table mft-table__ledigheter">
                    <thead>
                    <tr>

                        <th scope="col">Typ</th>
                        <th scope="col">Fr.o.m.</th>
                        <th scope="col">T.o.m.</th>
                        <th scope="col">Godkänd</th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr class="dayoff-item" v-for="item in items.ledighet" :key="item.id">


                        <td scope="row">{{item.typ.namn}} ({{item.typ.id}})</td>
                        <td>{{item.from}}</td>
                        <td>{{item.tom}}</td>
                        <td>
                            <div v-if="item.godkand === true">
                                Ja
                            </div>
                            <div v-else>
                                Nej
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
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

    .username {
        font-weight: bold;
    }

</style>