<template>

    <v-data-table
        :headers="headers"
        :items="roomUsage"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'RoomUsageView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "reservedCount", value: "reservedCount" },
                { text: "roomName", value: "roomName" },
            ],
            roomUsage : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/roomUsages'))

            temp.data._embedded.roomUsages.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.roomUsage = temp.data._embedded.roomUsages;
        },
        methods: {
        }
    }
</script>

