const APP = Vue.createApp({
    data() {
        return {
            clientes: [],
            nombreCliente: "",
            apellidoCliente: "",
            accounts: [],
            cards: [],
            cuentaOrigenSeleccionada: '',
            clienteActivo: [],
            accountType: '',
            selected: '',
            accountMethod: '',
            loansLength: 0,
            payingMethod: '',
        }
    },
    created() {
        axios.get('/api/clients/current')
            .then(datos => {
                this.clientes = datos.data
                this.rellenarCampos()
                this.loansLength = datos.data.loans.length
            })

    },
    methods: {
        rellenarCampos() {
            this.nombreCliente = this.clientes.firstName
            this.apellidoCliente = this.clientes.lastName
            this.cards = this.clientes.cards
            this.accounts = this.clientes.accounts.sort((a, b) => {
                if (a.number < b.number) {
                    return -1
                }
            })
            this.accounts.forEach(element => {
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
                .then(response => console.log('signed out!!!'))
        },
        goBack() {
            location = '/web/accounts.html';
        }

    },

    computed: {},
}).mount('#app')