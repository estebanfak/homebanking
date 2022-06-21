const APP = Vue.createApp({
    data() {
        return {
            loanName: "",
            importe: "",
            cuentaAcreditacion: "",
            cuentas: "",
            cantidadDePagos: [],
            cantidadDePagosSeleccionado: 0,
            loans: [],
            loanFiltrado: [],
            maxAmountSelected: "",
            maxAmountSelectedCompare: 0,
            loansLength: 0,
        }
    },

    created() {
        axios.get(`http://localhost:8080/api/clients/current`)
            .then(datos => {
                this.cuentas = datos.data.accounts
                this.prestamosSolicitados = datos.data.loans
                this.loansLength = datos.data.loans.length
            })
        axios.get('http://localhost:8080/api/loans')
            .then(datos => {
                this.loans = datos.data
            })
    },

    methods: {
        filtroLoans() {
            axios.get('http://localhost:8080/api/loans')
                .then(datos => {
                    this.loans = datos.data
                    this.loanFiltrado = this.loans.filter(loan => loan.name == this.loanName)
                    this.cantidadDePagos = this.loanFiltrado[0].payments
                    this.maxAmountSelected = "Max amount: " + this.loanFiltrado[0].maxAmount
                    this.maxAmountSelectedCompare = this.loanFiltrado[0].maxAmount

                })
        },


        solicitarPrestamo() {
            if (this.loanFiltrado[0].id != null && this.importe > 0 && this.importe <= this.loanFiltrado[0].maxAmount && this.cantidadDePagosSeleccionado != null && this.cuentas.filter(cuenta => cuenta.name == this.cuentaAcreditacion) != null && this.loans.filter(loan => loan.name == this.loanFiltrado[0].name) != null) {

                axios.post('/api/loans', {
                        id: this.loanFiltrado[0].id,
                        amount: this.importe,
                        payments: this.cantidadDePagosSeleccionado,
                        accountDestiny: this.cuentaAcreditacion,
                        loanName: this.loanFiltrado[0].name
                    })
                    .then(() => console.log('created'))
                    .then(() => swal("Solicitud de prestamo completada!"))
                    .then(() => location = 'http://localhost:8080/web/accounts.html')
                    .catch(() => console.log('error'))
            }
        },

        prestamosSolicitadosFunc(param) {
            let aux = this.prestamosSolicitados.filter(loan => loan.name == param)
            if ((aux.length == 0)) {
                return true
            } else {
                return false
            }
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


    computed: {

    },
    mounted() {},
    updated() {},
    destroyed() {},

}).mount('#app')

// --------------------------------------------------------------------------------Peticion de prestamos:---------------------------------------------------------------
// axios.post('/api/loans',{ id: 1, amount: 10000, payments: 12, accountDestiny: "VIN001", loanName: "Hipotecario"}).then(() => console.log('created')).catch(() => console.log('error'))