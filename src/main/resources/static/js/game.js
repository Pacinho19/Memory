var stompClient = null;
var privateStompClient = null;

socket = new SockJS('/ws');
privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
    var gameId = document.getElementById('gameId').value;
    privateStompClient.subscribe('/reload-board/' + gameId, function (result) {
        updateBoard();
    });
});

stompClient = Stomp.over(socket);

function updateBoard() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            $("#board").replaceWith(xhr.responseText);
        }
    }
    xhr.open('GET', "/memory/game/" + document.getElementById("gameId").value + "/board/reload", true);
    xhr.send(null);
}

function sendAnswer(cellIdx) {

     var xhr = new XMLHttpRequest();
     var url = '/memory/game/' + document.getElementById("gameId").value + '/answer';
     xhr.open("POST", url, true);
     xhr.setRequestHeader("Content-Type", "application/json");
     xhr.onreadystatechange = function () { };

     const answerRequest = new Object();
     answerRequest.cellIdx = cellIdx;
     var data = JSON.stringify(answerRequest);
     xhr.send(data);
}