# 

## Model
www.msaez.io/#/123912988/storming/meetingroom

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- reservationmanagement
- calendarintegration
- accesscontrol
- resourcemanagement
- statistics
- roommanagement


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- reservationmanagement
```
 http :8088/reservations reservationId="reservationId"startDate="startDate"endDate="endDate"meetingName="meetingName"location="location"ReservationStatus = "RESERVED"FacilityRequestId := '{"id": 0}'roomName="roomName"UserId := '{"id": 0}'
```
- calendarintegration
```
 http :8088/notifications notificationId="notificationId"userId="userId"startDate="startDate"endDate="endDate"roomName="roomName"location="location"message="message"meetingName="meetingName"
```
- accesscontrol
```
 http :8088/users userId="userId"name="name"rank="rank"department="department"
```
- resourcemanagement
```
 http :8088/facilityRequests facilityRequestId="facilityRequestId"ResourceType = "CAM"isUsable="isUsable"
```
- statistics
```
 http :8088/reservationStatistics statisticsId="statisticsId"reservedCount="reservedCount"ReservationStatus = "RESERVED"roomName="roomName"
 http :8088/facilityStatistics facilityId="facilityId"facilityName="facilityName"facilityCount="facilityCount"
```
- roommanagement
```
 http :8088/meetingRooms id="id"roomName="roomName"location="location"ReservationStatus = "AVAILABLED"rank="rank"department="department"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
