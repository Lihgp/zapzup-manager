'use strict';

document.querySelector('#welcomeForm').addEventListener('submit', connect, true)
document.querySelector('#dialogueForm').addEventListener('submit', sendMessage, true)

let stompClient = null;
let name = null;
let userId = null;

function connect(event) {
    name = document.querySelector('#name').value.trim();
    userId = document.querySelector('#userId').value.trim();

    if (name) {
        document.querySelector('#welcome-page').classList.add('hidden');
        document.querySelector('#dialogue-page').classList.remove('hidden');

        let socket = new SockJS('/zapzupapplication/messages');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, connectionSuccess);
    }
    event.preventDefault();
}

function connectionSuccess() {
    stompClient.subscribe('/topic/private/CHAT-1e703648-ae8c-4557-a6c7-b0c77045656a', onMessageReceived);

    stompClient.send("/app/chat.newUser", {}, JSON.stringify({
        sender: name,
        type: 'newUser'
    }))

}

function sendMessage(event) {
    let messageContent = document.querySelector('#chatMessage').value.trim();

    if (messageContent && stompClient) {
        let chatMessage = {
            userId: userId,
            chatId: 'CHAT-1e703648-ae8c-4557-a6c7-b0c77045656a',
            content: document.querySelector('#chatMessage').value
        };

        stompClient.send("/app/messages.send", {}, JSON
            .stringify(chatMessage));
        document.querySelector('#chatMessage').value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    moment.locale("pt-BR");
    let message = JSON.parse(payload.body);

    let messageElement = document.createElement('li');

    if (message.type === 'newUser') {
        messageElement.classList.add('event-data');
        message.content = message.sender + 'has joined the chat';
    } else if (message.type === 'Leave') {
        messageElement.classList.add('event-data');
        message.content = message.sender + 'has left the chat';
    } else {
        messageElement.classList.add('message-data');

        let element = document.createElement('i');
        let text = document.createTextNode(message.sender[0]);
        element.appendChild(text);

        messageElement.appendChild(element);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    let textElement = document.createElement('p');
    let spanElement = document.createElement('span');
    let messageText = document.createTextNode(`${message.content} - `);
    let dateText = document.createTextNode(moment(message.createdAt).format('L'));
    textElement.appendChild(messageText);
    spanElement.appendChild(dateText)
    spanElement.setAttribute('id', 'messageDate')
    textElement.appendChild(spanElement)

    messageElement.appendChild(textElement);

    document.querySelector('#messageList').appendChild(messageElement);
    document.querySelector('#messageList').scrollTop = document
        .querySelector('#messageList').scrollHeight;

}