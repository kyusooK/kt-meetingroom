<template>
    <div>
        <v-list two-line v-if="list.length > 0">
            <v-list-item-group 
                    v-model="selected" 
                    color="primary"
                    @change="select"
            >
                <v-list-item v-for="(item, idx) in list" :key="idx">
                    <template v-slot:default="{ active }">
                        <v-list-item-avatar color="primary-darker-1">
                        </v-list-item-avatar>
                        
                        <v-list-item-content>
                            <v-list-item-title>
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                ReservationId :  {{item.reservationId }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                StartDate :  {{item.startDate }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                EndDate :  {{item.endDate }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                MeetingName :  {{item.meetingName }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Location :  {{item.location }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                ReservationStatus :  {{item.reservationStatus }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                FacilityRequestId :  {{item.facilityRequestId }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                RoomName :  {{item.roomName }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                UserId :  {{item.userId }}
                            </v-list-item-subtitle>
                        </v-list-item-content>

                        <v-list-item-action>
                            <v-checkbox :input-value="active" color="primary-darker-1"></v-checkbox>
                        </v-list-item-action>
                    </template>
                </v-list-item>
            </v-list-item-group>
        </v-list>
    </div>
</template>


<script>
    const axios = require('axios').default;

    export default {
        name: 'ReservationmanagementReservationPicker',
        props: {
            value: [String, Object, Array, Number, Boolean],
        },
        data: () => ({
            list: [],
            selected: null,
        }),
        async created() {
            var me = this;
            var temp = await axios.get(axios.fixUrl('/reservations'))
            if(temp.data) {
                me.list = temp.data._embedded.reservations;
            }

            if(me.value && typeof me.value == "object" && Object.values(me.value)[0]) {
                var id = Object.values(me.value)[0];
                var tmpValue = await axios.get(axios.fixUrl('/reservations/' + id))
                if(tmpValue.data) {
                    var val = tmpValue.data
                    me.list.forEach(function(item, idx) {
                        if(item.name == val.name) {
                            me.selected = idx
                        }
                    })
                }
            }
        },
        methods: {
            select(val) {
                var obj = {}
                if(val != undefined) {
                    var arr = this.list[val]._links.self.href.split('/');
                    obj['reservationId'] = arr[4]; 
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    this.$emit('selected', obj);
                }
            },
        },
    };
</script>

