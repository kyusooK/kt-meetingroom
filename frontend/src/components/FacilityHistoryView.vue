<template>

    <v-data-table
        :headers="headers"
        :items="facilityHistory"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'FacilityHistoryView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "facilityName", value: "facilityName" },
                { text: "facilityCount", value: "facilityCount" },
            ],
            facilityHistory : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/facilityHistories'))

            temp.data._embedded.facilityHistories.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.facilityHistory = temp.data._embedded.facilityHistories;
        },
        methods: {
        }
    }
</script>

