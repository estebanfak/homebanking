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
        axios.get('http://localhost:8080/api/clients/current')
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

        agregarCuenta() {
            axios.post(`/api/clients/current/accounts?accountType=${this.accountType}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => console.log('Creado'))
                .then(response => { location = 'http://localhost:8080/web/accounts.html'; })
        },
    },

    computed: {},
}).mount('#app')