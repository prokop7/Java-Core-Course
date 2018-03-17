<template>
    <div id="app">
        <el-container>
            <el-header>
                <el-button v-if="!token" @click="dialogFormVisible = true">Login/Register</el-button>
                <el-button v-if="!!token" @click="logout()">Logout</el-button>
            </el-header>
            <el-container>
                <el-main>
                    <p v-if="number && token">Your random number: {{number}}</p>
                    <el-button v-if="number && token" type="success" @click="getNumber">Generate</el-button>
                    <p v-if="error">{{error}}</p>
                    <p v-if="!token">You need to authenticate</p>
                </el-main>
            </el-container>
        </el-container>
        <el-dialog title="Fill in forms" :visible.sync="dialogFormVisible">
            <p v-if="errorMessage">{{errorMessage}}</p>
            <el-form :model="form">
                <el-form-item label="Login" :label-width="formLabelWidth">
                    <el-input v-model="form.login" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="Password" :label-width="formLabelWidth">
                    <el-input type="password" v-model="form.password" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">Cancel</el-button>
                        <el-button type="primary" @click="login()">Login</el-button>
                        <el-button type="primary" @click="register()">Register</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    import Ajax from "@/api"

    export default {
        components: {},
        data: function () {
            return {
                number: undefined,
                error: "",
                errorMessage: "",
                dialogFormVisible: false,
                token: "",
                form: {
                    login: "",
                    password: ""
                },
                formLabelWidth: '120px'
            };
        },
        methods: {
            getNumber() {
                let _this = this;

                Ajax.getContent((data) => {
                    _this.number = data;
                    _this.error = "";
                }, (status, e) => {
                    _this.number = undefined;
                    _this.error = e.message;
                    _this.deleteToken();
                })
            },
            deleteToken() {
                console.log("Delete token");
                this.token = "";
                localStorage.removeItem('token');
                Ajax.setToken(null)
            },
            setToken(token) {
                console.log(token);
                this.token = token;
                localStorage.setItem("token", token);
                Ajax.setToken(token);
            },
            logout() {
                let _this = this;
                Ajax.logout(() => _this.deleteToken());
            },
            login() {
                let _this = this;
                Ajax.login(this.form.login, this.form.password, (data) => {
                    _this.setToken(data.token);
                    _this.dialogFormVisible = false;
                    _this.errorMessage = "";
                    _this.getNumber();
                }, (status, e) => {
                    _this.errorMessage = e.message;
                    console.log(e);
                });
            },
            register() {
                let _this = this;
                Ajax.register(
                    this.form.login,
                    this.form.password,
                    () => _this.login(),
                    (status, e) => {
                        _this.errorMessage = e.message;
                        console.log(e);
                    });
            }
        },
        mounted: function () {
            this.setToken(localStorage.getItem('token'));
            if (this.token)
                this.getNumber();
        },
    }
</script>

<style lang="scss">
    .el-header, .el-footer {
        background-color: #B3C0D1;
        /*color: #333;*/
        text-align: center;
        line-height: 60px;
        padding: 0;
    }

    .el-main {
        background-color: #E9EEF3;
        text-align: center;
        color: #333;
        line-height: 100%;
        height: 100%;
    }

    .el-container {
        height: 100%;
    }

    html, body {
        height: 100%;
        margin: 0;
    }

    #app {
        height: 100vh;
    }

    * {
        font-family: "Helvetica Neue", Arial, Helvetica, sans-serif;
        /*line-height: 18px;*/
        font-weight: 400;
    }
</style>