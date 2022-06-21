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
        axios.get('http://localhost:8080/api/clients/current')
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

    },

    computed: {},
}).mount('#app')