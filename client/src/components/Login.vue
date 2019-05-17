<template>

    <div class="h-100">

        <div class="card login-card">
            <div class="card-header">
                Login
            </div>
            <div class="card-body">
                <div class="input-group input-group-md mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroup-sizing-md">Kortid</span>
                    </div>
                    <input type="text" class="form-control" v-model="kortid" aria-label="Small"
                           aria-describedby="inputGroup-sizing-sm">
                </div>
            </div>
            <div class="card-footer dash-card-footer">
                <button class="btn btn-primary" @click="login" type="button">Login</button>
            </div>
        </div>
    </div>


</template>

<script>

    import {mapActions} from 'vuex';


    export default {
        name: "Login",
        data() {
            return {
                kortid: ''
            }
        },
        methods: {
            ...mapActions([
                'loginAction'
            ]),
            login: function () {
                if (this.kortid) {
                    let user = {
                        kortid: this.kortid,
                        name: "Testor Testersen"
                    };
                    console.log(user);
                    this.loginAction(this.kortid);
                }
            }
        }
    }
</script>

