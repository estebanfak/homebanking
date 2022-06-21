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
        axios.get(`http://localhost:8080/api/clients/current`)
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
            axios.get(`http://localhost:8080/api/transactions/destination?email=${this.cuentaDestino}`)
                .then(response => {
                    this.nombreClienteDestino = response.data
                })
        },



        realizarTransferencia() {
            axios.post('/api/transactions', `detail='${this.descripcion}'&amount=${this.importe}&accountOriginNumber=${this.cuentaOrigenSeleccionada}&accountDestinationNumber=${this.cuentaDestino}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(() => console.log('Transferencia realizada'))
                .then(() => swal("Transferencia realizada con éxito!"))
                .then(response => {
                    location = 'http://localhost:8080/web/accounts.html'

                })
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
                .then(() => console.log('signed out!!!'))
        },
    },


    computed: {},
    mounted() {},
    updated() {},
    destroyed() {},

}).mount('#app')