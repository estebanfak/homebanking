const APP = Vue.createApp({
    data() {
        return {
            cardType: "",
            cardColor: "",
            emailAdd: "",
            passwordAdd: "",
            email: "",
            password: "",
            tarjetas: [],
            tarjetasDebito: 0,
            tarjetasCredito: 0,
            debito: "DEBITO",
            credito: "CREDITO",
            loansLength: 0,
        }
    },

    created() {
        axios.get(`/api/clients/current`)
            .then(datos => {
                this.tarjetas = datos.data.cards
                this.loansLength = datos.data.loans.length
                this.tarjetasDebito = this.cantidadDeTarjetas(this.debito)
                this.tarjetasCredito = this.cantidadDeTarjetas(this.credito)
            })
    },

    methods: {

        cantidadDeTarjetas(param) {
            let aux = []
            this.tarjetas.forEach(element => {
                aux.push(element)
            });
            if (aux != null) {
                return aux.filter(tarjeta => tarjeta.cardType == param).length
            } else {
                return 0
            }
        },

        createCard() {
            axios.post('/api/clients/current/cards', `cardType=${this.cardType}&cardColor=${this.cardColor}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    location = '/web/cards.html'
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

// --------------------------------------------------------------------------------Peticion de prestamos:---------------------------------------------------------------
// axios.post('/api/loans',{ id: 1, amount: 10000, payments: 12, accountDestiny: "VIN001", loanName: "Hipotecario"}).then(() => console.log('created')).catch(() => console.log('error'))