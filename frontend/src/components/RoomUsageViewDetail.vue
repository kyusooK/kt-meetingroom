<template>

    <v-card outlined>
        <v-card-title>
            RoomUsage # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="ReservedCount" v-model="item.reservedCount" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="RoomName" v-model="item.roomName" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'RoomUsageViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/roomUsages/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

