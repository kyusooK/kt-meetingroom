<template>

    <v-card outlined>
        <v-card-title>
            MyReservation # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="RoomName" v-model="item.roomName" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Location" v-model="item.location" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="ReservationStatus" v-model="item.reservationStatus" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="StartDate" v-model="item.startDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="EndDate" v-model="item.endDate" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MyReservationViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/myReservations/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

