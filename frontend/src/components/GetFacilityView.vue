<template>

    <v-data-table
        :headers="headers"
        :items="getFacility"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'GetFacilityView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "facilityRequestId", value: "facilityRequestId" },
                { text: "resourceType", value: "resourceType" },
                { text: "quantity", value: "quantity" },
            ],
            getFacility : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/getFacilities'))

            temp.data._embedded.getFacilities.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.getFacility = temp.data._embedded.getFacilities;
        },
        methods: {
        }
    }
</script>

