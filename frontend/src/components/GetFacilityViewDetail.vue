<template>

    <v-card outlined>
        <v-card-title>
            GetFacility # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="FacilityRequestId" v-model="item.facilityRequestId" :editMode="editMode" @change="change" />
            </div>
            <ResourceType offline label="ResourceType" v-model="item.resourceType" :editMode="false" :key="false" @change="change" />
            <div>
                <Number label="Quantity" v-model="item.quantity" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'GetFacilityViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/getFacilities/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

