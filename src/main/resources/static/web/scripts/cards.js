const APP = Vue.createApp({
    data() {
        return {
            clientes: [],
            cuenta: [],
            nombreCliente: "",
            tarjetasCliente: [],
            tipoTarjeta: [],
            tarjetasClienteFiltradas: [],
            loansLength: 0,
            tarjetaSeleccionada: "",
            radioEliminarTarjeta: false,
        }
    },

    created() {
        axios.get(`/api/clients/current`)
            .then(datos => {
                this.clientes = datos.data
                this.loansLength = datos.data.loans.length
                this.rellenarCampos()
                this.tarjetasClienteFiltradas = this.tarjetasCliente
                this.tarjetasClienteFiltradas = this.tarjetasClienteFiltradas.sort((a, b) => {
                    if (a.id < b.id) {
                        return -1
                    }
                })
            })

    },

    methods: {
        rellenarCampos() {
            this.nombreCliente = this.clientes.firstName
            this.tarjetasCliente = this.clientes.cards
        },
        modificarNumeroTarjeta(param) {
            return param.slice(0, 4) + " " + param.slice(4, 8) + " " + param.slice(8, 12) + " " + param.slice(12, 16)
        },
        modificarFecha(param) {
            return param.slice(2, 10).slice(0, 5).split("-").reverse().join("/")
        },
        compararFechas(param) {
            let thruDateYear = param.slice(0, 4)
            let thruDateMonth = param.slice(5, 7)
            let today = new Date()
            if (today.getFullYear() < thruDateYear) {
                return false
            } else {
                if ((thruDateMonth - (today.getMonth() + 1)) > 1) {
                    return false
                } else if ((thruDateMonth - (today.getMonth() + 1)) == 1 || (thruDateMonth - (today.getMonth() + 1)) == 0) {
                    return true
                } else {
                    return "vencida"
                }
            }

        },
        filtrarPorTipoDeTarjeta() {
            if (this.tipoTarjeta.length == 0) {
                this.tarjetasClienteFiltradas = this.tarjetasCliente

            } else {
                this.tarjetasClienteFiltradas = []
                this.tipoTarjeta.forEach(tipo => {
                    this.tarjetasCliente.forEach(tarjeta => {
                        if (tipo == tarjeta.cardType) {
                            this.tarjetasClienteFiltradas.push(tarjeta)
                        }
                    })
                })
            }
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
        seleccionarTarjeta() {
            this.radioEliminarTarjeta = !this.radioEliminarTarjeta
        },
        eliminarTarjeta() {
            if (this.tarjetaSeleccionada != "") {
                axios.patch(`/api/clients/current/cards?cardNumber=${this.tarjetaSeleccionada}`)
                    .then(() => {
                        swal("", "Tarjeta eliminada", "info")
                            .then(() => {
                                location = "/web/cards.html"
                            })
                    })
                    .catch(function(error) {
                        if (error.response.status == 403) {
                            swal("Error", "Número de tarjeta no encontrado", "error");
                        } else {
                            swal("Error", error.message, "error");
                        }
                    });
            } else {
                swal("Error", "Debes elegir una tarjeta", "error");
            }
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