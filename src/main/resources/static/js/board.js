$(document).ready(function () {
    document.getElementById('update-button').style.display = 'block';
    document.getElementById('register-button').style.display = 'none';
    document.getElementById('close-button').style.display = 'none';
    $("#title").prop("disabled", true);
    $("#author").prop("disabled", true);
    $("#content").prop("disabled", true);
});

function disabled_on() {
    document.getElementById('update-button').style.display = 'block';
    document.getElementById('register-button').style.display = 'none';
    document.getElementById('close-button').style.display = 'none';
    $("#title").prop("disabled", true);
    $("#author").prop("disabled", true);
    $("#content").prop("disabled", true);
}

function disabled_off() {
    document.getElementById('update-button').style.display = 'none';
    document.getElementById('register-button').style.display = 'block';
    document.getElementById('close-button').style.display = 'block';
    $("#title").prop("disabled", false);
    $("#author").prop("disabled", false);
    $("#content").prop("disabled", false);
}

function post_board() {
    let title = $('#title').val();
    let author = $('#author').val();
    let password = $('#password').val();
    let content = $('#content').val();

    let data = {'title': title, 'author': author, 'password': password, 'content': content};

    $.ajax({
        type: "POST",
        url: "/boards",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (response) {
            alert('게시글이 성공적으로 작성되었습니다.');
            window.location.href = `/boards/${response.id}`;
        }
    });
}

function update_board(id) {
    let title = $('#title').val();
    let author = $('#author').val();
    let password = $('#password').val();
    let content = $('#content').val();

    let data = {'title': title, 'author': author, 'password': password, 'content': content};

    $.ajax({
        type: "PATCH",
        url: `/boards/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (response) {
            alert('게시글이 수정되었습니다.');
            window.location.href = `/boards/${response.id}`;
        }
    });
}

function delete_board(id, password) {
    $.ajax({
        type: "DELETE",
        url: `/boards/${id}`,
        data: {id: id, password: password},
        success: function (response) {
            alert('게시글이 삭제되었습니다.');
            window.location.href = "/";
        }
    });
}