const APP = Vue.createApp({
    data() {
        return {
            clientes: [],
            nombreCliente: "",
            apellidoCliente: "",
            cuentas: [],
            consolidatedBalance: 0,
            loans: [],
            loansLength: 0,
            clienteActivo: [],
            accountType: '',
        }
    },
    created() {
        axios.get('/api/clients/current')
            .then(datos => {
                this.clientes = datos.data
                this.rellenarCampos()
                if (this.clienteActivo.loans.length > 0) {
                    this.loans = this.clienteActivo.loans
                    this.loansLength = this.loans.length
                }
            })

    },
    methods: {
        rellenarCampos() {
            this.nombreCliente = this.clientes.firstName
            this.apellidoCliente = this.clientes.lastName
            this.cuentas = this.clientes.accounts.sort((a, b) => {
                if (a.number < b.number) {
                    return -1
                }
            })
            this.cuentas.forEach(element => {
                this.consolidatedBalance += element.balance
            });
            this.clienteActivo = this.clientes
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

        agregarCuenta() {
            axios.post(`/api/clients/current/accounts?accountType=${this.accountType}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => { location = '/web/accounts.html'; })
        },
    },

    computed: {},
}).mount('#app')