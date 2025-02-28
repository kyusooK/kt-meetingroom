<template>
    <v-app id="inspire">
        <div>
            <v-app-bar color="primary" app clipped-left flat>
                <v-toolbar-title>
                    <span class="second-word font uppercase"
                        style="font-weight:700;"
                    >
                        <v-app-bar-nav-icon
                            @click="openSideBar()"
                            style="z-index:1;
                            height:56px;
                            width:30px;
                            margin-right:10px;
                            font-weight:300;
                            font-size:55px;"
                        >
                            <div style="line-height:100%;">≡</div>
                        </v-app-bar-nav-icon>
                    </span>
                </v-toolbar-title>
                <span v-if="urlPath!=null" 
                    class="mdi mdi-home" 
                    key="" 
                    to="/" 
                    @click="goHome()"
                    style="margin-left:10px; font-size:20px; cursor:pointer;"
		        ></span> 
                <v-spacer></v-spacer>

            </v-app-bar>

            <v-navigation-drawer app clipped flat v-model="sideBar">
                <v-list>
                    <v-list-item
                        class="px-2"
                        key="reservations"
                        to="/reservationmanagements/reservations"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        예약
                    </v-list-item>


                    <v-list-item
                        class="px-2"
                        key="myReservations"
                        to="/reservationmanagements/myReservations"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        예약 내역 조회
                    </v-list-item>
                    <v-list-item
                        class="px-2"
                        key="notifications"
                        to="/calendarintegrations/notifications"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        알림
                    </v-list-item>


                    <v-list-item
                        class="px-2"
                        key="users"
                        to="/accesscontrols/users"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        사용자
                    </v-list-item>


                    <v-list-item
                        class="px-2"
                        key="facilityRequests"
                        to="/resourcemanagements/facilityRequests"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        설비 요청
                    </v-list-item>


                    <v-list-item
                        class="px-2"
                        key="reservationStatistics"
                        to="/statistics/reservationStatistics"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        예약분석
                    </v-list-item>

                    <v-list-item
                        class="px-2"
                        key="facilityStatistics"
                        to="/statistics/facilityStatistics"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        시설분석
                    </v-list-item>


                    <v-list-item
                        class="px-2"
                        key="roomUsages"
                        to="/statistics/roomUsages"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        회의실 예약 및 이용률
                    </v-list-item>
                    <v-list-item
                        class="px-2"
                        key="facilityHistories"
                        to="/statistics/facilityHistories"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        설비 이력
                    </v-list-item>
                    <v-list-item
                        class="px-2"
                        key="meetingRooms"
                        to="/roommanagements/meetingRooms"
                        @click="changeUrl()"
                        color="primary"
                        style="font-weight:700;"
                    >
                        회의실
                    </v-list-item>


                </v-list>
            </v-navigation-drawer>
        </div>

        <v-main>
            <v-container style="padding:0px;" v-if="urlPath" fluid>
                <router-view></router-view>
            </v-container>
            <v-container style="padding:0px;" v-else fluid>
                <div style="width:100%; margin:0px 0px 20px 0px; position: relative;">
                    <v-img style="width:100%; height:300px;"
                        src="/image/cabinet.png"
                    ></v-img>
                    <div class="App-main-text-overlap"></div>
                    <div class="App-sub-text-overlap"></div>
                </div>
                <v-row class="pa-0 ma-0">
                    <v-col cols="12" lg="3" md="4" sm="6" class="pa-0 pa-0" v-for="(aggregate, index) in aggregate" :key="index">
                        <div class="pa-4">
                            <v-card
                                :key="aggregate.key"
                                :to="aggregate.route"
                                @click="changeUrl()"
                                class="mx-auto main-card pa-4"
                                style="text-align: center; border-radius: 10px;"
                                outlined
                            >
                                <div class="d-flex justify-center" style="width:120px; height:120px; border-radius: 10px; margin: 0 auto; background-color:white;">
                                    <v-img style="width:100%; height:100%; object-fit:contain; border-radius: 10px;" :src="aggregate.ImageUrl"></v-img>
                                </div>
                                <div style="text-align: center;">
                                    <h2 class="main-card-title">{{ aggregate.title }}</h2>
                                </div>
                            </v-card>
                        </div>
                    </v-col>
                </v-row>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>

export default {
    name: "App",
    data: () => ({
        useComponent: "",
        drawer: true,
        components: [],
        sideBar: true,
        urlPath: null,
        flipped: [],
        ImageUrl: '',
        aggregate: [
            { 
                title: '예약', 
                description: 'Reservation을 관리하는 화면입니다.', 
                key: 'reservations', 
                route: '/reservationmanagements/reservations',
                ImageUrl: '/image/reservation.png',
            },
            { 
                title: '알림', 
                description: 'Notification을 관리하는 화면입니다.', 
                key: 'notifications', 
                route: '/calendarintegrations/notifications',
                ImageUrl: '/image/bell.png',
            },
            { 
                title: '사용자', 
                description: 'User을 관리하는 화면입니다.', 
                key: 'users', 
                route: '/accesscontrols/users',
                ImageUrl: '/image/person.png',
            },
            { 
                title: '설비 요청', 
                description: 'FacilityRequest을 관리하는 화면입니다.', 
                key: 'facilityRequests', 
                route: '/resourcemanagements/facilityRequests',
                ImageUrl: '/image/requirement.png',
            },
            { 
                title: '예약분석', 
                description: 'ReservationStatistics을 관리하는 화면입니다.', 
                key: 'reservationStatistics', 
                route: '/statistics/reservationStatistics',
                ImageUrl: '/image/analysis.png',
            },
            { 
                title: '시설분석', 
                description: 'FacilityStatistics을 관리하는 화면입니다.', 
                key: 'facilityStatistics', 
                route: '/statistics/facilityStatistics',
                ImageUrl: '/image/facilities-inspection.png',
            },
            { 
                title: '회의실', 
                description: 'MeetingRoom을 관리하는 화면입니다.', 
                key: 'meetingRooms', 
                route: '/roommanagements/meetingRooms',
                ImageUrl: '/image/meeting.png',
            },
            { 
                title: '예약 내역 조회', 
                description: 'MyReservation을 관리하는 화면입니다.', 
                key: 'myReservations', 
                route: '/reservationmanagements/myReservations',
                ImageUrl: '/image/document.png',
            },
            { 
                title: '회의실 예약 및 이용률', 
                description: 'RoomUsage을 관리하는 화면입니다.', 
                key: 'roomUsages', 
                route: '/statistics/roomUsages',
                ImageUrl: '/image/interpretation.png',
            },
            { 
                title: '설비 이력', 
                description: 'FacilityHistory을 관리하는 화면입니다.', 
                key: 'facilityHistories', 
                route: '/statistics/facilityHistories',
                ImageUrl: '/image/folder.png',
            },
        ],
    }),
    
    async created() {
      var path = document.location.href.split("#/")
      this.urlPath = path[1];

    },
    watch: {
        cards(newCards) {
            this.flipped = new Array(newCards.length).fill(false);
        },
    },

    mounted() {
        var me = this;
        me.components = this.$ManagerLists;
    },

    methods: {
        openSideBar(){
            this.sideBar = !this.sideBar
        },
        changeUrl() {
            var path = document.location.href.split("#/")
            this.urlPath = path[1];
            this.flipped.fill(false);
        },
        goHome() {
            this.urlPath = null;
        },
        flipCard(index) {
            this.$set(this.flipped, index, true);
        },
        unflipCard(index) {
            this.$set(this.flipped, index, false);
        },
    }
};
</script>
<style>
</style>
