const PARAMID = new URLSearchParams(window.location.search).get("id");


const APP = Vue.createApp({
    data() {
        return {
            cliente: [],
            cuenta: [],
            transacciones: [],
            nombreCuenta: "",
            saldoCuenta: 0,
            saldoInicial: 0,
            pepe: "hola",
            horaActual: "",
            horaActual2: "",
            nombreCliente: "",
            loansLength: 0,
        }
    },

    created() {
        axios.get("http://localhost:8080/api/clients/current")
            .then(datos => {
                this.nombreCliente = datos.data.firstName
                this.cuenta = datos.data.accounts.filter(accounts => accounts.id == PARAMID)
                this.transacciones = this.cuenta[0].transactions
                this.nombreCuenta = this.cuenta[0].number
                this.saldoCuenta = this.cuenta[0].balance
                this.loansLength = datos.data.loans.length
                this.transacciones = this.transacciones.sort((a, b) => {
                    if (a.id > b.id) {
                        return -1
                    }
                })
            })
    },


    methods: {
        convertirFecha(param) {
            param = param.split('T')[0].toString().split("-").reverse().join("-")
            return param
        },

        logout() {
            axios.post('http://localhost:8080/api/logout')
                .then(response => {
                    if (response.status == 201) {
                        location = 'http://localhost:8080/web/accounts.html';
                    } else {
                        location = 'http://localhost:8080/web/index.html';
                    }
                })
                .then(response => console.log('signed out!!!'))
        },
        closeAccount() {
            axios.patch(`http://localhost:8080/api/clients/current/account?accountNumber=${this.nombreCuenta}`)
                .then(response => {
                    if (response.status == 201) {
                        swal("Account closed successfully!")
                            .then(() => location = 'http://localhost:8080/web/accounts.html')

                    } else {
                        location = 'http://localhost:8080/web/accounts.html';
                    }
                })
                .catch(function(error) {
                    if (error.response.status == 403) {
                        swal("Error", "You can not close an account with balance positive or negative", "error");
                    } else {
                        console.log('Error', error.message);
                    }
                });
        },
    },

    computed: {

    },

    mounted() {},
    updated() {},
    destroyed() {},

}).mount('#app')