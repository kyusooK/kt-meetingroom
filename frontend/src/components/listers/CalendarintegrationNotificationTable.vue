<template>
    <div>
        <v-data-table
                :headers="headers"
                :items="values"
                :items-per-page="5"
                class="elevation-1"
        ></v-data-table>

        <v-col style="margin-bottom:40px;">
            <div class="text-center">
                <v-dialog v-model="openDialog" width="500">
                    <template v-slot:activator="{ on, attrs }">
                        <v-fab-transition>
                            <v-btn
                                    color="primary"
                                    fab
                                    dark
                                    large
                                    style="position:absolute; bottom: 5%; right: 2%; z-index:99"
                                    @click="openDialog=true;"
                            >
                                <v-icon v-bind="attrs" v-on="on">mdi-plus</v-icon>
                            </v-btn>
                        </v-fab-transition>
                    </template>

                    <CalendarintegrationNotification :offline="offline"  :isNew="true" :editMode="true" v-model="newValue" @add="append" v-if="tick"/>
                
                    <v-btn
                        class="gs-close-btn"
                        @click="closeDialog()"
                        depressed 
                        icon 
                        absolute
                    >
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-dialog>
            </div>
        </v-col>
    </div>
</template>

<script>
    const axios = require('axios').default;
    import CalendarintegrationNotification from './../CalendarintegrationNotification.vue';

    export default {
        name: 'CalendarintegrationNotificationManager',
        components: {
            CalendarintegrationNotification,
        },
        props: {
            offline: Boolean,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            values: [],
            headers: 
                [
                    { text: "id", value: "id" },
                    { text: "userId", value: "userId" },
                    { text: "startDate", value: "startDate" },
                    { text: "endDate", value: "endDate" },
                    { text: "roomName", value: "roomName" },
                    { text: "location", value: "location" },
                    { text: "message", value: "message" },
                    { text: "meetingName", value: "meetingName" },
                ],
            notification : [],
            newValue: {},
            tick : true,
            openDialog : false,
        }),
        async created() {
            if(this.offline){
                if(!this.values) this.values = [];
                return;
            }

            var temp = await axios.get(axios.fixUrl('/notifications'))
            temp.data._embedded.notifications.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])
            this.values = temp.data._embedded.notifications;

            this.newValue = {
                'userId': '',
                'startDate': '2025-02-25',
                'endDate': '2025-02-25',
                'roomName': '',
                'location': '',
                'message': '',
                'meetingName': '',
            }
        },
        methods: {
            closeDialog(){
                this.openDialog = false
            },
            append(value){
                this.tick = false
                this.newValue = {}
                this.values.push(value)
                
                this.$emit('input', this.values);

                this.$nextTick(function(){
                    this.tick=true
                })
            },
        }
    }
</script>

