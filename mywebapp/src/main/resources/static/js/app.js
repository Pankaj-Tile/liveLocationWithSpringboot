// document.addEventListener('DOMContentLoaded', (event) => {
//     const map = L.map('map').setView([51.505, -0.09], 13);

//     L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
//         attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
//     }).addTo(map);

//     const socket = new SockJS('/ws/location');
//     const stompClient = Stomp.over(socket);

//     let sessionId = null; // This should be unique for each user/session

//     stompClient.connect({}, (frame) => {
//         console.log('Connected: ' + frame);
//         sessionId = frame.headers['user-name']; // Assign a unique session ID

//         stompClient.subscribe('/topic/locations', (message) => {
//             const locations = JSON.parse(message.body);
//             locations.forEach(location => {
//                 let color = 'green';
//                 if (location.sessionId !== sessionId) {
//                     if (location.speed > 100) {
//                         color = 'red';
//                     }
//                     L.circleMarker([location.latitude, location.longitude], {
//                         color: color
//                     }).addTo(map);
//                 }
//             });
//         });
//     });

//     setInterval(() => {
//         if (navigator.geolocation) {
//             navigator.geolocation.getCurrentPosition((position) => {
//                 const userLocation = {
//                     sessionId: sessionId,
//                     latitude: position.coords.latitude,
//                     longitude: position.coords.longitude,
//                     speed: position.coords.speed || 0
//                 };
//                 stompClient.send('/app/locations', {}, JSON.stringify(userLocation));
//             });
//         }
//     }, 1000);
// });
