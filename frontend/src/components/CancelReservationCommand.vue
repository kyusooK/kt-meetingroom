<template>
    <v-card outlined>
        <v-card-title>
            CancelReservation
        </v-card-title>

        <v-card-text>
            <Number label="ReservationId" v-model="value.reservationId" :editMode="editMode"/>
            <ReservationStatus offline label="ReservationStatus" v-model="value.reservationStatus" :editMode="editMode" @change="change"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="cancelReservation"
            >
                CancelReservation
            </v-btn>
            
            <v-btn
                    color="primary"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'CancelReservationCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.reservationId = 0;
            this.value.reservationStatus = {};
        },
        watch: {
        },
        methods: {
            cancelReservation() {
                this.$emit('cancelReservation', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

