<template>
    <div style="margin: 0 -15px 0 -15px;">
        <v-card-title>
            ReservationStatus
        </v-card-title>
        <v-card-text>
            <div v-if="editMode">
                <v-select
                        v-model="newValue"
                        :label="'name'"
                        :items="reservationStatusList"
                ></v-select>
            </div>
            <div v-else>
                ReservationStatus : {{ value }}
            </div>
        </v-card-text>
    </div>
</template>

<script>
    export default {
        name: 'ReservationStatus',
        components:{},
        props: {
            value: [Object, String, Number, Boolean, Array],
            editMode: Boolean,
            isNew: Boolean,
            offline: Boolean,
            inList: Boolean,
            label: String,
        },
        data: () => ({
            reservationStatusList : [ "AVAILABLED",  "RESERVED",  "CANCELED", ],
            newValue: '',
        }),
        async created() {
            if(!this.value) {
                this.newValue = this.reservationStatusList[0];
            } else {
                this.newValue = this.value;
            }
        },
        watch: {
            value(val) {
                this.$emit('input', val);
            },
            newValue(val) {
                this.$emit('input', val);
            },
        },
        methods: {
        }
    }
</script>

