<template>
    <div style="margin: 0 -15px 0 -15px;">
        <v-card-title>
            {{label}}
        </v-card-title>
        <v-card-text v-if="value">
            <div v-if="editMode">
                <v-text-field label="PaymentType" v-model="value.paymentType"/>
            </div>
            <div v-else>
                PaymentType :  {{value.paymentType }}
            </div>
            <div v-if="editMode">
                <v-text-field type="number" label="Amount" v-model="value.amount"/>
            </div>
            <div v-else>
                Amount :  {{value.amount }}
            </div>
        </v-card-text>
    </div>
</template>

<script>
    export default {
        name:"Payment",
        props: {
            editMode: Boolean,
            value : Object,
            label : String,
        },
        created(){
            if(!this.value) {
                this.value = {
                    'paymentType': '',
                    'amount': 0,
                };
            }
        },
        watch: {
            value(newVal) {
                this.$emit('input', newVal);
            },
        },
    }
</script>

<style scoped>
</style>