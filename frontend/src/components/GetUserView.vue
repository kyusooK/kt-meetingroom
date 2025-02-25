<template>

    <v-data-table
        :headers="headers"
        :items="getUser"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'GetUserView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            getUser : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/getUsers'))

            temp.data._embedded.getUsers.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.getUser = temp.data._embedded.getUsers;
        },
        methods: {
        }
    }
</script>

