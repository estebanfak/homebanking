const APP = Vue.createApp({
    data() {
        return {
            message: "hello vue!",
            newLoanName: "",
            maxAmount: "",
            payments: "",
            paymentsArray: []
        }
    },

    created() {



    },

    methods: {
        addNewLoan() {
            this.paymentsArray = this.payments.split(",")
            this.paymentsArray = Array.from(this.paymentsArray)
            let aux = this.paymentsArray
            if (this.newLoanName != "" && this.maxAmount != "" && this.payments != "") {
                axios.post('/api/loan', {
                        "name": this.newLoanName,
                        "maxAmount": this.maxAmount,
                        "payments": aux
                    })
                    .then(response => {
                        console.log("Creado", response.status);
                        swal("Success", "Loan succesfully created!", "success");
                    })
                    .catch(function(error) {
                        console.log('Error', error.message);
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