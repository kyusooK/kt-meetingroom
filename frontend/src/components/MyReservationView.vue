<template>

    <v-data-table
        :headers="headers"
        :items="myReservation"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MyReservationView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "roomName", value: "roomName" },
                { text: "location", value: "location" },
                { text: "reservationStatus", value: "reservationStatus" },
                { text: "startDate", value: "startDate" },
                { text: "endDate", value: "endDate" },
            ],
            myReservation : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/myReservations'))

            temp.data._embedded.myReservations.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.myReservation = temp.data._embedded.myReservations;
        },
        methods: {
        }
    }
</script>

