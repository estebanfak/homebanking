const APP = Vue.createApp({
    data() {
        return {
            message: "hello vue!",
            firstNameAdd: "",
            lastNameAdd: "",
            emailAdd: "",
            passwordAdd: "",
            email: "",
            password: "",
        }
    },

    created() {



    },

    methods: {

        login(email, password) {
            axios.post('/api/login', `email=${email}&password=${password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    if (response.status == 200) {
                        console.log(response);
                        location = '/web/accounts.html';
                    }
                })
                .catch(function(error) {
                    if (error.response.status == 401) {
                        swal("Error", "Wrong username or password", "error");
                    } else {
                        console.log('Error', error.message);
                    }
                });
        },

        addNewClient(nombre, apellido, email, password) {
            if (nombre != "" && apellido != "" && email != "" && email.includes("@" && ".com") && password != "") {
                axios.post('/api/clients', `firstName=${nombre}&lastName=${apellido}&email=${email}&password=${password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => {
                        console.log(response.status);
                        if (response.status == 201) {
                            this.login(email, password)
                        }
                    })
                    .catch(function(error) {
                        if (error.response.status == 403) {
                            swal("Error", "Your E-mail is already registered", "error");
                        } else {
                            console.log('Error', error.message);
                        }
                    });

            } else {
                swal("Error", "You must complete all fields", "error");
            }
        },
    },


    computed: {},

    mounted() {},
    updated() {},
    destroyed() {},

}).mount('#app')