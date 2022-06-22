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
            horaActual: "",
            horaActual2: "",
            nombreCliente: "",
            loansLength: 0,
            toDate: '',
            dateFrom: ''
        }
    },

    created() {
        axios.get("/api/clients/current")
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
            axios.post('/api/logout')
                .then(response => {
                    if (response.status == 201) {
                        location = '/web/accounts.html';
                    } else {
                        location = '/web/index.html';
                    }
                })

        },
        closeAccount() {
            axios.patch(`/api/clients/current/account?accountNumber=${this.nombreCuenta}`)
                .then(response => {
                    if (response.status == 201) {
                        swal("Account closed successfully!")
                            .then(() => location = '/web/accounts.html')

                    } else {
                        location = '/web/accounts.html';
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

        date() {
            if (this.nombreCuenta != '' && this.dateFrom != '' && this.toDate != '') {
                axios.get(`/api/pdf/transactions?accountNumber=${this.nombreCuenta}&fromDate=${this.dateFrom}&toDate=${this.toDate}`)
                    .then(response => {
                        swal("Report created successfully!")
                    }).catch(function(error) {
                        swal("Error", error.message, "error");
                    });
            } else {
                swal("Error", "You must complete all the fields", "error");
            }
        },
        goBack() {
            location = '/web/accounts.html';
        }
    },

    computed: {

    },

    mounted() {},
    updated() {},
    destroyed() {},

}).mount('#app')