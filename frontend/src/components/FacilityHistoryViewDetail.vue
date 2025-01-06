<template>

    <v-card outlined>
        <v-card-title>
            FacilityHistory # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="FacilityName" v-model="item.facilityName" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="FacilityCount" v-model="item.facilityCount" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'FacilityHistoryViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/facilityHistories/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

