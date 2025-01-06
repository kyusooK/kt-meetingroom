<template>

    <v-card outlined>
        <v-card-title>
            Message
        </v-card-title>

        <v-card-text>
            <String label="Location" v-model="value.location" :editMode="editMode"/>
            <Date label="UsingStartDate" v-model="value.usingStartDate" :editMode="editMode"/>
            <Date label="UsingEndDate" v-model="value.usingEndDate" :editMode="editMode"/>
            <String label="MeetingName" v-model="value.meetingName" :editMode="editMode"/>
            <ReservationStatus offline label="ReservationStatus" v-model="value.reservationStatus" :editMode="editMode" @change="change"/>
        </v-card-text>

        <v-card-actions v-if="inList">
            <slot name="actions"></slot>
        </v-card-actions>
    </v-card>
</template>

<script>

    export default {
        name: 'Message',
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
        }),
        async created() {
            if(!Object.values(this.value)[0]) {
                this.$emit('input', {});
                this.newValue = {
                    'location': '',
                    'usingStartDate': '',
                    'usingEndDate': '',
                    'meetingName': '',
                    'reservationStatus': '',
                }
            }
            if(typeof this.value === 'object') {
                if(!('location' in this.value)) {
                    this.value.location = '';
                }
            }
            if(typeof this.value === 'object') {
                if(!('usingStartDate' in this.value)) {
                    this.value.usingStartDate = '2025-01-06';
                }
            }
            if(typeof this.value === 'object') {
                if(!('usingEndDate' in this.value)) {
                    this.value.usingEndDate = '2025-01-06';
                }
            }
            if(typeof this.value === 'object') {
                if(!('meetingName' in this.value)) {
                    this.value.meetingName = '';
                }
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
            edit() {
                this.editMode = true;
            },
            async add() {
                this.editMode = false;
                this.$emit('input', this.value);

                if(this.isNew){
                    this.$emit('add', this.value);
                } else {
                    this.$emit('edit', this.value);
                }
            },
            async remove(){
                this.editMode = false;
                this.isDeleted = true;

                this.$emit('input', this.value);
                this.$emit('delete', this.value);
            },
            change(){
                this.$emit('change', this.value);
            },
        }
    }
</script>

