const APP = Vue.createApp({
    data() {
        return {
            tipoDeTransferencia: "",
            cardColor: "",
            emailAdd: "",
            passwordAdd: "",
            email: "",
            password: "",
            cuentas: [],
            cuentaSeleccionada: [],
            cuentaOrigenSeleccionada: "",
            cuentasRestantes: [],
            importe: "",
            descripcion: "",
            cuentaDestino: "",
            loansLength: 0,
            nombreClienteDestino: '',
        }
    },

    created() {
        axios.get(`/api/clients/current`)
            .then(datos => {
                this.tarjetas = datos.data.cards
                this.cuentas = datos.data.accounts
                this.loansLength = datos.data.loans.length

            })
    },

    methods: {
        cuentasRestantesFunc() {
            this.cuentasRestantes = this.cuentas.filter(cuenta => cuenta.number != this.cuentaOrigenSeleccionada)
            if (this.cuentaOrigenSeleccionada) {
                this.cuentaSeleccionada = this.cuentas.filter(cuenta => cuenta.number == this.cuentaOrigenSeleccionada)[0]
            }
        },

        nombreClienteCuentaDestino() {
            axios.get(`/api/transactions/destination?email=${this.cuentaDestino}`)
                .then(response => {
                    this.nombreClienteDestino = response.data
                })
        },



        realizarTransferencia() {
            axios.post('/api/transactions', `detail='${this.descripcion}'&amount=${this.importe}&accountOriginNumber=${this.cuentaOrigenSeleccionada}&accountDestinationNumber=${this.cuentaDestino}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(() => swal("Transferencia realizada con éxito!"))
                .then(response => {
                    location = '/web/accounts.html'

                })
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
        goBack() {
            location = '/web/accounts.html';
        }
    },


    computed: {},
    mounted() {},
    updated() {},
    destroyed() {},

}).mount('#app')