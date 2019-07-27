var stompClient = null;

var headers = {
    login: 'guest',
    passcode: 'guest'
};

function wsConnect(url) {
    var ws = new SockJS(url);
    stompClient = Stomp.over(ws);

    //var ws = new WebSocket(url);
    //stompClient = Stomp.over(ws);

    // SockJS does not support heart-beat: disable heart-beats
    stompClient.heartbeat.outgoing = 0;
    stompClient.heartbeat.incoming = 0;

    stompClient.connect(headers, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/queue/Hello3', function (sms) {
            var obj = JSON.parse(sms.body)
            var count = obj.totalCount;

            console.log("count: " + count);
        });

    });
}

$(function(){
    var url = "http://host:15674/stomp";
    wsConnect(url);
});