<template>
    <v-card outlined>
        <v-card-title>
            CheckFacility
        </v-card-title>

        <v-card-text>
            <Number label="FacilityRequestId" v-model="value.facilityRequestId" :editMode="editMode"/>
            <Boolean label="IsUsable" v-model="value.isUsable" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="checkFacility"
            >
                CheckFacility
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
        name: 'CheckFacilityCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.facilityRequestId = 0;
            this.value.isUsable = false;
        },
        watch: {
        },
        methods: {
            checkFacility() {
                this.$emit('checkFacility', this.value);
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

