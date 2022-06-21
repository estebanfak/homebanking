Vue.createApp({
    data() {
        return {
            clientes: [],
            clientesJson: {},
            firstName: "",
            lastName: "",
            email: "",
            firstNameEdit: "",
            lastNameEdit: "",
            emailEdit: "",
            idSeleccionado: "",
            cuentaNumero: "",
            balance: "",
            capturarCliente: "",
        }
    },

    created() {
        axios.get('http://localhost:8080/api/clients')
            .then(datos => {
                this.clientes = datos.data
                this.clientesJson = datos.data
            })
    },

    methods: {
        //----------------------------------------------------------------------AGREGAR CLIENTE-----------------
        agregarCliente() {
            if (this.firstName != "" && this.lastName != "" && this.email != "" && this.email.includes("@" && ".com")) {
                axios.post('http://localhost:8080/rest/clients', {
                        firstName: this.firstName,
                        lastName: this.lastName,
                        email: this.email,
                    })
                    .then(function(response) {
                        console.log(response);
                    })
            }
        },
        //------------------------------------------------------------------FIN AGREGAR CLIENTE--------------------------------------
        //----------------------------------------------------------------------EDITAR CLIENTE------------------
        editarCliente(param) {
            this.idSeleccionado = param
            console.log(this.idSeleccionado)
            this.clientes.forEach(cliente => {
                if (cliente._links.self.href == param) {
                    this.firstNameEdit = cliente.firstName
                    this.lastNameEdit = cliente.lastName
                    this.emailEdit = cliente.email
                }
            })
        },

        editarClienteConfirmar() {
            if (this.firstNameEdit != "" && this.lastNameEdit != "" && this.emailEdit != "" && this.emailEdit.includes("@" && ".com")) {
                axios.patch(this.idSeleccionado, {
                        firstName: this.firstNameEdit,
                        lastName: this.lastNameEdit,
                        email: this.emailEdit,
                    })
                    .then(function(loadData) {
                        location.reload(loadData);
                    })
            }
        },
        //------------------------------------------------------------------FIN EDITAR CLIENTE--------------------------------------
        //----------------------------------------------------------------------ELIMINAR CLIENTE-------------------
        eliminarCliente(param) {
            this.idSeleccionado = ""
            this.idSeleccionado = param
            console.log(this.idSeleccionado)
        },

        confirmarEliminarCliente() {
            axios.delete(this.idSeleccionado)
                .then(function(loadData) {
                    location.reload(loadData);
                })
        },
        //------------------------------------------------------------------FIN ELIMINAR CLIENTE--------------------------------------
        //----------------------------------------------------------------------AGREGAR CUENTAS--------------------------------------- NO FUNCIONA (POR AHORA....)
        agregarCuenta(param) {
            this.capturarCliente = param
            console.log(this.capturarCliente)

        },

        confirmarAgregarCuenta() {
            console.log(this.capturarCliente)
            axios.post('http://localhost:8080/rest/accounts', {
                    number: this.cuentaNumero,
                    creationDate: Date.now(),
                    balance: this.balance,
                    client: this.capturarCliente
                })
                .then(function(loadData) {
                    location.reload(loadData);
                })

        },
        //------------------------------------------------------------------FIN AGREGAR CUENTAS--------------------------------------------
    },

    computed: {

    },
}).mount('#app')



//Las cuentas de melba se agregan a: http://localhost:8080/rest/clients/1/accounts